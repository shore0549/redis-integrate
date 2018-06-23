package com.git.hui.timeslice;

import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * Created by yihui in 14:56 18/6/21.
 */
public class CountTimeWindow {

    public static void main(String[] args) {
        TimeWheelContainer timeWheelContainer = new TimeWheelContainer(new TimeWheelCalculate(2, 20));

        timeWheelContainer.add(0, 1);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 0, "first");

        timeWheelContainer.add(1, 1);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 0, "first");

        timeWheelContainer.add(2, 1);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 2, "second");
        Assert.isTrue(timeWheelContainer.getCounts()[0] == 2, "second");

        for (int i = 3; i < 20; i++) {
            timeWheelContainer.add(i, 1);
            System.out.println("add index: " + i + " count: " + timeWheelContainer.getTimeWheelCount());
        }

        // 刚好一轮

        timeWheelContainer.add(20, 3);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 20, "third");
        timeWheelContainer.add(21, 3);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 20, "third");


        // 减去过期的那个数据
        timeWheelContainer.add(22, 3);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 26 - 2, "fourth");
        Assert.isTrue(timeWheelContainer.getCounts()[0] == 6, "fourth");


        timeWheelContainer.add(26, 3);
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 24 - 2 - 2 + 3, "fifth");
        System.out.println(Arrays.toString(timeWheelContainer.getCounts()));


        timeWheelContainer.add(43, 3);
        System.out.println(Arrays.toString(timeWheelContainer.getCounts()));
        Assert.isTrue(timeWheelContainer.getTimeWheelCount() == 6, "six");
    }
}
