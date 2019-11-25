package me.linbo.web.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.linbo.web.api.domain.User;
import me.linbo.web.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author LinBo
 * @date 2019-11-25 14:27
 */
@RestController
@RequestMapping("/users")
public class UserController extends ApiController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public R<IPage<User>> list(Page<User> params) {
        IPage<User> page = userService.page(params);
        return success(page);
    }

    @GetMapping("/{id}")
    public R<User> list(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return success(user);
    }

    @GetMapping("/add")
    public R<User> add(User user) {
        user.setNickName(UUID.randomUUID().toString());
        userService.save(user);
        return success(user);
    }
}
