package com.liminghuang.proxy;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.proxy
 * Description: 静态代理Subject, 静态代理模式本身有个大问题，如果类方法数量越来越多的时候，代理类的代码量是十分庞大的。
 * <p>
 * CreateTime: 2017/10/25 19:38
 * Modifier: Adaministrator
 * ModifyTime: 2017/10/25 19:38
 * Comment:
 *
 * @author Adaministrator
 */
public class SubjectProxy implements Subject {
    Subject subImpl = new RealSubject();
    
    @Override
    public void doSomething(String args) {
        subImpl.doSomething(args);
    }
    
    @Override
    public void undoSomething(String args) {
        subImpl.undoSomething(args);
    }
    
    public static void main(String args[]) {
        Subject sub = new SubjectProxy();
        sub.doSomething("my name is huangliming");
    }
    
}
