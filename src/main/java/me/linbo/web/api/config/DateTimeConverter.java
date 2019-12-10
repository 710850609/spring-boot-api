package me.linbo.web.api.config;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author LinBo
 * @date 2019-12-03 17:46
 */
public class DateTimeConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (source != null) {
            return new Date(Long.parseLong(source));
        }
        return null;
    }
}
