package me.linbo.web.security.filter;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;

/**
 * @author LinBo
 * @date 2020-01-21 16:01
 */
//@Component
public class MethodAuthenticationFilter extends MethodSecurityInterceptor {

    @PostConstruct
    public void tethodAuthenticationFilter() {
        super.setSecurityMetadataSource(new MapBasedMethodSecurityMetadataSource());
        super.setAccessDecisionManager(new UnanimousBased(Collections.singletonList(new AccessDecisionVoter() {
            @Override
            public boolean supports(ConfigAttribute attribute) {
                return true;
            }

            @Override
            public int vote(Authentication authentication, Object object, Collection collection) {
                return 0;
            }

            @Override
            public boolean supports(Class clazz) {
                System.out.println(clazz.getName());
                return true;
            }
        })));
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        System.out.println(mi);
        return super.invoke(mi);
    }
}
