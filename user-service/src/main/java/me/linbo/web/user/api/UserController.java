package me.linbo.web.user.api;

import me.linbo.web.core.api.BaseController;
import me.linbo.web.core.entity.Response;
import me.linbo.web.core.entity.UserInfo;
import me.linbo.web.user.entity.User;
import me.linbo.web.user.service.UserService;
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
public class UserController extends BaseController<UserService, User, Long> {

    @GetMapping("/test")
    public Response<UserInfo> test(UserInfo userInfo) {
        return ok(userInfo);
    }

}
