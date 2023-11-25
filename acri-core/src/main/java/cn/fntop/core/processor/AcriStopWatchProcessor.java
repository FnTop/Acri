package cn.fntop.core.processor;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author fn
 * @description 请求耗时统计拦截处理器
 * @date 2023/11/26 1:20
 */
@Slf4j
@Component
public class AcriStopWatchProcessor implements AcriProcessor {
    private ApplicationContext context;


    private final String prodProfile = "prod";

    public AcriStopWatchProcessor(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    private final TransmittableThreadLocal<StopWatch> STOP_WATCH = new TransmittableThreadLocal<>();

    @Override
    public void doBefore(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //非生产环境打印
        if (!isProduction()) {
//            String url = request.getMethod() + " " + request.getRequestURI();
            StopWatch stopWatch = new StopWatch();
            STOP_WATCH.set(stopWatch);
            stopWatch.start();
        }
    }

    @Override
    public void doDuring(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void doAfter(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!isProduction()) {
            StopWatch stopWatch = STOP_WATCH.get();
            stopWatch.stop();
            log.info("[Acri]结束请求 => URL[{}],耗时:[{}]毫秒", request.getMethod() + " " + request.getRequestURI(), stopWatch.getTime());
            STOP_WATCH.remove();
        }
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }

    private boolean isProduction() {
        if (getActiveProfile() == null || Arrays.stream(getActiveProfile()).noneMatch(prodProfile::equals)) {
            return false;
        }
        return true;
    }

    private String[] getActiveProfile() {
        return context == null ? null : context.getEnvironment().getActiveProfiles();
    }
}
