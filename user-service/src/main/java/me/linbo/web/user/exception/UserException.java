package me.linbo.web.user.exception;


import me.linbo.web.core.execption.BizException;

/**
 * 用户异常
 * <p>
 *     异常编码定义： 3位子系统编码，3位模块编码，4位业务编码
 *              如:  001 001 0001
 * </p>
 * @author LinBo
 * @date 2019-11-26 16:02
 */
public class UserException extends BizException {

    public static final BizException USER_NOT_EXISTS = new UserException("0010010001", "用户不存在");

    public UserException(String code, String message) {
        super(code, message);
    }
}
