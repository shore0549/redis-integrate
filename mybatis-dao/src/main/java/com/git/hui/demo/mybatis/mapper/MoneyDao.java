package com.git.hui.demo.mybatis.mapper;

import com.git.hui.demo.mybatis.entity.MoneyEntity;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yihui in 20:08 18/5/12.
 */
public interface MoneyDao {


    MoneyEntity queryMoney(@Param("id") int userId);


    // 转账
    int incrementMoney(@Param("id") int userId, @Param("addMoney") int addMoney);


}
