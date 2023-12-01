<div align="center">
<img style="margin: 5px 3px" src="static/img/logo.png" alt="Fn">

<p>🍍🍍注解式自定义请求/方法拦截🍍🍍</p>
</div>

<div align="center">

<div style="display: flex; justify-content: center;">  
    <img style="margin: 5px 3px" src="https://gitee.com/FnTop/acri/badge/star.svg?theme=light" alt="Fn">
    <img style="margin: 5px 3px" src="https://gitee.com/FnTop/acri/badge/fork.svg?theme=dark" alt="Fn">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/VERSION-1.3.0-green" alt="Fn">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/APACHE-2.0-green" alt="Apache2.0">

</div>

<div style="display: flex; justify-content: center;">  
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/JAVA-1.8-blue">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/SPRINGBOOT-2.7.15-blue">
</div>

</div>

# 🍌🍌介绍


[文档](http://fntop.gitee.io/acri-doc)

Acri（全称Annotation Custom Request Interception）

一个注解即可实现请求的拦截处理或AOP增强

# 🍊🍊特点

* 一个`@Acri`注解搞定请求拦截
* 支持自定义拦截器，自定义扩展简单易用
* 一个`@AcriAspect`注解搞定AOP切面
* 支持自定义切面类、切面方法，自定义扩展简单易用
* 支持单个方法单拦截
* 支持单个方法多个拦截
* 支持多方法多拦截
* 不管是拦截还是切面，完全实现代码与业务分离


# 🫐🫐依赖
```xml
<dependency>
    <groupId>cn.fntop</groupId>
    <artifactId>acri-spring-boot-starter</artifactId>
    <version>1.3.0</version>
</dependency>

implementation 'cn.fntop:acri-spring-boot-starter:1.3.0'
//方式2
implementation group: 'cn.fntop', name: 'acri-spring-boot-starter', version: '1.3.0'
```

# 🍈🍈使用方式

## 添加注解

```java
@Acri(value = AcriStopWatchProcessor.class, before = true, during = true, after = true)
@GetMapping("/login")
public String login() {
    log.info("登录中");
    return "登录成功";
}
```
## 效果
<img style="margin: 5px 3px" src="static/img/img_1.png" alt="Fn">

# 🍍🍍AOP切面支持

[详细文档](http://fntop.gitee.io/acri-doc/guide/aop.html)

## 切面描述
`@AcriAspect(fallback=Custom.class,before=true，beforeMethod="before")`

`注释事项:`任何自定义切面方法，方法的参数都是`AcriContainer container`,可以从该容器中获取调用方法的`返回值、参数、异常信息`

| 配置项    | 默认值   | 备注                                 |
|:-------|:------|:-----------------------------------|
| fallback  | null  | 自定义切面类 |
| before  | false  | 是否启用前置通知 |
| around  | false  | 是否启用环绕通知 |
| after  | false  | 是否启用后置通知 |
| throwing  | false  | 是否启用异常通知 |
| beforeMethod  | before  | 启用前置通知调用的默认方法名称 |
| beforeAroundMethod  | beforeAround  | 启用环绕通知（前置环绕）调用的默认方法名称 |
| afterAroundMethod  | afterAround  | 启用环绕通知（后置环绕）调用的默认方法名称 |
| afterMethod  | after  | 启用后置通知调用的默认方法名称 |
| throwingMethod  | throwing  | 启用异常通知调用的默认方法名称 |

## 使用实例
`@AcriAspect(fallback = TestController.class, around = true, throwing = true, before = true, after = true)`

```
@RestController
@Slf4j
public class TestController {
    //Acri AOP
    @AcriAspect(fallback = TestController.class, around = true, throwing = true, before = true, after = true)
    @GetMapping("/login")
    public User login(User param) {
        log.info("登录中,{}", param);
//        int i = 1 / 0;
        return new User();
    }
    //前置通知
    public void before(AcriContainer container) {
        log.info("before => {}", container.getParams() == null ? "" : container.getParams().toString());
    }
    //后置通知
    public void after(AcriContainer container) {
        log.info("after => {}", container.getResult() == null ? "" : container.getResult().toString());
        log.info("after => {}", container.getParams() == null ? "" : container.getParams().toString());
    }
    //前置环绕
    public void beforeAround(AcriContainer container) {
        log.info("beforeAround => {}", container.getParams() == null ? "" : container.getParams().toString());
    }
    //后置环绕
    public void afterAround(AcriContainer container) {
        log.info("afterAround => {}", container.getResult() == null ? "" : container.getResult().toString());
        log.info("afterAround => {}", container.getParams() == null ? "" : container.getParams().toString());
    }
    //异常通知
    public void throwing(AcriContainer container) {
        log.info("throwing => {}", container.getException() == null ? "" : container.getException().toString());
        log.info("throwing => {}", container.getParams() == null ? "" : container.getParams().toString());
    }
}

```

# 🥝🥝参与贡献
- 进群讨论
- [提issue](https://gitee.com/FnTop/acri/issues) 先搜索再提，看是否搜到相关的issue（问题）
- Fork 本项目
- 新建分支，如果是加新特性，分支名格式为feat_${issue的ID号}，如果是修改bug，则命名为fix_${issue的ID号}。
- 本地自测，提交前请通过所有的单元测试，以及为您要解决的问题新增单元测试。
- 提交代码
- 新建 Pull Request
- 我会对您的PR进行验证和测试，如通过测试，我会合到dev分支上随新版本发布时再合到master分支上。

欢迎小伙伴们多提issue和PR，被接纳PR的小伙伴会列在贡献者列表中

# 🥝🥝联系方式

## 微信群
![img.png](static/img/wx.png)
## QQ群
`697135336`
## 微信
`gensui_`


