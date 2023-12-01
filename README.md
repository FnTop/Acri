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

implementation 'cn.fntop:acri-core:1.3.0'
//方式2
implementation group: 'cn.fntop', name: 'acri-corer', version: '1.3.0'
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

# 🥝🥝联系方式

`QQ群：697135336`
`微信：gensui_`






