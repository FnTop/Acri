package cn.fntop.core.annotation;

import java.lang.annotation.*;

/**
 * 注解自定义请求拦截[多个]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AcriAspect {
    /**
     * 是否启用前置通知
     */
    boolean before() default false;

    /**
     * 是否启用环绕通知
     */
    boolean around() default false;

    /**
     * 是否启用后置通知
     */
    boolean after() default false;

    /**
     * 是否启用异常通知
     */
    boolean throwing() default false;

    /**
     * 启用前置通知调用的默认方法名称
     */
    String beforeMethod() default "before";

    /**
     * 启用环绕通知（前置环绕）调用的默认方法名称
     */
    String beforeAroundMethod() default "beforeAround";

    /**
     * 启用环绕通知（后置环绕）调用的默认方法名称
     */
    String afterAroundMethod() default "afterAround";

    /**
     * 启用后置通知调用的默认方法名称
     */
    String afterMethod() default "after";

    /**
     * 启用异常通知调用的默认方法名称
     */
    String throwingMethod() default "throwing";

    /**
     * 存放调用方法名的类
     */
    Class<?> fallback();
}