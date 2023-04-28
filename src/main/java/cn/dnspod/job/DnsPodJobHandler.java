package cn.dnspod.job;



import cn.dnspod.pojo.Result;
import cn.dnspod.pojo.constant.DnsPodConstant;
import cn.dnspod.pojo.constant.SysConstant;
import cn.dnspod.pojo.po.ConfigPO;
import cn.dnspod.pojo.po.DnsPodLogPO;
import cn.dnspod.pojo.po.DomainPO;
import cn.dnspod.pojo.po.SubDomainPO;
import cn.dnspod.service.IConfigService;
import cn.dnspod.service.IDnsPodLogService;
import cn.dnspod.service.IDomainService;
import cn.dnspod.service.ISubDomainService;
import cn.dnspod.utils.HttpHelper;
import cn.dnspod.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @className: DnsPodJobHandler
 * @author: weixuan
 * @date: 2023/4/23 15:18
 **/
@Component
public class DnsPodJobHandler implements Job {

    private static final Logger logger = LoggerFactory.getLogger(DnsPodJobHandler.class);

    @Autowired
    private IDomainService domainService;

    @Autowired
    private ISubDomainService subDomainService;

    @Autowired
    private IDnsPodLogService dnsPodLogService;

    @Autowired
    private IConfigService configService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        // 获取所有的域名
        List<DomainPO> domains = domainService.findDomainByEnable();

        logger.error("共获取{}个域名");

        if (Utils.isEmpty(domains)) {
            return;
        }

        // 获取公网地址
        String publicNetworkAddress = HttpHelper.getPublicNetworkAddress();

        // 获取动态域名解析的token
        ConfigPO configByKey = configService.findConfigByKey(DnsPodConstant.DNS_POD_TOKEN);
        String dnsPodToken = configByKey.getConfigValue();

        // 成功修改的域名
        int isSuccess = 0;
        List<String> successDomain = new ArrayList<>();
        for (DomainPO domain : domains) {
            // 获取所有的子域名
            List<SubDomainPO> subDomains = subDomainService.findSubDomainByDomainId(domain.getId());
            logger.error("域名【{}】下共有{}个子域名", domain.getDomainValue(), subDomains.size());
            for (SubDomainPO subDomain : subDomains) {
                // 不可用状态下跳过
                if (SysConstant.STATE.ENABLE != subDomain.getState()) {
                    continue;
                }
                // 获取域名解析列表
                String resultStr = HttpHelper.httpPost(DnsPodConstant.URL.RECORD_LIST, recordListParam(dnsPodToken, domain.getDomainValue(), subDomain.getSubDomainValue()));

                Map resRecordList = JSONObject.parseObject(resultStr, Map.class);

                List recordList = (List) resRecordList.get("records");

                Map recordMap = (Map) recordList.get(0);

                // 获取 recordId 和 recordValue
                String recordId = (String) recordMap.get("id");
                String recordValue = (String) recordMap.get("value");

                // 判断记录值是否和公网一致，不一致则修改
                if (!recordValue.equals(publicNetworkAddress)) {

                    String resStr = HttpHelper.httpPost(DnsPodConstant.URL.RECORD_MODIFY,
                            recordModifyParam(dnsPodToken, domain.getDomainValue(), subDomain.getSubDomainValue(), recordId, publicNetworkAddress));

                    Map resRecordModify = JSONObject.parseObject(resStr, Map.class);

                    Map resStatus = (Map) resRecordModify.get("status");

                    Object code = resStatus.get("code");

                    String domainStr = subDomain.getSubDomainValue() + "." + domain.getDomainValue();

                    DnsPodLogPO dnsPodLogPO = new DnsPodLogPO();
                    dnsPodLogPO.setDomainId(domain.getId());
                    dnsPodLogPO.setSubDomainId(subDomain.getId());
                    dnsPodLogPO.setDomainValue(domainStr);
                    dnsPodLogPO.setOldIpAddress(recordValue);
                    dnsPodLogPO.setNewIpAddress(publicNetworkAddress);
                    dnsPodLogPO.setMessage(JSON.toJSONString(resRecordModify));
                    dnsPodLogPO.setCreateTime(new Date());

                    // 成功修改
                    if ("1".equals(code)) {

                        dnsPodLogPO.setResult(SysConstant.STATE.SUCCESS);

                        logger.error("域名【{}】原IP地址：{}，新IP地址：{}", domainStr, recordValue, publicNetworkAddress);

                        isSuccess++;

                        successDomain.add(domainStr);

                    } else {
                        dnsPodLogPO.setResult(SysConstant.STATE.FAIL);
                        logger.error("域名【{}】更新IP地址【{}】失败", domainStr, publicNetworkAddress);
                    }
                    dnsPodLogService.save(dnsPodLogPO);
                }
            }
        }
        if (isSuccess > 0) {
            jobExecutionContext.setResult(Result.success(successDomain.toString() + "域名修改成功为：【"+ publicNetworkAddress +"】"));
        }
    }

    /**
     * 获取域名解析记录列表请求参数
     * @param token
     * @param domain
     * @return
     */
    public JSONObject recordListParam(String token, String domain, String subDomain) {
        JSONObject param = new JSONObject();
        param.put("login_token", token);
        param.put("format", "json");
        param.put("domain", domain);
        param.put("sub_domain", subDomain);
        param.put("record_type", "A");
        return param;
    }

    /**
     * 修改域名记录值请求参数
     * @param token
     * @param domain
     * @param recordId
     * @param ip
     * @return
     */
    public JSONObject recordModifyParam(String token, String domain, String subDomain, String recordId, String ip) {
        JSONObject param = new JSONObject();
        param.put("login_token", token);
        param.put("format", "json");
        param.put("domain", domain);
        param.put("record_id", recordId);
        param.put("sub_domain", subDomain);
        param.put("record_type", "A");
        param.put("record_line", "默认");
        param.put("value", ip);
        return param;
    }
}
