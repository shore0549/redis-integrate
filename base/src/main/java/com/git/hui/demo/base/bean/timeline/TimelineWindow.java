package com.git.hui.demo.base.bean.timeline;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 时间窗口，内部只保存最近多长时间的内容，超过这个时间的数据，会丢掉
 *
 * - 内部持有记录列表，获取当前时间窗口下的所有记录
 * - 添加记录
 * - 删除过期记录
 *
 * 注意点：
 *
 * - 如何删除过期的记录
 * - 如何确保并发安全
 *
 * Created by yihui in 17:12 18/5/31.
 */
public class TimelineWindow<T extends ITimelineElement> {

    private LinkedList<ElementList.Node<T>> removeList;

    private ElementList<T> elementContainer;

    // 时间窗口的长度
    private long period;
    // 时间窗口刷新的频率
    private int rate;

    // 时间窗口启动的时间
    private long runTime;

    public TimelineWindow(long period, int rate) {
        this.period = period;
        this.rate = rate;

        elementContainer = new ElementList<>();
        removeList = new LinkedList<>();
        runTime = 0;
    }

    private void checkAndSetRunTime() {
        if (runTime == 0) { // 首次启动时，刷新runTime
            runTime = System.currentTimeMillis();

            registerScheduleClearTask();
        }
    }

    private boolean inSamePeriod(long origin, long now) {
        return (origin - runTime) / rate == (now - runTime) / rate;
    }

    public ElementList<T> getElements() {
        return elementContainer;
    }

    public void appendElement(T element) {
        checkAndSetRunTime();

        ElementList.Node<T> enode = elementContainer.add(element);

        if (removeList.isEmpty()) {
            removeList.add(enode);
            return;
        }

        long now = System.currentTimeMillis();
        ElementList.Node<T> latest = removeList.getLast();
        if (latest != null && latest.getItem() != null && inSamePeriod(latest.getItem().getTimeStamp(), now)) {
            removeList.removeLast();
        }
        removeList.add(enode);
    }


    public void expireElement() {
        if (removeList.isEmpty()) {
            return;
        }

        System.out.println("--- auto remove ---" + removeList);

        long now = System.currentTimeMillis();
        long expectRemoveTime = now - period;

        ElementList.Node<T> expect = null;
        for (ElementList.Node<T> node : removeList) {
            if (node.getItem().getTimeStamp() < expectRemoveTime) {
                expect = node;
                removeList.removeFirst();
                continue;
            }
            break;
        }


        if (expect == null) {
            // 整个都删除
            elementContainer.remove(removeList.getLast());
        } else {
            elementContainer.removeBefore(expect);
        }

        System.out.println("--- auto remove ---" + expect);
    }

    private void registerScheduleClearTask() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(this::expireElement, rate, rate, TimeUnit.MILLISECONDS);
    }
}
