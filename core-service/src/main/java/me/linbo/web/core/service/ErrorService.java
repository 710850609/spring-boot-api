package me.linbo.web.core.service;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.core.entity.Response;
import me.linbo.web.core.execption.BizException;
import me.linbo.web.core.execption.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LinBo
 * @date 2019-12-15 22:22
 */
@Slf4j
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorService extends AbstractErrorController {

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;
    
    @Autowired
    private ErrorAttributes errorAttributes;

    public ErrorService(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> error(HttpServletRequest request) {
        Throwable error = errorAttributes.getError(new ServletWebRequest(request));
        if (error != null && BizException.class.isAssignableFrom(error.getClass())) {
            return Response.error((BizException) error);
        }
        HttpStatus status = getStatus(request);
        log.error("错误", error);
        switch (status) {
            case NOT_FOUND:
                return Response.error(SystemException.SERVICE_NOT_FOUND);
            case FORBIDDEN:
                return Response.error(SystemException.FORBIDDEN);
            case UNAUTHORIZED:
                return Response.error(SystemException.UNAUTHORIZED);
            default:
                return Response.error(SystemException.SYSTEM_ERROR);
        }
    }

}
