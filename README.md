### 微服务应用(轻量级架构)解决方案



## 目录

* [前言](#前言)
* [模块化开发](#模块化开发)
* [业务模块的开发规范](#业务模块的开发规范)
* [RPC服务](#RPC服务)
* [网络请求](#网络请求)
* [accessToken](#accessToken)
* [分布式事务](#分布式事务)
* [spring-cloud集成](#spring-cloud集成)
* [敏捷开发及集成测试](#敏捷开发及集成测试)
* [安全认证](#安全认证)
* [快速生成API调试数据](#快速生成API调试数据)
* [swagger2文档](#swagger2文档)
* [docker部署](#docker部署)
* [本地编译](#本地编译)
* [代码提交](#代码提交)
* [Java编程规范](#Java编程规范)
* [参考链接文档](#参考链接文档)


### 前言

目前很多项目交付模块都基本类似。为了实现快速迭代和交付,实现一套轻量级架构应用。
为方便web相关的开发,整套技术体系基于spring-boot.业务模块和组件分离。
每个模块都可以单独运行,模块责任到具体的开发人员。对微服务也可以无缝迁移和切换。
按照spring boot的思想,将各个不同的功能按照starter的形式拆分开来,做到灵活组合。
这里我们是以业务模块为拆分。各个业务模块可以自由组合。

原则:

1. 较少第三方中间件的引入
2. 减少冗余代码
3. 每个模块隔离要极度清晰并可独立运行(切记不可写垃圾代码)
4. 出现服务耦合的情况可以采用轻量级RPC的实现
5. 模块和模块之间有功能重叠的情况,由模块相关开发人员协商解决。


### 模块化开发

模块说明:


模块名                      |模块说明                          |作者
---------------------------| --------------------------------|---------------------
oh-common-boot          |基础公共模块                       |  Feel
oh-druid-boot           |数据库连接池                       |  Feel
oh-example-boot         |开发模块模板                       |  Feel
oh-execl-boot           |execl操作模块                     |  Feel
oh-pay-boot             |第三方支付                         | Feel
oh-swagger-ui           |swagger主题UI                     | Feel
oh-security-boot        |系统管理(权限系统）                 | Feel
oh-swagger-boot         |swagger文档                       | Feel
oh-stater-boot          |项目整合启动                       | Feel







### RPC服务

对于有些项目可能出现服务之间的调用。由于考虑轻量级的实现。避免引入太多的中间。对服务的高可靠性会有所牺牲。具体项目还是由开发人员自己考虑。以下给出几点参考方案

1. 最简单的实现采用http调用
   该部分实现主要针对服务直接的调用频率不是太高
2. Hessian实现(spring-web内部集成)
   这种方式注意采用二进制的rpc实现相对http性能高些
3. grpc/netty的实现
    对于要求性能更高的场景可以考虑此种方式。我们组件中会提供轻量级的实现


### 网络请求

看到还有很多同学使用网络请求还在使用httpclient。导致代码混乱，不好管理。这里有必要强调一下，对于一些特殊的格式网络请求可以使用
retrofit+okhttp。 对应api请求格式的(如机智云企业api)我们使用spring自身提供的RestTemplate来满足我们的需求。对应一些代码比较混乱的地方
尽可能去完善和补充。


### accessToken

对于accessToken的使用,我们应用中或多或少都会使用第三方服务。比如机智云企业API,微信，微博应用。这个token都有一定的
过期时间，如何缓存这些是一个问题。看到有些同学使用redis做缓存。我们这边考虑到轻量级，建议使用@Scheduled+guava或者Scheduled+HashMap
使用spingboot-Scheduled定时去获取token值,刷新token的失效。



###  分布式事务

如果项目中有些服务需要考虑分布式事务(轻量级的实现)。可以考虑使用
(Event-sourcing+MQ). 可能对这个比较陌生的同学去了解下。storm(高性能实时流处理框架)中是分布式事务就是采用这种解决方案.
或者使用springboot + JTA + jotm/Automikos实现



###  spring-cloud集成

对项目要求较高或者对springcoud 熟悉的可以无缝迁移到spring-cloud



###  敏捷开发及集成测试



###  安全认证

* 前后端分离权限系统

推荐使用 security+jwt架构

* 移动端token认证

推荐使用 security+jwt架构


目前基础组件库`oh-security-boot` 已经完全实现了pc和移动端token认证，可以使用同一套架构。



**spring-security-jwt Restful API Token 安全认证**

基于security+jwt的实现机制,实现动态权限和动态菜单
可以直接集成到任何项目中，无代码侵入性。
优点也非常明显

1. JWT作为一个无状态的授权校验技术，也非常适合于分布式系统架构。
AccessToken字符串中包含用户信息和权限范围，我们所需的全部信息都有了，所以不需要维护Token存储，资源服务器也不必要求Token检查。
不需要存储用户状态，权限相关信息，无需共享数据。

2. app和pc能共用同一套认证机制,前后端彻底的分离

###### 疑问
1. 为什么使用JWT而不是其他的token方式？

当我们的授权服务器，或者角色和资源变更。我们需要存储多次token请求，为了需求也没有办法。但对扩展token的存储会很大影响我们的系统。对
系统的扩展性比较低。而采用JWT机制完全可以解决这个问题。

JWT的一个不好地方就是。当你的资源或者权限相关的信息也需要使用JWT存储时,会导致生成的token信息很长。这是无法避免的.
其实到这一步可能就有人会想了，HTTP 请求总会带上 token，这样这个 token 传来传去占用不必要的带宽啊。
如果你这么想了，那你可以去了解下 HTTP2，HTTP2 对头部进行了压缩，相信也解决了这个问题。
而一般我们资源权限对应的都是菜单，所有这里也不是什么问题。





###### 安全框架的使用

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


### 快速生成API调试数据

如何快速架构API,mock 数据给前端使用呢？只要根据规范声明如下返回的泛型类型信息。 具体业务逻辑之后实现。可以快速部署一套API进行测试。
使用案例如下:

```
    @GetMapping("/error/mock")
    @ApiOperation(value = "mock数据示例", notes = "mock数据示例")
    public ApiResponse<List<SysStatusCode>> getError() {

        //TODD
        return ApiResponse.success(new ModelEntity().resolveBean(new TypeToken<List<SysStatusCode>>() {
        }.getType()));

    }
```
返回mock数据如下:


```
{
  "code": 90019001,
  "message": "成功",
  "data": [
    {
      "code": 81,
      "message": "ruby"
    }
  ]
}

```




#### swagger2文档


 ##### swagger2使用说明

 ```
 - swagger2使用说明：
    - @Api：用在类上，说明该类的作用
    - @ApiOperation：用在方法上，说明方法的作用
    - @ApiIgnore：使用该注解忽略这个API
    - @ApiImplicitParams：用在方法上包含一组参数说明
    - @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
      - paramType：参数放在哪个地方
        header-->请求参数的获取：@RequestHeader
        query-->请求参数的获取：@RequestParam
        path（用于restful接口）-->请求参数的获取：@PathVariable
        body（不常用）
        form（不常用）
        name：参数名
        dataType：参数类型
        required：参数是否必须传
        value：参数的意思
        defaultValue：参数的默认值
    - @ApiResponses：用于表示一组响应
    - @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
      - code：数字，例如400
      - message：信息，例如"请求参数没填好"
      - response：抛出异常的类
    - @ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    - @ApiModelProperty：描述一个model的属性
 ```  


  ##### 注解使用方式

  在项目启动类上标注`@EnableSwagge2Doc` 注解


  ##### 默认配置

   ```
   swagger.enabled=是否启用swagger，默认：true
   swagger.title=标题
   swagger.description=描述
   swagger.version=版本
   swagger.license=许可证
   swagger.licenseUrl=许可证URL
   swagger.termsOfServiceUrl=服务条款URL
   swagger.contact.name=维护人
   swagger.contact.url=维护人URL
   swagger.contact.email=维护人email
   swagger.base-package=swagger扫描的基础包，默认：全扫描
   swagger.base-path=需要处理的基础URL规则，默认：/**
   swagger.exclude-path=需要排除的URL规则，默认：空
   swagger.host=文档的host信息，默认：空
   swagger.enabledSecurity=是否开启安全认证
   swagger.header[0]=头部信息
   ## 安全认证选择方式也可以使用如下配置
   swagger.globalOperationParameters[0].name=参数名
   swagger.globalOperationParameters[0].description=描述信息
   swagger.globalOperationParameters[0].modelRef=指定参数类型
   swagger.globalOperationParameters[0].parameterType=指定参数存放位置,可选header,query,path,body.form
   swagger.globalOperationParameters[0].required=指定参数是否必传，true,false

   ```


  ##### 分组配置

   ```
   swagger.docket.<name>.title=标题
   swagger.docket.<name>.description=描述
   swagger.docket.<name>.version=版本
   swagger.docket.<name>.license=许可证
   swagger.docket.<name>.licenseUrl=许可证URL
   swagger.docket.<name>.termsOfServiceUrl=服务条款URL
   swagger.docket.<name>.contact.name=维护人
   swagger.docket.<name>.contact.url=维护人URL
   swagger.docket.<name>.contact.email=维护人email
   swagger.docket.<name>.base-package=swagger扫描的基础包，默认：全扫描
   swagger.docket.<name>.base-path=需要处理的基础URL规则，默认：/**
   swagger.docket.<name>.exclude-path=需要排除的URL规则，默认：空
   swagger.docket.<name>.enabledSecurity=是否开启安全认证
   swagger.docket.<name>.header[0]=头部信息
   ## 安全认证选择方式也可以使用如下配置
   swagger.docket.<name>.name=参数名
   swagger.docket.<name>.modelRef=指定参数类型
   swagger.docket.<name>.parameterType=指定参数存放位置,可选header,query,path,body.form
   swagger.docket.<name>.required=true=指定参数是否必传，true,false
   swagger.docket.<name>.globalOperationParameters[0].name=参数名
   swagger.docket.<name>.globalOperationParameters[0].description=描述信息
   swagger.docket.<name>.globalOperationParameters[0].modelRef=指定参数存放位置,可选header,query,path,body.form
   swagger.docket.<name>.globalOperationParameters[0].parameterType=指定参数是否必传，true,false

   ```

   ##### 分组使用案例

   ```
   swagger.enabled=true
   swagger.docket.a.title=swagger
   swagger.docket.a.groupName=\u57fa\u7840\u516c\u5171Api\u6587\u6863
   swagger.docket.a.description=swagger
   swagger.docket.a.version=2.7.0.RELEASE
   swagger.docket.a.license=Apache License, Version 2.0
   swagger.docket.a.licenseUrl=https://www.apache.org/licenses/LICENSE-2.0.html
   swagger.docket.a.contact.url=http://www.gizwits.com
   swagger.docket.a.base-path=/**
   swagger.docket.a.exclude-path=/error, /ops/**
   swagger.docket.a.enabledSecurity=true
   swagger.docket.a.header[0]=access_token

  ```

注意配置文件中文乱码的问题使用native2ascii进行转换.swagger参考开源实现,增加了安全认证。





###  docker部署

docker-compose.yml 模板


```
framework:
  container_name: oh-framework
  image: daocloud.io/oh-framework
  working_dir: /data
  ports:
    - "8080:8080"
  environment:
   SERVER_PORT: 8080
   SPRING_DATASOURCE_DRUID_URL: "jdbc:mysql:/127.0.0.0:3306/oh?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false"
   SPRING_DATASOURCE_DRUID_USERNAME: "root"
   SPRING_DATASOURCE_DRUID_PASSWORD: "root"
   SERVER_CONTEXT_PATH: "/"
   SPRING_DEVTOOLS_RESTART_ENABLED: "false"
  volumes:
      - ./data:/data
  restart: always

```



### 本地编译

```
mvn -e -DskipTests=true clean package

```

### 代码提交

开发人员在代码提交之前确保代码是可以编译通过运行，不可提交不可运行的代码。通过如下命令检查

```

mvn -e -DskipTests=true clean package

```

### Java编程规范

**声明**

每个人开发的风格都不一样，设计思路也不一样，关于编程规范,业界有很多声音也有很多疑问？
我们尽可能是参考和学习阿里和google的java编程规范。参考链接如下:

**文件头注释**

添加到你IDEA模板中


```
/**
 * @author  Feel
 * @date    ${DATE}
 * @since   1.0
 * @email   fye@gizwits.com
 */
```


* [阿里巴巴java编程规范](docs/阿里巴巴java编程规范2017版.pdf)
* [Googlejava编程规范](docs/google-java-styleguide-zh.pdf)






### 参考链接文档

* [springfox-swagger-ui](https://github.com/Bestfeel/springfox-swagger-ui)
* [spring-boot-starter-swagger](https://github.com/SpringForAll/spring-boot-starter-swagger)
* [spring-boot-reference](https://docs.spring.io/spring-boot/docs/current/reference/)
* [spring-data](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [spring-security](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/)
* [spring-security-zhcn](https://springcloud.cc/spring-security-zhcn.html)
