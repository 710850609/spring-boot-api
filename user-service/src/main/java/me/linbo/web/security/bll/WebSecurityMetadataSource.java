package me.linbo.web.security.bll;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 请求和权限的匹配关系
 * @author LinBo
 * @date 2020-01-08 14:10
 */
@Component
public class WebSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static HashMap<String, Collection<ConfigAttribute>> map =null;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if (authentication != null) {
            if (null == map) {
                loadResourceDefine();
            }
            //object 中包含用户请求的request 信息
            HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
            for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
                String url = it.next();
                if (new AntPathRequestMatcher( url ).matches( request )) {
                    return map.get( url );
                }
            }
        }

        return null;
    }

    private void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        array = new ArrayList<>();
        cfg = new SecurityConfig("admin");
        //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
        array.add(cfg);
        //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
        map.put("/users/page", array);
        map.put("/users", array);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
