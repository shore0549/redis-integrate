package com.git.hui.demo.base.bean.test.timeline;

import com.git.hui.demo.base.bean.timeline.ITimelineElement;
import com.git.hui.demo.base.bean.timeline.TimelineWindow;
import lombok.Data;
import org.junit.Test;

/**
 * Created by yihui in 10:22 18/6/11.
 */
public class TimeLineWindowTest {

    @Data
    static class InnerClz implements ITimelineElement {

        private long time;
        private int item;

        public InnerClz(int item) {
            this.item = item;
            time = System.currentTimeMillis();
        }

        @Override
        public long getTimeStamp() {
            return time;
        }
    }

    @Test
    public void testTimeWindow() throws InterruptedException {
        long period = 60 * 100; // 一分钟的时间窗口
        int rate = 10 * 100; // 10s 干掉一次的频率

        TimelineWindow<InnerClz> timelineWindow = new TimelineWindow<>(period, rate);
        for (int i = 0; i < 15; i++) {
            Thread.sleep(3 * 100);
            timelineWindow.appendElement(new InnerClz(i));

            System.out.println("line " + i + " : " + timelineWindow.getElements().toString());
        }

        System.out.println(timelineWindow.getElements().toString());
        Thread.sleep(period + 1000 * 60 * 10);
        System.out.println(timelineWindow.getElements().toString());
    }

}
