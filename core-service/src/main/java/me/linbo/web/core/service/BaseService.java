package me.linbo.web.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.core.bll.BaseBiz;
import me.linbo.web.core.entity.PageQuery;
import me.linbo.web.core.entity.PageResult;
import me.linbo.web.core.entity.Response;
import me.linbo.web.core.execption.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * 基础控制器
 * @author LinBo
 * @date 2019-11-26 15:56
 */
@Slf4j
//public abstract class BaseService<B extends BaseBiz<T, M>, I extends Serializable, T, M> {
public abstract class BaseService<B extends BaseBiz, T extends BaseMapper<M>, M, I extends Serializable> {

    @Autowired
    protected BaseBiz<T, M> baseBiz;

    /**
     * 分页查询
     * @param param1
     * @param param2
     * @return {@link Response< PageResult<T>>}
     **/
    @GetMapping(path = "")
    public Response<PageResult<M>> page(PageQuery param1, M param2) {
        IPage<M> page = new Page<>(param1.getPageNo(), param1.getPageSize());
        QueryWrapper<M> query = new QueryWrapper<M>(param2);
        page = baseBiz.page(page, query);
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
    public Response<M> getById(I id) {
        M model = baseBiz.getById(id);
        return ok(model);
    }

    /**
     * 新增
     * @param param
     * @return {@link Response<M>}
     **/
    @PostMapping(path = "")
    public Response<M> add(M param) {
        baseBiz.save(param);
        return ok(param);
    }

    /**
     * 根据id更新
     * @param id
     * @param param
     * @return {@link Response<M>}
     **/
    @PutMapping(path = "/{id}")
    public Response<M> update(@PathVariable("id") I id, M param) {
        baseBiz.updateById(param);
        return ok();
    }

    /**
     * 根据id删除
     * @param id
     * @return {@link Response}
     **/
    @DeleteMapping(path = "/{id}")
    public Response deleteById(@PathVariable("id") I id) {
        baseBiz.removeById(id);
        return ok();
    }

    /**
     * 请求成功
     * @param <M>  对象泛型
     * @return ignore
     */
    protected <M> Response<M> ok() {
        return Response.ok(null);
    }

    /**
     * 请求成功
     * @param data 数据内容
     * @param <M>  对象泛型
     * @return ignore
     */
    protected <M> Response<M> ok(M data) {
        return Response.ok(data);
    }

    /**
     * 请求失败
     * @param e 业务异常
     * @return ignore
     */
    protected <M> Response <M> error(BizException e) {
        return Response.error(e);
    }

}
