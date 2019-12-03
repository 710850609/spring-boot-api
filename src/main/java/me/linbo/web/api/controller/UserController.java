package me.linbo.web.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.linbo.web.api.common.BaseController;
import me.linbo.web.api.domain.User;
import me.linbo.web.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author LinBo
 * @date 2019-11-25 14:27
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户信息
     * @Author LinBo
     * @Date 2019-11-26 15:52
     * @param params
     * @return {@link R< IPage< User>>}
     **/
    @GetMapping("")
    public R<IPage<User>> page(Page<User> params) {
        IPage<User> page = userService.page(params);
        return success(page);
    }

    /**
     * id查询用户
     * @Author LinBo
     * @Date 2019-11-26 15:52
     * @param id
     * @return {@link R< User>}
     **/
    @GetMapping("/{id}")
    public R<User> list(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return success(user);
    }

    /**
     * 新增用户
     * @Author LinBo
     * @Date 2019-11-26 15:53
     * @param user
     * @return {@link R< User>}
     **/
    @PostMapping("")
    public R<User> add(User user) {
        user.setNickName(UUID.randomUUID().toString());
        user.setCreateTime(new Date());
        userService.save(user);
        return success(user);
    }
}
