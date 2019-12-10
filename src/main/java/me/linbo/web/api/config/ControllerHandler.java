package me.linbo.web.api.config;

import me.linbo.web.api.common.BizException;
import me.linbo.web.api.common.Response;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author LinBo
 * @date 2019-12-03 17:53
 */
@ControllerAdvice
public class ControllerHandler {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        GenericConversionService conversionService = (GenericConversionService) webDataBinder.getConversionService();
        if (conversionService != null) {
            conversionService.addConverter(new DateTimeConverter());
        }
//        webDataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                setValue(new Date(Long.parseLong(text)));
//            }
//
//            @Override
//            public String getAsText() {
//                Date value = (Date) getValue();
//                return (value != null ? String.valueOf(value.getTime()) : null);
//            }
//        });
    }

    @ExceptionHandler({BizException.class})
    public Response<String> bizExceptionHandler(BizException e) {
        return Response.error(e);
    }

    @ExceptionHandler({Exception.class})
    public Response<String> unknownExceptionHandler(Exception e) {
        return Response.systemError(e.getMessage());
    }
}
