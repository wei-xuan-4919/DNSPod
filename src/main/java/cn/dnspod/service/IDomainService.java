package cn.dnspod.service;


import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.po.DomainPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 动态域名解析域名表 服务类
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
public interface IDomainService extends IService<DomainPO> {

    /**
     * 获取所有可用的域名
     * @return
     */
    List<DomainPO> findDomainByEnable();

    /**
     * 分页查询域名
     * @param domainValue
     * @param state
     * @param curPage
     * @param pageSize
     * @return
     */
    PageInfo<DomainPO> pageDomain(String domainValue, Integer state, int curPage, int pageSize);


    /**
     * 删除域名
     */
    boolean deleteDomain(Long domainId);


    /**
     * 通过域名查询数据
     */
    DomainPO findDomainByDomainValue(String domainValue);

}
