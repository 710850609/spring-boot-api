package me.linbo.web.core.entity;

import lombok.Data;

import java.util.List;

/**
 * @author LinBo
 * @date 2019-12-06 16:20
 */
@Data
public class PageResult<T> {

    private long total;

    private List<T> data;

}
