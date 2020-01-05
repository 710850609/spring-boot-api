package me.linbo.web.core.bll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.linbo.web.core.entity.PageParams;
import me.linbo.web.core.entity.PageResult;

/**
 * @author LinBo
 * @date 2019-12-04 15:01
 */
public abstract class BaseBiz<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public PageResult<T> page(PageParams params) {
        IPage<T> page = new Page<>(params.getPageNo(), params.getPageSize());
        QueryWrapper<T> query = new QueryWrapper<>((T) params);
        page = super.page(page, query);
        PageResult<T> result = new PageResult<>();
        result.setData(page.getRecords());
        result.setTotal(page.getTotal());
        return result;
    }

}
