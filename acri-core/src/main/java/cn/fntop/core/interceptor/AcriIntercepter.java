package cn.fntop.core.interceptor;


import cn.fntop.core.annotation.Acri;
import cn.fntop.core.annotation.Acries;
import cn.fntop.core.processor.AcriProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author fn
 * @description Acri拦截器
 * @date 2023/11/26 0:23
 */
public class AcriIntercepter implements HandlerInterceptor {
    private ApplicationContext context;

    public AcriIntercepter(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            Class<?> clz = ((HandlerMethod) handler).getBeanType();
            //getDeclaredAnnotation 与getDeclaredAnnotation 的区别 ：getDeclaredAnnotation允许获取继承类的注解
            Acries clzAcries = clz.getDeclaredAnnotation(Acries.class);
            Acri clzAcri = clz.getDeclaredAnnotation(Acri.class);
            Acries acries = method.getDeclaredAnnotation(Acries.class);
            Acri acri = method.getDeclaredAnnotation(Acri.class);
            //后面添加覆盖前面添加的，方法中 > 类 && @Acries > @Acri
            Set<Class<? extends AcriProcessor>> beans = new LinkedHashSet<>();
            cacheAcriProcessor(clzAcries, clzAcri, beans, 0); //处理类的
            cacheAcriProcessor(acries, acri, beans, 0);//处理方法的
            beans.forEach(b -> context.getBean(b).doBefore(request, response, handler));
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            Class<?> clz = ((HandlerMethod) handler).getBeanType();
            //getDeclaredAnnotation 与getDeclaredAnnotation 的区别 ：getDeclaredAnnotation允许获取继承类的注解
            Acries clzAcries = clz.getDeclaredAnnotation(Acries.class);
            Acri clzAcri = clz.getDeclaredAnnotation(Acri.class);
            Acries acries = method.getDeclaredAnnotation(Acries.class);
            Acri acri = method.getDeclaredAnnotation(Acri.class);
            //后面添加覆盖前面添加的，方法中 > 类 && @Acries > @Acri
            Set<Class<? extends AcriProcessor>> beans = new LinkedHashSet<>();
            cacheAcriProcessor(clzAcries, clzAcri, beans, 1); //处理类的
            cacheAcriProcessor(acries, acri, beans, 1);//处理方法的
            beans.forEach(b -> context.getBean(b).doDuring(request, response, handler, modelAndView));
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            Class<?> clz = ((HandlerMethod) handler).getBeanType();
            //getDeclaredAnnotation 与getDeclaredAnnotation 的区别 ：getDeclaredAnnotation允许获取继承类的注解
            Acries clzAcries = clz.getDeclaredAnnotation(Acries.class);
            Acri clzAcri = clz.getDeclaredAnnotation(Acri.class);
            Acries acries = method.getDeclaredAnnotation(Acries.class);
            Acri acri = method.getDeclaredAnnotation(Acri.class);
            //后面添加覆盖前面添加的，方法中 > 类 && @Acries > @Acri
            Set<Class<? extends AcriProcessor>> beans = new LinkedHashSet<>();
            cacheAcriProcessor(clzAcries, clzAcri, beans, 2); //处理类的
            cacheAcriProcessor(acries, acri, beans, 2);//处理方法的
            beans.forEach(b -> context.getBean(b).doAfter(request, response, handler, ex));
        }
    }

    /**
     * 缓存需要处理的bean
     * @param acries
     * @param acri
     * @param beans
     * @param step
     */
    private void cacheAcriProcessor(Acries acries, Acri acri, Set<Class<? extends AcriProcessor>> beans, int step) {
        // 先处理@Acri,再处理@Acries
        addBean(beans, step, acri);
        if (acries != null && acries.acries() != null) {
            Arrays.stream(acries.acries()).forEach(a -> {
                addBean(beans, step, a);
            });
        }
    }

    /**
     * 根据具体流程进行缓存
     * @param beans
     * @param step
     * @param a
     */
    private void addBean(Set<Class<? extends AcriProcessor>> beans, int step, Acri a) {
        if (a != null && a.value() != null) {
            boolean enabled = false;
            switch (step) {
                case 0:
                    enabled = a.before();
                    break;
                case 1:
                    enabled = a.during();
                    break;
                case 2:
                    enabled = a.after();
                    break;
            }
            // 有重复的进行覆盖
            if (enabled) {
                //启用了，并且配置了处理类
                beans.add(a.value());
            } else {
                beans.remove(a.value());
            }
        }
    }
}
