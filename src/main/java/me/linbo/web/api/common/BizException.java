package me.linbo.web.api.common;

/**
 * @author LinBo
 * @date 2019-11-26 16:02
 */
public abstract class BizException {

    private String code;

    private String message;

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
