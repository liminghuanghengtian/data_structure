package com.liminghuang.str;

/**
 * ProjectName: data_structure
 * PackageName: com.liminghuang.str
 * Description:
 * <p>
 * CreateTime: 2020/4/23 16:39
 * Modifier: Adaministrator
 * ModifyTime: 2020/4/23 16:39
 * Comment:
 *
 * @author Adaministrator
 */
public class StringDemo {
    
    public static void m1() {
        String a = "abcd";// 堆中的老年代字符串常量池
        String b = new String("abcd");// 堆中
        String c = "a" + "b" + "c" + "d";
        System.out.println("a.hashCode() = " + a.hashCode());
        System.out.println("b.hashCode() = " + b.hashCode());
        System.out.println("a == b? " + (a == b));
        System.out.println("a == c? " + (a == c));
        System.out.println("a.equals(b)? " + a.equals(b) + "\n");
    }
    
    public static void m2() {
        String a = new String("abcd");
        String b = new String("abcd");
        System.out.println("a.hashCode() = " + a.hashCode());
        System.out.println("b.hashCode() = " + b.hashCode());
        System.out.println("a == b? " + (a == b));
        System.out.println("a.equals(b)? " + a.equals(b) + "\n");
    }
    
    public static void m3() {
        String c = "ab";
        String a = c + "cd";
        String b = new String("abcd");
        System.out.println("a.hashCode() = " + a.hashCode());
        System.out.println("b.hashCode() = " + b.hashCode());
        System.out.println("a == b? " + (a == b));
        System.out.println("a.equals(b)? " + a.equals(b) + "\n");
    }
    
    public static void m4() {
        String a = "ab" + "c" + "d";
        String b = new String("abcd");
        String c = b.intern();
        String d = new String("abcd").intern();
        System.out.println("a.hashCode() = " + a.hashCode());
        System.out.println("b.hashCode() = " + b.hashCode());
        System.out.println("c.hashCode() = " + c.hashCode());
        System.out.println("a == b? " + (a == b));
        System.out.println("a == c? " + (a == c)); // 注意：这里为true
        System.out.println("a == d? " + (a == d)); // 注意：这里为true
        System.out.println("a.equals(b)? " + a.equals(b) + "\n");
    }
    
    public static void m5() {
        char[] tChar = {'a', 'b', 'c'};
        if (tChar.getClass() != null) {
            System.out.println(tChar.getClass());
            System.out.println(tChar.toString());
            System.out.println("It is a Object");
        }
    }
    
    private static String params = "1234567";
    
    public static void m6(String p) {
        p = p + "8";// p指向了堆中新的String对象，其实是StringBuffer或者StringBuilder对象append(8)，toString后返回一个新的String对象
        System.out.println("p: " + p);
        System.out.println("params: " + params);// 依旧是"1234567"
        
        String t = params.concat("89");// t也是新创建的String类型
        System.out.println("t: " + t);
        System.out.println("params: " + params);
        params = "abcdefg";
        System.out.println("params: " + params);
        System.out.println("params.toCharArray() -> " + params.toCharArray());// 是一个新的char[]
    }
    
    public static void main(String args[]) {
        // m1();
        // m2();
        // m3();
        // m4();
        m5();
        m6(params);
    }
}
