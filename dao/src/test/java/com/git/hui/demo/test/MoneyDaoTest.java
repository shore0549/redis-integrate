package com.git.hui.demo.test;

import com.git.hui.demo.mybatis.entity.MoneyEntity;
import com.git.hui.demo.mybatis.entity.PoetryEntity;
import com.git.hui.demo.mybatis.mapper.MoneyDao;
import com.git.hui.demo.mybatis.mapper.PoetryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yihui on 2018/4/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/*.xml")
public class MoneyDaoTest {

    @Autowired
    private MoneyDao moneyDao;


    @Test
    public void testQuery() {
        MoneyEntity entity = moneyDao.queryMoney(1);
        System.out.println("ans: " + entity);
    }

}
