package me.linbo.web.core.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.core.entity.PageQuery;
import me.linbo.web.core.entity.PageResult;
import me.linbo.web.core.entity.Response;
import me.linbo.web.core.entity.UserInfo;
import me.linbo.web.core.execption.BizException;
import me.linbo.web.core.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 基础控制器
 * @author LinBo
 * @date 2019-11-26 15:56
 */
@Slf4j
public abstract class BaseController<S extends BaseService, M, T extends Serializable> {

    @Autowired
    protected BaseService<M> baseService;

    /**
     * 分页查询
     * @param param1
     * @param param2
     * @return {@link Response< PageResult<M>>}
     **/
    @GetMapping(path = "")
    public Response<PageResult<M>> page(PageQuery param1, M param2) {
        IPage<M> page = new Page<>(param1.getPageNo(), param1.getPageSize());
        QueryWrapper<M> query = new QueryWrapper<M>(param2);
        page = baseService.page(page, query);
        PageResult<M> result = new PageResult<>();
        result.setData(page.getRecords());
        result.setTotal(page.getTotal());
        return ok(result);
    }

    /**
     * id查询
     * @param id
     * @return {@link Response<M>}
     **/
    @GetMapping(path = "/{id}")
    public Response<M> getById(T id) {
        M model = baseService.getById(id);
        return ok(model);
    }

    /**
     * 新增
     * @param param
     * @return {@link Response<M>}
     **/
    @PostMapping(path = "")
    public Response<M> add(M param) {
        baseService.save(param);
        return ok(param);
    }

    /**
     * 根据id更新
     * @param id
     * @param param
     * @return {@link Response<M>}
     **/
    @PutMapping(path = "/{id}")
    public Response<M> update(@PathVariable("id") T id, M param) {
        baseService.updateById(param);
        return ok();
    }

    /**
     * 根据id删除
     * @param id
     * @return {@link Response}
     **/
    @DeleteMapping(path = "/{id}")
    public Response deleteById(@PathVariable("id") T id) {
        baseService.removeById(id);
        return ok();
    }

    /**
     * 请求成功
     * @param <T>  对象泛型
     * @return ignore
     */
    protected <T> Response<T> ok() {
        return Response.ok(null);
    }

    /**
     * 请求成功
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return ignore
     */
    protected <T> Response<T> ok(T data) {
        return Response.ok(data);
    }

    /**
     * 请求失败
     * @param e 业务异常
     * @return ignore
     */
    protected <T> Response <T> error(BizException e) {
        return Response.error(e);
    }

}
