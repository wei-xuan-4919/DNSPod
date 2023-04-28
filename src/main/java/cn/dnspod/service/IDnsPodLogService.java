package cn.dnspod.service;


import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.po.DnsPodLogPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 动态域名解析日志 服务类
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
public interface IDnsPodLogService extends IService<DnsPodLogPO> {

    /**
     * 分页查询日志
     */
    PageInfo<DnsPodLogPO> pageDnsPodLog(String domainValue, Integer state, int curPage, int pageSize);

}
