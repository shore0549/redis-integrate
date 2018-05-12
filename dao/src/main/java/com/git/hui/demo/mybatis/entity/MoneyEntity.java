package com.git.hui.demo.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yihui in 20:04 18/5/12.
 */
@Data
public class MoneyEntity implements Serializable {

    private static final long serialVersionUID = -7074788842783160025L;

    private int id;

    private String name;

    private int money;

    private int isDeleted;

    private int created;

    private int updated;
}
