package com.git.hui.rabbit.spring;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yihui in 17:51 18/5/31.
 */
@Component
public class TestBean {
    private static AtomicInteger count = new AtomicInteger(1);

    public TestBean() {
        System.out.println("testBean count: " + count.getAndAdd(1));
    }
}
