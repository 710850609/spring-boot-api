package me.linbo.web.core.execption;

import lombok.Getter;

import java.text.MessageFormat;

/**
 * 业务异常
 * <p>
 *     异常编码定义： 3位模块编码，4位业务编码
 *              如:  001 0001
 * </p>
 * @author LinBo
 * @date 2019-11-26 16:02
 */
@Getter
public class BizException extends RuntimeException {

    public static final String SYSTEM_MODULE_CODE = "000";
    public static final String BASE_MODULE_CODE = "001";
    public static final String USER_MODULE_CODE = "002";
    public static final String SECURITY_MODULE_CODE = "003";

    private String code;

    private String message;

    protected BizException(String moduleCode, String bizCode, String message) {
        this.code = moduleCode + bizCode;
        this.message = message;
    }

    /**
     * 格式化错误信息
     * @param params
     * @return {@link BizException}
     **/
    public BizException format(Object... params) {
        this.message = MessageFormat.format(this.message, params);
        return this;
    }

    /** 参数为空 */
    public static final BizException COMMON_PARAMS_NOT_NULL = new BizException(BASE_MODULE_CODE, "0001", "参数为空: {0}");
    /** 参数非法 */
    public static final BizException COMMON_PARAMS_ILLEGAL = new BizException(BASE_MODULE_CODE, "0002", "参数非法: {0}");

}
