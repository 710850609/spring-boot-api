package me.linbo.web.user.service.impl;

import me.linbo.web.core.service.BaseServiceImpl;
import me.linbo.web.user.domain.User;
import me.linbo.web.user.mapper.UserMapper;
import me.linbo.web.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author LinBo
 * @date 2019-11-25 14:25
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
}
