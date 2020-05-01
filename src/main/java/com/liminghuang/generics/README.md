# 泛型
泛型即**泛化技术**，提供了**编译时的类型安全**保证，也称为**参数化类型**。泛型使得很多程序中的错误能在编译时刻被发现，从而增加代码的**稳定性和正确性**。通过定义**一个或多个类型参数**的类或接口，对具有类似特征与行为的类进行抽象。
## 上溯造型
符合里氏替换——任何父类可以出现的地方，子类一定可以出现。符合java的多态性。
## 下溯造型
将父类对象显式的**强制转换**成子类对象。对象不允许不经过上溯造型而直接下溯造型。
## 1. 泛化类型及子类
定义泛化类型(generic type)，也就是定义具有泛化结构的类或接口。在类名后增加**由尖括号标识的类型变量**，一般用`T`表示，可以**将`T`看作一类特殊的变量，该变量的值在使用时指定。**

泛型的一个调用*使得泛型被固定为参数`T`所指定的类型*，所以一般被称为**参数化类型(parameterized type)**。

一个泛型可以有多个类型参数，当每个参数在该泛型中应是唯一的。

## 2. 通配符
通过在泛型的类型形参中使用通配符(wildcards)，以提高程序的**灵活性**。以通配符“？”替代泛型尖括号中的具体类型，表明该泛型的类型是一种未知的类。

### 2.1 受限通配符
- `? extends Animal`含义是**Animal**或其某种未知的**子类**，Animal被认为是**泛型变量的上限**。
- `? super Animal`含义是Animal或其未知的某个父类。
- `?`称为无限制通配符，等价于`? extends Object`

通配符示例：
```java
void feedAnimals(Cage<? extends Animal> someCage){
    for(Animal a : someCage)
        a.feedMe();
}
```

引入通配符的主要目的：**支持泛型中的子类，从而实现多态。**
## 3. 泛型方法
类型参数可以出现在**方法声明**中，以定义泛化方法(generic methods)。泛化方法中**类型参数的作用域只限于声明它的方法**。<font color="orange">具体是在**方法声明的各种修饰符，例如`public`，`final`，`static`，`abstract`，`synchronized`等，与方法返回类型之间**，增加一个*带尖括号的类型参数列表*。</font>示例：
```java
public <U> void inspect(U u){
    System.out.println("T: " + T.getClass().getName());
    System.out.println("U: " + U.getClass().getName());
}
```
除了以上实例的方法，也可以对*静态方法*，*构造方法*进行泛化，即**所有方法都可以定义为泛化方法**。
<font color="green">因为Java编译器具有**类型推理能力**，它根据调用方法时**实参的类型**，推理出被调用方法中**类型变量的具体类型**，据此检查方法调用中*类型的正确性*。</font>

泛化方法实现的功能也可以通过带有通配符的泛型实现。例如：
```java
interface Collection<E>{
    public <T> boolean containAll(Collection<T> c);
    public <T extends E> boolean addAll(Collection<T> c);
}

interface Collection<E>{
    public boolean containAll(Collection<?> c);
    public boolean addAll(Collection<? extends E> c);
}
```
<font color="red">**如果方法泛化的目的只是为了能够适用于多种不用的类型，或支持多态，则应使用通配符。**</font>泛化方法中类型参数的优势是可以**表达多个参数或返回值之间的类型依赖关系**。
## 4. 类型擦除
Java虚拟机中，并没有泛型类型的对象。<font color="red">**泛型通过编译器执行一个被称为类型擦除(type erases)的前端转换来实现。**</font>可以理解为将带有泛型的程序转换为不包含泛型的版本。
擦除的处理：
1. 用**泛型的原生类型(raw type)替代泛型**。
原生类型是泛型中**去除尖括号及其中的类型参数**的*类或接口*。泛型中所有对类型变量的引用都替换为类型变量的<font color="orange">**最近上限类型**。</font>例如：<font color="green">`Cage<T extends Animal>`，`T`的引用将用`Animal`替换，而对于`Cage<T>`，`T`的引用将用`Object`替换。</font>
2. 对于**含泛型的表达式**，用**原生类型替换泛型**。
例如：<font color="green">`List<String>`的原生类型是`List`。类型擦除时，`List<String>`将被转换成`List`。</font>对于泛型方法的调用，如果擦除后返回值类型与泛型声明的类型不一致，则会**插入强制类型转换**。
3. 对于**泛化方法**的擦除，<font color="green">是将方法声明中的**类型参数声明去掉**，并进行**类型变量的替换**。</font>例如：`public static <T extends Comparable> T min(T[] e)`类型擦除后`public static Comparable min(Comparable[] e)`