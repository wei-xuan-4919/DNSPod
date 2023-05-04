package cn.dnspod.controller;

import cn.dnspod.pojo.Result;
import cn.dnspod.pojo.constant.DnsPodConstant;
import cn.dnspod.pojo.po.ConfigPO;
import cn.dnspod.service.IConfigService;
import cn.dnspod.service.IDnsPodJobService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weixuan
 * @date 2023/5/1 1:28
 * @Description:
 */
@Controller
@RequestMapping("/admin/config")
public class ConfigController {

    @Autowired
    private IConfigService configService;

   @Autowired
    private IDnsPodJobService dnsPodJobService;

   @GetMapping("/")
   public String index() {
       return "index";
   }

    @GetMapping("/getConfig")
    @ResponseBody
    public Result getConfig() {
        Map<String, String> result = new HashMap<>();
        // 获取token
        ConfigPO token = configService.findConfigByKey(DnsPodConstant.DNS_POD_TOKEN);
        if (token != null) {
            result.put(DnsPodConstant.DNS_POD_TOKEN, token.getConfigValue());
        }
        // 获取cron
        ConfigPO cron = configService.findConfigByKey(DnsPodConstant.DNS_POD_CRON);
        if (cron != null) {
            result.put(DnsPodConstant.DNS_POD_CRON, cron.getConfigValue());
        }
        return Result.success(result);
    }

    @PostMapping("/saveConfig")
    @ResponseBody
    public Result saveConfig(@RequestParam("token") String token, @RequestParam("cron") String cron) {
        // 获取token
        ConfigPO tokenConfig = configService.findConfigByKey(DnsPodConstant.DNS_POD_TOKEN);
        if (!token.equals(tokenConfig.getConfigValue())) {
            String[] split = token.split(",");
            if (split.length != 2) {
                return Result.fail("Token格式不正确");
            }
            ConfigPO configPO = new ConfigPO();
            configPO.setId(tokenConfig.getId());
            configPO.setConfigValue(token);
            configService.updateById(configPO);
        }
        // 获取cron
        ConfigPO cronConfig = configService.findConfigByKey(DnsPodConstant.DNS_POD_CRON);
        if (!cron.equals(cronConfig.getConfigValue())) {
            // 判断cron表达式是否正确
            if (!CronExpression.isValidExpression(cron)) {
                return Result.fail("Cron表达式格式不正确");
            }
            ConfigPO configPO = new ConfigPO();
            configPO.setId(cronConfig.getId());
            configPO.setConfigValue(cron);
            configService.updateById(configPO);
            // 重载任务
            dnsPodJobService.updateDnsPodJob();
        }
        return Result.success("保存成功");
    }

}
