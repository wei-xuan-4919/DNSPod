package cn.dnspod.service.impl;


import cn.dnspod.mapper.IConfigMapper;
import cn.dnspod.pojo.po.ConfigPO;
import cn.dnspod.service.IConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
* @author weixuan
* @date 2022/10/27 16:06:01
* @Description:
*/
@Service
public class ConfigServiceImpl extends ServiceImpl<IConfigMapper, ConfigPO> implements IConfigService{

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Override
    public ConfigPO findConfigByKey(String key) {
        QueryWrapper<ConfigPO> wrapper = new QueryWrapper<>();
        wrapper.eq(ConfigPO.CONFIG_KEY, key);
        return this.baseMapper.selectOne(wrapper);
    }
}
