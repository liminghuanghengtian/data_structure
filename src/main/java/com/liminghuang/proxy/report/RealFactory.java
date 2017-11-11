package com.liminghuang.proxy.report;

/**
 * Created by Adaministrator on 2017/11/11.
 */

public class RealFactory implements ReporterFactory {
    @Override
    public LogReporter create() {
        return new BugglyReporter();
    }
}
