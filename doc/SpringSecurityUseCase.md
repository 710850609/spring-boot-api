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
    
- 3、设置某些uri访问需要鉴权

- 4、设置某些uri访问不需要鉴权

- 5、设置Token鉴权

- 6、设置基于注解进行鉴权，不同注解走不一样鉴权逻辑

## 三、SpringSecurity最常用适配编码方式
## 1 使用原则
### 1.1 SpringSecurity的基本原理
<pre>
SpringSecurity的实现，是基于Web服务过滤器FilterSecurityInterceptor 。
通过向Web容器注册一个过滤器，并在这个过滤器下，采用责任链模式自定义多个不同安全处理的过滤器，来实现请求接口支持不同鉴权、授权逻辑。
</pre>

<p>请求某接口需要经过SpringSecurity一系列的鉴权授权</p> 
类比: 
<p>拜访某公司领导需要经过多人确认，这里的确认包括不限制于大厦保安、公司前台、领导秘书</p>

过滤器机制是SpringSecurity最基本的实现原理

### 1.2 接入机制的选用原则
- 鉴权授权实现最少程度和SpringSecurity耦合
- 最大程度使用SpringSecurity提供适应项目鉴权授权功能

### 1.3 SpringSecurity弊端
- 最少需要实现```UserDetailsService```，这个接口只能用户名查询用户相关信息，可能需要通过手机号、用户id查询
- ```UserDetailsService```的返回类型是```User```，这个已经定义了用户信息，很容易和项目中已有的用户类重复，而且属性名会重复
- SpringSecurity把认证也融合到鉴权过滤器上，

## 2 基于HTTP请求相关参数鉴权
### 2.1 适用场景
<pre>不同uri走不同的访问控制逻辑</pre>
<pre>Token鉴权、refreshToken鉴权</pre>

### 2.2 实现
- 自定义```org.springframework.web.filter.OncePerRequestFilter```实现类，实现鉴权逻辑。在过滤器上识别当前请求uri是不是属于当前鉴权访问。
- 把鉴权Filter添加到SpringSecurity过滤链上的```NamePasswordAuthenticationFilter```后面。如果没有使用到SpringSecuriyt的```NamePasswordAuthenticationFilter```，可以添加到他的前面


## 3. 基于注解鉴权
### 3.1 适用场景
每一个接口固定一种访问控制方式，一般对于接口已经抽象了一定授权抽象，如角色
这种方式对接口权限分类在系统设计过程已经要有明确的归类角色要求，不支持运行时动态修改所属角色

### 开启```@EnableGlobalMethodSecurity```注解
