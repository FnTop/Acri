package cn.fntop.core.annotation;

import cn.fntop.core.processor.AcriProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解自定义请求拦截
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Acri {

    Class<? extends AcriProcessor> value();
    /**
     * 请求前
     */
    boolean before() default false;
    /**
     * 请求时
     */
    boolean during() default false;
    /**
     * 请求后
     */
    boolean after() default false;
}