# String
## 特性
- `String`设计就是final，不被继承，不可修改。
- 内部由字符数组`private final char value[];`构成，字符数组是final的，一旦指定对象，引用则不可修改
- 默认无参构造是一个空的数组`"".value`

## 构造方法
### String(String)
初始化新创建的{@code String}对象，使其表示与参数相同的字符序列;换句话说，新创建的字符串是参数字符串的副本。除非需要显式地复制{@code original}，否则没有必要使用这个构造函数，因为字符串是不可变的。
```java

public String(String original) {
        this.value = original.value;
        this.hash = original.hash;
    }
```
### String(char[])
```java
public String(char value[]) {
        // 区别于入参的数组
        this.value = Arrays.copyOf(value, value.length);
    }
    
public static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];// 申请新的char数组空间
        // 原数组元素copy到新申请的数组
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
```
### String(StringBuffer)
StringBuffer通过在其自身方法上加`synchronized`来保证同步，线程安全，其toString会有缓存，但会在修改后置空。这里getValue非其自身的方法，所以需要保护，防止被修改。
```java
public String(StringBuffer buffer) {
        synchronized(buffer) {
            this.value = Arrays.copyOf(buffer.getValue(), buffer.length());
        }
    }
```
## 拷贝char[]的方式
```java
/**
     * Copy characters from this string into dst starting at dstBegin.
     * This method doesn't perform any range checking.
     */
    void getChars(char dst[], int dstBegin) {
        System.arraycopy(value, 0, dst, dstBegin, value.length);
    }
```
## 比较的方式很有趣
```java
public int compareTo(String anotherString) {
        int len1 = value.length;
        int len2 = anotherString.value.length;
        int lim = Math.min(len1, len2);
        char v1[] = value;
        char v2[] = anotherString.value;

        int k = 0;
        while (k < lim) {
            char c1 = v1[k];
            char c2 = v2[k];
            if (c1 != c2) {
                return c1 - c2; // 字符比较
            }
            k++;
        }
        // 有限长度范围内对比相等，则比较char[]数组长度之差
        return len1 - len2;
    }
```
## [String.intern()](https://blog.csdn.net/qq_34115899/article/details/86583262)
重用String对象，以节省内存消耗。

如果运行时常量池中已经包含一个等于此 String 对象内容的字符串，则返回常量池中该字符串的引用；如果没有，在jdk1.6中，将此String对象添加到常量池中，然后返回这个String对象的引用（此时引用的串在常量池）。在jdk1.7中，放入一个引用，指向堆中的String对象的地址，返回这个引用地址（此时引用的串在堆）。根据《java虚拟机规范 Java SE 8版》记录，如果某String实例所包含的Unicode码点序列与CONSTANT——String_info结构所给出的序列相同，而之前又曾在该实例上面调用过String.intern方法，那么此次字符串常量获取的结果将是一个指向相同String实例的引用。Unicode码点序列的直接理解：这玩意不就是字符连起来看equals是否相同不就完了吗！