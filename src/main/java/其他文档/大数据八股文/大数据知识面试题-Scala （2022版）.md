# 大数据知识面试题-Scala （2022版）
|序列号|内容|链接|
| :-----| :-----| :-----|
|1|大数据知识面试题-通用（2022版）|https://blog.csdn.net/qq_43061290/article/details/124819089|
|2|大数据知识面试题-Hadoop（2022版）|https://blog.csdn.net/qq_43061290/article/details/124822293|
|3|大数据知识面试题-MapReduce和yarn（2022版）|https://blog.csdn.net/qq_43061290/article/details/124841929|
|4|大数据知识面试题-Zookeepr （2022版）|https://blog.csdn.net/qq_43061290/article/details/124548428|
|5|大数据知识面试题-Hive （2022版）|https://blog.csdn.net/qq_43061290/article/details/125105485|
|6|大数据知识面试题-Flume（2022版）|https://blog.csdn.net/qq_43061290/article/details/125132610|
|7|大数据知识面试题-Hbase（2022版）|https://blog.csdn.net/qq_43061290/article/details/125145399|
|8|大数据知识面试题-sqoop（2022版）|https://blog.csdn.net/qq_43061290/article/details/125145736|
|9|大数据知识面试题-Kafka（2022版）|https://blog.csdn.net/qq_43061290/article/details/125145841|
|10|大数据知识面试题-Azkaban（2022版）|https://blog.csdn.net/qq_43061290/article/details/125146859|
|11|大数据知识面试题-Scala （2022版）|https://blog.csdn.net/qq_43061290/article/details/125145976|
|12|大数据知识面试题-Spark （2022版）|https://blog.csdn.net/qq_43061290/article/details/125146030|
|13|大数据知识面试题-Flink（2022版）|https://blog.csdn.net/qq_43061290/article/details/125182137|


 # 文章目录
1、scala
1.1、scala介绍
1.2、scala解释器
1.3、scala的基本语法
1.3.1、声明变量
1.3.2、字符串
1.3.3、数据类型
1.3.3.1、scala类型层次结构
1.3.4、表达式
1.3.4.1、条件表达式
1.3.4.2、块表达式
1.3.5、循环
1.3.5、for循环
1.3.5.1、嵌套for循环
1.3.5、while循环
1.3.6、方法
1.3.6.1、方法参数
1.3.7、函数
1.4、数据结构
1.4.1、数组
1.4.1、元组
1.4.2、列表
1.4.1、set
1.4.1、映射
1.5、函数式编程
1.6、伴生对象
1.7、样例类
1.8、样例对象
1.9、模式匹配
1.9.1、 简单模式匹配
1.9.2、匹配样例类
1.10、高阶函数

## 1、scala

<img src="https://img-blog.csdnimg.cn/91c6b786feda422494159182a7095af7.png" alt="在这里插入图片描述"/>

### 1.1、scala介绍

​ scala是运行在`JVM`上的**多范式编程语言**，同时支持面向对象和面向函数式编程。

### 1.2、scala解释器

要启动scala解释器，只需要以下几步：
- 按住`windows键 + r`- 输入`scala`即可
<img src="https://img-blog.csdnimg.cn/aa247c21fb014d7f99bf2b26044d597d.png#pic_center" alt="在这里插入图片描述"/>
- 在scala命令提示窗口中执行`:quit`，即可退出解释器
### 1.3、scala的基本语法

#### 1.3.1、声明变量

在scala中，可以使用`val`或者`var`来定义变量，语法格式如下:

```
val/var 变量标识:变量类型 = 初始值

```

其中
- `val`定义的是不可重新赋值的变量- `var`定义的是可重新赋值的变量
>  
 [!NOTE] 
 - scala中定义变量类型写在变量名后面- scala的语句最后不需要添加分号 


**问题**：val 和 var修饰的变量有什么区别？

#### 1.3.2、字符串

scala提供多种定义字符串的方式，将来我们可以根据需要来选择最方便的定义方式。
<li> 使用双引号 <pre><code class="prism language-scala">val/var 变量名 = “字符串”
</code></pre> </li><li> 使用插值表达式 <pre><code class="prism language-scala">val/var 变量名 = s"${<!-- -->变量/表达式}字符串"
</code></pre> </li><li> 使用三引号 <pre><code class="prism language-scala">val/var 变量名 = """字符串1
字符串2"""    
</code></pre> ​ </li>
#### 1.3.3、数据类型

|基础类型|类型说明|
| :-----| :-----|
|Byte|8位带符号整数|
|Short|16位带符号整数|
|Int|32位带符号整数|
|Long|64位带符号整数|
|Char|16位无符号Unicode字符|
|String|Char类型的序列（字符串）|
|Float|32位单精度浮点数|
|Double|64位双精度浮点数|
|Boolean|true或false|


注意下 scala类型与Java的区别

>  
 [!NOTE] 
 - scala中所有的类型都使用**大写字母**开头- 整形使用`Int`而不是Integer- scala中定义变量可以不写类型，让scala编译器自动推断 


##### 1.3.3.1、scala类型层次结构

<img src="https://img-blog.csdnimg.cn/dbd26712568c432d9dd5e37ffc5cb6f8.png#pic_center" alt="在这里插入图片描述"/>

#### 1.3.4、表达式

##### 1.3.4.1、条件表达式

条件表达式就是if表达式，if表达式可以根据给定的条件是否满足，根据条件的结果（真或假）决定执行对应的操作。

scala条件表达式的语法和Java一样。与Java不一样的是，

>  
 [!NOTE] 
 - 在scala中，条件表达式也是有返回值的- 在scala中，没有三元表达式，可以使用if表达式替代三元表达式 


##### 1.3.4.2、块表达式
- scala中，使用{}表示一个块表达式- 和if表达式一样，块表达式也是有值的- 值就是最后一个表达式的值
**问题**

请问以下代码，变量a的值是什么？

```
scala> val a = {<!-- -->
     | println("1 + 1")
     | 1 + 1
     | }

```

#### 1.3.5、循环

在scala中，可以使用for和while，但一般推荐使用for表达式，因为for表达式语法更简洁

##### 1.3.5、for循环

语法

```
for(i <- 表达式/数组/集合) {<!-- -->
    // 表达式
}

```

###### 1.3.5.1、嵌套for循环

使用for表达式，打印以下字符

```
*****
*****
*****

```

**步骤**
1. 使用for表达式打印3行，5列星星1. 每打印5个星星，换行
**参考代码**

```
for(i <- 1 to 3; j <- 1 to 5) {<!-- -->print("*");if(j == 5) println("")}

```

##### 1.3.5、while循环

scala中while循环和Java中是一致的

**示例**

打印1-10的数字

**参考代码**

```
scala> var i = 1
i: Int = 1

scala> while(i <= 10) {<!-- -->
     | println(i)
     | i = i+1
     | }

```

#### 1.3.6、方法

**语法**

```
def methodName (参数名:参数类型, 参数名:参数类型) : [return type] = {<!-- -->
    // 方法体：一系列的代码
}

```

>  
 [!NOTE] 
 - 参数列表的参数类型不能省略- 返回值类型可以省略，由scala编译器自动推断- 返回值可以不写return，默认就是{}块表达式的值 


**示例**
1. 定义一个方法，实现两个整形数值相加，返回相加后的结果1. 调用该方法
**参考代码**

```
scala> def add(a:Int, b:Int) = a + b
m1: (x: Int, y: Int)Int

scala> add(1,2)
res10: Int = 3

```

##### 1.3.6.1、方法参数

scala中的方法参数，使用比较灵活。它支持以下几种类型的参数：
- 默认参数- 带名参数- 变长参数
**默认参数**

在定义方法时可以给参数定义一个默认值。

**示例**
1. 定义一个计算两个值相加的方法，这两个值默认为01. 调用该方法，不传任何参数
**参考代码**

```
// x，y带有默认值为0 
def add(x:Int = 0, y:Int = 0) = x + y
add()

```

**带名参数**

在调用方法时，可以指定参数的名称来进行调用。

**示例**
1. 定义一个计算两个值相加的方法，这两个值默认为01. 调用该方法，只设置第一个参数的值
**参考代码**

```
def add(x:Int = 0, y:Int = 0) = x + y
add(x=1)

```

**变长参数**

如果方法的参数是不固定的，可以定义一个方法的参数是变长参数。

语法格式：

```
def 方法名(参数名:参数类型*):返回值类型 = {<!-- -->
    方法体
}

```

>  
 [!NOTE] 
 在参数类型后面加一个`*`号，表示参数可以是0个或者多个 


**示例**
1. 定义一个计算若干个值相加的方法1. 调用方法，传入以下数据：1,2,3,4,5
**参考代码**

```
scala> def add(num:Int*) = num.sum
add: (num: Int*)Int

scala> add(1,2,3,4,5)
res1: Int = 15

```

#### 1.3.7、函数

**语法**

```
val 函数变量名 = (参数名:参数类型, 参数名:参数类型....) => 函数体

```

>  
 [!TIP] 
 - 函数是一个**对象**（变量）- 类似于方法，函数也有输入参数和返回值- 函数定义不需要使用`def`定义- 无需指定返回值类型 


**示例**
1. 定义一个两个数值相加的函数1. 调用该函数
**参考代码**

```
scala> val add = (x:Int, y:Int) => x + y
add: (Int, Int) => Int = <function2>

scala> add(1,2)
res3: Int = 3

```

### 1.4、数据结构

#### 1.4.1、数组

**定长数组**
- 定长数组指的是数组的**长度**是**不允许改变**的- 数组的**元素**是**可以改变**的
**语法**

```
// 通过指定长度定义数组
val/var 变量名 = new Array[元素类型](数组长度)

// 用元素直接初始化数组
val/var 变量名 = Array(元素1, 元素2, 元素3...)

```

>  
 [!NOTE] 
 - 在scala中，数组的泛型使用`[]`来指定- 使用`()`来获取元素 


**变长数组**

创建变长数组，需要提前导入ArrayBuffer类`import scala.collection.mutable.ArrayBuffer`

**语法**
<li> 创建空的ArrayBuffer变长数组，语法结构： <pre><code class="prism language-scala">val/var a = ArrayBuffer[元素类型]()
</code></pre> </li><li> 创建带有初始元素的ArrayBuffer <pre><code class="prism language-scala">val/var a = ArrayBuffer(元素1，元素2，元素3....)
</code></pre> </li>
**数组的相关操作**
- 使用`+=`添加元素- 使用`-=`删除元素- 使用`++=`追加一个数组到变长数组- 使用for遍历数组
#### 1.4.1、元组

元组可以用来包含一组不同类型的值。例如：姓名，年龄，性别，出生年月。元组的元素是不可变的。

**语法**

使用括号来定义元组

```
val/var 元组 = (元素1, 元素2, 元素3....)

```

使用箭头来定义元组（元组只有两个元素）

```
val/var 元组 = 元素1->元素2

```

**示例**

定义一个元组，包含一个学生的以下数据

|id|姓名|年龄|地址|
| :-----| :-----| :-----| :-----|
|1|zhangsan|20|beijing|


**参考代码**

```
scala> val a = (1, "zhangsan", 20, "beijing")
a: (Int, String, Int, String) = (1,zhangsan,20,beijing)

```

**访问元组**

使用_1、_2、_3…来访问元组中的元素，_1表示访问第一个元素，依次类推

**示例**
- 定义一个元组，包含一个学生的姓名和性别，“zhangsan”, “male”- 分别获取该学生的姓名和性别
**参考代码**

```
scala> val a = "zhangsan" -> "male"
a: (String, String) = (zhangsan,male)

// 获取第一个元素
scala> a._1
res41: String = zhangsan

// 获取第二个元素
scala> a._2
res42: String = male

```

#### 1.4.2、列表

**不可变列表**

**语法**

使用`List(元素1, 元素2, 元素3, ...)`来创建一个不可变列表，语法格式：

```
val/var 变量名 = List(元素1, 元素2, 元素3...)

```

使用`Nil`创建一个不可变的空列表

```
val/var 变量名 = Nil

```

使用`::`方法创建一个不可变列表

```
val/var 变量名 = 元素1 :: 元素2 :: Nil

```

>  
 [!TIP] 
 使用**::**拼接方式来创建列表，必须在最后添加一个**Nil 


**可变列表**

可变列表就是列表的元素、长度都是可变的。

要使用可变列表，先要导入`import scala.collection.mutable.ListBuffer`

>  
 [!NOTE] 
 - 可变集合都在`mutable`包中- 不可变集合都在`immutable`包中（默认导入） 


**定义**

使用ListBuffer[元素类型]()创建空的可变列表，语法结构：

```
val/var 变量名 = ListBuffer[Int]()

```

使用ListBuffer(元素1, 元素2, 元素3…)创建可变列表，语法结构：

```
val/var 变量名 = ListBuffer(元素1，元素2，元素3...)

```

**可变列表的操作**
- 获取元素（使用括号访问`(索引值)`）- 添加元素（`+=`）- 追加一个列表（`++=`）- 更改元素（`使用括号获取元素，然后进行赋值`）- 删除元素（`-=`）- 转换为List（`toList`）- 转换为Array（`toArray`）
#### 1.4.1、set

Set(集)是代表没有重复元素的集合。Set具备以下性质：
1. 元素不重复1. 不保证插入顺序
scala中的集也分为两种，一种是不可变集，另一种是可变集。

**不可变集**

**语法**

创建一个空的不可变集，语法格式：

```
val/var 变量名 = Set[类型]()

```

给定元素来创建一个不可变集，语法格式：

```
val/var 变量名 = Set(元素1, 元素2, 元素3...)

```

**可变集**

**定义**

可变集合不可变集的创建方式一致，只不过需要提前导入一个可变集类。

手动导入：`import scala.collection.mutable.Set`

#### 1.4.1、映射

Map可以称之为映射。它是由键值对组成的集合。在scala中，Map也分为不可变Map和可变Map。

**不可变Map**

**定义**

语法

```
val/var map = Map(键->值, 键->值, 键->值...)	// 推荐，可读性更好
val/var map = Map((键, 值), (键, 值), (键, 值), (键, 值)...)

```

示例
<li> 定义一个映射，包含以下学生姓名和年龄数据 <pre><code class="prism language-scala">"zhangsan", 30
"lisi", 40
</code></pre> </li>1.  获取zhangsan的年龄 
**可变Map**

**定义**

定义语法与不可变Map一致。但定义可变Map需要手动导入`import scala.collection.mutable.Map`

**示例**
<li> 定义一个映射，包含以下学生姓名和年龄数据 <pre><code class="prism language-scala">"zhangsan", 30
"lisi", 40
</code></pre> </li>1.  修改zhangsan的年龄为20 
```
scala> val map = Map("zhangsan"->30, "lisi"->40)
map: scala.collection.mutable.Map[String,Int] = Map(lisi -> 40, zhangsan -> 30)

// 修改value
scala> map("zhangsan") = 20

```

### 1.5、函数式编程
- 遍历（`foreach`）- 映射（`map`）- 映射扁平化（`flatmap`）- 过滤（`filter`）- 是否存在（`exists`）- 排序（`sorted`、`sortBy`、`sortWith`）- 分组（`groupBy`）- 聚合计算（`reduce`）- 折叠（`fold`）
### 1.6、伴生对象

一个class和object具有同样的名字。这个object称为**伴生对象**，这个class称为**伴生类**
- 伴生对象必须要和伴生类一样的名字- 伴生对象和伴生类在同一个scala源文件中- 伴生对象和伴生类可以互相访问private属性
**参考代码**

```
object _11ObjectDemo {<!-- -->

  class CustomerService {<!-- -->
    def save() = {<!-- -->
      println(s"${<!-- -->CustomerService.SERVICE_NAME}:保存客户")
    }
  }

  // CustomerService的伴生对象
  object CustomerService {<!-- -->
    private val SERVICE_NAME = "CustomerService"
  }

  def main(args: Array[String]): Unit = {<!-- -->
    val customerService = new CustomerService()
    customerService.save()
  }
}

```

### 1.7、样例类

样例类是一种特殊类，它可以用来快速定义一个用于**保存数据**的类（类似于Java POJO类）

**语法格式**

```
case class 样例类名(var/val 成员变量名1:类型1, 成员变量名2:类型2, 成员变量名3:类型3)

```
- 如果要实现某个成员变量可以被修改，可以添加var- 默认为val，可以省略
**需求**
- 定义一个Person样例类，包含姓名和年龄成员变量- 创建样例类的对象实例（“张三”、20），并打印它
**参考代码**

```
object _01CaseClassDemo {<!-- -->
  case class Person(name:String, age:Int)

  def main(args: Array[String]): Unit = {<!-- -->
    val zhangsan = Person("张三", 20)

    println(zhangsan)
  }
}

```

### 1.8、样例对象

它主要用在两个地方：
1. 作为没有任何参数的消息传递
使用case object可以创建样例对象。样例对象是单例的，而且它**没有主构造器**

**语法格式**

```
case object 样例对象名

```

### 1.9、模式匹配

#### 1.9.1、 简单模式匹配

**语法格式**

```
变量 match {<!-- -->
    case "常量1" => 表达式1
    case "常量2" => 表达式2
    case "常量3" => 表达式3
    case _ => 表达式4		// 默认配
}

```

**示例**

**需求说明**
1. 从控制台输入一个单词（使用StdIn.readLine方法）1. 判断该单词是否能够匹配以下单词，如果能匹配，返回一句话1. 打印这句话
|单词|返回|
| :-----| :-----|
|hadoop|大数据分布式存储和计算框架|
|zookeeper|大数据分布式协调服务框架|
|spark|大数据分布式内存计算框架|
|未匹配|未匹配|


**参考代码**

```
println("请输出一个词：")
// StdIn.readLine表示从控制台读取一行文本
val name = StdIn.readLine()

val result = name match {<!-- -->
    case "hadoop" => "大数据分布式存储和计算框架"
    case "zookeeper" => "大数据分布式协调服务框架"
    case "spark" => "大数据分布式内存计算框架"
    case _ => "未匹配"
}

println(result)

```

#### 1.9.2、匹配样例类

scala可以使用模式匹配来匹配样例类，从而可以快速获取样例类中的成员数据。后续，我们在开发Akka案例时，还会用到。

**示例**

**需求说明**
<li>创建两个样例类Customer、Order 
  <ul>- Customer包含姓名、年龄字段- Order包含id字段
**参考代码**

```
// 1. 创建两个样例类
case class Person(name:String, age:Int)
case class Order(id:String)

def main(args: Array[String]): Unit = {<!-- -->
    // 2. 创建样例类对象，并赋值为Any类型
    val zhangsan:Any = Person("张三", 20)
    val order1:Any = Order("001")

    // 3. 使用match...case表达式来进行模式匹配
    // 获取样例类中成员变量
    order1 match {<!-- -->
        case Person(name, age) => println(s"姓名：${<!-- -->name} 年龄：${<!-- -->age}")
        case Order(id1) => println(s"ID为：${<!-- -->id1}")
        case _ => println("未匹配")
    }
}

```

### 1.10、高阶函数

高阶函数包含
- 作为值的函数- 匿名函数- 闭包- 柯里化等等
**作为值的函数**

**示例说明**

将一个整数列表中的每个元素转换为对应个数的小星星

```
List(1, 2, 3...) => *, **, *** 

```

**步骤**
1. 创建一个函数，用于将数字装换为指定个数的小星星1. 创建一个列表，调用map方法1. 打印转换为的列表
**参考代码**

```
val func = (num:Int) => "*" * num

println((1 to 10).map(func))

```

**匿名函数**

没有赋值给变量的函数就是**匿名函数**

**参考代码**

```
println((1 to 10).map(num => "*" * num))
// 因为此处num变量只使用了一次，而且只是进行简单的计算，所以可以省略参数列表，使用_替代参数
println((1 to 10).map("*" * _))

```

**闭包**

闭包其实就是一个函数，只不过这个函数的返回值依赖于声明在函数外部的变量。

可以简单认为，就是可以访问不在当前作用域范围的一个函数。

**示例**

定义一个闭包

```
val y=10

val add=(x:Int)=>{<!-- -->
    x+y
}

println(add(5)) // 结果15

```

add函数就是一个闭包

**柯里化**

柯里化（Currying）是指将原先接受多个参数的方法转换为多个参数列表的过程。

**示例**

**示例说明**
- 编写一个方法，用来完成两个Int类型数字的计算- 具体如何计算封装到函数中- 使用柯里化来实现上述操作
**参考代码**

```
// 柯里化：实现对两个数进行计算
def calc_carried(x:Double, y:Double)(func_calc:(Double, Double)=>Double) = {<!-- -->
    func_calc(x, y)
}

def main(args: Intrray[String]): Unit = {<!-- -->
    println(calc_carried(10.1, 10.2){<!-- -->
        (x,y) => x + y
    })
    println(calc_carried(10, 10)(_ + _))
    println(calc_carried(10.1, 10.2)(_ * _))
    println(calc_carried(100.2, 10)(_ - _))
}

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125145976