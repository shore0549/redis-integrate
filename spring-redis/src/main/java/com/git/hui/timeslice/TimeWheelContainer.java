package com.git.hui.timeslice;

import lombok.Data;

import java.util.List;

/**
 * Created by @author yihui in 15:21 18/6/21.
 */
@Data
public class TimeWheelContainer {
    private TimeWheelCalculate calculate;

    /**
     * 历史时间片计数，每个时间片对应其中的一个元素
     */
    private int[] counts;

    /**
     * 实时的时间片计数
     */
    private int realTimeCount;

    /**
     * 整个时间轮计数
     */
    private int timeWheelCount;

    private Long lastInsertTime;


    public TimeWheelContainer(TimeWheelCalculate calculate) {
        this.counts = new int[calculate.getCellNum()];
        this.calculate = calculate;
        this.realTimeCount = 0;
        this.timeWheelCount = 0;
        this.lastInsertTime = null;
    }

    public void add(long now, int amount) {
        if (lastInsertTime == null) {
            realTimeCount = amount;
            lastInsertTime = now;
            return;
        }


        List<Integer> removeIndex = calculate.getExpireIndexes(lastInsertTime, now);
        if (removeIndex == null) {
            // 两者时间间隔超过一轮，则清空计数
            realTimeCount = amount;
            lastInsertTime = now;
            timeWheelCount = 0;
            clear();
            return;
        }

        if (removeIndex.isEmpty()) {
            // 没有跨过时间片，则只更新实时计数
            realTimeCount += amount;
            lastInsertTime = now;
            return;
        }

        // 跨过了时间片，则需要在总数中删除过期的数据，并追加新的数据
        for (int index : removeIndex) {
            timeWheelCount -= counts[index];
            counts[index] = 0;
        }
        timeWheelCount += realTimeCount;
        counts[calculate.calculateIndex(lastInsertTime)] = realTimeCount;
        lastInsertTime = now;
        realTimeCount = amount;
    }

    private void clear() {
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
    }
}
