package com.liminghuang.proxy;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.proxy
 * Description: 动态代理
 * <p>
 * CreateTime: 2017/10/25 19:41
 * Modifier: Adaministrator
 * ModifyTime: 2017/10/25 19:41
 * Comment:
 * 1. Proxy类的代码量被固定下来，不会因为业务的逐渐庞大而庞大；
 * 2. 可以实现AOP编程，实际上静态代理也可以实现，总的来说，AOP可以算作是代理模式的一个典型应用；
 * 3. 解耦，通过参数就可以判断真实类，不需要事先实例化，更加灵活多变。
 *
 * @author Adaministrator
 */
public class ProxyHandler implements InvocationHandler {
    
    private Object tar;
    
    //绑定委托对象，并返回代理类
    public Object bind(Object tar) {
        this.tar = tar;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }
    
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        // 这里就可以进行所谓的AOP编程了
        // 在调用具体函数方法前，执行功能处理
        method.getName();
        result = method.invoke(tar, args);
        //在调用具体函数方法后，执行功能处理
        return result;
    }
    
    public static void main(String args[]) {
        ProxyHandler proxy = new ProxyHandler();
        //绑定该类实现的所有接口
        Subject sub = (Subject) proxy.bind(new RealSubject());
        sub.doSomething("my name is huangliming");
        sub.undoSomething("my name is liminghuang");
    }
}
