package me.linbo.web.api.common;

import lombok.Getter;

/**
 * @author LinBo
 * @date 2019-11-26 16:02
 */
@Getter
public abstract class BizException extends RuntimeException {

    private String code;

    private String message;

    public BizException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
