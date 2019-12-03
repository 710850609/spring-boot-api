package me.linbo.web.api.config;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @author LinBo
 * @date 2019-12-03 17:46
 */
public class DateTimeConverter implements Converter<Long, Date> {

    @Override
    public Date convert(Long value) {
        if (value == null) {
            return null;
        }
        return new Date(value);
    }

}
