package me.linbo.web.core.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import me.linbo.web.core.execption.BizException;
import me.linbo.web.core.execption.SystemException;
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

    /** 业务错误码 */
    private String code;

    /** 描述 */
    private String message;

    /** 结果集 */
    private T data;

    /**
     * 业务正常数据返回
     * @param data
     * @return {@link Response<T>}
     **/
    public static <T> Response<T> ok(T data) {
        return build(SUCCESS_CODE, "请求成功", data);
    }

    /**
     * 业务异常数据返回
     * @param e
     * @return {@link Response<T>}
     **/
    public static <T> Response<T> error(BizException e) {
        Assert.notNull(e, "业务异常为空");
        return build(e.getCode(), e.getMessage(), null);
    }

    /**
     * 其他异常数据返回
     * @param e
     * @return {@link Response<T>}
     **/
    public static <T> Response<T> error(Exception e) {
        Assert.notNull(e, "业务异常为空");
        return build(SystemException.SYSTEM_ERROR.getCode(), e.getMessage(), null);
    }

    public static <T> Response<T> build(String code, String message, T data) {
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
