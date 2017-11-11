package com.liminghuang.proxy.report;

import com.liminghuang.proxy.ProxyHandler;
import com.liminghuang.proxy.RealSubject;
import com.liminghuang.proxy.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Adaministrator on 2017/11/11.
 */

public class ProxyReporter implements InvocationHandler {
    private Object sub;
    
    //绑定委托对象，并返回代理类
    public Object bind(Object tar) {
        this.sub = tar;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before calling " + method);
        Object result = null;
        result = method.invoke(sub, args);
        System.out.println("after calling " + method);
        return result;
    }
    
    /**
     * test使用
     * @param args
     */
    public static void main(String args[]) {
        ReporterFactory factory = new RealFactory();
        LogReporter reporter = factory.create();
        LogReporter logReporter = (LogReporter) new ProxyReporter().bind(reporter);  //初始化代理类
        // 各个模块去各自调用
        logReporter.report();
        
        factory = new RealFactory1();
        reporter = factory.create();
        logReporter = (LogReporter) new ProxyReporter().bind(reporter);  //初始化代理类
        // 各个模块去各自调用
        logReporter.report();
    }
}
