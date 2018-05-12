package com.git.hui.demo.base.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by yihui in 16:46 18/5/9.
 */
public class ServiceAFactoryBean implements FactoryBean<ServiceA> {

    private String msg;

    public ServiceAFactoryBean(String msg) {
        this.msg = msg;
    }

    public ServiceA getObject() throws Exception {
        return new ServiceA(msg);
    }

    public Class<?> getObjectType() {
        return ServiceA.class;
    }
}
