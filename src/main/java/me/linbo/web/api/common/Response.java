package me.linbo.web.api.common;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author LinBo
 * @date 2019-12-04 14:14
 */
@Data
@Accessors(chain = true)
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "0";

    public static final String SYSTEM_ERROR_CODE = "-1";

    /**
     * 业务错误码
     */
    private String code;
    /**
     * 结果集
     */
    private T data;
    /**
     * 描述
     */
    private String message;

    public static <T> Response<T> ok(T data) {
        return build(SUCCESS_CODE, "请求成功", data);
    }

    public static <T> Response<T> error(BizException e) {
        return error(e, null);
    }

    public static <T> Response<T> error(BizException e, T data) {
        Assert.notNull(e, "业务异常为空");
        return build(e.getCode(), e.getMessage(), data);
    }

    public static <T> Response<T> systemError(T data) {
        return build(SYSTEM_ERROR_CODE, "未知的错误", null);
    }

    public static <T> Response<T> build(String code, String message, T data) {
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
