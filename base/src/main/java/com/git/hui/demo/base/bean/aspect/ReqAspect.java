package com.git.hui.demo.base.bean.aspect;

import org.aspectj.lang.JoinPoint;

/**
 * Created by yihui in 16:53 18/5/11.
 */
public class ReqAspect {

    public void doBefore(JoinPoint point) {
        System.out.println("req before");
    }

    public void doAfter(JoinPoint point) {
        System.out.println("req after");
    }

}
