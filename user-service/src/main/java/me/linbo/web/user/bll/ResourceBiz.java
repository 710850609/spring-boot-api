package me.linbo.web.user.bll;

import me.linbo.web.user.mapper.ResourceMapper;
import me.linbo.web.user.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LinBo
 * @date 2020-01-14 17:13
 */
@Service
public class ResourceBiz {

    @Autowired
    private ResourceMapper resourceMapper;

    public List<Resource> listByUserId(Long id) {
        return resourceMapper.listByUserId(id);
    }
}
