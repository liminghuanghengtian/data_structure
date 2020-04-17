# HashMap(java8)
## 特性
1. 默认初始长度为16，数组长度必须是2的幂（为了key映射到index时候的hash算法更高效，通过`（n-1）& hash`值的位运算，等效于取模），扩容是之前容量的两倍
2. hash算法结果是均匀的，即能保证index较为均匀；java8的hash算法通过key.hashcode，将其高16位和低16位做异或（`h ^ h>>>16`），这个综合考虑了速度，作用和质量
3. java7 hash碰撞通过链表实现，总是**添加到链头**（设计者认为后插入的数据被查找的可能性大）；java8在链表的长度**超过6（大于等于TREEIFY_THRESHOLD - 1），将链表转为红黑树**(查找效率快，时间复杂度O（logN）)，无法解决hash碰撞的问题，但通过红黑树优秀的查找性能来解决问题
4. 扩容是创建新长度数组，然后将原数组内容重新计算index后放入新数组；java8 仅重新计算index，省略重新hash过程，因为hash算法所得hash值不变，所以新index要么等于原index，要么原index加上原长；resize的过程，均匀的把之前的冲突的节点分散到新的bucket了
5. 高并发下出现链表的环形结构。当调用Get查找一个不存在的Key，而这个Key的Hash结果恰好在index位置的时候，由于位置index处带有环形链表，所以程序将会进入死循环（链表中不存元素，一直遍历）！

## 1. put的实现
put函数大致的思路为：
1. 对key的hashCode()做hash，然后再计算index;
2. 如果没碰撞直接放到bucket里；
3. 如果碰撞了，以链表的形式存在buckets后；
4. 如果碰撞导致链表过长(大于等于TREEIFY_THRESHOLD=8？？？)，就把链表转换成红黑树；
5. 如果节点已经存在就替换old value(保证key的唯一性)
6. 如果bucket满了(超过load factor*current capacity)，就要resize。
结合代码分析：
```
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
    Node<K,V>[] tab; // hash桶数组
    Node<K,V> p; // 哨兵p
    int n, i;
    // 数组tab为空（即桶数组还未初始化）则创建桶数组
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
        
    // 计算索引index，让哨兵p指向这个桶；桶内暂无数据，则构造数据节点并在index位置的桶存入
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
        
    // 索引index位置已存在数据，发生碰撞
    else {
        // 查找哨兵e
        Node<K,V> e; K k;
        // 判断是同一个节点存入的情况
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // 该链为红黑树
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        // 该链为链表
        else {
            for (int binCount = 0; ; ++binCount) {
                // 遍历单链表（链上可能仅有一个链头元素），根据key和hash查找
                if ((e = p.next) == null) {
                    // java8是直接放在链表尾部的，和java7不同
                    p.next = newNode(hash, key, value, null);
                    
                    // 转成红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st， TREEIFY_THRESHOLD=8
                        treeifyBin(tab, hash);
                    break;
                }
                
                // 根据hash&key查找到链上对应节点e
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                    
                // 这一步很重要，遍历链表的关键，否则无法完成遍历
                p = e;
            }
        }
        
        // 已存在节点，则覆盖写入新值
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            // onlyIfAbsent在put时为false，覆盖
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
                
            // 这个方法主要是为LinkedHashMap设计的，后续LinkedHashMap内介绍
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    // 超过load factor*current capacity，resize
    if (++size > threshold)
        resize();
        
    // 这个方法主要是为LinkedHashMap设计的，后续LinkedHashMap内介绍
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

## 2. 如何扩容？
```$java
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
## 3. 获取节点（根据key）
查找过程设想：
1. 查找条件是传入key，根据hash方法计算hash值
2. 判断数组是否非空，非空则3，否则直接返回null
3. index索引处是否存在节点，不存在则返回null，存在则比对hash和key是否相等，相等则返回该节点value，否则进入4；
4. 判断桶内是否还有更多节点，无其他更多节点则返回null, 否则判断桶的首节点类型，为红黑树，则在红黑树内查找，否则在链表内遍历查找（条件依旧是hash和key都匹配）

```
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; 
        Node<K,V> first, e; 
        int n; K k;
        
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            // index位置出节点直接匹配上
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
                
            if ((e = first.next) != null) {// 桶内是否有后续节点
                // 红黑树内查找
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                    
                // 链表上查找
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```

## 4. 移除节点removeNode
```
final Node<K,V> removeNode(int hash, Object key, Object value,
                               boolean matchValue, boolean movable) {
    Node<K,V>[] tab; // hash桶数组
    Node<K,V> p; // 哨兵p
    int n, index;
    
    // hash桶数组不为空，数组长度大于零，且哨兵p指向index位置的桶不为空
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (p = tab[index = (n - 1) & hash]) != null) {
        Node<K,V> node = null, e;// node哨兵指向我们查找到的节点，未查找到则为空；e则是遍历的指针
        K k; // 查到的节点的key
        V v; // 查到的节点的value
        
        // 桶内哨兵p指向链头节点hash值匹配且key匹配（存在可能key不一致，但生成的hash一致的情况），index位置即需要查找的节点
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            node = p;
        else if ((e = p.next) != null) {
            // 桶内是红黑树
            if (p instanceof TreeNode)
                node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
            else {
                // 桶内是链表，在链表中遍历查找，找到则退出，否则遍历结束才退出。
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key ||
                         (key != null && key.equals(k)))) {
                        node = e;
                        break;
                    }
                    p = e;// 注意：哨兵p指向已查找到node节点的前节点
                } while ((e = e.next) != null);// 如果是环形列表，且查找一个不存在的key，其hash值映射的index刚好落在这个桶内
            }
        }
        
        // 待移除节点已查找到，matchValue为true，则需要比对value
        if (node != null && (!matchValue || (v = node.value) == value ||
                             (value != null && value.equals(v)))) {
                             
            // 从红黑树上移除
            if (node instanceof TreeNode)
                ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
            // 桶内仅一个元素
            else if (node == p)
                tab[index] = node.next;
            // 桶内已形成链表
            else
                // 前节点指针直接指向node后一个节点，将node释放出来，但是node.next指针依旧指向则他的后一个节点
                p.next = node.next;
            ++modCount;
            --size;
            
            // 专门给LinkedHashMap设计的，
            afterNodeRemoval(node);
            return node;
        }
    }
    return null;
}
```

## 5. 红黑树介绍

### 5.1 二叉查找树特征
- 任意节点的左子树不空，则左子树上所有节点的值均小于根节点的值
- 任意节点的右子树不空，则右子树上所有节点的值均大于根节点的值
- 任意节点左右子树也一定分别为二叉排序树
- 没有键值相等的节点

### 5.2 [红黑树](https://www.jianshu.com/p/e136ec79235c)定义和性质

红黑树是一种含有红黑结点并能**自平衡**的二叉查找树。它必须满足下面性质：

- 性质1：每个节点要么是黑色，要么是红色。
- 性质2：根节点是黑色。
- 性质3：每个叶子节点（NIL）是黑色。
- 性质4：每个红色结点的两个子结点一定都是黑色。
- 性质5：任意一结点到每个叶子结点的路径都包含数量相同的黑结点。这种平衡为**黑色完美平衡**。

前面讲到红黑树能自平衡，它靠的是什么？三种操作：左旋、右旋和变色。

**左旋**：以某个结点作为支点(旋转结点)，其右子结点变为旋转结点的父结点，右子结点的左子结点变为旋转结点的右子结点，左子结点保持不变。如图3。

**右旋**：以某个结点作为支点(旋转结点)，其左子结点变为旋转结点的父结点，左子结点的右子结点变为旋转结点的左子结点，右子结点保持不变。如图4。

**变色**：结点的颜色由红变黑或由黑变红。

红黑树总是通过旋转和变色达到自平衡。

# HashTable(java8)
同hashmap的不同：
1. 初始容量11
2. 线程安全，同步的（方法上加同步锁synchronized）
3. hash算法(采用取模低效，因为长度非2的指数次的长度)：`int hash = key.hashCode();
                  int index = (hash & 0x7FFFFFFF) % tab.length;`
4. 扩容通过rehash，扩容长度为原长两倍加一，最大长度`MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;`

# LinkedHashMap(java8)
## 特性
1. LinkedHashMap是HashMap+双向链表的数据结构，支持访问排序和插入排序，所以其内部是有序的，不想像HashMap。基于**访问排序**的数据结构非常适合实现`LRUCache`算法。
2. 为了实现双向链表的结构，在HashMap.Node继承基础上增加before和after两个双向指针
3. put方法复用父类HashMap的，覆写父类HashMap的`newNode`方法，创建节点并添加节点到双向链表尾部
4. 双向链表的**尾部（tail）才是最新鲜的节点**，新添加的节点和设置了访问排序后访问的节点都会被调整到链尾

## 1. 构造器
```
public LinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        accessOrder = false;
    }
    
public LinkedHashMap(int initialCapacity) {
        super(initialCapacity);
        accessOrder = false;
    }
    
public LinkedHashMap() {
        super();
        accessOrder = false;
    }
    
public LinkedHashMap(Map<? extends K, ? extends V> m) {
        super();
        accessOrder = false;
        putMapEntries(m, false);
    }
    
public LinkedHashMap(int initialCapacity,
                         float loadFactor,
                         boolean accessOrder) {
        super(initialCapacity, loadFactor);
        this.accessOrder = accessOrder;
    }
```
依上观察，构造器主要使用的是父类HashMap的初始化逻辑，然后`accessOrder`默认都是false，说明主要是依据插入进行排序的，除非构造的时候特殊指定`accessOrder`为true。

## 2. 节点的双向列表结构
```java
// 直接继承了HashMap的Node节点的数据结构
static class Entry<K,V> extends HashMap.Node<K,V> {
        // 节点拥有一个before的前指针和一个after的后指针，用以形成双向列表结构
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
```
## 3. put操作
LinkedHashMap内部并没有重新定义put方法，只是覆写了`newNode`方法和专门为其设计的`afterNodeInsertion`方法。

### newNode
```
Node<K,V> newNode(int hash, K key, V value, Node<K,V> e) {
        // 创建节点
        LinkedHashMap.Entry<K,V> p =
            new LinkedHashMap.Entry<K,V>(hash, key, value, e);
        // 将新建节点添加到双向链表的尾部
        linkNodeLast(p);
        return p;
    }


private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
        LinkedHashMap.Entry<K,V> last = tail;// last哨兵指向当前链表的尾节点
        tail = p;// tail指向当前节点，只是逻辑上的标记成尾节点，真正的添加操作还未完成
        
        // 双向链表为空，暂无任何元素
        if (last == null)
            head = p;// 头尾指针均指向p
        else {
            // 双向链表不为空，添加节点到双向链表尾部，分为两个步骤
            // 1. 被添加节点的前指针指向链表尾部，此时尚未完成双向链接的过程，还有第2步
            p.before = last;
            // 2. 此前链表尾节点的尾指针指向当前添加节点，此时被添加节点正式成为链表的尾节点
            last.after = p;
        }
    }
```

### afterNodeInsertion

afterNodeInsertion方法**用于移除链表中的最旧的节点对象**，也就是**链表头部**的对象。
但是在JDK1.8版本中，可以看到removeEldestEntry一直返回false，所以该方法并不生效。
如果存在特定的需求，比如链表中长度固定，并保持最新的N的节点数据，可以通过重写该方法来进行实现。

```
// possibly remove eldest
void afterNodeInsertion(boolean evict) { 
        LinkedHashMap.Entry<K,V> first;// first哨兵
        // evict为true表示要清理旧对象，first哨兵指向头节点; removeEldestEntry总是返回false, 移除逻辑无法执行，可以继承覆写
        if (evict && (first = head) != null && removeEldestEntry(first)) {
            K key = first.key;
            // 移除当前头节点的实现在HashMap中
            removeNode(hash(key), key, null, false, true);
        }
    }
    
protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return false;
    }
```

**removeNode实现**
设想以下需要做哪几件事： 
1. 从HashMap中移除相应查找的节点，这部分属于HashMap类的逻辑
2. 从双向链表中移除查找到的节点

### afterNodeRemoval

```
/**
 * 这里就是从双向列表中移除节点，主要包括两个步骤：
 * 1. 先释放自身节点指向外部的两个指针
 * 2. 再释放外部前后两个节点对自身的引用指向，头尾两种情况的话，还需要调整头尾指针的指向
 */
void afterNodeRemoval(Node<K,V> e) { // unlink
    LinkedHashMap.Entry<K,V> p = (LinkedHashMap.Entry<K,V>)e, 
    b = p.before, a = p.after;
    
    p.before = p.after = null;// p的前指针、后指针释放掉
    if (b == null)// 前指针指向的内存为空，说明p就是头节点。p要移除掉，head指针得指向p的后一个节点a
        head = a;
    else
        // p前面有节点，则为了取出p，让p的前节点直接指向p的后节点
        b.after = a;
        
        
    if (a == null)// 后指针指向的内存为空，说明p是尾节点。p要被移除掉，tail指针得指向p的前一个节点b
        tail = b;
    else
        // p后面有节点，则为了取出p，让p的后节点的前指针
        a.before = b;
}
```

### afterNodeAccess
afterNodeAccess方法实现的逻辑，是把作为入参的节点放置在链表的尾部。
```
/**
 * 若是依据访问排序，则将当前访问的节点添加到双向链表的尾部
 */
void afterNodeAccess(Node<K,V> e) { // move node to last
        LinkedHashMap.Entry<K,V> last;
        
        // 判断是否依据访问排序，这是首要条件，不符合就无需调整双向链表的顺序；或者本身被访问的节点就是尾节点也无需调整
        if (accessOrder && (last = tail) != e) {
            LinkedHashMap.Entry<K,V> p =
                (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;// b和a需要指向p的前节点和后节点，因为p即将从这里解开添加到尾部
                
            // 完成p从链上解开的过程
            // 1. p对后节点的指向移除
            p.after = null;
            
            // 2. 前后节点对p的指向先移除
            if (b == null)// p即头节点或双向链表为空仅p节点，则需要调整头指针指向p的后节点
                head = a;
            else
                // p在链上是中间节点，则p的前节点after指针指向p的后节点
                b.after = a;
                
            if (a != null) // p在链上是中间节点，则p的后节点before指针指向p的前节点
                a.before = b;
            else // 仅p节点
                last = b;
                
                
            // 原先空链表，p也没有before和after，last才可能为空   
            if (last == null)
                head = p;
            else {
                // 3. p对前节点的指向调整为链尾 
                p.before = last;
                // 4. 链尾新增p
                last.after = p;
            }
            
            // p正式成为尾节点
            tail = p;
            // 注意：双向链表的调整也属于修改
            ++modCount;
        }
    }
```

## 4. get操作
```
    public V get(Object key) {
        Node<K,V> e;
        if ((e = getNode(hash(key), key)) == null)
            return null;
            
        // 当前节点被访问了，需要添加到尾部
        if (accessOrder)
            afterNodeAccess(e);
        return e.value;
    }
```

## 5. 迭代器
```java
abstract class LinkedHashIterator {
        LinkedHashMap.Entry<K,V> next;
        LinkedHashMap.Entry<K,V> current;
        int expectedModCount;

        LinkedHashIterator() {
            next = head;// 头节点作为起始节点
            expectedModCount = modCount;
            current = null;
        }

        public final boolean hasNext() {
            return next != null;
        }

        // 迭代器Iterator#next获取下一个节点是会调用这个方法
        final LinkedHashMap.Entry<K,V> nextNode() {
            LinkedHashMap.Entry<K,V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            // 无元素时，头节点是指向null，这个等会儿从初始化看
            if (e == null)
                throw new NoSuchElementException();
            // 
            current = e;
            next = e.after;
            return e;
        }

        public final void remove() {
            Node<K,V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }
```

# ConcurrentHashMap(java8)                  
1. 采用分段锁来保证同步及高效性能
2. hash算法调整`static final int spread(int h) {
                    return (h ^ (h >>> 16)) & HASH_BITS;
                }`
3. 