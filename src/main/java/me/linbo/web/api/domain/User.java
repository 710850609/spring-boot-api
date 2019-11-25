package me.linbo.web.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {

    private Long id;

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
    private LocalDateTime birthday;

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

}