package com.git.hui.demo.mybatis.repository.transaction;

import com.git.hui.demo.mybatis.entity.MoneyEntity;
import com.git.hui.demo.mybatis.mapper.MoneyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Repository;


/**
 * Created by yihui in 22:17 18/5/12.
 */
@Repository
public class XmlDemo3 {
    @Autowired
    private MoneyDao moneyDao;


    /**
     * 转账
     *
     * @param inUserId
     * @param outUserId
     * @param payMoney
     * @param status    0 表示正常转账， 1 表示内部抛出一个异常， 2 表示新开一个线程，修改inUserId的钱 +200， 3 表示新开一个线程，修改outUserId的钱 + 200
     */
    public void transfor(final int inUserId, final int outUserId, final int payMoney, final int status) {

        MoneyEntity entity = moneyDao.queryMoney(outUserId);
        if (entity.getMoney() > payMoney) { // 可以转账

            // 先减钱
            moneyDao.incrementMoney(outUserId, -payMoney);


            testCase(inUserId, outUserId, status);


            // 再加钱
            moneyDao.incrementMoney(inUserId, payMoney);
            System.out.println("转账完成! now: " + System.currentTimeMillis());
        }


    }


    private void testCase(final int inUserId, final int outUserId, final int status) {
        if (status == 1) {
            throw new IllegalArgumentException("转账异常!!!");
        } else if (status == 2) {
            addMoney(inUserId);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (status == 3) {
            addMoney(outUserId);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void addMoney(final int userId) {
        System.out.println("内部加钱: " + System.currentTimeMillis());
        new Thread(new Runnable() {
            public void run() {
                moneyDao.incrementMoney(userId, 200);
                System.out.println("sub modify success! now: " + System.currentTimeMillis());
            }
        }).start();
    }
}
