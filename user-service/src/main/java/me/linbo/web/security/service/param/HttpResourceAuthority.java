package me.linbo.web.security.service.param;

import me.linbo.web.user.model.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;


/**
 * Http资源认证对象
 * @author LinBo
 * @date 2020-01-14 17:19
 */
public class HttpResourceAuthority extends Resource implements GrantedAuthority {

    public static HttpResourceAuthority build(Resource resource) {
        HttpResourceAuthority authority = new HttpResourceAuthority();
        BeanUtils.copyProperties(resource, authority);
        return authority;
    }

    @Override
    public String getAuthority() {
        return getUri();
    }
}
