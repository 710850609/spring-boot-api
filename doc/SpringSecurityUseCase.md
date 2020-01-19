## Spring Security 使用说明
## 一、SpringSecurity为什么上手难
- 1、SpringSecurity的配置入口过多，需要多处编码配置
- 2、SpringSecurity的配置耦合度过高，需要实现SpringSecurity多接口
- 3、官方没有给出的demo都是比较基础，想要定制化程度比较高的安全实现，需要熟悉SpringSecurity的实现原理

## 二、捋清实际需要的功能
- 1、一种或多种登入方式，包括不限于：
    <p>账号+密码+验证码登入方式</p>
    <p>邮箱+密码登入方式</p>
    <p>手机号+验证码登入方式</p>
    <p>第三方账号登入方式</p>
    
- 2、会话续约，包括不限于：
  <p>remember me功能</p>
  <p>刷新token功能</p>
    
- 2、设置某些uri访问需要鉴权

- 3、设置某些uri访问不需要鉴权

## 三、SpringSecurity最常用适配编码方式