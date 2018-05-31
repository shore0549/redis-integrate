package com.git.hui.demo.base.bean.beanconfig.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui in 18:56 18/5/31.
 */
@Component
public class AService {
    private static final AtomicInteger count = new AtomicInteger(1);

    public AService() {
        System.out.println(count.getAndAdd(1));
    }
}
