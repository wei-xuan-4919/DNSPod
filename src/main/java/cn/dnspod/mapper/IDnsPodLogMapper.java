package cn.dnspod.mapper;



import cn.dnspod.pojo.po.DnsPodLogPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 动态域名解析日志 Mapper 接口
 * </p>
 *
 * @author weixuan
 * @since 2023-04-23
 */
@Mapper
public interface IDnsPodLogMapper extends BaseMapper<DnsPodLogPO> {

}
