# synchronized原理详解（通俗易懂超级好）
---


## 目标

---


【理解】synchronized底层原理 【掌握】synchronized 的用法

 # 目录
目标
一、synchronized原理
1.1 原子性
1.2 可见性
1.3 有序性
1.4 可重入性
二、synchronized 的用法
三、synchronized 的底层原理
3.1 对象头在JVM中存储的形式
3.1.1 对象头
3.1.2 对象实例数据
3.1.3 对齐填充
3.2 Monitor
3.3 synchronized锁的优化
3.3.1 synchronized锁的状态
3.3.2 偏向锁
3.3.3. 轻量级锁
3.3.4 重量级锁
四、总结
4.1 偏向锁、轻量级锁、重量级锁的应用场景
4.2 偏向锁、轻量级锁、重量级锁的升级过程

# 一、synchronized原理

如果某一个资源被多个线程共享，为了避免因为资源抢占导致资源数据错乱，我们需要对线程进行同步，那么synchronized就是实现线程同步的关键字，可以说在并发控制中是必不可少的部分。

## 1.1 原子性

所谓原子性就是指一个操作或者多个操作，要么全部执行并且执行的过程不会被任何因素打断，要么就都不执行。

在Java中，对基本数据类型的变量的读取和赋值操作是原子性操作，即这些操作是不可被中断的，要么执行，要么不执行。但是像i++、i+=1等操作字符就不是原子性的，它们是分成读取、计算、赋值几步操作，原值在这些步骤还没完成时就可能已经被赋值了，那么最后赋值写入的数据就是脏数据，无法保证原子性。

被synchronized修饰的类或对象的所有操作都是原子的，因为在执行操作之前必须先获得类或对象的锁，直到执行完才能释放，这中间的过程无法被中断（除了已经废弃的stop()方法），即保证了原子性。

（注意！面试时经常会问比较synchronized和volatile，它们俩特性上最大的区别就在于原子性，volatile不具备原子性。）

## 1.2 可见性

可见性是指多个线程访问一个资源时，该资源的状态、值信息等对于其他线程都是可见的。

synchronized和volatile都具有可见性，其中synchronized对一个类或对象加锁时，一个线程如果要访问该类或对象必须先获得它的锁，而这个锁的状态对于其他任何线程都是可见的，并且在释放锁之前会将 对变量的修改 刷新到主存当中，保证资源变量的可见性，如果某个线程占用了该锁，其他线程就必须在锁池中等待锁的释放。

而volatile的实现类似，被volatile修饰的变量，每当值需要修改时都会立即更新主存，主存是共享的，所有线程可见，所以确保了其他线程读取到的变量永远是最新值，保证可见性。

## 1.3 有序性

有序性值程序执行的顺序按照代码先后执行。

synchronized和volatile都具有有序性，Java允许编译器和处理器对 指令进行重排 （指令重排会提高运行效率），但是指令重排并不会影响单线程的顺序，它影响的是多线程并发执行的顺序性。synchronized保证了每个时刻都只有一个线程访问同步代码块，也就确定了线程执行同步代码块是分先后顺序的，保证了有序性。

## 1.4 可重入性

synchronized和ReentrantLock都是可重入锁。当一个线程试图操作一个由其他线程持有的对象锁的临界资源时，将会处于阻塞状态，但当一个线程再次请求自己持有对象锁的临界资源时，这种情况属于重入锁。通俗一点讲就是说一个线程拥有了锁仍然还可以重复申请同一个锁。可重复锁，表示该锁能够支持一个线程对资源的重复加锁。

# 二、synchronized 的用法

synchronized可以修饰：

>  
 - 静态方法- 成员函数- 代码块 


但是归根究底，synchronized上锁的资源只有两类：一个是对象，一个是类。 即synchronized底层其实就是给对象或者类上锁。

通过代码讲解，有如下代码：

<img src="https://img-blog.csdnimg.cn/d6ab660315424645bbef1974d43e7402.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>
1.  先看第一个加 synchronized 的方法 add1()。可以看到，add1() 是没有加static修饰的，意味着 add1() 是归Test1的实例对象管的，因此synchronized 加的锁 是给 Test1实例对象加的。 1.  再看第二个加 synchronized 的方法 add2()。可以看到，add2() 是加了static的，即它是静态方法，静态方法是属于类的，因此这里的 synchronized 所加的锁是给Test1类 加的。 1.  最后是method 中的两个同步块，第一个锁定的锁定 Test1.class，即给 Test1类 加锁。而下面的同步块是给 instance加锁，instance是 Test1的实例对象，因此就是给Test1的实例对象加锁。 
（因此，弄清 synchronized 的关键是，只要记住进入同步方法或同步块必须先获得相对应的锁才行。）

下面是一个容易进入误区的例子：

下面代码是两个线程都对同一个变量i进行加操作，两个线程都是要加100万 次，那么按理说，运行结果应该是200万才对，但是程序运行结果往往是低于200万。而且add()方法还加了synchronized关键字，保证线程安全，但是为什么结果还是少于200万呢？

我们要记住，synchronized 加锁是加给对象的( 若是static方法则是加给类的 )，所以线程在执行带 synchronized 的方法前，必须先获得 synchronized 加的锁。那么我们看看我们在创建线程时，两个线程(thread1、thread2) 是分别 new 了一个Test2对象的，意味着，两个线程的 synchronized 加锁并不是加在同一个实例对象上的，并没有达到同步的效果。

<img src="https://img-blog.csdnimg.cn/ba330d73679943a7b51d03690a8e3751.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

那我们应该怎么修改呢？我们只需要在创建线程时，传入的参数是同一个对象即可。

<img src="https://img-blog.csdnimg.cn/34ef70e0394b451584997dbaafd8062d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

# 三、synchronized 的底层原理

在讲上锁之前，先了解以下在JVM中，对象的存储状态。

对象存储在堆中，主要分为三部分内容：对象头、对象实例数据、对齐填充。

在理解锁实现原理之前先了解一下Java的对象头和Monitor。

## 3.1 对象头在JVM中存储的形式

<img src="https://img-blog.csdnimg.cn/f512c01c066242a4bfcdf8959157f3e8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

而synchronized则和对象头中的 Mark Word字段息息相关。

下图是 Mark Word 的具体字段：

<img src="https://img-blog.csdnimg.cn/c554fde025904030a650f8054cfe8755.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

## 3.1.1 对象头

对象头由如下两个部分组成：

>  
 - Mark Word：存储的是 对象的 hashCode、锁信息、或分代年龄、GC标注等信息。- Class Metadata Address： 存储对象所属类(元数据) 的指针，JVM通过这个确定这个对象属于哪个类。 


## 3.1.2 对象实例数据

上图中，date就属于对象的实例数据

## 3.1.3 对齐填充

JVM要求对象占用的空间必须是8的倍数，方便内存分配，因此这部分是为了填满不够的空间凑数用的。

## 3.2 Monitor

每一个锁都对应一个Monitor对象，每个对象都有一个与之关联的Monitor对象，在HotSpot虚拟机中它是由ObjectMonitor实现的(C++实现)。

Monitor 对象 存在于每个 Java对象的对象头 里(存储的指针的指向)。

Monitor对象属性如下：（HotSpot1.7代码）

```
//详细介绍重要变量的作用
ObjectMonitor() {<!-- -->
    _header       = NULL;
    _count        = 0;   // 重入次数
    _waiters      = 0,   // 等待线程数
    _recursions   = 0;
    _object       = NULL;
    _owner        = NULL;  // 当前持有锁的线程
    _WaitSet      = NULL;  // 调用了 wait 方法的线程被阻塞 放置在这里
    _WaitSetLock  = 0 ;
    _Responsible  = NULL ;
    _succ         = NULL ;
    _cxq          = NULL ;
    FreeNext      = NULL ;
    _EntryList    = NULL ; // 等待锁 处于block的线程 有资格成为候选资源的线程
    _SpinFreq     = 0 ;
    _SpinClock    = 0 ;
    OwnerIsThread = 0 ;
  }

```

**ObjectMonitor 中有几个值得关注的成员变量：**
- _owner：指向获得ObjectMonitor对象的线程。（即获得锁的线程）- _EntryList：处于等待锁block状态的线程，会被加入到这里。- _WaitSet：处理wait状态的线程，会被加入到这里。（调用同步对象wait方法） （block和wait的区别：
总结： BLOCKED 和WAITING 都是非活动线程的状态. WAITING 线程是已经分配到了CPU时间，但是需要等待事件发生所以主动释放了CPU，直到某些事件完成后调用了notify()唤醒, 也就是WAITTING线程是自己现在不想要CPU时间，但是BLOCKED线程是想要的，但是BLOCKED线程没有获得锁，所以轮不到BLOCKED线程。）

当多个线程同时访问一同步代码时，首先会进入 _EntryList 集合，进行阻塞等待，当线程获取到对象的 Monitor(锁)后，会把_owner变量指向该线程，同时 Monitor 的计数器_count自加1。

若线程调用同步对象的方法 wait()方法，将释放当前持有的Monitor(锁)，_owner变量重置为null，且 _count会自减1，同时线程进入 _WaitSet中等待唤醒。当线程执行完同步代码后，也将_owner和_count重置。

具体如下图： <img src="https://img-blog.csdnimg.cn/5f05ee11e8b945b3a1b0aac8345eb79b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

重量级锁是依赖对象内部的Monitor锁来实现的，而Monitor又依赖操作系统的MutexLock(互斥锁)来实现的，所以重量级锁也称为互斥锁。

## 3.3 synchronized锁的优化

上面说过，Monitor实现的锁是互斥锁，也称为重量级锁。

**但是重量级锁有一个缺点就是：线程开销很大。**

原因是当系统检测到锁是重量级锁后，会把等待的想要获得锁的线程进行阻塞，被阻塞的线程不会消耗CPU，但是阻塞或者唤醒一个线程时，都需要操作系统来帮忙，这就需要从用户态切换到内核态，而转换状态是需要消耗很多时间的，有可能比用户执行代码的时间还长。

## 3.3.1 synchronized锁的状态

Java的开发团队一直对synchronized进行优化，其中最大一次优化就是在jdk6的时候，新增了两个锁状态，分别是：偏向锁 和 轻量级锁

现在synchronized一共有四种锁状态：
- 无锁- 偏向锁- 轻量级锁- 重量级锁
锁的级别由低到高分别是：无锁状态->偏向锁状态->轻量级锁状态->重量级锁状态。这几个状态会随着锁的竞争情况逐渐升级（只能升级，不能降级），也叫锁的膨胀。

## 3.3.2 偏向锁

一句话总结他的作用：减少同一线程获取锁的代价。

**“偏向”的意思是：**

>  
 **偏向锁假定将来只有第一个申请锁的线程会使用锁（不会有任何线程再来申请锁），因此，只需要在Mark Word中CAS记录owner（本质上也是更新，但初始值为空），如果记录成功，则偏向锁获取成功，记录锁状态为偏向锁，以后当前线程等于owner就可以零成本的直接获得锁；否则，说明有其他线程竞争，膨胀为轻量级锁。** 


**当一个线程多次获取同一个锁时(可重入锁)，**

为什么一个线程需要反复加锁？ 例如有些场景，某个对象中的 synchronized方法1 调用 另一个synchronized方法2，但方法1和方法2都是被同一个线程调用的，且处于同一对象，那么相当于这个线程获取了两次相同的锁，这就是可重入锁。要是不可重入锁，那么方法2就执行不了了。

**为什么要引入偏向锁？**

>  
 引入偏向锁是为了在无多线程竞争的情况下尽量减少不必要的轻量级锁执行路径，因为轻量级锁的获取及释放依赖多次 CAS 原子指令，而偏向锁只需要在置换 ThreadID 的时候依赖一次 CAS 原子指令，代价就是一旦出现多线程竞争的情况就必须撤销偏向锁。 


具体来说就是因为经过 HotSpot 的作者大量的研究发现，大多数时候是不存在锁竞争的，常常是一个线程多次获得同一个锁，因此如果每次都要竞争锁会增大很多没有必要付出的代价，为了降低获取锁的代价，才引入的偏向锁。

为了方便理解加锁和解锁过程，再把 markword 放在下面： <img src="https://img-blog.csdnimg.cn/7f823aca34424478a8b372561e016580.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

加锁过程：

>  
 当锁对象第一次被线程获取的时候，虚拟机把对象头中的锁标志位设为“01”，即偏向模式。同时使用CAS操作把获取到这个锁的线程的ID记录在对象的Mark Word之中的偏向线程ID，并将是否偏向锁的状态位置置为1。 如果CAS操作成功，持有偏向锁的线程以后每次进入这个锁相关的同步块时，直接检查markword中的ThreadId是否和 自身线程Id一致，如果一致，则认为当前线程已经获取了锁，JVM就可以不再进行任何同步操作（例如Locking、Unlocking及对Mark Word的Update等）。 当有另外一个线程去尝试获取这个锁时，偏向模式就宣告结束。根据锁对象目前是否处于被锁定的状态，撤销偏向（Revoke Bias）后恢复到未锁定状态或 轻量级锁定（标志位为“00”）的状态 解锁过程： 


一个对象刚开始实例化的时候，没有任何线程来访问它的时候。它是可偏向的，意味着，它现在认为只可能有一个线程来访问它，所以当第一个线程来访问它的时候，它会偏向这个线程，此时，对象持有偏向锁。偏向第一个线程，这个线程在修改对象头成为偏向锁的时候使用CAS操作，并将对象头中的ThreadID改成自己的ID，之后再次访问这个对象时，只需要对比ID，不需要再使用CAS在进行操作。

一旦有第二个线程访问这个对象，因为偏向锁不会主动释放，所以第二个线程可以看到对象是偏向状态，这时表明在这个对象上已经存在竞争了，检查原来持有该对象锁的线程是否依然存活，如果挂了，则可以将对象变为无锁状态，然后重新偏向新的线程，如果原来的线程依然存活，则马上执行那个线程的操作栈，检查该对象(锁)的使用情况，如果仍然需要持有偏向锁，则偏向锁升级为轻量级锁，（偏向锁就是这个时候升级为轻量级锁的）。如果不存在使用了，则可以将对象(锁)回复成无锁状态，然后重新偏向。

## 3.3.3. 轻量级锁

当有别的线程参与到偏向锁的竞争中时，会先判断 markword 中的线程ID与这个线程是否一致，如果不一致，则会立即撤销偏向锁，升级为轻量级锁。

**线程拿到锁的底层原理：**

>  
 每个线程都会在自己的栈中维护一个 LockRecord(LR)，然后每个线程在竞争锁时，都试图将 锁对象头 中的markword 设置为指向自己LR的指针，哪个线程设置成功，则意味着哪个线程成功获取到锁。 


**加锁过程：**

>  
 在代码进入同步块前，如果该同步块没有被锁定(即锁的标志位为“01”)，那么JVM在将当前线程的栈帧中，创建一个 LockRecord(锁记录LR)，并将锁对象头中的 markWord 信息复制到锁记录(LR)中，这个官方称为 Displaced Mard Word。 然后线程尝试使用 CAS 将对象头中的 MarkWord 替换为指向锁记录(LR)的指针。 


<img src="https://img-blog.csdnimg.cn/4d710114a5aa4172971c9bdb392d7cc9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

如果这个更新动作成功了，那么这个线程就拥有了该对象的锁，并且对象Mark Word的锁标志位（Mark Word的最后2bit）将转变为“00”，即表示此对象处于轻量级锁定状态。如果失败，表示有其他线程竞争锁，当前线程便尝试使用自旋来获取锁。如果在自旋一定次数后仍为获得锁，那么轻量级锁将会升级成重量级锁。

**解锁过程：**

>  
 与加锁一样，用CAS操作，如果对象的markword 仍然指向着线程的 LockRecord(锁记录 LR)，那就用CAS操作把对象当前的Mark Word和线程中复制的Displaced Mark Word替换回来。 


## 3.3.4 重量级锁

当线程的自旋次数过长依旧没获取到锁，为避免CPU无端耗费，锁由轻量级锁升级为重量级锁。获取锁的同时会阻塞其他正在竞争该锁的线程，依赖对象内部的监视器（monitor）实现，monitor又依赖操作系统底层，需要从用户态切换到内核态，成本非常高。

**为什么成本高？**

>  
 当系统检查到锁是重量级锁之后，会把等待想要获得锁的线程进行阻塞，被阻塞的线程不会消耗cpu。但是阻塞或者唤醒一个线程时，都需要操作系统来帮忙，这就需要从用户态转换到内核态，而转换状态是需要消耗很多时间的，有可能比用户执行代码的时间还要长。 


# 四、总结

## 4.1 偏向锁、轻量级锁、重量级锁的应用场景

<img src="https://img-blog.csdnimg.cn/a067076671264959b2e5208e4afea8c9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

由于轻量级锁会自旋，即不会放弃CPU，那么对一些执行时间短的任务而言，用轻量级锁可以减少线程切换的时间(对比重量级锁)。

>  
 偏向锁通过对比 Mark Word 解决加锁问题，避免执行CAS操作。 轻量级锁是通过用 CAS 操作和自旋来解决加锁问题，避免线程阻塞和唤醒而影响性能。 重量级锁是将除了拥有锁的线程以外的线程都阻塞。 


## 4.2 偏向锁、轻量级锁、重量级锁的升级过程

<img src="https://img-blog.csdnimg.cn/bd7b6c8c979f4db2a5c14baabb56db26.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/124187639