package com.git.hui.task;

import com.git.hui.task.api.ITask;
import com.git.hui.task.container.TaskContainer;
import com.git.hui.task.task.ScriptTaskDecorate;
import com.git.hui.task.util.FileUtils;
import com.git.hui.task.util.ScriptLoadUtil;
import com.git.hui.task.watch.TaskChangeWatcher;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by @author yihui in 18:08 18/6/28.
 */
@Slf4j
public class ScriptExecuteEngine {
    private static final String SCRIPT_TYPE = ".groovy";
    private static final String SOURCE_PATH =
            "/Users/user/Project/GitHub/study-demo/task-engine/src/test/java/com/git/hui/task";

    public void run(String source) {
        List<File> scripts = FileUtils.loadFiles(source, new Predicate<File>() {
            @Override
            public boolean test(File file) {
                return !file.getName().endsWith(SCRIPT_TYPE);
            }
        });

        ITask task;
        ScriptTaskDecorate scriptTask;
        for (File f : scripts) {
            task = ScriptLoadUtil.loadScript(f);
            if (task == null) {
                continue;
            }

            scriptTask = new ScriptTaskDecorate(task);
            TaskContainer.registerTask(f.getAbsolutePath(), scriptTask);
        }

        try {
            TaskChangeWatcher.registerWatcher(new File(source));
        } catch (Exception e) {
            log.error("register task change watcher error! e:{}", e);
            System.exit(1);
        }
    }


    private static volatile boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        new ScriptExecuteEngine().run(SOURCE_PATH);
        registerHook();
        while (run) {
            Thread.sleep(1000 * 10 * 10);
        }
        log.info("application over!!!!");
    }


    /**
     * 注册一个程序关闭的钩子, 用于回收现场
     */
    private static void registerHook() {
        Runtime.getRuntime().addShutdownHook(new Thread("shutdown-thread") {
            @Override
            public void run() {
                log.info("closing Application......");
                run = false;
                log.info("closing over........");
            }
        });
    }
}
