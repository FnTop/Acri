package cn.fntop.core.aspect;

import cn.fntop.core.annotation.AcriAspect;
import cn.fntop.core.domain.AcriContainer;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Acri切面
 */
@Aspect
@Slf4j
public class AcriesAspect {
    // 定义一个切点，匹配所有带有@Acri注解的类的方法  
    @Pointcut("@annotation(cn.fntop.core.annotation.AcriAspect)")
    public void pointcut() {
    }

    /**
     * 前置通知，请求前
     *
     * @param joinPoint
     * @throws Throwable
     */
    // 在方法执行之前进行处理，并调用后备方法（通过Main类）
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] params = joinPoint.getArgs();
        // 获取方法上的@Acri注解  getDeclaredAnnotation 与getDeclaredAnnotation 的区别 ：getDeclaredAnnotation允许获取继承类的注解
        AcriAspect acri = method.getDeclaredAnnotation(AcriAspect.class);
        if (acri != null && acri.before()) {
            log.info("[AcriAspect:前置通知开启]");
            //处理调用
            process(acri, acri.fallback(), acri.beforeMethod(), CollUtil.newArrayList(params), null, null);
        }
    }

    /**
     * 后置通知：方法正常执行后，有返回值，执行该后置通知：如果该方法执行出现异常，则不执行该后置通知
     *
     * @param joinPoint
     * @param returnVal
     */
    @AfterReturning(value = "pointcut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] params = joinPoint.getArgs();
        // 获取方法上的@Acri注解
        AcriAspect acri = method.getDeclaredAnnotation(AcriAspect.class);
        if (acri != null && acri.after()) {
            log.info("[AcriAspect:后置通知开启]");
            //处理调用
            process(acri, acri.fallback(), acri.afterMethod(), CollUtil.newArrayList(params), returnVal, null);
        }
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] params = joinPoint.getArgs();
        // 获取方法上的@Acri注解
        AcriAspect acri = method.getDeclaredAnnotation(AcriAspect.class);
        List<Object> paramList = CollUtil.newArrayList(params);
        if (acri != null) {
            //处理调用
            if (acri.around()) {
                log.info("[AcriAspect:环绕通知开启：前置环绕]");
            }
            Object returnVal = joinPoint.proceed();
            //处理调用
            if (acri.around()) {
                log.info("[AcriAspect:环绕通知开启：后置环绕]");
                process(acri, acri.fallback(), acri.afterAroundMethod(), paramList, returnVal, null);
            }
            return returnVal;
        }
        return null;
    }

    /**
     * 异常通知：方法出现异常时，执行该通知
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "pointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] params = joinPoint.getArgs();
        // 获取方法上的@Acri注解
        AcriAspect acri = method.getDeclaredAnnotation(AcriAspect.class);
        if (acri != null && acri.throwing()) {
            log.info("[AcriAspect:异常通知开启]");
            //处理调用
            process(acri, acri.fallback(), acri.throwingMethod(), CollUtil.newArrayList(params), null, ex);
        }
    }

    /**
     * 核心逻辑
     *
     * @param acri          方法上的注解
     * @param fallbackClass 注解中的回调类
     * @param params        注解方法的参数列表值
     * @param returnVal     注解方法的返回值
     * @param ex            注解方法的异常值
     * @return
     */
    private void process(AcriAspect acri, Class<?> fallbackClass, String fallbackMethod, List<Object> params, Object returnVal, Exception ex) {
        // 获取后备方法
        Method callMethod = null;
        //用于处理后置通知的方法参数
        AcriContainer acriContainer = new AcriContainer();

        acriContainer.setException(ex);
        acriContainer.setResult(returnVal);
        acriContainer.setParams(params);
        try {
            callMethod = fallbackClass.getDeclaredMethod(fallbackMethod, AcriContainer.class);
        } catch (NoSuchMethodException e) {
            List<String> notice = new ArrayList<>();
            if (acri.before()) {
                notice.add("before");
            }
            if (acri.after()) {
                notice.add("after");
            }
            if (acri.around()) {
                notice.add("around");
            }
            if (acri.throwing()) {
                notice.add("throwing");
            }
            log.error("\n[AcriesAspect =》 注解方法{}在类{}中不存在或参数不对应]" + "\n当前开启的通知=》{}" + "\n前置通知（before）=》通知方法需要方法参数" + "\n后置通知（after）=》通知方法需要方法参数+返回值参数" + "\n异常通知（throwing）=》通知方法需要异常参数" + "\n环绕通知（around）=》前置通知方法需要方法参数，后置通知方法需要方法参数和返回值参数", fallbackMethod, fallbackClass.getName(), StringUtils.join(notice, ","));
            return;
        }
        // 判断方法是否为静态方法
        boolean isStatic = Modifier.isStatic(callMethod.getModifiers());
        if (isStatic) {
            // 方法是静态方法，传入了null作为实例对象
            try {
                callMethod.invoke(null, acriContainer);
            } catch (IllegalAccessException e) {
                log.error("[AcriesAspect:IllegalAccessException] 参数无法访问");
            } catch (InvocationTargetException e) {
                log.error("[AcriesAspect:InvocationTargetException] 调用失败");
            }
        } else {
            Object instance = null;
            try {
                instance = fallbackClass.newInstance();
            } catch (InstantiationException e) {
                log.error("[AcriesAspect:InstantiationException] 实例化异常");
                return;
            } catch (IllegalAccessException e) {
                log.error("[AcriesAspect:IllegalAccessException] 类无法访问");
                return;
            }
            try {
                callMethod.invoke(instance, acriContainer);
            } catch (IllegalAccessException e) {
                log.error("[AcriesAspect:IllegalAccessException] 方法无法访问");
            } catch (InvocationTargetException e) {
                log.error("[AcriesAspect:InvocationTargetException] 调用失败");
            } catch (IllegalArgumentException e) {
                log.error("[AcriesAspect:IllegalArgumentException] 参数类型不匹配");
            }
        }
    }
}