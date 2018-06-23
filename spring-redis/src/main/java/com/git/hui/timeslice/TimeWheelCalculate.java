package com.git.hui.timeslice;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 时间轮计算器
 *
 * Created by yihui in 15:00 18/6/21.
 */
@Data
public class TimeWheelCalculate {
    private static final long START = 0;

    private int period;
    private int length;

    /**
     * 划分的时间片个数
     */
    private int cellNum;

    private void check() {
        if (length % period != 0) {
            throw new IllegalArgumentException(
                    "length % period should be zero but not! now length: " + length + " period: " + period);
        }
    }

    public TimeWheelCalculate(int period, int length) {
        this.period = period;
        this.length = length;

        check();

        this.cellNum = length / period;
    }

    public int calculateIndex(long time) {
        return (int) ((time - START) % length / period);
    }

    /**
     * 获取所有过期的时间片索引
     *
     * @param lastInsertTime 上次更新时间轮的时间戳
     * @param nowInsertTime  本次更新时间轮的时间戳
     * @return
     */
    public List<Integer> getExpireIndexes(long lastInsertTime, long nowInsertTime) {
        if (nowInsertTime - lastInsertTime >= length) {
            // 已经过了一轮，过去的数据全部丢掉
            return null;
        }

        List<Integer> removeIndexList = new ArrayList<>();
        int lastIndex = calculateIndex(lastInsertTime);
        int nowIndex = calculateIndex(nowInsertTime);
        if (lastIndex == nowIndex) {
            // 还没有跨过这个时间片，则不需要删除过期数据
            return Collections.emptyList();
        } else if (lastIndex < nowIndex) {
            for (int tmp = lastIndex; tmp < nowIndex; tmp++) {
                removeIndexList.add(tmp);
            }
        } else {
            for (int tmp = lastIndex; tmp < cellNum; tmp++) {
                removeIndexList.add(tmp);
            }

            for (int tmp = 0; tmp < nowIndex; tmp++) {
                removeIndexList.add(tmp);
            }
        }
        return removeIndexList;
    }
}
