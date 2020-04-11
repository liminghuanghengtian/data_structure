# queue结构
## 1. BlockingQueue
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

 <pre> {@code
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

内存一致性的影响:与其他并发集合一样，在将对象放入{@code BlockingQueue} <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>>之前，从另一个线程中的{@code BlockingQueue}中访问或删除该元素后的操作。

<p>This interface is a member of the
<a href="{@docRoot}/../technotes/guides/collections/index.html">
Java Collections Framework</a>.

## 2. SynchronousQueue
> 一个{@linkplain BlockingQueue blocking queue}，其每个插入操作必须等待另一个线程执行相应的删除操作，反之亦然。
  同步队列没有任何内部容量，甚至连一个容量都没有。您不能对同步队列执行{@code peek}操作，因为一个元素只有在您试图删除它时才会出现;你不能(使用任何方法)插入一个元素，除非另一个线程试图移除它;
  您不能进行迭代，因为没有可以迭代的内容。
  队列的<em>头</em>是第一个队列插入线程试图添加到队列中的元素;如果没有这样的排队线程，那么就没有可以删除的元素，{@code poll()}将返回{@code null}。对于其他{@code集合}方法(例如{@code contains})， {@code SynchronousQueue}充当一个空集合。这个队列不允许{@code null}元素。
  同步队列类似于CSP和Ada中使用的会合通道。它们非常适合于切换设计，在这种设计中，在一个线程中运行的对象必须与在另一个线程中运行的对象同步，以便传递一些信息、事件或任务。
  该类支持一个可选的公平性策略，用于对正在等待的生产者和消费者线程进行排序。默认情况下，不保证这种顺序。但是，将公平性设置为{@code true}的队列将按FIFO顺序授予线程访问权。
  这个类及其迭代器实现了{@link Collection}和{@link Iterator}接口的所有<em>可选的</em>方法。
  这个类是<a href> Java集合框架</a>的成员。

## 3. ArrayBlockingQueue
> 一个依靠数组支持的有界{@linkplain BlockingQueue blocking queue}。这个队列对元素FIFO(先进先出)排序。 
  队列的<em>头</em>是队列中存在时间最长的元素。 
  队列的<em>尾</em>是队列中出现时间最短的元素。
  新元素插入到队列的尾部，队列检索操作获取队列头部的元素。
  这是一个经典的“有界缓冲”，其中一个固定大小的数组容纳由生产者插入并由消费者提取的元素。 
  一旦创建，容量就不能更改。 
  试图{@code put}将一个元素放入一个满队列将导致操作阻塞;尝试{@code take}从空队列中获取元素同样会阻塞。   
  该类支持一个可选的公平性策略，用于对正在等待的生产者和消费者线程进行排序。 
  默认情况下，不保证这种顺序。但是，将公平性设置为{@code true}的队列将按FIFO顺序授予线程访问权。
  公平性通常会降低吞吐量，但会降低可变性并避免饥饿。
  这个类及其迭代器实现了{@link集合}和{@link迭代器}接口的所有<em>可选的</em>方法。
  这个类是<a href> Java集合框架</a>的成员。