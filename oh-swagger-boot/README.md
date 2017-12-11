 
 swagger2使用说明
 =============================
 
 
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
    
  
  
  ### 使用方式
  
  在项目启动类上标注`@EnableSwagge2Doc` 注解
  
  
  ### 默认配置
   
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
   
   
  ### 分组配置
  
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
   
   ### 分组使用案例
   
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
  
  
   
   
   注意配置文件中文乱码的问题使用native2ascii进行转换
   
   参考文档:
  
   [spring-boot-starter-swagger](https://github.com/SpringForAll/spring-boot-starter-swagger)
   
   
   
   参考开源实现,增加了安全认证。