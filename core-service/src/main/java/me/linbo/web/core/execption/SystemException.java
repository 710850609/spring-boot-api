package me.linbo.web.core.execption;


/**
 * 系统异常编码：
 * @author LinBo
 * @date 2019-12-15 19:40
 */
public class SystemException extends BizException {

    protected SystemException(String code, String message) {
        super(SYSTEM_MODULE_CODE, code, message);
    }

    /** 系统错误 */
    public static final SystemException SYSTEM_ERROR = new SystemException("0000", "系统错误");
    /** 服务不存在 */
    public static final SystemException SERVICE_NOT_FOUND = new SystemException("0001", "服务不存在");
    /** 无权限访问 */
    public static final SystemException FORBIDDEN = new SystemException("0002", "无权限访问");
    /** 未认证 */
    public static final SystemException UNAUTHORIZED = new SystemException("0003", "认证失败");


}
