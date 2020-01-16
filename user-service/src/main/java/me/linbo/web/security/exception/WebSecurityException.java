package me.linbo.web.security.exception;

import me.linbo.web.core.execption.BizException;

/**
 * 安全异常
 * @author LinBo
 * @date 2020-01-16 14:26
 */
public class WebSecurityException extends BizException {

    /** token未生效 */
    public static final WebSecurityException TOKEN_NOT_ACTIVE = new WebSecurityException("0001", "token未生效");
    /** token已失效 */
    public static final WebSecurityException TOKEN_EXPIRED = new WebSecurityException("0002", "token已失效");
    /** token有误 */
    public static final WebSecurityException TOKEN_ERROR = new WebSecurityException("0003", "token有误");
    /** token为空 */
    public static final WebSecurityException TOKEN_EMPTY = new WebSecurityException("0004", "token为空");

    protected WebSecurityException(String code, String message) {
        super(SECURITY_MODULE_CODE, code, message);
    }
}
