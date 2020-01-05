package me.linbo.web.user.service;

import me.linbo.web.core.entity.PageResult;
import me.linbo.web.core.entity.Response;
import me.linbo.web.core.entity.UserInfo;
import me.linbo.web.user.bll.UserBiz;
import me.linbo.web.user.model.User;
import me.linbo.web.user.service.params.UserQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * @author LinBo
 * @date 2019-11-25 14:27
 */
@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserService {

    @Autowired
    private UserBiz userBiz;

    /**
     * 分页查询
     * @param param
     * @return {@link Response< PageResult<User>>}
     **/
    @GetMapping(path = "")
    public Response<PageResult<User>> page(UserQueryParams param) {
        PageResult<User> result = userBiz.page(param);
        return Response.ok(result);
    }

    /**
     * id查询
     * @param id
     * @return {@link Response<User>}
     **/
    @GetMapping(path = "/{id}")
    public Response<User> getById(Long id) {
        User model = userBiz.getById(id);
        return Response.ok(model);
    }

    /**
     * 新增
     * @param param
     * @return {@link Response<User>}
     **/
    @PostMapping(path = "")
    public Response<User> add(User param) {
        userBiz.save(param);
        return Response.ok(param);
    }

    /**
     * 根据id更新
     * @param id
     * @param param
     * @return {@link Response<User>}
     **/
    @PutMapping(path = "/{id}")
    public Response<User> update(@PathVariable("id") Long id, User param) {
        userBiz.updateById(param);
        return Response.ok();
    }

    /**
     * 根据id删除
     * @param id
     * @return {@link Response}
     **/
    @DeleteMapping(path = "/{id}")
    public Response deleteById(@PathVariable("id") Long id) {
        userBiz.removeById(id);
        return Response.ok();
    }


    @GetMapping("/test")
    public Response<UserInfo> test(UserInfo userInfo) {
        return Response.ok(userInfo);
    }

}
