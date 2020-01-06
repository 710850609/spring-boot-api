package me.linbo.web.security.bll;

import me.linbo.web.user.bll.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author LinBo
 * @date 2020-01-06 16:02
 */
@Service
public class UserDetailBiz implements UserDetailsService {

    @Autowired
    private UserBiz userBiz;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        User user = userBiz.getByName(name);
        return new User(name, "{bcrypt}$2a$10$UaASZX590cBjyXzmHZnIUe76YyJNfswjpQmzstmgV0QpZXJ6a4QV2", Collections.emptyList());
    }
}
