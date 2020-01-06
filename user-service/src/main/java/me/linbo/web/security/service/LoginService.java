package me.linbo.web.security.service;

import me.linbo.web.core.entity.Response;
import me.linbo.web.security.bll.LoginBiz;
import me.linbo.web.security.service.param.LoginParam;
import me.linbo.web.user.service.result.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LinBo
 * @date 2020-01-06 11:02
 */
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginService {

    @Autowired
    private LoginBiz loginBiz;

    @PostMapping("/login")
    public Response login(@RequestBody LoginParam param) {
        LoginResult loginResult = loginBiz.login(param.getName(), param.getPassword());
        return Response.ok(loginResult);
    }



}
