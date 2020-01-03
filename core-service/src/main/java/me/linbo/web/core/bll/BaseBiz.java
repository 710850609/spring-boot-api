package me.linbo.web.core.bll;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author LinBo
 * @date 2019-12-04 15:01
 */
public abstract class BaseBiz<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
}
