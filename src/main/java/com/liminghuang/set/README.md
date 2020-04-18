# HashSet
内部存储数据结构是`HashMap`，`Set`存储的元素作为`HashMap`的`key`，`HashMap`存储的`value`固定为`private static final Object PRESENT = new Object();`。
为什么选择使用`HashMap`的`key`，因为`key`可以保证唯一性，这一点满足`Set`的唯一性要求即可，并且访问速度快. `Set`集合遍历使用的`HashMap#KetSet`的迭代器。
**`HashSet`所有的操作实现都基于`HashMap`**。 

## 添加元素
```java
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
```

## 移除元素
```java
public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }
```

其余方法也是类似。

# LinkedHashSet

和`HashSet`唯一的不同：`HashSet`使用`HashMap`作为数据结构，`LinkedHashSet`使用的是[`LinkedHashMap`](../map/README.md)作为实现的数据结构。借用`LinkedHashMap`的有序性来成就自身的有序性。