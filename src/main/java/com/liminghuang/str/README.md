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
## [String.intern()](https://blog.csdn.net/soonfly/article/details/70147205?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1)
new String都是在堆上创建字符串对象。当调用 intern() 方法时，编译器会将字符串添加到常量池中（stringTable维护），并返回指向该常量的引用。