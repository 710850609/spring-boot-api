package me.linbo.web.user.service;

import me.linbo.web.core.entity.Response;
import me.linbo.web.core.entity.UserInfo;
import me.linbo.web.core.service.BaseService;
import me.linbo.web.user.bll.UserBiz;
import me.linbo.web.user.mapper.UserMapper;
import me.linbo.web.user.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author LinBo
 * @date 2019-11-25 14:27
 */
@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserService extends BaseService<UserBiz, UserMapper, User, Long> {

    @GetMapping("/test")
    public Response<UserInfo> test(UserInfo userInfo) {
        return ok(userInfo);
    }

}
