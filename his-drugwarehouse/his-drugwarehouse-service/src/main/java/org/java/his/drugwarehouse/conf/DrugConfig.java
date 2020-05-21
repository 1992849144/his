package org.java.his.drugwarehouse.conf;

import org.java.his.drugwarehouse.interceptor.DrugItercetpor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration//配置类
public class DrugConfig implements WebMvcConfigurer {

    //注入拦截器的类
    @Autowired
    private DrugItercetpor drugItercetpor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //hopping-cart微服务，不论什么请求，首先都会进入拦截器
        registry.addInterceptor(drugItercetpor)
                .addPathPatterns("/breakage/**")
                .addPathPatterns("/drugPricing/**");
    }
}
