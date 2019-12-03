package me.linbo.web.api.service.impl;

import me.linbo.web.api.common.BaseServiceImpl;
import me.linbo.web.api.domain.User;
import me.linbo.web.api.mapper.UserMapper;
import me.linbo.web.api.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author LinBo
 * @date 2019-11-25 14:25
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
}
