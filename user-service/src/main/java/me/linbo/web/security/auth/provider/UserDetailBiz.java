package me.linbo.web.security.auth.provider;

import me.linbo.web.security.service.param.HttpResourceAuthority;
import me.linbo.web.user.bll.ResourceBiz;
import me.linbo.web.user.bll.UserBiz;
import me.linbo.web.user.model.Resource;
import me.linbo.web.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户名认证
 * @author LinBo
 * @date 2020-01-06 16:02
 */
@Service
public class UserDetailBiz implements UserDetailsService {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private ResourceBiz resourceBiz;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userBiz.getByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(name);
        }
        List<Resource> resources = resourceBiz.listByUserId(user.getId());
        List<HttpResourceAuthority> collect = resources.stream().map(r -> HttpResourceAuthority.build(r)).collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User.withUsername(user.getLoginName())
                .password(user.getPassword())
                .authorities(collect)
                .roles("linbo")
                .build();
    }
}
