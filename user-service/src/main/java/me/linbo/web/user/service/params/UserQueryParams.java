package me.linbo.web.user.service.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.linbo.web.core.entity.PageParams;

import java.util.Date;

/**
 * @author LinBo
 * @date 2020/1/3 20:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryParams extends PageParams {


    private Long id;

    /** 登录名 */
    private String loginName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别（1：男性。2：女性。0未知性别）
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 手机号码
     */
    private Integer phoneNo;

    /**
     * 头像
     */
    private String photo;

    /** 密码 */
    private String password;

    /**
     * 加盐值
     */
    private String salt;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 创建时间
     */
//    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
