package com.git.hui.demo.base.bean.beanconfig;

import com.git.hui.demo.base.bean.beanconfig.service.AService;
import com.git.hui.demo.base.bean.beanconfig.service.AServiceFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yihui in 18:56 18/5/31.
 */
@Configuration
@ComponentScan("com.git.hui.demo.base.bean.beanconfig")
public class BeanSpringConfig {

    @Bean(name = "aService2")
    public AService aService() {
        return new AService();
    }

    // 创建了一个AService bean，默认的bean名为 factoryAService
    @Bean
    public AServiceFactoryBean factoryAService() {
        return new AServiceFactoryBean();
    }

    @Bean(name = "aService3")
    public AService aService3(AServiceFactoryBean aServiceFactoryBean) throws Exception {
        return aServiceFactoryBean.getObject();
    }

}
