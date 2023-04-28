package cn.dnspod.service;

import cn.dnspod.pojo.po.ConfigPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author weixuan
* @date 2022/10/27 16:00:05
* @Description:
*/
public interface IConfigService extends IService<ConfigPO> {

    /**
     * 查询key值是否存在
     */
    ConfigPO findConfigByKey(String key);

}
