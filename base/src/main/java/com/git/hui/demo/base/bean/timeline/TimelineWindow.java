package com.git.hui.demo.base.bean.timeline;

import java.util.List;

/**
 * 时间窗口，内部只保存最近多长时间的内容，超过这个时间的数据，会丢掉
 *
 *  - 内部持有记录列表，获取当前时间窗口下的所有记录
 *  - 添加记录
 *  - 删除过期记录
 *
 *  注意点：
 *
 *  - 如何删除过期的记录
 *  - 如何确保并发安全
 *
 * Created by yihui in 17:12 18/5/31.
 */
public class TimelineWindow<T extends ITimelineElement> {

    private T oldestElement;

    private List<T> elementContainer;



}
