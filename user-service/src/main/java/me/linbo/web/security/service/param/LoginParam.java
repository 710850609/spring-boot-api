package me.linbo.web.security.service.param;

import lombok.Data;

/**
 * @author LinBo
 * @date 2020/1/6 21:39
 */
@Data
public class LoginParam {

    private String name;

    private String password;
}
