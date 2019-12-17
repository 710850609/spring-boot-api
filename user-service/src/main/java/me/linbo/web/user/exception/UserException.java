package me.linbo.web.user.exception;


import me.linbo.web.core.execption.BizException;

/**
 * @author LinBo
 * @date 2019-11-26 16:02
 */
public class UserException extends BizException {

    public UserException(String code, String message) {
        super(code, message);
    }
}
