package me.linbo.web.security.service.param;

import lombok.Data;

/**
 * 手机、验证码登录参数
 * @author LinBo
 * @date 2020/1/6 21:39
 */
@Data
public class MobileCodeLoginParam {

    /** 手机号码 */
    private String mobile;

    /** 手机验证码 */
    private String code;
}
