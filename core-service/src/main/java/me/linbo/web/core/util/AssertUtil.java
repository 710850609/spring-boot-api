package me.linbo.web.core.util;

import me.linbo.web.core.execption.BizException;

/**
 * 断言工具
 * @author LinBo
 * @date 2020-01-06 15:33
 */
public class AssertUtil {

    public static void notNull(Object obj, BizException e) throws BizException {
        if (obj == null) {
            throw e;
        }
    }
}
