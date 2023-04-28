package cn.dnspod.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 动态域名解析子域名表
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sub_domain")
public class SubDomainPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 域名id
     */
    private Long domainId;

    /**
     * 子域名
     */
    private String subDomainValue;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    public static final String ID = "id";

    public static final String DOMAIN_ID = "domain_id";

    public static final String SUB_DOMAIN_VALUE = "sub_domain_value";

    public static final String STATE = "state";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
