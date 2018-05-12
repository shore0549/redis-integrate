package com.git.hui.demo.base.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yihui in 09:21 18/5/10.
 */
@Configuration
public class BeansConfiguration {

    @Bean
    public ServiceA serviceA() {
        return new ServiceA("test");
    }

}
