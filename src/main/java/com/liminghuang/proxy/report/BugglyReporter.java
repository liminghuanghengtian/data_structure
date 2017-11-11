package com.liminghuang.proxy.report;


import sun.rmi.runtime.Log;

/**
 * Created by Adaministrator on 2017/11/11.
 */

public class BugglyReporter implements LogReporter {
    @Override
    public void report() {
        System.out.println("Buggly report log!");
    }
}
