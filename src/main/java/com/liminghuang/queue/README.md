# Queue
## 1.Queue接口（单向队列）
Queue接口，是集合框架Collection的子接口，是一种常见的数据结构，遵循**先进先出**的原则。
是<font color="red">**基于链表**来进行实现的**单向队列**</font>。

## 2.Deque接口（双端队列）
Deque接口，是Queue接口的子接口，是指**队列两端的元素，既能入队（offer）也能出队(poll)。**

如果将Deque限制为只能**从一端进行入队和出队**，就是**栈的数据结构**的实现。对于栈而言，有**入栈（push）和出栈（pop）**，遵循**先进后出**的规则。

**双端队列：**

- `add(e)`或`offer(e)`：将元素增加到队列的末尾，如果成功，返回true。
- `remove()`或`poll()`：将元素从队列的末尾删除。
- `element()`或`peek()`：返回队首的元素，但不进行删除。

**栈：**

- `push(e)`：入栈
- `pop(e)`：出栈
- `peek()`：返回栈首元素，但不进行删除。

## 3. ArrayDeque
### 头注释翻译
> Resizable-array implementation of the {@link Deque} interface.  Array deques have no capacity restrictions; they grow as necessary to support usage.  They are not thread-safe; in the absence of external synchronization, they do not support concurrent access by multiple threads. Null elements are prohibited.  This class is likely to be faster than {@link Stack} when used as a stack, and faster than {@link LinkedList} when used as a queue.

实现{@link Deque}接口的可调整大小的数组。ArrayDeque**没有容量限制**;它们根据需要增长以支持使用。它们**不是线程安全的**;在缺乏外部同步的情况下，它们不支持多线程的并发访问。<font color="red">**禁止空元素**</font>。这个类用作堆栈时可能比{@link Stack}快，用作队列时可能比{@link LinkedList}快。

> Most {@code ArrayDeque} operations run in amortized constant time. Exceptions include {@link #remove(Object) remove}, {@link #removeFirstOccurrence removeFirstOccurrence}, {@link #removeLastOccurrence removeLastOccurrence}, {@link #contains contains}, {@link #iterator
  iterator.remove()}, and the bulk operations, all of which run in linear time.

大多数{@code ArrayDeque}操作在平摊常数时间内运行。异常包括{@link #remove(Object) remove}， {@link #removeFirstOccurrence removeFirstOccurrence}， {@link #removeLastOccurrence removeLastOccurrence}， {@link #contains contains}， {@link #iterator iterator.remove() 
和批量操作，所有这些操作都在线性时间内运行。

> The iterators returned by this class's {@code iterator} method are <i>fail-fast</i>: If the deque is modified at any time after the iterator is created, in any way except through the iterator's own {@code remove} method, the iterator will generally throw a {@link ConcurrentModificationException}.  Thus, in the face of concurrent modification, the iterator fails quickly and cleanly, rather than risking arbitrary, non-deterministic behavior at an undetermined time in the future.

这个类的{@code iterator}方法返回的迭代器是<i> failure -fast(快速失败))</i>:如果deque在迭代器创建后的任何时候被修改，除了通过迭代器自己的{@code remove}方法，迭代器通常会抛出一个{@link ConcurrentModificationException}。因此，在面对并发修改时，迭代器会快速而干净地失败，而不是在将来某个不确定的时间冒任意的、不确定的行为的风险。

> Note that the fail-fast behavior of an iterator cannot be guaranteed as it is, generally speaking, impossible to make any hard guarantees in the presence of unsynchronized concurrent modification.  Fail-fast iterators throw {@code ConcurrentModificationException} on a best-effort basis. Therefore, it would be wrong to write a program that depended on this exception for its correctness: <i>the fail-fast behavior of iterators should be used only to detect bugs.</i>

注意，不能保证迭代器的快速故障行为，因为通常来说，在存在非同步并发修改的情况下，不可能做出任何严格的保证。故障快速迭代器尽最大努力抛出{@code ConcurrentModificationException}。因此，编写一个依赖于这个异常来保证其正确性的程序是错误的:<i>迭代器的快速失效行为应该只用于检测bug。</i>

> This class and its iterator implement all of the <em>optional</em> methods of the {@link Collection} and {@link Iterator} interfaces.

这个类及其迭代器实现了{@link Collection}和{@link Iterator}接口的所有<em>可选的</em>方法。

### 特性                                                                         
1. 默认**初始容量16，长度需要是2的幂**
2. **逻辑上是循环数组**，没有固定头和尾，拥有`head`和`tail`两个指针，可往头尾插入数据，长度计算`(tail - head) & (elements.length - 1)`. 
3. 大部分API设计都支持了**两端的对应操作**，例如：`addFirst`和`addLast`，`offerFirst`和`offerLast`，`removeFirst`和`removeLast`，`pollFirst`和`pollLast`，`getFirst`和`getLast`，`peekFirst`和`peekLast` 

### 3.1 重要属性
```java
// 存储的容器-对象数组
transient Object[] elements; // non-private to simplify nested class access
// 循环数组-头指针哨兵
transient int head;
// 循环数组-尾指针哨兵
transient int tail;
// 容器的最小长度
private static final int MIN_INITIAL_CAPACITY = 8;
```
### 3.2 默认构造器
```java
public ArrayDeque() {
    elements = new Object[16];// 长度为16
}
```

### 3.3 ArrayDeque规范的实现
主要还是Queue的规范，尾部入列，头部出列，先进先出
```java
// 尾部添加
public boolean add(E e) {
    addLast(e);
    return true;
}

// 头部移除
public E remove() {
    return removeFirst();
}

// 尾部添加
public boolean offer(E e) {
    return offerLast(e);
}

// 头部移除
public E poll() {
    return pollFirst();
}

// 检查队列头
public E element() {
    return getFirst();
}

// 检查队列头
public E peek() {
    return peekFirst();
}

// 队头入栈
public void push(E e) {
    addFirst(e);
}

// 队头出栈
public E pop() {
    return removeFirst();
}

public int size() {
    return (tail - head) & (elements.length - 1);
}

public boolean isEmpty() {
    return head == tail;
}
```

### 3.4 循环数组
为了提高效率，ArrayDeque采用了**循环数组**的设计，也就是说虽然基础容器是一个普通的数组（默认容量16），但是在逻辑上，这个数组没有固定的开头或结尾，既可以直接向尾部添加数据，也**可以直接向头部之前添加数据，不需要大面积地移动数据**。逻辑上的概念大概是这样的：
![ArrayDeque](../../../../resources/drawable/ArrayDeque.png)

循环数组在**概念上没有左右边界**，但是Java并没有这样的数组，**Java只能提供固定大小的数组**，这样的话，如何实现循环数组就转变为如何利用固定数组实现循环数组。
相对于线性数组，循环数组是连续的，但是**数组的头和尾可能在任何位置(通过head和tail指针的移动来实现)**，所以循环数组在真实数据中的映射大概是这样：

![ArrayDeque_CycleArray](../../../../resources/drawable/ArrayDeque_CycleArray.png)

### 3.5 添加操作
初始创建ArrayDeque对象，**创建长度为16的数组，head和tail此时为零，指向第一个索引位置**，添加数据后`head`或`tail`才开始变化。<font color="orange">**`addFirst`第一个添加的元素位置在`index=15`处，`addLast`第一个添加的元素在`index=0`处。**</font>

#### 3.5.1 头部添加
1. 计算新head索引`(head - 1) & (elements.length - 1)`，采用**位与运算**，保证head新索引位置在数组的有效长度范围内
2. 数组head索引对应槽位填入数据

```java
public void addFirst(E e) {
    if (e == null)
        throw new NullPointerException();
    // @1 计算head新索引
    elements[head = (head - 1) & (elements.length - 1)] = e;
    if (head == tail)// head与tail缠绕时需要扩容，头尾相碰表示容量已用完
        doubleCapacity();// 扩容为原长两倍
}
```
步骤1原理介绍：
- 当head-1为-1时，实际上是11111111&00001111，结果是00001111，也就是物理数组的尾部15；
- 当head-1为较小的值如3时，实际上是00000011&00001111，结果是00000011，还是3。
- 当head增长如head+1超过物理数组长度如16时，实际上是00010000&00001111，结果是00000000，也就是0，这样就回到了物理数组的头部。

#### 3.5.2 尾部添加
大致同头部添加一致，主要不同点在于：
1. tail位置是在上一次添加元素后，tail新位置已计算好，始终指向一个空槽
2. 每次直接在空槽放入数据即可

```java
    public void addLast(E e) {
        if (e == null)
            throw new NullPointerException();
        elements[tail] = e;
        if ( (tail = (tail + 1) & (elements.length - 1)) == head)
            doubleCapacity();
    }
```
### 3.6 移除
#### 3.6.1 头部移除
1. 查找head索引槽位的数据
2. 数据为空，直接返回null
3. 数据不为空，先释放索引处的槽位，重新调整head指向后一个槽位`(h + 1) & (elements.length - 1)`，成为新head
```java
    public E removeFirst() {
        E x = pollFirst();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }
    
    public E pollFirst() {
        int h = head;
        @SuppressWarnings("unchecked")
        E result = (E) elements[h];
        // Element is null if deque empty
        if (result == null)
            return null;
        // 去除数据，槽位释放
        elements[h] = null;     // Must null out slot
        // head指针位置调整
        head = (h + 1) & (elements.length - 1);
        return result;
    }    
```

#### 3.6.2 尾部移除
1. tail始终指向的是尾巴后面的一个空槽位，需要先计算尾巴数据的索引位置`(tail - 1) & (elements.length - 1)`，这点和从头部取不同，head始终是头部数据的索引位置
2. 根据计算好的索引位置获取尾部数据
3. 数据为空，则直接返回`null`
4. 数据不为空，则释放槽位，调整tail指向该释放的槽位，返回移除的数据内容
```java
    public E removeLast() {
        E x = pollLast();
        if (x == null)
            throw new NoSuchElementException();
        return x;
    }
    
    public E pollLast() {
        int t = (tail - 1) & (elements.length - 1);
        @SuppressWarnings("unchecked")
        E result = (E) elements[t];
        if (result == null)
            return null;
        elements[t] = null;
        tail = t;
        return result;
    }
```

### 3.7 如何扩容？
扩容为原先的两倍长度，数组数据拷贝到新数组中。
1. head索引到数组物理末端的元素，这段内容拷贝到新数组中，从索引0的位置开始填充
2. 原数组索引0-head段拷贝到新数组，从索引r开始填充

```java
    private void doubleCapacity() {
        assert head == tail;
        int p = head;
        int n = elements.length;
        int r = n - p; // number of elements to the right of p
        int newCapacity = n << 1;
        if (newCapacity < 0)
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        // @1
        System.arraycopy(elements, p, a, 0, r);
        // @2
        System.arraycopy(elements, 0, a, r, p);
        elements = a;
        head = 0;
        tail = n;// tail始终指向尾巴元素后的空槽位
    }
```

### 3.8 [ArrayDeque第三方解析](https://www.jianshu.com/p/132733115f95)

# BlockingQueue
## 头注释翻译
> A {@link java.util.Queue} that additionally supports operations
that wait for the queue to become non-empty when retrieving an
element, and wait for space to become available in the queue when
storing an element.

一个额外支持在检索元素时等待队列变为非空，以及在存储元素时等待队列中的空间可用的操作{@link java.util.Queue} 。

> {@code BlockingQueue} methods come in four forms, with different ways
of handling operations that cannot be satisfied immediately, but may be
satisfied at some point in the future:
one throws an exception, the second returns a special value (either
{@code null} or {@code false}, depending on the operation), the third
blocks the current thread indefinitely until the operation can succeed,
and the fourth blocks for only a given maximum time limit before giving
up.  These methods are summarized in the following table:

{@code BlockingQueue}方法有四种形式,以不同的方式处理不能立即满足的操作,但可以在未来的某个时刻满足:第一个是抛出一个异常,第二个是返回一个特殊的值({@code null}或{@code false},具体看操作),第三个线程无限期地阻塞当前线程，直到操作成功为止,而第四种只有给定的最大时限阻塞才会放弃。这些方法总结如下表:

<table BORDER CELLPADDING=3 CELLSPACING=1>
<caption>Summary of BlockingQueue methods</caption>
 <tr>
   <td></td>
   <td ALIGN=CENTER><em>Throws exception</em></td>
   <td ALIGN=CENTER><em>Special value</em></td>
   <td ALIGN=CENTER><em>Blocks</em></td>
   <td ALIGN=CENTER><em>Times out</em></td>
 </tr>
 <tr>
   <td><b>Insert</b></td>
   <td>add(e)</td>
   <td>offer(e)</td>
   <td>put(e)</td>
   <td>offer(e, time, unit)</td>
 </tr>
 <tr>
   <td><b>Remove</b></td>
   <td>remove()</td>
   <td>poll()</td>
   <td>take()</td>
   <td>(time, unit)</td>
 </tr>
 <tr>
   <td><b>Examine</b></td>
   <td>element()</td>
   <td>peek()</td>
   <td><em>not applicable不适用</em></td>
   <td><em>not applicable不适用</em></td>
 </tr>
</table>

>A {@code BlockingQueue} does not accept {@code null} elements.
Implementations throw {@code NullPointerException} on attempts
to {@code add}, {@code put} or {@code offer} a {@code null}.  A
{@code null} is used as a sentinel value to indicate failure of
{@code poll} operations.

{@code BlockingQueue}<font color="red">**不接受{@code null}元素**</font>。实现类会尝试在{@code add}、{@code put}或{@code offer}传递{@code null}时抛出一个{@code NullPointerException}。{@code null}用作标记值，表示{@code poll}操作失败。

>A {@code BlockingQueue} may be capacity bounded. At any given
time it may have a {@code remainingCapacity} beyond which no
additional elements can be {@code put} without blocking.
A {@code BlockingQueue} without any intrinsic capacity constraints always
reports a remaining capacity of {@code Integer.MAX_VALUE}.

{@code BlockingQueue}可能容量有界。在任何给定的时间，它都可能有一个{@code remain capacity}，超出这个范围，任何附其他元素都不能{@code put}而不被阻塞。没有任何内在容量约束的{@code BlockingQueue}总是报告{@code Integer.MAX_VALUE}的剩余容量。

>{@code BlockingQueue} implementations are designed to be used
primarily for producer-consumer queues, but additionally support
the {@link java.util.Collection} interface.  So, for example, it is
possible to remove an arbitrary element from a queue using
{@code remove(x)}. However, such operations are in general
<em>not</em> performed very efficiently, and are intended for only
occasional use, such as when a queued message is cancelled.

{@code BlockingQueue}实现主要用于生产者-消费者队列，但还支持{@link java.util。集合}接口。因此，例如，可以使用{@code remove(x)}从队列中删除任意元素。然而，这样的操作一般执行得<em>不是</em>非常有效，并且只打算偶尔使用，例如当队列消息被取消时。

>{@code BlockingQueue} implementations are thread-safe.  All
queuing methods achieve their effects atomically using internal
locks or other forms of concurrency control. However, the
<em>bulk</em> Collection operations {@code addAll},
{@code containsAll}, {@code retainAll} and {@code removeAll} are
<em>not</em> necessarily performed atomically unless specified
otherwise in an implementation. So it is possible, for example, for
{@code addAll(c)} to fail (throwing an exception) after adding
only some of the elements in {@code c}.

{@code BlockingQueue}实现是<font color="red">**线程安全**</font>的。所有的排队方法都是使用**内部锁**或其他形式的并发控制来自动地达到它们的效果。但是，<em>批量</em>集合操作{@code addAll}、{@code containsAll}、{@code retainAll}和{@code removeAll}<em>不</em>一定是原子性的，除非在实现中另外指定。因此，例如，在仅添加了{@code c}中的一些元素之后，{@code addAll(c)}可能会失败(引发异常)。

<p>A {@code BlockingQueue} does <em>not</em> intrinsically support
any kind of &quot;close&quot; or &quot;shutdown&quot; operation to
indicate that no more items will be added.  The needs and usage of
such features tend to be implementation-dependent. For example, a
common tactic is for producers to insert special
<em>end-of-stream</em> or <em>poison</em> objects, that are
interpreted accordingly when taken by consumers.

{@code BlockingQueue}不会从本质上支持任何类型的“关闭”或“shutdown”操作表面不再添加任何项。这些特性的需求和用法往往依赖于实现。例如，一个常见的策略是，生产者插入特殊的<em>end-of-stream</em>或<em>poison</em>对象，当消费者使用时，这些对象将被相应地解释。

>Usage example, based on a typical producer-consumer scenario.
Note that a {@code BlockingQueue} can safely be used with multiple
producers and multiple consumers.

基于典型的生产者-消费者场景的使用示例。注意，{@code BlockingQueue}可以安全地用于多个生产者和多个消费者。

 <pre> 
class Producer implements Runnable {
  private final BlockingQueue queue;
  Producer(BlockingQueue q) { queue = q; }
  public void run() {
    try {
      while (true) { queue.put(produce()); }
    } catch (InterruptedException ex) { ... handle ...}
  }
  Object produce() { ... }
}

class Consumer implements Runnable {
  private final BlockingQueue queue;
  Consumer(BlockingQueue q) { queue = q; }
  public void run() {
    try {
      while (true) { consume(queue.take()); }
    } catch (InterruptedException ex) { ... handle ...}
  }
  void consume(Object x) { ... }
}

class Setup {
  void main() {
    BlockingQueue q = new SomeQueueImplementation();
    Producer p = new Producer(q);
    Consumer c1 = new Consumer(q);
    Consumer c2 = new Consumer(q);
    new Thread(p).start();
    new Thread(c1).start();
    new Thread(c2).start();
  }
}}</pre>

>Memory consistency effects: As with other concurrent
collections, actions in a thread prior to placing an object into a
{@code BlockingQueue}
<a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
actions subsequent to the access or removal of that element from
the {@code BlockingQueue} in another thread.

内存一致性的影响：与其他并发集合一样，在将对象放入{@code BlockingQueue} <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>>之前，从另一个线程中的{@code BlockingQueue}中访问或删除该元素后的操作。

<p>This interface is a member of the
<a href="{@docRoot}/../technotes/guides/collections/index.html">
Java Collections Framework</a>.

## 特性
- 阻塞的添加`put()`和获取`take()`


## 1. SynchronousQueue
### 头注释翻译

一个{@linkplain BlockingQueue blocking queue}，其每个插入操作必须等待另一个线程执行相应的删除操作，反之亦然。
同步队列没有任何内部容量，甚至连一个容量都没有。您**不能对同步队列执行{@code peek}操作，因为一个元素只有在您试图删除它时才会出现**；你不能(使用任何方法)插入一个元素，除非另一个线程试图移除它；您不能进行迭代，因为没有可以迭代的内容。
队列的<em>头</em>是第一个队列插入线程试图添加到队列中的元素;如果没有这样的排队线程，那么就没有可以删除的元素，{@code poll()}将返回{@code null}。对于其他{@code集合}方法(例如{@code contains})， {@code SynchronousQueue}充当一个空集合。这个**队列不允许{@code null}元素**。
同步队列类似于CSP和Ada中使用的会合通道。它们非常适合于切换设计，在这种设计中，在一个线程中运行的对象必须与在另一个线程中运行的对象同步，以便传递一些信息、事件或任务。
该类支持一个可选的公平性策略，用于对正在等待的生产者和消费者线程进行排序。默认情况下，不保证这种顺序。但是，将公平性设置为{@code true}的队列将按FIFO顺序授予线程访问权。
这个类及其迭代器实现了{@link Collection}和{@link Iterator}接口的所有<em>可选的</em>方法。
这个类是<a href> Java集合框架</a>的成员。

## 2. ArrayBlockingQueue
### 头注释翻译
一个依靠数组支持的**有界**{@linkplain BlockingQueue blocking queue}。这个队列对元素FIFO(先进先出)排序。 
队列的<em>头</em>是队列中存在时间最长的元素。 
队列的<em>尾</em>是队列中出现时间最短的元素。
新元素**插入到队列的尾部**，队列检索操作**获取队列头部的元素**。
这是一个经典的“有界缓冲”，其中一个**固定大小的数组**容纳由生产者插入并由消费者提取的元素。 
<font color="red">**一旦创建，容量就不能更改。**</font>
试图{@code put}将一个元素**放入一个满队列将导致操作阻塞**；尝试{@code take}**从空队列中获取元素同样会阻塞。**     

该类支持一个**可选的公平性策略**，用于**对正在等待的生产者和消费者线程进行排序**。 默认情况下，不保证这种顺序。但是，将公平性设置为{@code true}的队列**将按FIFO顺序授予线程访问权**。
公平性通常会降低吞吐量，但会降低可变性并**避免饥饿**。
这个类及其迭代器实现了{@link Collection}和{@link Iterator}接口的所有<em>可选的</em>方法。
这个类是<a href> Java集合框架</a>的成员。

### 特性
- 有界（定长）缓冲，同步的（线程安全），阻塞添加和获取
- 参见上述翻译
  
## 3. PriorityBlockingQueue
PriorityBlockingQueue是一个带优先级的队列，而**不是先进先出**队列。元素按优先级顺序被移除，该队列也没有上限（看了一下源码，PriorityBlockingQueue是对 PriorityQueue的再次包装，是基于**堆数据结构**的，而PriorityQueue是没有容量限制的，与ArrayList一样，所以在优先阻塞 队列上**put时是不会受阻的**。虽然此队列**逻辑上是无界**的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError），但是如果队列为空，那么取元素的操作take就会阻塞，所以它的检索操作**take是受阻**的。另外，往入该队列中的元 素要具有比较能力。

### 特性
1. 物理结构上是数组，逻辑结构上是堆，初始容量11（算法一书限定是`queue[0]`不存储数据，实际是存储了？？？），最大容量`MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;`
2. 堆，即有序的完全二叉树[^heap]
3. `put`/`offer`方法写入线程在数组满时不会阻塞，`take`方法执行线程在数组空时会阻塞
4. `take`是从队头（`queue[0]`）获取

### 3.1 put非阻塞操作
1. 通过lock保证多线程执行put，存入数据是安全的
2. put操作（写入）是非阻塞的，容量满11了的话可以扩容，但是扩容操作需要获取扩容锁
3. 数据插入通过比较器实现堆修复，堆修复过程中即完成数据插入
4. size增加并通知take阻塞的锁从等待转入锁池，获取数据
5. put操作的线程释放lock锁，take进入RUNNABLE，等待调度
```java
    public void put(E e) {
        offer(e); // never need to block
    }

    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        int n, cap;
        Object[] array;
        
        //如果队列满了，就调用tryGrow进行扩容
        while ((n = size) >= (cap = (array = queue).length))
            tryGrow(array, cap);
        try {
            Comparator<? super E> cmp = comparator;
            if (cmp == null)
                siftUpComparable(n, e, array);
            else
                siftUpUsingComparator(n, e, array, cmp);
            size = n + 1;
            notEmpty.signal();// 通知take释放，数据已存入，可以获取了
        } finally {
            lock.unlock();
        }
        return true;
    }
```
#### 如何排序优先级？
这个过程叫做堆的有序化。此处根据Comparator可以判断是大根堆还是小根堆。
1. 从队尾开始，一直往前根据堆的算法性质查找parent节点
2. 和parent节点比对，若是比parent小，则插入在parent的子节点；否则将parent元素交换到子元素位置，然后持续往前查找parent节点
3. 当插入元素大于parent节点时，
```java
    /**
     * 通过Comparator上移到堆的正确位置 
     */
    private static <T> void siftUpUsingComparator(int k, T x, Object[] array,
                                       Comparator<? super T> cmp) {
        // 从队尾元素开始一直往前查找父节点
        while (k > 0) {
            // 父节点索引
            int parent = (k - 1) >>> 1;
            Object e = array[parent];
            
            // 新增元素x和父节点array[parent]比较，元素x大于parent节点，则直接添加到parent的子节点处
            if (cmp.compare(x, (T) e) >= 0)
                break;
            
            // parent移动到当前判断的位置
            array[k] = e;
            // k指向parent继续遍历，查找元素何时的插入位置K
            k = parent;
        }
        
        // 查找到插入位置K，新增元素存入
        array[k] = x;
    }
```

### 3.2 扩容操作
```java
 private void tryGrow(Object[] array, int oldCap) {
        lock.unlock(); // must release and then re-acquire main lock
        Object[] newArray = null;
        if (allocationSpinLock == 0 &&
            UNSAFE.compareAndSwapInt(this, allocationSpinLockOffset,
                                     0, 1)) {
            try {
                int newCap = oldCap + ((oldCap < 64) ?
                                       (oldCap + 2) : // grow faster if small
                                       (oldCap >> 1));
                if (newCap - MAX_ARRAY_SIZE > 0) {    // possible overflow
                    int minCap = oldCap + 1;
                    if (minCap < 0 || minCap > MAX_ARRAY_SIZE)
                        throw new OutOfMemoryError();
                    newCap = MAX_ARRAY_SIZE;
                }
                if (newCap > oldCap && queue == array)
                    newArray = new Object[newCap];
            } finally {
                allocationSpinLock = 0;
            }
        }
        if (newArray == null) // back off if another thread is allocating
            Thread.yield();
        lock.lock();
        if (newArray != null && queue == array) {
            queue = newArray;
            System.arraycopy(array, 0, newArray, 0, oldCap);
        }
    }
```
[^heap]: 所有叶子节点都出现在k层或k-1层；对任一节点，若其右子树的最大层次为L，则其左子树最大层次为L或L+1