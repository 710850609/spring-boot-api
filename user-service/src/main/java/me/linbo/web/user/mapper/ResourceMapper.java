package me.linbo.web.user.mapper;

import me.linbo.web.user.model.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * <p>
 * 服务资源表 Mapper 接口
 * </p>
 *
 * @author LinBo
 * @since 2020-01-06
 */
@MapperScan
public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> listByUserId(@Param("id") Long id);
}
