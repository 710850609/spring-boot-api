package me.linbo.web.security.filter;


import java.lang.annotation.*;

/**
 * @author LinBo
 * @date 2020-01-21 16:27
 */
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Auth {
}
