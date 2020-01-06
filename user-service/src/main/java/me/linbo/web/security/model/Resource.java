package me.linbo.web.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 服务资源表
 * </p>
 *
 * @author LinBo
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resource")
public class Resource implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 资源类型（1：前端页面；2：后台接口）
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * URI
     */
    private String uri;

    /**
     * 上级id
     */
    private Integer parentId;

    /**
     * 同级排序
     */
    private Integer sortNo;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
