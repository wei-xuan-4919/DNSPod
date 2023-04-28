package cn.dnspod.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author weixuan
 * @date 2022/4/24 16:36
 * @Description: 配置类
 */
@Data
@TableName("param_config")
public class ConfigPO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名
     */
    private String configKey;

    /**
     * 参数值
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 可用状态
     */
    private int state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    public static final String ID = "id";

    public static final String CONFIG_KEY = "config_key";

    public static final String CONFIG_VALUE = "config_value";

    public static final String REMARK = "remark";

    public static final String STATE = "state";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";


    public ConfigPO() {
    }

    public ConfigPO(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }
}
