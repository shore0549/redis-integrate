package com.git.hui.demo.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yihui on 2018/4/4.
 */
@Data
public class PoetryEntity implements Serializable {
    private static final long serialVersionUID = 4888857290009801223L;

    private Long id;

    /**
     * 作者名
     */
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 解释
     */
    private String explain;

    /**
     * 诗词的类型 0 成语，1 唐前诗词
     */
    private Integer type;


    /**
     * 标记，对应诗词的朝代
     */
    private Integer tag;


    /**
     * 诗词的题材，七言，五言等
     */
    private String theme;


    private Integer isDeleted;

    private Integer created;

    private Integer updated;

}
