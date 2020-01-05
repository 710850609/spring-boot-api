package me.linbo.web.core.entity;

import lombok.Data;

/**
 * @author LinBo
 * @date 2020/1/3 20:56
 */
@Data
public abstract class PageParams {

    private long pageNo = 1;

    private long pageSize = 10;
}
