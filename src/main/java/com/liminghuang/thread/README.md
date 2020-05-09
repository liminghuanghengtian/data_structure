# 1. ThreadLocal
ThreadLocal内部定义的`ThreadLocalMap`结构类似`HashMap`内部的存储结构，通过**数组**的方式存储，节点`Entry`结构上继承了弱引用（仅有一个弱引用的话，GC时会被回收）。ThreadLocal主要是对`Thread#threadLocals`这个成员属性相关操作的**包装**.

## 特性
- 默认初始容量`INITIAL_CAPACITY=16`，且扩容的话需要**指数倍的增长**
- 扩容的条件阈值是`len * 2 / 3`，这点和HashMap`len * 3 / 4 `略有区别
- 存储key是**当前的ThreadLocal实例对象**，hash计算方式：`firstKey.threadLocalHashCode`，index计算方式同HashMap一致，采用位与运算：`hash() & (len -1)`
- 根据如下hash值的获取方式，不会出现碰撞的情况，所以`Entry`节点未设计链表或者树的结构


## 1.1 存储节点`Entry`设计
Entry节点是一个弱引用，如果存储的内容在内存中仅此一个弱引用的话，则会在GC时被回收
```java
static class Entry extends WeakReference<ThreadLocal<?>> {
    
    /** The value associated with this ThreadLocal. */
    Object value;

    Entry(ThreadLocal<?> k, Object v) {
        super(k);
        value = v;
    }
}
```
## 1.2 ThreadLocal中的一个常量和几个静态属性
```java
/**
 * 这个常量作为线程变量存储key-ThreadLocal的hash值
 */
private final int threadLocalHashCode = nextHashCode();

private static AtomicInteger nextHashCode =
    new AtomicInteger();

/**
 * The difference between successively generated hash codes - turns
 * implicit sequential thread-local IDs into near-optimally spread
 * multiplicative hash values for power-of-two-sized tables.
 */
// 将隐式顺序thread-local IDs转换为接近最优扩散的乘法散列值，用于两倍幂大小的表。
private static final int HASH_INCREMENT = 0x61c88647;

/**
 * Returns the next hash code. 
 */
private static int nextHashCode() {
    // 先获取再加
    return nextHashCode.getAndAdd(HASH_INCREMENT);
}
```
## 1.3 ThreadLocalMap构造
```java
/**
 * The initial capacity -- MUST be a power of two.
 */
private static final int INITIAL_CAPACITY = 16;

/**
 * Construct a new map initially containing (firstKey, firstValue).
 * ThreadLocalMaps are constructed lazily, so we only create
 * one when we have at least one entry to put in it.
 */
ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
    table = new Entry[INITIAL_CAPACITY];
    int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
    table[i] = new Entry(firstKey, firstValue);
    size = 1;
    setThreshold(INITIAL_CAPACITY);
}
```
### 1.3.1 何时构造
在`createMap`方法内去构造`ThreadLocalMap`的实例对象，以在Thread内存储线程变量值
1. 设置值的时候 `public void ThreadLocal#set()`
2. 设置初始值的时候 `private T ThreadLocal#setInitialValue()`

### 1.3.2 get操作及初始值
1. `get`操作是从当前Thread内存空间获取其`threadLocals`成员属性
2. `threadLocals`成员属性引用为空，`ThreadLocalMap`的空间还未建立
```java
public T get() {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    
    // map为空，存储数据的ThreadLocalMap还未创建
    return setInitialValue();
}
```
3. 通过初始化方法`setInitialValue`从`initialValue`方法获取初始值，创建空间并存入
```java
private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
    
    
// 不覆写默认返null
protected T initialValue() {
        return null;
    }
```

#### ThreadLocalMap#getEntry查找
```java
private Entry getEntry(ThreadLocal<?> key) {
    // 计算索引
    int i = key.threadLocalHashCode & (table.length - 1);
    Entry e = table[i];
    // 从弱引用e中获取，判断key是否和当前ThreadLocal对象是否一致
    if (e != null && e.get() == key)
        return e;
    else
        return getEntryAfterMiss(key, i, e);
}

/**
 * Version of getEntry method for use when key is not found in
 * its direct hash slot.
 *
 * @param  key the thread local object
 * @param  i the table index for key's hash code
 * @param  e the entry at table[i]
 * @return the entry associated with key, or null if no such
 */
private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
    Entry[] tab = table;
    int len = tab.length;

    while (e != null) {
        ThreadLocal<?> k = e.get();
        if (k == key)
            return e;
        if (k == null)
            // key对象仅弱应用，现已被GC，则擦除节点
            expungeStaleEntry(i);
        else
            // 位置索引自增1，挨个往数组后方查找
            i = nextIndex(i, len);
        e = tab[i];
    }
    return null;
}
```

### 1.3.3 set操作
```java
private void set(ThreadLocal<?> key, Object value) {

    // We don't use a fast path as with get() because it is at
    // least as common to use set() to create new entries as
    // it is to replace existing ones, in which case, a fast
    // path would fail more often than not.

    Entry[] tab = table;
    int len = tab.length;
    int i = key.threadLocalHashCode & (len-1);// 根据hash与len-1做位与运算

    // 退出for循环的条件是没有可以匹配key的节点
    for (Entry e = tab[i];
         e != null;
         e = tab[i = nextIndex(i, len)]) {
        // 从弱引用获取引用对象
        ThreadLocal<?> k = e.get();

        // key匹配则查找成功，赋新值
        if (k == key) {
            e.value = value;
            return;
        }
        
        // 弱引用已被回收
        if (k == null) {
            replaceStaleEntry(key, value, i);
            return;
        }
    }

    tab[i] = new Entry(key, value);
    int sz = ++size;
    // 
    if (!cleanSomeSlots(i, sz) && sz >= threshold)
        rehash();
}
```

# 2. 无锁CAS保证原子性
原理解释：CAS全称（Compare And Swap），是一种用于在多线程环境下实现同步功能的机制。CAS 操作包含三个操作数 -- 内存位置、预期数值和新值。CAS 的实现逻辑是将内存位置处的数值与预期数值相比较，若相等，则将内存位置处的值替换为新值。若不相等，则不做任何操作。
CAS在java层没有直接实现，是通过unsafe类进入native实现的。
ABA问题处理措施是对**每一次 CAS 操作设置版本号**。

# 3. yield操作
yield()方法主要是为了保障线程间调度的连续性，防止某个线程一直长时间占用cpu资源。但是他的使用应该基于详细的分析和测试。这个方法一般不推荐使用，它主要用于debug和测试程序，用来减少bug以及对于并发程序结构的设计。

# 4. volatile关键字

原理和实现



