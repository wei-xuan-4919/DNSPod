package cn.dnspod.service;


import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.po.SubDomainPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 动态域名解析子域名表 服务类
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
public interface ISubDomainService extends IService<SubDomainPO> {

    /**
     * 通过域名获取子域名列表
     * @param domainId  域名 id
     * @return
     */
    List<SubDomainPO> findSubDomainByDomainId(Long domainId);

    /**
     * 分页查询子域名
     * @param domainValue
     * @param state
     * @param domainId
     * @param curPage
     * @param pageSize
     * @return
     */
    PageInfo<SubDomainPO> pageSubDomain(String domainValue, Integer state, Long domainId, int curPage, int pageSize);

    /**
     * 通过子域名查询信息
     */
    SubDomainPO findSubDomainByValue(Long domainId, String subDomainValue);
}
