# data_structure(JDK 1.8)
java数据结构

# Java中的Collections API主要包含两个独立的树形结构——`Collection`和`Map`
## Collection接口
![image](https://upload-images.jianshu.io/upload_images/2671018-d29cd465fee0e87e.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/490/format/webp‪)

### 1. [Queue](./src/main/java/com/liminghuang/queue/README.md)

除了基本的`Collection`接口中定义的操作，还提供其他**插入**、**删除**、和**元素检查**等操作。限定元素个数的称为<font color="red">**有界队列**</font>。

```java
public interface Queue<E> extends Collection<E>{
    // 元素检查
    E element();
    
    // 插入
    boolean offer(E e);
    
    // 元素检查
    E peek();
    
    // 移除
    E poll();
    
    // 移除
    E remove();
}
```
队列操作的两种方式：

| 队列操作 | 功能说明 | 异常情况 | 抛出异常的方法 | 返回特定值的方法 |
| --- | :---: | :---: | :---: | :---: |
| 插入 | 向队列中加入元素 | 有界队列已满 | add(e) | offer(e)，返回false |
| 移除 | 从队首移走一个元素 | 队列空 | remove() | poll()，返回null  |
| 元素检查 | 返回队首元素，但不删除该元素 | 队列空 | element() | peek()，返回null |


### 2. Set
Set继承了`Collection`接口，方法全部从`Collection`**继承**，自身没有声明其他方法。

```java
public interface Set<E> extends Collection<E>{
    // 基本操作
    // 返回集合中元素的数量，如果超过int型最大值Integer.MAX_VALUE，则仅返回最大值
    int size();
    
    // 集合中不包含任何元素就返回true
    boolean isEmpty();
    
    // 集合是否包含指定元素
    boolean contains(Object element);
    
    // 往集合里添加元素，添加成功返回true；集合不允许添加重复元素返回false；其他情况抛异常；能否添加null看具体实现规则
    boolean add(E element);
    
    // 移除一个或多个与指定的元素匹配的实例，成功返回true；其余抛出异常
    boolean remove(Object o);
    
    // 返回当前集合元素的迭代器iterator，无法保证有关的顺序
    Iterator<E> iterator();
    
    // 集合元素批量操作
    // 返回当前集合是否包含指定集合C的所有元素
    boolean containsAll(Collection<?> c);
    
    // 将指定集合C的元素都添加到当前集合，自己无法添加自己，成功true；不知何时返回false;其余抛异常，不支持的操作，空指针，类型转换，非法状态，不允许添加等
    boolean addAll(Collection<? extends E> c);
    
    // 从当前集合中去除指定集合中的所有元素，成功返回true；其余抛出异常，不支持的操作、类型转换、空指针等
    boolean removeAll(Collection<?> c);
    
    // 在当前集合中只保留属于C的元素，如果当前集合发生变化则返回true；其余抛出异常，不支持的操作、类型装还、空指针等
    boolean retainAll(Collection<?> c);
    
    // 清除集合中的所有元素
    void clear();
    
    // 数组操作
    // 返回包含当前集合所有元素的数组，保证元素的顺序（依据迭代器的顺序）
    Object[] toArray();
    
    // 返回包含当前集合所有元素的数组，所返回的数组的运行时类型是数组a的类型。保证元素的顺序（依据迭代器的顺序）；如果数组a能容下集合的所有元素，则将集合元素写入a并返回，否则创建类型与a相同、长度等于集合长度的数组
    <T> T[] toArray(T[] a);
}
```
JDK提供实现Set接口的3个实用类：***HashSet***、***TreeSet***、***LinkedHashSet***;
#### 2.1 [HashSet](./src/main/java/com/liminghuang/set/README.md)

采用Hash表实现了`Set`接口（内部使用的是`HashMap`结构存储），一个`HashSet`对象中的元素存储在一个`Hash`表中，**元素没有固定顺序**；Hash表结构**支持大数据量的访问**，所以比线性列表快

#### 2.2 TreeSet

实现了`SortedSet`接口（源码中未发现，待研究），采用一种**有序树的结构**存储集合中的元素，`TreeSet`对象中的元素按照升序排序

#### 2.3 [LinkedHashSet](./src/main/java/com/liminghuang/set/README.md)
实现了`Set`接口,**采用`Hash`表和双端链表**相结合的结构(即`LinkedHashMap`)存储集合中的元素，元素**具有固定的顺序**，集中了`HashSet`与`TreeSet`的优点，即能保证顺序又能够具有较高的存取效率（待研究）

### 3. List
List是一种有序集合，继承自`Collection`接口。除了`Collection`中的方法，List接口还增加如下操作：
- 按位置存取元素，按照元素**在list中的序号**对其进行操作
- 查找，在list中**搜寻指定的对象**并返回该对象的序号
- 遍历，使用`ListIterator`实现List的遍历
- 截取子List，建立List视图

```java
public interface List<E> extends Collection<E>{
    // 按位置存取元素
    E get(int index);
    E set(int index, E element);
    Boolean add(E element);
    void add(int index, E element);
    E remove(int index);
    boolean addAll(int index, Collection<? extends E> c);
    
    // 查找
    int indexOf(Object o);
    int lastIndexOf(Object o);
    
    // 遍历
    ListIterator<E> listIterator();
    ListIterator<E> listIterator(int index);
    
    // 子List的截取
    List<E> subList(int from, int to);
}
```
#### 3.1 [ArrayList](./src/main/java/com/liminghuang/list/README.md)
采用**可变大小的数组**实现List接口，默认**增长为1.5倍**。ArrayList会随着元素的增加其容积**自动扩大**，非同步。除此之外，几乎与`Vector`操作是同等的。

#### 3.2 Vector
采用**可变体积的数组**实现List接口，默认增长为**两倍**。该类像数组一样，可以通过索引序号对所包含的元素进行访问，**同步的(线程安全)**。

#### 3.3 [LinkedList](./src/main/java/com/liminghuang/list/README.md)
采用**链表结构**实现List接口。除了List中的方法，该类还提供了**在List的开头和结尾进行`get`，`remove`和`insert`等操作**。这些操作使得LinkedList可以用来实现**堆栈、队列或双端队列**，非同步(线程不安全)。

### 4. Stack
继承Vector，所以也是线程安全，同步的
```java
package java.util;

/**
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects. It extends class <tt>Vector</tt> with five
 * operations that allow a vector to be treated as a stack. The usual
 * <tt>push</tt> and <tt>pop</tt> operations are provided, as well as a
 * method to <tt>peek</tt> at the top item on the stack, a method to test
 * for whether the stack is <tt>empty</tt>, and a method to <tt>search</tt>
 * the stack for an item and discover how far it is from the top.
 * <p>
 * When a stack is first created, it contains no items.
 *
 * <p>A more complete and consistent set of LIFO stack operations is
 * provided by the {@link Deque} interface and its implementations, which
 * should be used in preference to this class.  For example:
 * <pre>   {@code
 *   Deque<Integer> stack = new ArrayDeque<Integer>();}</pre>
 *
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public class Stack<E> extends Vector<E> {
    /**
     * Creates an empty Stack. 构造一个空的栈
     */
    public Stack() {
    }

    /**
     * 添加一个元素到栈顶
     * Pushes an item onto the top of this stack. This has exactly
     * the same effect as:
     * <blockquote><pre>
     * addElement(item)</pre></blockquote>
     *
     * @param   item   the item to be pushed onto this stack.
     * @return  the <code>item</code> argument.
     * @see     java.util.Vector#addElement
     */
    public E push(E item) {
        addElement(item);

        return item;
    }

    /**
     * 移除栈顶元素并返回值
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return  The object at the top of this stack (the last item
     *          of the <tt>Vector</tt> object).
     * @throws  EmptyStackException  if this stack is empty.
     */
    public synchronized E pop() {
        E       obj;
        int     len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    /**
     * 返回栈顶的元素值，但不移除栈顶元素
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return  the object at the top of this stack (the last item
     *          of the <tt>Vector</tt> object).
     * @throws  EmptyStackException  if this stack is empty.
     */
    public synchronized E peek() {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    /**
     * 判断栈是否为空
     * Tests if this stack is empty.
     *
     * @return  <code>true</code> if and only if this stack contains
     *          no items; <code>false</code> otherwise.
     */
    public boolean empty() {
        return size() == 0;
    }

    /**
     * 返回距离栈顶最近的一个元素的位置
     * Returns the 1-based position where an object is on this stack.
     * If the object <tt>o</tt> occurs as an item in this stack, this
     * method returns the distance from the top of the stack of the
     * occurrence nearest the top of the stack; the topmost item on the
     * stack is considered to be at distance <tt>1</tt>. The <tt>equals</tt>
     * method is used to compare <tt>o</tt> to the
     * items in this stack.
     *
     * @param   o   the desired object.
     * @return  the 1-based position from the top of the stack where
     *          the object is located; the return value <code>-1</code>
     *          indicates that the object is not on the stack.
     */
    public synchronized int search(Object o) {
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = 1224463164541339165L;
}

```
由此可见，JDK1.0 中Stack的实现基于Vector，而Vector是List接口的一个直接实现类（**线程安全**），所以Stack的栈操作是基于Vector的基本操作。
栈的几种常用操作方式：

| 栈操作 | 功能说明 | 操作方法 | 抛出异常 |
| -------- | :---: | :---: | :---: |
| 入栈 | 向队列中加入元素 | push(e) | No |
| 出栈 | 从队首移走一个元素 | synchronized pop | 栈空时抛异常 |
| 元素检查 | 返回队首元素，但不删除该元素 | synchronized peek-窥视 |  栈空时抛异常 |
| 栈空间检查 | 栈结构是否为空 | empty | No |
| 栈内查询 | 查询元素在栈内最近出现的位置 | synchronized search(o) | No |

仔细观察源码，不难发现，类的头部声明中推荐开发者自主实现Deque接口来替代Stack。
### 5. [Map](./src/main/java/com/liminghuang/map/README.md)
### 6. [SparseArray](https://www.jianshu.com/p/186d1e466aae)