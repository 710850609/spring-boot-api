package me.linbo.web.security.service;

import me.linbo.web.core.entity.Response;
import me.linbo.web.security.bll.LoginBiz;
import me.linbo.web.user.service.result.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LinBo
 * @date 2020-01-06 11:02
 */
@RestController
public class LoginService {

    @Autowired
    private LoginBiz loginBiz;

    @PostMapping("/login")
    public Response login(String name, String password) {
        LoginResult loginResult = loginBiz.login(name, password);
        return Response.ok(loginResult);
    }



}
