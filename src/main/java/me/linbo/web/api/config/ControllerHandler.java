package me.linbo.web.api.config;

import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
    }
}
