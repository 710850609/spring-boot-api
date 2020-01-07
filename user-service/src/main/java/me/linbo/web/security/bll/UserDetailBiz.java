package me.linbo.web.security.bll;

import me.linbo.web.user.bll.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        String encode = passwordEncoder.encode("123");
        return User.withUsername(name)
                .password("{bcrypt}$2a$10$UaASZX590cBjyXzmHZnIUe76YyJNfswjpQmzstmgV0QpZXJ6a4QV2")
                .roles(Collections.emptyList().toArray(new String[]{}))
                .build();
    }
}
