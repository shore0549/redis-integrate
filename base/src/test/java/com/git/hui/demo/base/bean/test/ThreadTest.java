package com.git.hui.demo.base.bean.test;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yihui in 15:29 18/5/15.
 */
public class ThreadTest {

    public Lock lock = new ReentrantLock();

    private Map<String, String> map = new HashMap<>();


    public String get(String key) {
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    public void set(String key, String value) {
        lock.lock();
        try {
            map.put(key, value);
        } finally {
            lock.unlock();
        }
    }


    public void synch(int status) {
        if (status != 1) { // 确保抛异常的类先进入同步代码块
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " in !");
            if (status == 1) {
                throw new IllegalArgumentException("异常");
            } else {
                System.out.println("继续执行");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final ThreadTest test = new ThreadTest();


        // 如果同步代码块内发生异常，会自动释放锁么？
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                test.synch(1);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                test.synch(2);
            }
        });

        t1.start();
        Thread.sleep(10);
        System.out.println(t1.getState());

        t2.start();
    }
}
