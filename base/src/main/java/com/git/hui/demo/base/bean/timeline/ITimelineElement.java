package com.git.hui.demo.base.bean.timeline;

/**
 * 每个记录要求实现这个接口，用于返回当前记录绑定的时间戳，毫秒为单位
 * Created by yihui in 17:15 18/5/31.
 */
public interface ITimelineElement {

    long getTimeStamp();

}
