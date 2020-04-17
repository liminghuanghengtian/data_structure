# ThreadLocal
ThreadLocal内部定义的`ThreadLocalMap`结构类似`HashMap`内部的存储结构，通过数组的方式存储，节点结构`Entry`是继承弱应用（仅有一个弱引用的话，GC时会被回收），泛型类型是ThreadLocal。ThreadLocal主要**包装**了`Thread.threadLocals`成员属性的操作.
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
## 特性
- 默认初始容量`INITIAL_CAPACITY=16`，且扩容的话需要指数倍的增长
- 扩容的条件阈值是`len * 2 / 3;`
- 存储key是**当前的ThreadLocal对象**，hash值计算方式：`firstKey.threadLocalHashCode`，index计算方式同hashMap一致：`hash() & (len -1)`
- 根据如下hash值的获取方式，不会出现碰撞的情况，所以`Entry`节点未设计链表或者树的结构
## 1. ThreadLocal中的一个常量和几个静态属性
```java
/**
 * 这个常量作为线程变量
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
## 2. ThreadLocalMap构造
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
### 2.1 何时构造
createMap方法内去构造ThreadLocalMap的实例，以存储线程变量值
1. 设置值的时候 `public void ThreadLocal#set()`
2. 设置初始值的时候 `private T ThreadLocal#setInitialValue()`

### 2.2 get操作
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

#### ThreadLocalMap#getEntry查找
```java
private Entry getEntry(ThreadLocal<?> key) {
    // 计算索引
    int i = key.threadLocalHashCode & (table.length - 1);
    Entry e = table[i];
    // 从弱引用e中获取，判断key是否和当前ThreadLocal
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

### 2.3 set操作
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