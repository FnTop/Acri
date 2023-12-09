package cn.fntop.core.annotation;

import java.lang.annotation.*;

/**
 * 注解自定义请求拦截[多个]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Acries {
    Acri[] acries();
}