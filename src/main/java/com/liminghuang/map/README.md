# HashMap(java8)
## 特性
1. 默认初始长度为16，数组长度必须是2的幂（为了key映射到index时候的hash算法更高效，通过（n-1）&hash值的位运算，等效于取模），扩容是之前容量的两倍
2. hash算法结果是均匀的，即能保证index较为均匀；java8的hash算法通过key.hashcode，将其高16位和低16位做异或（h ^ h>>>16），这个综合考虑了速度，作用和质量
3. java7 hash碰撞通过链表实现，总是添加到链头（设计者认为后插入的数据被查找的可能性大）；java8在链表的长度超过8，将链表转为红黑树(查找效率快，时间复杂度O（logN）)，无法解决hash碰撞的问题，通过红黑树优秀的查找性能来解决问题
4. 扩容是创建新长度数组，然后将原数组内容重新计算index后放入新数组；java8 仅重新计算index，省略重新hash过程，因为hash算法所得hash值不变，所以新index要么等于原index，要么原index加上原长；resize的过程，均匀的把之前的冲突的节点分散到新的bucket了
5. 高并发下出现链表的环形结构。当调用Get查找一个不存在的Key，而这个Key的Hash结果恰好在index位置的时候，由于位置index处带有环形链表，所以程序将会进入死循环（链表中不存元素，一直遍历）！
6. 

## put的实现
put函数大致的思路为：
1. 对key的hashCode()做hash，然后再计算index;
2. 如果没hash碰撞直接放到bucket里；
3. 如果碰撞了，以链表的形式存在buckets后；
4. 如果碰撞导致链表过长(大于等于TREEIFY_THRESHOLD)，就把链表转换成红黑树；
5. 如果节点已经存在就替换old value(保证key的唯一性)
6. 如果bucket满了(超过load factor*current capacity)，就要resize。
结合代码分析：
```$java
public V put(K key, V value) {
    // 对key的hashcode（）做定义的hash方法
    return putVal(hash(key), key, value, false, true);
}

/**
 * hashMap的hash算法
 */
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);// key hash值的高16位与低16位异或    
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    // 数组tab为空则创建
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
        
    // 计算索引index，并对null做处理：index位置存入数据节点
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
     // 索引index位置已存在数据
    else {
        // 哨兵e
        Node<K,V> e; K k;
        // 数据节点已经存在
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // 该链为树
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        // 该链为链表
        else {
            for (int binCount = 0; ; ++binCount) {
                // 查找到单链表尾部（链上可能仅有一个链头元素）
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 转成红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st， TREEIFY_THRESHOLD=8
                        treeifyBin(tab, hash);
                    break;
                }
                // 查找到链上已存在节点e
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        
        // 覆盖写入
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            // onlyIfAbsent在put时为false，覆盖
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    // 超过load factor*current capacity，resize
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}

/**
 * 链表转成红黑树
 */
final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    // tab表为空或者长度小于64，则扩容
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY) // MIN_TREEIFY_CAPACITY=64
        resize();
    // tab表长度超过或等于64
    else if ((e = tab[index = (n - 1) & hash]) != null) {
        TreeNode<K,V> hd = null, tl = null;
        do {
            TreeNode<K,V> p = replacementTreeNode(e, null);
            if (tl == null)
                hd = p;
            else {
                p.prev = tl;
                tl.next = p;
            }
            tl = p;
        } while ((e = e.next) != null);
        if ((tab[index] = hd) != null)
            hd.treeify(tab);
    }
}
```

## 如何扩容？
```
/**
 * 扩容
 */
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        // 超过最大值就不再扩充了，就只好随你碰撞去吧
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        // 没超过最大值，就扩充为原来的2倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    
    // 计算新的resize上限
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        // 把每个bucket都移动到新的buckets中
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;// 原hash桶清理
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        // 原索引
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        // 原索引+oldCap
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    // 原索引放到bucket里
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    // 原索引+oldCap放到bucket里
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```
## 红黑树

### 二叉查找树特征
- 任意节点的左子树不空，则左子树上所有节点的值均小于根节点的值
- 任意节点的右子树不空，则右子树上所有节点的值均大于根节点的值
- 任意节点左右子树也一定分别为二叉排序树
- 没有键值相等的节点

### [红黑树](https://www.jianshu.com/p/e136ec79235c)定义和性质

红黑树是一种含有红黑结点并能**自平衡**的二叉查找树。它必须满足下面性质：

- 性质1：每个节点要么是黑色，要么是红色。
- 性质2：根节点是黑色。
- 性质3：每个叶子节点（NIL）是黑色。
- 性质4：每个红色结点的两个子结点一定都是黑色。
- 性质5：任意一结点到每个叶子结点的路径都包含数量相同的黑结点。这种平衡为**黑色完美平衡**。

前面讲到红黑树能自平衡，它靠的是什么？三种操作：左旋、右旋和变色。

**左旋：**以某个结点作为支点(旋转结点)，其右子结点变为旋转结点的父结点，右子结点的左子结点变为旋转结点的右子结点，左子结点保持不变。如图3。

**右旋：**以某个结点作为支点(旋转结点)，其左子结点变为旋转结点的父结点，左子结点的右子结点变为旋转结点的左子结点，右子结点保持不变。如图4。

**变色：**结点的颜色由红变黑或由黑变红。

红黑树总是通过旋转和变色达到自平衡。


