package me.linbo.web.security.config;

import me.linbo.web.security.filter.Auth;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.access.method.DelegatingMethodSecurityMetadataSource;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.*;

/**
 * https://www.jianshu.com/p/6c77dcba6fc7
 * @author LinBo
 * @date 2020-01-21 16:39
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        List<MethodSecurityMetadataSource> sources = new ArrayList<>();
        Map<String, List<ConfigAttribute>> methodMap = new HashMap<>(1);
        List<ConfigAttribute> configAttributes = Arrays.asList(new SecurityConfig("linbo"));
        methodMap.put("me.linbo.web.user.service.UserService.changePassword", configAttributes);
        MethodSecurityMetadataSource methodSecurityMetadataSource = new MapBasedMethodSecurityMetadataSource(methodMap);

        Map<String, List<ConfigAttribute>> methodMap2 = new HashMap<>(1);
        List<ConfigAttribute> configAttributes2 = Arrays.asList(new SecurityConfig("admin"));
        methodMap2.put("me.linbo.web.user.service.UserService.changePassword", configAttributes2);
        MethodSecurityMetadataSource methodSecurityMetadataSource2 = new MapBasedMethodSecurityMetadataSource(methodMap2);


        MethodSecurityMetadataSource methodSecurityMetadataSource3 = new SecuredAnnotationSecurityMetadataSource(new AnnotationMetadataExtractor<Auth>(){
            @Override
            public Collection<? extends ConfigAttribute> extractAttributes(Auth securityAnnotation) {
                return Arrays.asList(new SecurityConfig("aaa"));
            }
        });


//        sources.add(methodSecurityMetadataSource);
//        sources.add(methodSecurityMetadataSource2);
        sources.add(methodSecurityMetadataSource3);
        return new DelegatingMethodSecurityMetadataSource(sources);
    }
}
