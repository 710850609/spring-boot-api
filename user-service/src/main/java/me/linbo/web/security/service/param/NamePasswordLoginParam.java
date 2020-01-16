package me.linbo.web.security.service.param;

import lombok.Data;

/**
 * 名字、密码登录参数
 * @author LinBo
 * @date 2020/1/6 21:39
 */
@Data
public class NamePasswordLoginParam {

    /** 登录名 */
    private String name;

    /** 密码 */
    private String password;
}
