package me.linbo.web.api.controller;

import me.linbo.web.api.common.BaseController;
import me.linbo.web.api.domain.User;
import me.linbo.web.api.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LinBo
 * @date 2019-11-25 14:27
 */
@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController<UserService, User, Long> {

}
