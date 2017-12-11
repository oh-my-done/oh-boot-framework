spring-security-jwt Restful API Token 安全认证
=================================================

基于security+jwt的实现机制,实现动态权限和动态菜单
可以直接集成到任何项目中，无代码侵入性。
优点也非常明显

1. JWT作为一个无状态的授权校验技术，也非常适合于分布式系统架构。
AccessToken字符串中包含用户信息和权限范围，我们所需的全部信息都有了，所以不需要维护Token存储，资源服务器也不必要求Token检查。
不需要存储用户状态，权限相关信息，无需共享数据。

2. app和pc能共用同一套认证机制,前后端彻底的分离 

### 疑问
1. 为什么使用JWT而不是其他的token方式？

当我们的授权服务器，或者角色和资源变更。我们需要存储多次token请求，为了需求也没有办法。但对扩展token的存储会很大影响我们的系统。对
系统的扩展性比较低。而采用JWT机制完全可以解决这个问题。

JWT的一个不好地方就是。当你的资源或者权限相关的信息也需要使用JWT存储时,会导致生成的token信息很长。这是无法避免的.
其实到这一步可能就有人会想了，HTTP 请求总会带上 token，这样这个 token 传来传去占用不必要的带宽啊。
如果你这么想了，那你可以去了解下 HTTP2，HTTP2 对头部进行了压缩，相信也解决了这个问题。
而一般我们资源权限对应的都是菜单，所有这里也不是什么问题。





#### 安全框架的使用

1. 忽略不需要权限的路径`@IgnoreAuthenticate`

使用注解 @IgnoreAuthenticate 即可忽略这个路径的认证


```
    @GetMapping("/status/version")
    @IgnoreAuthenticate
    @ApiOperation(value = "版本号", notes = "版本号")
    public ApiResponse<Profile> getVersions() {

        return ApiResponse.success((version == null) ? new Profile("1.0") : new Profile(version));
    }
    
    
```


2.  需要使用的认证

需要在header中认证access_token信息

```
     @ApiOperation(value = "获取菜单树", notes = "获取菜单树", authorizations = {@Authorization("${jwt.header}")})
     @GetMapping(value = "/menus")
     public ApiResponse menus() {
 
         return new ApiResponse(ResponseCode.TOKEN_NOT_EMPTY.getCode(), ResponseCode.TOKEN_NOT_EMPTY.getName());
 
     }
````