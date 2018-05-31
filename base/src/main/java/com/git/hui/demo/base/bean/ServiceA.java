package com.git.hui.demo.base.bean;

import org.springframework.stereotype.Service;

/**
 * Created by yihui in 16:46 18/5/9.
 */
@Service
public class ServiceA {

    private String msg;

    public ServiceA(String msg) {
        this.msg = msg;
    }

    public void print() {
        System.out.println("hello " + msg);
    }
}
