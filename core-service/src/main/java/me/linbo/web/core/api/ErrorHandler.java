package me.linbo.web.core.api;

import me.linbo.web.core.entity.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LinBo
 * @date 2019-12-15 22:22
 */
@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorHandler implements ErrorController {

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping
    public Response<String> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        switch (status) {
            case NOT_FOUND:
                return Response.error(new RuntimeException());
        }
        return Response.error(new RuntimeException());
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
