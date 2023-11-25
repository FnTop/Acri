package cn.fntop.core.processor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fn
 * @description 拦截处理抽象接口
 * @date 2023/11/26 1:01
 */
public interface AcriProcessor {
    void doBefore(HttpServletRequest request, HttpServletResponse response, Object handler);

    void doDuring(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView);

    void doAfter(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
}
