package cn.dnspod.service.impl;


import cn.dnspod.mapper.ISubDomainMapper;
import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.po.SubDomainPO;
import cn.dnspod.service.ISubDomainService;
import cn.dnspod.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 动态域名解析子域名表 服务实现类
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Service
public class SubDomainServiceImpl extends ServiceImpl<ISubDomainMapper, SubDomainPO> implements ISubDomainService {

    @Override
    public List<SubDomainPO> findSubDomainByDomainId(Long domainId) {
        QueryWrapper<SubDomainPO> queryWrapper = new QueryWrapper();
        queryWrapper.eq(SubDomainPO.DOMAIN_ID, domainId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<SubDomainPO> pageSubDomain(String domainValue, Integer state, Long domainId, int curPage, int pageSize) {
        Page<SubDomainPO> page = new Page<>(curPage, pageSize);
        QueryWrapper<SubDomainPO> wrapper = new QueryWrapper();
        if (!Utils.isEmpty(domainValue)){
            wrapper.like(SubDomainPO.SUB_DOMAIN_VALUE, domainValue);
        }
        if (!Utils.isEmpty(state)) {
            wrapper.eq(SubDomainPO.STATE, state);
        }
        wrapper.eq(SubDomainPO.DOMAIN_ID, domainId);
        Page<SubDomainPO> subDomainPage = this.baseMapper.selectPage(page, wrapper);
        return new PageInfo<>(subDomainPage);
    }

    @Override
    public SubDomainPO findSubDomainByValue(Long domainId, String subDomainValue) {
        QueryWrapper<SubDomainPO> wrapper = new QueryWrapper();
        wrapper.eq(SubDomainPO.DOMAIN_ID, domainId);
        wrapper.eq(SubDomainPO.SUB_DOMAIN_VALUE, subDomainValue);
        return this.baseMapper.selectOne(wrapper);
    }

}
