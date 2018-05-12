package com.git.hui.demo.base.bean.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by yihui in 15:43 18/5/11.
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.git.hui.demo.base.bean.*.*(..))")
    private void pcut() {

    }

    @Before("execution(public * com.git.hui.demo.base.bean.*.*(..))")
    public void proccessBefore(JoinPoint joinPoint) {
        System.out.println("do before!!!");
    }


    @After("pcut()")
    public void processAfter(JoinPoint joinPoint) {
        System.out.println("do after");
    }
}
