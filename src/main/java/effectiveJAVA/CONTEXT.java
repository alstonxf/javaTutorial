package effectiveJAVA;

import effectiveJAVA.Chapter2.rule1.rule1;

public class CONTEXT {
    /**
     * 示例：可以跳转到类，变量，方法等。
     *      @see rule1    类
     *      @see DEMO.E2.exercise2.e2 变量
     *      @see DEMO.E2.exercise2.d 方法
     *         第一章简介
     *         忽略
     *         第二章 创建和销毁对象
     *      @see rule1   1. 考虑使用静态工厂方法替代构造方法
     *      @see rule2   2. 当构造方法参数过多时使用builder模式
     *      @see rule3   3. 使用私有构造方法或枚类实现Singleton属性
     *      @see rule4   4. 使用私有构造方法执行非实例化
     *      @see rule5   5. 使用依赖注入取代硬连接资源
     *      @see rule6   6. 避免创建不必要的对象
     *      @see rule7   7. 消除过期的对象引用
     *      @see rule8   8. 避免使用Finalizer和Cleaner机制
     *      @see rule9   9. 使用try-with-resources语句替代try-finally语句
     *         第三章 所有对象的通用方法
     *      @see rule10   10. 重写equals方法时遵守通用约定
     *      @see rule11   11. 重写equals方法时同时也要重写hashcode方法
     *      @see rule12   12. 始终重写 toString 方法
     *      @see rule13   13. 谨慎地重写 clone 方法
     *      @see rule14   14.考虑实现Comparable接口
     *         第四章 类和接口
     *      @see rule15   15. 使类和成员的可访问性最小化
     *      @see rule16   16. 在公共类中使用访问方法而不是公共属性
     *      @see rule17   17. 最小化可变性
     *      @see rule18   18. 组合优于继承
     *      @see rule19   19. 如果使用继承则设计，并文档说明，否则不该使用
     *      @see rule20   20. 接口优于抽象类
     *      @see rule21   21. 为后代设计接口
     *      @see rule22   22. 接口仅用来定义类型
     *      @see rule23   23. 优先使用类层次而不是标签类
     *      @see rule24   24. 优先考虑静态成员类
     *      @see rule25   25. 将源文件限制为单个顶级类
     *         第五章 泛型
     *      @see rule26   26. 不要使用原始类型
     *      @see rule27   27. 消除非检查警告
     *      @see rule28   28. 列表优于数组
     *      @see rule29   29. 优先考虑泛型
     *      @see rule30   30. 优先使用泛型方法
     *      @see rule31   31. 使用限定通配符来增加API的灵活性
     *      @see rule32   32. 合理地结合泛型和可变参数
     *      @see rule33   33. 优先考虑类型安全的异构容器
     *         第六章 枚举和注解
     *      @see rule34   34. 使用枚举类型替代整型常量
     *      @see rule35   35. 使用实例属性替代序数
     *      @see rule36   36. 使用EnumSet替代位属性
     *      @see rule37   37. 使用EnumMap替代序数索引
     *      @see rule38   38. 使用接口模拟可扩展的枚举
     *      @see rule39   39. 注解优于命名模式
     *      @see rule40   40. 始终使用Override注解
     *      @see rule41   41. 使用标记接口定义类型
     *         第七章 Lambda表达式和Stream流
     *      @see rule42   42. lambda表达式优于匿名类
     *      @see rule43   43. 方法引用优于lambda表达式
     *      @see rule44   44. 优先使用标准的函数式接口
     *      @see rule45   45. 明智审慎地使用Stream
     *      @see rule46   46. 在流中优先使用无副作用的函数
     *      @see rule47   47. 优先使用Collection而不是Stream来作为方法的返回类型
     *      @see rule48   48. 谨慎使用流并行
     *         第八章 方法
     *      @see rule49   49. 检查参数有效性
     *      @see rule50   50. 必要时进行防御性拷贝
     *      @see rule51   51. 仔细设计方法签名
     *      @see rule52   52. 明智而审慎地使用重载
     *      @see rule53   53. 明智而审慎地使用可变参数
     *      @see rule54   54. 返回空的数组或集合不要返回null
     *      @see rule55   55. 明智而审慎地返回Optional
     *      @see rule56   56. 为所有已公开的API元素编写文档注释
     *         第九章 通用编程
     *      @see rule57   57. 最小化局部变量的作用域
     *      @see rule58   58. for-each循环优于传统for循环
     *      @see rule59   59. 熟悉并使用Java类库
     *      @see rule60   60. 需要精确的结果时避免使用float和double类型
     *      @see rule61   61. 基本类型优于装箱的基本类型
     *      @see rule62   62. 当有其他更合适的类型时就不用字符串
     *      @see rule63   63. 注意字符串连接的性能
     *      @see rule64   64. 通过对象的接口引用对象
     *      @see rule65   65. 接口优于反射
     *      @see rule66   66. 明智谨慎地使用本地方法
     *      @see rule67   67. 明智谨慎地进行优化
     *      @see rule68   68. 遵守普遍接受的命名约定
     *         第十章 异常
     *      @see rule69   69. 仅在发生异常的条件下使用异常
     *      @see rule70   70. 对可恢复条件使用已检查异常，对编程错误使用运行时异常
     *      @see rule71   71. 避免不必要地使用检查异常
     *      @see rule72   72. 赞成使用标准异常
     *      @see rule73   73. 抛出合乎于抽象的异常
     *      @see rule74   74. 文档化每个方法抛出的所有异常
     *      @see rule75   75. 在详细信息中包含失败捕获信息
     *      @see rule76   76. 争取保持失败原子性
     *      @see rule77   77. 不要忽略异常
     *         第十一章 并发
     *      @see rule78   78. 同步访问共享的可变数据
     *      @see rule79   79. 避免过度同步
     *      @see rule80   80. EXECUTORS, TASKS, STREAMS 优于线程
     *      @see rule81   81. 优先使用并发实用程序替代wait和notify
     *      @see rule82   82. 线程安全文档化
     *      @see rule83   83. 明智谨慎地使用延迟初始化
     *      @see rule84   84. 不要依赖线程调度器
     *         第十二章 序列化
     *      @see rule85   85. 其他替代方式优于Java本身序列化
     *      @see rule86   86. 非常谨慎地实现SERIALIZABLE接口
     *      @see rule87   87. 考虑使用自定义序列化形式
     *      @see rule88   88. 防御性地编写READOBJECT方法
     *      @see rule89   89. 对于实例控制，枚举类型优于READRESOLVE
     *      @see rule90   90. 考虑序列化代理替代序列化实例

     */
}
