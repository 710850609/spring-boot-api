package me.linbo.web.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色-资源关联表
 * </p>
 *
 * @author LinBo
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role_resource")
public class RoleResource implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 资源id
     */
    private Integer resourceId;


}
