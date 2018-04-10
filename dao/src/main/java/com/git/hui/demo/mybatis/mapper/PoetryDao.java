package com.git.hui.demo.mybatis.mapper;

import com.git.hui.demo.mybatis.entity.PoetryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yihui on 2018/4/4.
 */
public interface PoetryDao {

    PoetryEntity queryPoetryById(@Param("id") Long id);


    List<PoetryEntity> queryPoetryByIds(@Param("ids") List<Long> ids);


    List<PoetryEntity> queryPoetryByAuthor(@Param("author") String author,
                                           @Param("offset") Long offset,
                                           @Param("begin") Integer start,
                                           @Param("size") Integer size);



    /**
     * 非精确查询
     * @return
     */
    List<PoetryEntity> queryPoetryByContent(@Param("content") String content,
                                            @Param("offset") Long offset,
                                            @Param("begin") Integer start,
                                            @Param("size") Integer size);


}
