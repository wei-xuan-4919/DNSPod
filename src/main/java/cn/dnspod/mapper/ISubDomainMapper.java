package cn.dnspod.mapper;


import cn.dnspod.pojo.po.SubDomainPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 动态域名解析子域名表 Mapper 接口
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Mapper
public interface ISubDomainMapper extends BaseMapper<SubDomainPO> {

}
