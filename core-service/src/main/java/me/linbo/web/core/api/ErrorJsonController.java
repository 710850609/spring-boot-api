package me.linbo.web.core.api;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.core.entity.Response;
import me.linbo.web.core.execption.SystemException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LinBo
 * @date 2019-12-15 22:22
 */
@Slf4j
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorJsonController implements ErrorController {

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping
    public Response<String> error(HttpServletRequest request, Throwable t) {
        HttpStatus status = getStatus(request);
        switch (status) {
            case NOT_FOUND:
                return Response.error(SystemException.SERVICE_NOT_FOUND);
            default:
                return Response.error(SystemException.SYSTEM_ERROR);
        }
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
