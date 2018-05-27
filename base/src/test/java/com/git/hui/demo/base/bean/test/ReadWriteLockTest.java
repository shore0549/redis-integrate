package com.git.hui.demo.base.bean.test;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by yihui in 10:19 18/5/15.
 */
public class ReadWriteLockTest {

    private int[] args = new int[]{1, 2, 3};
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public int get(int index) {
        lock.readLock().lock();
        try {
            System.out.println("in get: " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return args[index];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            System.out.println("out get: " + Thread.currentThread().getName());
            lock.readLock().unlock();
        }
    }

    public int set(int index, int val) {
        lock.writeLock().lock();
        try {
            System.out.println("in set: " + Thread.currentThread().getName());
            Thread.sleep(2000);
            int old = args[index];
            args[index] = val;
            return old;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            System.out.println("out set: " + Thread.currentThread().getName());
            lock.writeLock().unlock();
        }
    }


    @Test
    public void testWrite() {
        new Thread(new Runnable() {
            public void run() {
                System.out.println(get(1));
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println(get(1));
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                System.out.println(get(1));
            }
        }).start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testSet() throws InterruptedException {

        wait();

        new Thread(new Runnable() {
            public void run() {
                System.out.println(get(1));
            }
        }).start();


        Thread.sleep(400);
        new Thread(new Runnable() {
            public void run() {
                System.out.println(get(1));
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println(set(1, 10));
            }
        }).start();

        Thread.sleep(1200);
        new Thread(new Runnable() {
            public void run() {
                System.out.println(get(1));
            }
        }).start();


        Thread.sleep(10000);
    }


    volatile int count = 0;

    @Test
    public void testWait() throws InterruptedException {
        final ReadWriteLockTest test = this;

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                synchronized (test) {
                    System.out.println("in thread1");
                    while (count == 0) {
                        try {
                            wait();
                            System.out.println("1 over");
                            break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                synchronized (test) {
                    System.out.println("in thread2");
                    count = 1;
                    notify();
                    System.out.println("2 over");
                }
            }
        });

        thread1.start();
        Thread.sleep(1000);
        thread2.start();

        Thread.sleep(5000);
    }
}
