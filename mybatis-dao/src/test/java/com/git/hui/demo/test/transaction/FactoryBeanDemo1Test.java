package com.git.hui.demo.test.transaction;

import com.git.hui.demo.mybatis.mapper.MoneyDao;
import com.git.hui.demo.mybatis.repository.transaction.FactoryBeanDemo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yihui in 20:32 18/5/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/service.xml", "classpath*:test-datasource2.xml"})
public class FactoryBeanDemo1Test {

    @Resource(name = "factoryBeanDemoProxy")
    private FactoryBeanDemo2 factoryBeanDemo2;

    @Autowired
    private MoneyDao moneyDao;


    @Test
    public void testTransfor() {

        System.out.println("---------before----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());


        factoryBeanDemo2.transfor(1, 2, 10, 0);

        System.out.println("---------after----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());
    }


    // 内部抛异常的情况
    @Test
    public void testTransforException() {

        System.out.println("---------before----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());


        try {
            factoryBeanDemo2.transfor(1, 2, 10, 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        System.out.println("---------after----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());
    }



    // 内部修改的情况
    @Test
    public void testTransforModify() {

        System.out.println("---------before----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());


        try {
            factoryBeanDemo2.transfor(1, 2, 10, 2);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("---------after----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());
    }


    // 内部修改的情况
    @Test
    public void testTransforModify2() {

        System.out.println("---------before----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());


        try {
            factoryBeanDemo2.transfor(1, 2, 10, 3);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("---------after----------");
        System.out.println("id: 1 money = " + moneyDao.queryMoney(1).getMoney());
        System.out.println("id: 2 money = " + moneyDao.queryMoney(2).getMoney());
    }
}
