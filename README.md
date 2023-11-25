<div align="center">
<img align="center" src="" alt="Fn">
<p>Acri 注解式自定义拦截器</p>
</div>

<div align="center">

<div style="display: flex; justify-content: center;">  
    <img style="margin: 5px 3px" src="https://gitee.com/FnTop/Acri/badge/star.svg?theme=light" alt="Fn">
    <img style="margin: 5px 3px" src="https://gitee.com/FnTop/Acri/badge/fork.svg?theme=dark" alt="Fn">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/Version-1.0.0-green" alt="Fn">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/Apache-License2.0-green" alt="Apache2.0">

</div>

<div style="display: flex; justify-content: center;">  
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/Maven-3.5.2-blue" alt="Maven">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/Java-1.8-blue">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/SpringBoot-2.6.2-blue">
    <img style="margin: 5px 3px" src="https://img.shields.io/badge/Author-4n-blue.svg" alt="Fn">
</div>

</div>

# 介绍
Acri注解式自定义请求拦截（全称Annotation custom request interception）
使用一个简单的注解即可实现请求的拦截处理



# 特点



## 版本说明
| version | spring boot version | java version |
|:-------:|:-------------------:|:------------:|
|  1.0.0  |        2.7.15        |     1.8      |

## Maven
```xml
<dependency>
    <groupId>cn.fntop</groupId>
    <artifactId>acri-core</artifactId>
    <version>1.0.0</version>
</dependency>
```
## Gradle
``` 
//方式1
implementation 'cn.fntop:acri-core:1.0.0'
//方式2
implementation group: 'cn.fntop', name: 'acri-corer', version: '1.0.0'
```

## 注解说明
@Acri

| 配置项    | 默认值   | 备注                                 |
|:-------|:------|:-----------------------------------|
| value  | null  | 具体拦截处理器，实现AcriProcessor并注入Spring容器 |
| before | false | 是否处理请求前执行doBefore方法                |
| during | false | 是否处理请求方法后执行doDuring方法              |
| after  | false | 是否在返回响应后执行doAfter方法                |



```
# 群聊
`QQ群：697135336`
`微信：gensui_`





