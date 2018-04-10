package com.git.hui.demo.test;

import com.git.hui.demo.mybatis.entity.PoetryEntity;
import com.git.hui.demo.mybatis.mapper.PoetryDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yihui on 2018/4/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/*.xml")
public class PoetryDaoTest {

    @Autowired
    private PoetryDao poetryDao;


    @Test
    public void testGet() {
        PoetryEntity entity = poetryDao.queryPoetryById(3L);
        System.out.println("query result: {}" + entity);
    }

    @Test
    public void testBatchQuery() {
        List<Long> ids = Arrays.asList(100L, 200L, 300L);
        List<PoetryEntity> list = poetryDao.queryPoetryByIds(ids);
        System.out.println("query result: {}" + list);
    }


    @Test
    public void testQueryByAuthor() {
        String author = "杜甫";
        List<PoetryEntity> list = poetryDao.queryPoetryByAuthor(author, null, null, 10);
        System.out.println("query result: {}" + list);


        list = poetryDao.queryPoetryByAuthor(author, null, list.size(), 5);
        System.out.println("query result: {}" + list);
    }


    @Test
    public void testQueryByContent() {
        String content = "杏花";
        List<PoetryEntity> list = poetryDao.queryPoetryByContent(content, null, null, 3);
        System.out.println("query result: [}" + list);


        List<PoetryEntity> list2 = poetryDao.queryPoetryByContent(content, null, list.size(), 4);
        System.out.println("query result2: {}" + list2);


        List<PoetryEntity> list3 = poetryDao.queryPoetryByContent(content, list2.get(list2.size() - 1).getId(), 0, 4);
        System.out.println("query result3: {}" + list3);
    }
}
