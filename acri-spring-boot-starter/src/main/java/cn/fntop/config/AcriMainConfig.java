package cn.fntop.config;

import cn.fntop.core.aspect.AcriesAspect;
import cn.fntop.core.interceptor.AcriIntercepter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fn
 * @description 拦截器配置
 * @date 2023/11/27 9:42
 */
@Configuration
public class AcriMainConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Acri自定义拦截器
        registry.addInterceptor(new AcriIntercepter(applicationContext));
    }

    /**
     * Acri切面配置
     * @return
     */
    @Bean
    public AcriesAspect acriesAspect() {
        return new AcriesAspect();
    }
}
