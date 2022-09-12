# XStream 组件使用手册
 # 文章目录
一、XStream 定义
二、XStream 特性
三、XStream 用途
四、XStream 局限
五、XStream 使用

#### 一、XStream 定义

  `XStream`</a> 是一个简单的 `JAVA` 类库，其主要用于将对象序列化为 `XML`，或者将 `XML` 反序列化为对象，可以轻易的见 `JAVA` 对象和 `XML` 结构来回转换。

#### 二、XStream 特性

|特性|特性结解释|
| :-----| :-----|
|便于使用|提供了一套高层次的门面，可以简化常见的用例|
|无需映射|大多数对象可以序列化，无需指定映射|
|关于性能|速度和低内存占用是设计的关键部分，使其适用于大型对象图或具有高消息吞吐量的系统|
|洁净XML|没有重复的信息通过反射获取，这样生产的 XML 比人类机器 JAVA 序列化便于阅读并且更加紧凑|
|不修改对象|序列化内部字段，包括私有字段和最终字段。支持非公共和内部类。类不需要具有默认构造函数|
|完整对象图支持|在对象模型中遇到的重复引用将得到维护。支持循环引用|
|与其他 XML API 集成|通过实现接口，XStream 可以直接与任何树结构（不仅仅是 XML）进行序列化|
|可定制的转换策略|可以注册策略，以允许定制如何将特定类型表示为XML|
|安全框架|对非编组类型进行精细控制，以防止操纵输入引起安全性问题|
|错误消息|当由于 XML 格式错误而导致异常时，将提供详细的诊断信息以帮助隔离和解决问题|
|替代输出格式|模块化设计允许其他输出格式。XStream 当前附带 JSON 支持和变形|


#### 三、XStream 用途

**常见用途**
-  `XStream` 在运行时使用 `JAVA` 反射机制对要进行序列化的对象树的结构进行探索，并不需要对对象作出修改。`XStream` 可以序列化内部字段，包括私 `private` 和 `final` 字段，并且支持非公开类以及内部类 -  在缺省情况下，`XStream` 不需要配置映射关系，对象和字段将映射为同名 `XML` 元素。但是当对象和字段名与 `XML` 中的元素名不同时，`XStream` 支持指定别名。`XStream` 支持以方法调用的方式，或是 `JAVA` 标注的方式指定别名 -  `XStream` 在进行数据类型转换时，使用系统缺省的类型转换器。同时，也支持用户自定义的类型转换器 
**用途归纳**
- 传输- 持久化- 配置- 单元测试
#### 四、XStream 局限
-  如果使用增强模式，`XStream` 可以重新实例化没有默认构造函数的类。但是如果使用不同的 `JVM （例如：JDK1.4）`或由于 `SecurityManager` 而受到限制，则需要默认的构造函数。 -  增强模式对于恢复 `< 1.5`的任何 `JDK` 的最终字段也是必须的，这意味着内部类实例的反序列化。 -  自动检测注解可能会导致竞争资源的情况，预处理注释是安全的 
#### 五、XStream 使用
<li> 添加 `XStream` 的 `pom` 依赖 <pre><code class="prism language-pom"><!-- XML 解析工具 2020-11-16-->
<dependency>
    <groupId>com.thoughtworks.xstream</groupId>
    <artifactId>xstream</artifactId>
    <version>1.4.14</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
    <optional>true</optional>
</dependency>
</code></pre> </li><li> **`XStream` 的基本使用** <pre><code class="prism language-java">@AllArgsConstructor
@ToString
public class Person implements Serializable {
    private int id;
    private String name;
    private int age;

    public static void main(String[] args) {
        Person person = new Person(1,"Rambo",100);
        XStream xStream = new XStream();
        // XStream对象设置默认安全防护
        XStream.setupDefaultSecurity(xStream);
        // 设置允许的类
        xStream.allowTypes(new Class[]{Person.class});

        String xml = xStream.toXML(person);
        System.out.println("将 Person 对象转换为 XML = \n" + xml);
        System.out.println("\n=================================\n");
        Person personFromXML = (Person)xStream.fromXML(xml);
        System.out.println("将 XML 转为 Person 对象 = \n" + personFromXML.toString());
    }
}
</code></pre> <pre><code class="prism language-text">将 Person 对象转换为 XML = 
<com.rambo.utils.Person>
  <id>1</id>
  <name>Rambo</name>
  <age>100</age>
</com.rambo.utils.Person>

=================================

将 XML 转为 Person 对象 = 
Person(id=1, name=Rambo, age=100)
</code></pre> **P.S** 
  1.  在使用 `XStream` 序列化时，对 `JAVABean` 没有任何限制，`JAVABean` 的字段可以使私有的，也可以没有 `getter` 和 `setter` 方法，还可以没有默认构造函数 1.  `XStream` 序列化 `XML` 时可以允许用户使用不同的 `XML` 解析器，用户可以使用一个标准的 `JAXP DOM` 解析器或者 `JAVA 6` 集成的 `STAX` 解析器。这样用户就不需要依赖 `XPP3` 的依赖库。  </li><li> **`XStream` 注解使用** 
  <ul>1.  混叠是定制生成 `XML` 或者使用 `XStream` 特定格式化 `XML` 的一种技术手段。假设下面给出的 `XML` 格式，用于序列化和反序列化 `Garden` 对象 <li> `XML` 格式 <pre><code class="prism language-xml"><garden name="农夫果园">
  <fruit>
    <fruitName>苹果</fruitName>
    <description>红富士</description>
  </fruit>
  <fruit>
    <fruitName>香蕉</fruitName>
    <description>国产香蕉</description>
  </fruit>
</garden>
</code></pre> </li><li> 根据以上 `XML` 格式创建实体类 <pre><code class="prism language-java">@AllArgsConstructor
@NoArgsConstructor
@ToString
@XStreamAlias("fruit")
public class Fruit implements Serializable {
    private String fruitName;
    private String description;
}

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XStreamAlias("garden")
public class Garden implements Serializable {
    @XStreamAsAttribute()
    @XStreamAlias("name")
    private String gardenName;

    @XStreamImplicit(itemFieldName = "fruit")
    private List<Fruit> fruits;

    public static void main(String[] args) {
        Fruit apple = new Fruit("苹果", "红富士");
        Fruit banana = new Fruit("香蕉", "国产香蕉");
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(apple);
        fruits.add(banana);

        Garden garden = new Garden("农夫果园", fruits);

        XStream xStream = new XStream();
        // XStream对象设置默认安全防护
        XStream.setupDefaultSecurity(xStream);
        // 设置允许的类
        xStream.allowTypes(new Class[]{Garden.class});
        // 应用 Bean 类的注解
        xStream.processAnnotations(Garden.class);
        // 自动检测注解
        xStream.autodetectAnnotations(true);

        String xml = xStream.toXML(garden);
        System.out.println(xml);

        System.out.println("\n=============================\n");

        Garden gardenFromXML = (Garden)xStream.fromXML(xml);
        System.out.println(gardenFromXML.toString());
    }
}
</code></pre> </li><li> 运行结果 <pre><code class="prism language-text"><garden name="农夫果园">
  <fruit>
    <fruitName>苹果</fruitName>
    <description>红富士</description>
  </fruit>
  <fruit>
    <fruitName>香蕉</fruitName>
    <description>国产香蕉</description>
  </fruit>
</garden>

=============================

Garden(gardenName=农夫果园, fruits=[Fruit(fruitName=苹果, description=红富士), Fruit(fruitName=香蕉, description=国产香蕉)])
</code></pre> </li></ul> </li>-  混叠是定制生成 `XML` 或者使用 `XStream` 特定格式化 `XML` 的一种技术手段。假设下面给出的 `XML` 格式，用于序列化和反序列化 `Garden` 对象 <li> `XML` 格式 <pre><code class="prism language-xml"><garden name="农夫果园">
  <fruit>
    <fruitName>苹果</fruitName>
    <description>红富士</description>
  </fruit>
  <fruit>
    <fruitName>香蕉</fruitName>
    <description>国产香蕉</description>
  </fruit>
</garden>
</code></pre> </li><li> 根据以上 `XML` 格式创建实体类 <pre><code class="prism language-java">@AllArgsConstructor
@NoArgsConstructor
@ToString
@XStreamAlias("fruit")
public class Fruit implements Serializable {
    private String fruitName;
    private String description;
}

@AllArgsConstructor
@NoArgsConstructor
@ToString
@XStreamAlias("garden")
public class Garden implements Serializable {
    @XStreamAsAttribute()
    @XStreamAlias("name")
    private String gardenName;

    @XStreamImplicit(itemFieldName = "fruit")
    private List<Fruit> fruits;

    public static void main(String[] args) {
        Fruit apple = new Fruit("苹果", "红富士");
        Fruit banana = new Fruit("香蕉", "国产香蕉");
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(apple);
        fruits.add(banana);

        Garden garden = new Garden("农夫果园", fruits);

        XStream xStream = new XStream();
        // XStream对象设置默认安全防护
        XStream.setupDefaultSecurity(xStream);
        // 设置允许的类
        xStream.allowTypes(new Class[]{Garden.class});
        // 应用 Bean 类的注解
        xStream.processAnnotations(Garden.class);
        // 自动检测注解
        xStream.autodetectAnnotations(true);

        String xml = xStream.toXML(garden);
        System.out.println(xml);

        System.out.println("\n=============================\n");

        Garden gardenFromXML = (Garden)xStream.fromXML(xml);
        System.out.println(gardenFromXML.toString());
    }
}
</code></pre> </li><li> 运行结果 <pre><code class="prism language-text"><garden name="农夫果园">
  <fruit>
    <fruitName>苹果</fruitName>
    <description>红富士</description>
  </fruit>
  <fruit>
    <fruitName>香蕉</fruitName>
    <description>国产香蕉</description>
  </fruit>
</garden>

=============================

Garden(gardenName=农夫果园, fruits=[Fruit(fruitName=苹果, description=红富士), Fruit(fruitName=香蕉, description=国产香蕉)])
</code></pre> </li># **文章地址： **    https://blog.csdn.net/Rambo_Yang/article/details/109725575?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-4-109725575-blog-101693263.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-4-109725575-blog-101693263.pc_relevant_aa&utm_relevant_index=7