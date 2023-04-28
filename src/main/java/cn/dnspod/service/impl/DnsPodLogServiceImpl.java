package cn.dnspod.service.impl;



import cn.dnspod.mapper.IDnsPodLogMapper;
import cn.dnspod.pojo.PageInfo;
import cn.dnspod.pojo.po.DnsPodLogPO;
import cn.dnspod.service.IDnsPodLogService;
import cn.dnspod.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 动态域名解析日志 服务实现类
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Service
public class DnsPodLogServiceImpl extends ServiceImpl<IDnsPodLogMapper, DnsPodLogPO> implements IDnsPodLogService {

    @Override
    public PageInfo<DnsPodLogPO> pageDnsPodLog(String domainValue, Integer state, int curPage, int pageSize) {
        Page<DnsPodLogPO> page = new Page<>(curPage, pageSize);
        QueryWrapper<DnsPodLogPO> wrapper = new QueryWrapper<>();
        if (!Utils.isEmpty(domainValue)) {
            wrapper.like(DnsPodLogPO.DOMAIN_VALUE, domainValue);
        }
        if (!Utils.isEmpty(state)) {
            wrapper.eq(DnsPodLogPO.RESULT, state);
        }
        wrapper.orderByDesc(DnsPodLogPO.CREATE_TIME);
        Page<DnsPodLogPO> dnsPodLogPage = this.baseMapper.selectPage(page, wrapper);
        return new PageInfo<>(dnsPodLogPage);
    }
}
