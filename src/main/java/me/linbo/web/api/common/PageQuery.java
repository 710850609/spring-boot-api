package me.linbo.web.api.common;

import lombok.Data;

/**
 * @author LinBo
 * @date 2019-12-04 14:53
 */
@Data
public class PageQuery {

    private long pageNo = 1;

    private long pageSize = 10;
}
