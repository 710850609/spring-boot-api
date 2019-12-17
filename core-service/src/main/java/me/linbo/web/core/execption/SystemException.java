package me.linbo.web.core.execption;


/**
 * @author LinBo
 * @date 2019-12-15 19:40
 */
public class SystemException extends BizException {

    protected SystemException(String code, String message) {
        super(code, message);
    }

    /** 系统错误 */
    public static final SystemException SYSTEM_EXCEPTION = new SystemException("-1", "系统错误");

}
