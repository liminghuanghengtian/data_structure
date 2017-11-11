package com.liminghuang.proxy.report;


/**
 * Created by Adaministrator on 2017/11/11.
 */

public class YmengReporter implements LogReporter {
    @Override
    public void report() {
        System.out.println("Youmeng report log!");
    }
}
