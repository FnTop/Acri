package cn.fntop.core.interceptor;


import cn.fntop.core.annotation.Acri;
import cn.fntop.core.annotation.Acries;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author fn
 * @description
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
            Acries acries = method.getAnnotation(Acries.class);
            if (acries != null && acries.acries().length > 0) {
                Arrays.stream(acries.acries()).forEach(acri -> {
                    if (acri != null && acri.value() != null && acri.before()) {
                        context.getBean(acri.value()).doBefore(request, response, handler);
                    }
                });
            } else {
                Acri acri = method.getAnnotation(Acri.class);
                if (acri != null && acri.value() != null && acri.before()) {
                    context.getBean(acri.value()).doBefore(request, response, handler);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            Acries acries = method.getAnnotation(Acries.class);
            if (acries != null && acries.acries().length > 0) {
                Arrays.stream(acries.acries()).forEach(acri -> {
                    if (acri != null && acri.value() != null && acri.during()) {
                        context.getBean(acri.value()).doDuring(request, response, handler, modelAndView);
                    }
                });
            } else {
                Acri acri = method.getAnnotation(Acri.class);
                if (acri != null && acri.value() != null && acri.during()) {
                    context.getBean(acri.value()).doDuring(request, response, handler, modelAndView);
                }
            }
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            Acries acries = method.getAnnotation(Acries.class);
            if (acries != null && acries.acries().length > 0) {
                Arrays.stream(acries.acries()).forEach(acri -> {
                    if (acri != null && acri.value() != null && acri.after()) {
                        context.getBean(acri.value()).doAfter(request, response, handler, ex);
                    }
                });
            } else {
                Acri acri = method.getAnnotation(Acri.class);
                if (acri != null && acri.value() != null && acri.after()) {
                    context.getBean(acri.value()).doAfter(request, response, handler, ex);
                }
            }
        }
    }
}
