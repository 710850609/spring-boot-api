package me.linbo.web.core.execption;


/**
 * 编码：
 *  子系统：000
 * @author LinBo
 * @date 2019-12-15 19:40
 */
public class SystemException extends BizException {

    protected SystemException(String code, String message) {
        super(code, message);
    }

    /** 系统错误 */
    public static final SystemException SYSTEM_ERROR = new SystemException("0000000000", "系统错误.{0}");
    /** 服务不存在 */
    public static final SystemException SERVICE_NOT_FOUND = new SystemException("0000000001", "服务不存在");
    public static final SystemException FORBIDDEN = new SystemException("0000000002", "无权限访问");


}
