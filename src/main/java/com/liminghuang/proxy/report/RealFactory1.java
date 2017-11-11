package com.liminghuang.proxy.report;

/**
 * Created by Adaministrator on 2017/11/11.
 */

public class RealFactory1 implements ReporterFactory {
    @Override
    public LogReporter create() {
        return new YmengReporter();
    }
}
