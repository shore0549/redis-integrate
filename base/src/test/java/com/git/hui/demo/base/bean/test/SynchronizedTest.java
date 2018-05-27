package com.git.hui.demo.base.bean.test;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by yihui in 16:02 18/5/15.
 */
public class SynchronizedTest {

    public void print(Object obj) {
        try {
            synchronized (obj) {
                System.out.println(Thread.currentThread().getName() + " in ");
                Thread.sleep(2000);
                System.out.println(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final SynchronizedTest test = new SynchronizedTest();
        Hashtable<String, String> map = new Hashtable<>();
        map.put("123", "test");


        Thread t1 = new Thread(() -> test.print(map));
        Thread t2 = new Thread(() -> test.print(map));

        t1.start();
        t2.start();

        Thread.sleep(1000);
        map.put("333", "world");

        Thread.sleep(3000);


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt"), Charset.forName("utf-8")));
            String tmp;
            while((tmp = reader.readLine()) != null) {
                System.out.println(tmp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
