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
    /**
     * 请求前处理
     * @param request
     * @param response
     * @param handler
     */
    void doBefore(HttpServletRequest request, HttpServletResponse response, Object handler);

    /**
     * 方法执行完后处理
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */

    void doDuring(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView);

    /**
     * 请求后处理
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    void doAfter(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
}
