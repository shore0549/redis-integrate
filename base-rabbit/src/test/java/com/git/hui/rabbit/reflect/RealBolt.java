package com.git.hui.rabbit.reflect;

/**
 * Created by yihui in 18:51 18/5/29.
 */
public class RealBolt implements IBolt<String, Boolean> {
    public void print() {
        System.out.println("realbolt: String + Boolean" );
    }
}
