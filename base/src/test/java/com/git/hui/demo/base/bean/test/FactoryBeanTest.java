package com.git.hui.demo.base.bean.test;

import com.git.hui.demo.base.bean.ServiceA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui in 16:51 18/5/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:*.xml")
public class FactoryBeanTest {

    @Autowired
    private ServiceA serviceA;

    @Autowired
    private ApplicationContext applicationContext;


//    @Autowired
//    private ServiceB serviceB;

    @Test
    public void testPrint() {
        serviceA.print();

//        serviceB.print();
    }
}
