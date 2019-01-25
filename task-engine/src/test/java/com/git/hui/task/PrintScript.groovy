package com.git.hui.task

import com.git.hui.task.api.ITask

/**
 * Created by @author yihui in 14:56 18/7/2.
 */
class PrintScript implements ITask {
    @Override
    void run() {
        println "print script run"
    }

    @Override
    void interrupt() {
        println "print script over!"
    }
}
