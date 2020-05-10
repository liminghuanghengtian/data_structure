# 反编译分析Class字节码
```
Microsoft Windows [版本 10.0.16299.1087]
(c) 2017 Microsoft Corporation。保留所有权利。

H:\IdeaProjects\data_structure>javap -verbose target/classes/com/liminghuang/thread/thizescape/ThisEscapeTest.class
Classfile /H:/IdeaProjects/data_structure/target/classes/com/liminghuang/thread/thizescape/ThisEscapeTest.class
  Last modified 2020-5-9; size 1821 bytes
  MD5 checksum 9d31bdfcdd99174481808f15da7c1cd0
  Compiled from "ThisEscapeTest.java"
public class com.liminghuang.thread.thizescape.ThisEscapeTest
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #20.#57        // com/liminghuang/thread/thizescape/ThisEscapeTest.doSomething:(Ljava/util/Observable;)V
   #2 = Methodref          #22.#58        // java/lang/Object."<init>":()V
   #3 = Class              #59            // com/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable
   #4 = Methodref          #3.#60         // com/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable."<init>":(Lcom/liminghuang/thread/thizescape/ThisEscapeTest$1;)V
   #5 = Fieldref           #20.#61        // com/liminghuang/thread/thizescape/ThisEscapeTest.observable:Lcom/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable;
   #6 = Class              #62            // java/lang/Thread
   #7 = Class              #63            // com/liminghuang/thread/thizescape/ThisEscapeTest$1
   #8 = Methodref          #7.#64         // com/liminghuang/thread/thizescape/ThisEscapeTest$1."<init>":(Lcom/liminghuang/thread/thizescape/ThisEscapeTest;)V
   #9 = Methodref          #6.#65         // java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
  #10 = Methodref          #6.#66         // java/lang/Thread.start:()V
  #11 = Long               50l
  #13 = Methodref          #6.#67         // java/lang/Thread.sleep:(J)V
  #14 = Class              #68            // java/lang/InterruptedException
  #15 = Methodref          #14.#69        // java/lang/InterruptedException.printStackTrace:()V
  #16 = Fieldref           #20.#70        // com/liminghuang/thread/thizescape/ThisEscapeTest.num:I
  #17 = Fieldref           #71.#72        // java/lang/System.out:Ljava/io/PrintStream;
  #18 = String             #73            // Race condition detected
  #19 = Methodref          #74.#75        // java/io/PrintStream.println:(Ljava/lang/String;)V
  #20 = Class              #76            // com/liminghuang/thread/thizescape/ThisEscapeTest
  #21 = Methodref          #20.#58        // com/liminghuang/thread/thizescape/ThisEscapeTest."<init>":()V
  #22 = Class              #77            // java/lang/Object
  #23 = Utf8               MyObservable
  #24 = Utf8               InnerClasses
  #25 = Utf8               num
  #26 = Utf8               I
  #27 = Utf8               observable
  #28 = Utf8               Lcom/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable;
  #29 = Utf8               <init>
  #30 = Utf8               ()V
  #31 = Utf8               Code
  #32 = Utf8               LineNumberTable
  #33 = Utf8               LocalVariableTable
  #34 = Utf8               e
  #35 = Utf8               Ljava/lang/InterruptedException;
  #36 = Utf8               this
  #37 = Utf8               Lcom/liminghuang/thread/thizescape/ThisEscapeTest;
  #38 = Utf8               t
  #39 = Utf8               Ljava/lang/Thread;
  #40 = Utf8               StackMapTable
  #41 = Class              #76            // com/liminghuang/thread/thizescape/ThisEscapeTest
  #42 = Class              #62            // java/lang/Thread
  #43 = Class              #68            // java/lang/InterruptedException
  #44 = Utf8               doSomething
  #45 = Utf8               (Ljava/util/Observable;)V
  #46 = Utf8               Ljava/util/Observable;
  #47 = Utf8               main
  #48 = Utf8               ([Ljava/lang/String;)V
  #49 = Utf8               args
  #50 = Utf8               [Ljava/lang/String;
  #51 = Utf8               access$100
  #52 = Utf8               (Lcom/liminghuang/thread/thizescape/ThisEscapeTest;Ljava/util/Observable;)V
  #53 = Utf8               x0
  #54 = Utf8               x1
  #55 = Utf8               SourceFile
  #56 = Utf8               ThisEscapeTest.java
  #57 = NameAndType        #44:#45        // doSomething:(Ljava/util/Observable;)V
  #58 = NameAndType        #29:#30        // "<init>":()V
  #59 = Utf8               com/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable
  #60 = NameAndType        #29:#78        // "<init>":(Lcom/liminghuang/thread/thizescape/ThisEscapeTest$1;)V
  #61 = NameAndType        #27:#28        // observable:Lcom/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable;
  #62 = Utf8               java/lang/Thread
  #63 = Utf8               com/liminghuang/thread/thizescape/ThisEscapeTest$1
  #64 = NameAndType        #29:#79        // "<init>":(Lcom/liminghuang/thread/thizescape/ThisEscapeTest;)V
  #65 = NameAndType        #29:#80        // "<init>":(Ljava/lang/Runnable;)V
  #66 = NameAndType        #81:#30        // start:()V
  #67 = NameAndType        #82:#83        // sleep:(J)V
  #68 = Utf8               java/lang/InterruptedException
  #69 = NameAndType        #84:#30        // printStackTrace:()V
  #70 = NameAndType        #25:#26        // num:I
  #71 = Class              #85            // java/lang/System
  #72 = NameAndType        #86:#87        // out:Ljava/io/PrintStream;
  #73 = Utf8               Race condition detected
  #74 = Class              #88            // java/io/PrintStream
  #75 = NameAndType        #89:#90        // println:(Ljava/lang/String;)V
  #76 = Utf8               com/liminghuang/thread/thizescape/ThisEscapeTest
  #77 = Utf8               java/lang/Object
  #78 = Utf8               (Lcom/liminghuang/thread/thizescape/ThisEscapeTest$1;)V
  #79 = Utf8               (Lcom/liminghuang/thread/thizescape/ThisEscapeTest;)V
  #80 = Utf8               (Ljava/lang/Runnable;)V
  #81 = Utf8               start
  #82 = Utf8               sleep
  #83 = Utf8               (J)V
  #84 = Utf8               printStackTrace
  #85 = Utf8               java/lang/System
  #86 = Utf8               out
  #87 = Utf8               Ljava/io/PrintStream;
  #88 = Utf8               java/io/PrintStream
  #89 = Utf8               println
  #90 = Utf8               (Ljava/lang/String;)V
{
  com.liminghuang.thread.thizescape.ThisEscapeTest$MyObservable observable;
    descriptor: Lcom/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable;
    flags:

  public com.liminghuang.thread.thizescape.ThisEscapeTest();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=5, locals=3, args_size=1
         0: aload_0
         1: invokespecial #2                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: new           #3                  // class com/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable
         8: dup
         9: aconst_null
        10: invokespecial #4                  // Method com/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable."<init>":(Lcom/liminghuang/thread/thizescape/ThisEscapeTest$1;)V
        13: putfield      #5                  // Field observable:Lcom/liminghuang/thread/thizescape/ThisEscapeTest$MyObservable;
        16: new           #6                  // class java/lang/Thread
        19: dup
        20: new           #7                  // class com/liminghuang/thread/thizescape/ThisEscapeTest$1
        23: dup
        24: aload_0
        25: invokespecial #8                  // Method com/liminghuang/thread/thizescape/ThisEscapeTest$1."<init>":(Lcom/liminghuang/thread/thizescape/ThisEscapeTest;)V
        28: invokespecial #9                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
        31: astore_1
        32: aload_1
        33: invokevirtual #10                 // Method java/lang/Thread.start:()V
        36: ldc2_w        #11                 // long 50l
        39: invokestatic  #13                 // Method java/lang/Thread.sleep:(J)V
        42: goto          50
        45: astore_2
        46: aload_2
        47: invokevirtual #15                 // Method java/lang/InterruptedException.printStackTrace:()V
        50: aload_0
        51: bipush        10
        53: putfield      #16                 // Field num:I
        56: return
      Exception table:
         from    to  target type
            36    42    45   Class java/lang/InterruptedException
      LineNumberTable:
        line 24: 0
        line 25: 4
        line 27: 16
        line 39: 32
        line 48: 36
        line 51: 42
        line 49: 45
        line 50: 46
        line 52: 50
        line 53: 56
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           46       4     2     e   Ljava/lang/InterruptedException;
            0      57     0  this   Lcom/liminghuang/thread/thizescape/ThisEscapeTest;
           32      25     1     t   Ljava/lang/Thread;
      StackMapTable: number_of_entries = 2
        frame_type = 255 /* full_frame */
          offset_delta = 45
          locals = [ class com/liminghuang/thread/thizescape/ThisEscapeTest, class java/lang/Thread ]
          stack = [ class java/lang/InterruptedException ]
        frame_type = 4 /* same */

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: new           #20                 // class com/liminghuang/thread/thizescape/ThisEscapeTest
         3: dup
         4: invokespecial #21                 // Method "<init>":()V
         7: pop
         8: return
      LineNumberTable:
        line 62: 0
        line 63: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;

  static void access$100(com.liminghuang.thread.thizescape.ThisEscapeTest, java.util.Observable);
    descriptor: (Lcom/liminghuang/thread/thizescape/ThisEscapeTest;Ljava/util/Observable;)V
    flags: ACC_STATIC, ACC_SYNTHETIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: invokespecial #1                  // Method doSomething:(Ljava/util/Observable;)V
         5: return
      LineNumberTable:
        line 20: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       6     0    x0   Lcom/liminghuang/thread/thizescape/ThisEscapeTest;
            0       6     1    x1   Ljava/util/Observable;
}
SourceFile: "ThisEscapeTest.java"
InnerClasses:
     #7; //class com/liminghuang/thread/thizescape/ThisEscapeTest$1

H:\IdeaProjects\data_structure>

```
## 1. 局部变量表（Local Variable Table）
局部变量表是用于存放**方法参数**和**方法内定义的局部变量**，对应到Class文件，就是方法的Code属性的max_locals数据项确定了该方法所需分配的局部变量表的最大容量。容量以变量槽（Slot）为最小单位，每个Slot没有明确指明所占用内存空间大小，一个Slot可以存放一个32位以内的数据类型，具体包括：
- boolean
- byte
- char
- short
- int
- float
- reference
- returnAddress

第七种reference类型表示对一个对象实例的引用，虚拟机规范没有对其有明确的长度和结构定义。但需要做到亮点：
1. 从此引用可以直接或间接的查找到对象在**Java堆**中的数据存放的*起始地址索引*
2. 此引用中直接或间接地查找对象所属的数据类型在**方法区**中存储的*类型信息*


