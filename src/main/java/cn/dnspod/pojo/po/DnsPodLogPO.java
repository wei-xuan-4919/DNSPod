package cn.dnspod.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 动态域名解析日志
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Data
@TableName("dns_pod_log")
public class DnsPodLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 域名id
     */
    private Long domainId;

    /**
     * 子域名id
     */
    private Long subDomainId;

    /**
     * 域名
     */
    private String domainValue;

    /**
     * 结果状态
     */
    private Integer result;

    /**
     * 返回结果信息
     */
    private String message;

    /**
     * 原ip地址
     */
    private String oldIpAddress;

    /**
     * 新ip地址
     */
    private String newIpAddress;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    public static final String ID = "id";

    public static final String DOMAIN_ID = "domain_id";

    public static final String SUB_DOMAIN_ID = "sub_domain_id";

    public static final String DOMAIN_VALUE = "domain_value";

    public static final String RESULT = "result";

    public static final String MESSAGE = "message";

    public static final String OLD_IP_ADDRESS = "old_ip_address";

    public static final String NEW_IP_ADDRESS = "new_ip_address";

    public static final String CREATE_TIME = "create_time";

}
