package me.linbo.web.api.config;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.api.common.Response;
import me.linbo.web.api.common.execption.BizException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 * @author LinBo
 * @date 2019-12-03 17:53
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({BizException.class})
    public Response<String> bizExceptionHandler(BizException e) {
        return Response.error(e);
    }

    @ExceptionHandler(BindException.class)
    public Response<String> bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuilder buf = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            buf.append("'").append(fieldError.getField()).append("'").append(fieldError.getDefaultMessage()).append("\r\n");
        }
        logException("参数绑定失败", e);
        return Response.error(e);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public Response<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        logException("参数校验不通过", e);
        return Response.error(e);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public Response<String> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logException("参数非法", e);
        BizException illegalException = BizException.COMMON_PARAMS_ILLEGAL.format(e.getMessage());
        return Response.error(illegalException);
    }

    @ExceptionHandler({Exception.class})
    public Response<String> unknownExceptionHandler(Exception e) {
        return Response.error(e);
    }

    /**
     * 打印异常
     * @param errorType
     * @param e
     * @return
     **/
    private void logException(String errorType, Exception e) {
        if (log.isDebugEnabled()) {
            log.debug(errorType, e);
        }
    }

}
