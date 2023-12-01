package cn.fntop.config;

import cn.fntop.core.processor.AcriStopWatchProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fn
 * @description 特定拦截器注册
 * @date 2023/12/1 9:28
 */
@Configuration
public class AcirProcessorConfig{
    /**
     * 接口耗时统计拦截器
     * @param applicationContext
     * @return
     */
    @Bean
    public AcriStopWatchProcessor acriStopWatchProcessor(ApplicationContext applicationContext) {
        return new AcriStopWatchProcessor(applicationContext);
    }
}
