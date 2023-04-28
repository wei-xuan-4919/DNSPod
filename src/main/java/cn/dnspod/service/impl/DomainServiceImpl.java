package cn.dnspod.service.impl;

import cn.dnspod.mapper.IDomainMapper;
import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.constant.SysConstant;
import cn.dnspod.pojo.po.DomainPO;
import cn.dnspod.pojo.po.SubDomainPO;
import cn.dnspod.service.IDomainService;
import cn.dnspod.service.ISubDomainService;
import cn.dnspod.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 动态域名解析域名表 服务实现类
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Service
public class DomainServiceImpl extends ServiceImpl<IDomainMapper, DomainPO> implements IDomainService {

    @Autowired
    private ISubDomainService subDomainService;


    @Override
    public List<DomainPO> findDomainByEnable() {
        QueryWrapper<DomainPO> queryWrapper = new QueryWrapper();
        queryWrapper.eq(DomainPO.STATE, SysConstant.STATE.ENABLE);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<DomainPO> pageDomain(String domainValue, Integer state, int curPage, int pageSize) {
        Page<DomainPO> page = new Page<>(curPage, pageSize);
        QueryWrapper<DomainPO> wrapper = new QueryWrapper();
        if (!Utils.isEmpty(domainValue)){
            wrapper.like(DomainPO.DOMAIN_VALUE, domainValue);
        }
        if (!Utils.isEmpty(state)) {
            wrapper.eq(DomainPO.STATE, state);
        }
        Page<DomainPO> domainPage = this.baseMapper.selectPage(page, wrapper);
        return new PageInfo<>(domainPage);
    }

    @Override
    public boolean deleteDomain(Long domainId) {
        // 删除全部子域名
        subDomainService.remove(new QueryWrapper<SubDomainPO>().eq(SubDomainPO.DOMAIN_ID, domainId));
        return this.baseMapper.deleteById(domainId) > 0;
    }

    @Override
    public DomainPO findDomainByDomainValue(String domainValue) {
        QueryWrapper<DomainPO> wrapper = new QueryWrapper();
        wrapper.eq(DomainPO.DOMAIN_VALUE, domainValue);
        return this.baseMapper.selectOne(wrapper);
    }
}
