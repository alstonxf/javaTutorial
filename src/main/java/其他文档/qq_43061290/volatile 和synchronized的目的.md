# volatile 和synchronized的目的
问题：主要是多线程并发问题；

## volatile关键字

#### volatile关键字如何保存内存可见性

volatile关键字的作用
1. 保存内存的可见性1. 防止指令重排1. <font color="#dd0000">注意 :volatile并不保证原子性</font>
内存可见性
- volatile保证可见性的原理是在每次访问变量时都会进行一次刷新，因此每次访问都是主内存中最新的版本。所以volatile作用之一就是保证变量修改的实时可见性。
当且仅当满足一下所有条件时，才应该使用volatile变量
- 对变量的写入操作不依赖变量的当前值，或者你能确保只有单个线程更新变量的值。- 该变量没有包含在具有其他变量的不变式中。
volatie使用建议
- 在两个或者多个线程需要访问的成员变量上使用volatile。当要访问的变量已在synchronized代码块中，或者为常量时，没必要使用volatile- 由于使用volatile屏蔽掉了jvm中必要的代码优化，所以在效率上比较底，所以一定要在必要时才使用关键字# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/108623669