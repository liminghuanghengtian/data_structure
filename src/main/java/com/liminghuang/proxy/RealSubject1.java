package com.liminghuang.proxy;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.proxy
 * Description:
 * <p>
 * CreateTime: 2017/10/25 19:37
 * Modifier: Adaministrator
 * ModifyTime: 2017/10/25 19:37
 * Comment:
 *
 * @author Adaministrator
 */
public class RealSubject1 implements Subject {
    
    @Override
    public void doSomething(String args) {
        System.out.println("call doSomething(" + args + ")");
    }
    
    @Override
    public void undoSomething(String args) {
        System.out.println("call undoSomething(" + args + ")");
    }
}
