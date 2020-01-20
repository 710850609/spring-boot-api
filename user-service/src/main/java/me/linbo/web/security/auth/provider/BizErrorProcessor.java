package me.linbo.web.security.auth.provider;

import org.springframework.security.config.annotation.ObjectPostProcessor;

/**
 * @author LinBo
 * @date 2020-01-19 17:14
 */
public class BizErrorProcessor implements ObjectPostProcessor {
    @Override
    public Object postProcess(Object object) {
        System.out.println(object);
        return object;
    }
}
