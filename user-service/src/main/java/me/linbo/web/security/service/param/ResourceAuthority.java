package me.linbo.web.security.service.param;

import me.linbo.web.user.model.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;


/**
 * @author LinBo
 * @date 2020-01-14 17:19
 */
public class ResourceAuthority extends Resource implements GrantedAuthority {

    public static ResourceAuthority build(Resource resource) {
        ResourceAuthority authority = new ResourceAuthority();
        BeanUtils.copyProperties(resource, authority);
        return authority;
    }

    @Override
    public String getAuthority() {
        return getUri();
    }
}
