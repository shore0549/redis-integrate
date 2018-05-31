package com.git.hui.demo.mybatis.repository;

import com.git.hui.demo.mybatis.mapper.MoneyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by yihui in 20:17 18/5/12.
 */
@Repository
public class MoenyRepository {

    @Autowired
    private MoneyDao moneyDao;


    // 转账，三步走
    // 1. 查询fromUserId 余额是否足够
    // 2. 扣钱
    // 3. 加钱
    public void transferAccounts(int fromUserId, int toUserId, int payMoney) {

    }
}
