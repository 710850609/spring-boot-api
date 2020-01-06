package me.linbo.test.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author LinBo
 * @date 2020-01-06 10:00
 */
@Mapper
public interface UserMapper {

    @Select("select id from user")
    List<Map<String, Object>> page();
}
