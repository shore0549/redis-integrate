package com.git.hui.demo.base.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

/**
 * Created by yihui in 10:39 18/5/10.
 */
public class ServiceB implements BeanNameAware, BeanPostProcessor, InitializingBean {

    private ServiceA serviceA;

    public ServiceB() {
        System.out.println("搞糟方法");
    }

    public ServiceA getServiceA() {
        return serviceA;
    }

    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
        System.out.println("set serviceA!!!");
    }

    public void print() {
        System.out.println("service B Print: ");
        serviceA.print();
    }

    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before process");
        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("over process");
        return bean;
    }

    public void setBeanName(String s) {
        System.out.println("set bean name: " + s);
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("initializing bean! ");
    }
}
