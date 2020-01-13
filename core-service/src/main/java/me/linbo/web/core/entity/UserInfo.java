package me.linbo.web.core.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author LinBo
 * @date 2019-12-17 15:10
 */
@Data
@ToString
public class UserInfo implements Serializable {

    /** 用户id */
    private String uid;

    /** 登录名 */
    private String loginName;
}
