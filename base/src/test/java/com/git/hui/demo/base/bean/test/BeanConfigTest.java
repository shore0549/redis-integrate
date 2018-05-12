package com.git.hui.demo.base.bean.test;

import com.git.hui.demo.base.bean.BeansConfiguration;
import com.git.hui.demo.base.bean.ServiceA;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by yihui in 09:22 18/5/10.
 */
public class BeanConfigTest {


    // java bean 配置方式
    @Test
    public void testServiceA() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeansConfiguration.class);
        ServiceA serviceA = (ServiceA) context.getBean("serviceA");
        serviceA.print();
    }

}
