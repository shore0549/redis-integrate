package com.git.hui.demo.base.bean.beanconfig.service;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by yihui in 18:58 18/5/31.
 */
public class AServiceFactoryBean implements FactoryBean<AService> {
    
    @Override
    public AService getObject() throws Exception {
        return new AService();
    }

    @Override
    public Class<?> getObjectType() {
        return AService.class;
    }
}
