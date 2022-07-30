# [CSS简单入门](https://www.cnblogs.com/tanghaorong/p/12421159.html)



**目录**

- [1、CSS介绍](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label0)
- 2、CSS样式
  - [2.1、行内样式](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label1_0)
  - [2.2、内部样式](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label1_1)
  - [2.3、外部样式](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label1_2)
  - [2.4、样式的优先级](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label1_3)
  - [2.5、样式继承](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label1_4)
- 3、CSS选择器
  - [3.1、基本选择器](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label2_0)
  - [3.2、复合选择器](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label2_1)
- 4、CSS常用属性
  - [4.1、字体属性：font](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label3_0)
  - [4.2、文本：text](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label3_1)
  - [4.3、字体样式：font-style](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label3_2)
  - [4.4、列表：list-style](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label3_3)
  - [4.5、边框：border](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label3_4)
  - [4.6、背景：background](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label3_5)
- [ 5、CSS盒子模型](https://www.cnblogs.com/tanghaorong/p/12421159.html#_label4)

 

------

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200305165204464-1765044291.png)

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12421159.html#_labelTop)

## 1、CSS介绍

如果把网页的构成比作成一个人，那么HTML是网页的骨架，CSS就是网页的颜值，这样HTML+CSS好看的网页就制作完成了。如果还要再加点智商的话，那就需要使用到JavaScript了。等这些全部整合在一起就可以尽情的出去装逼了。不过本文介绍的是CSS入门，所以就不扯到其他的了。

CSS的英文全称：Cascading Style Sheets，翻译过来为层叠样式表，它允许我们在HTML的基础上创建非常美观的网页。CSS不仅可以静态地修饰网页，还可以配合各种脚本语言动态地对网页各元素进行格式化。能够对网页中元素位置的排版进行像素级精确控制，支持几乎所有的字体字号样式，拥有对网页对象和模型样式编辑的能力。

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12421159.html#_labelTop)

## 2、CSS样式



### **2.1、行内样式**

行内样式通过style属性嵌入在标签中来设置，属性值可以任意的CSS样式。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>行内样式</title>
</head>
<body>
    <p style="color: red; margin-left: 20px;font-size: 50px;">行内样式</p>
</body>
</html>
```



### **2.2、内部样式**

内部样式定义在head标签中，而且必须放在一对style标签来设置。需要使用元素选择器（p）来关联样式和要设置样式的标签（p标签）。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>内部样式</title>
    <style>
        p {
            color: red;
            margin-left: 20px;
            font-size: 50px;
        }
    </style>
</head>
<body>
    <p>内部样式</p>
</body>
</html>
```



### **2.3、外部样式**

外部样式就是以*.css结尾在外部定义的CSS样式。然后再然后在html的head部分通过link元素引入。注意：外部样式的引入有两种方式 link式和 import式。

**①、link式导入：**

<link> 标签定义文档与外部资源的关系，它的主要用途是引入一些外部文件。在HTML中<link>标签是没有结束标签的。

link标签中相关的属性：

- href：引入文件的地址。
- rel：当前文档与被引入文档之间的关系。常用stylesheet:文档的外部样式表；contents:文档的目录；index:文档的索引。
- type：引入文档的类型。最常见的类型是 "text/css"，该类型描述样式表。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>外部样式</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <p>外部样式</p>
</body>
</html>
```

style.css文件内容：

```
p {
    color: red;
    margin-left: 20px;
    font-size: 50px;
}
```

**②、import式**

引入式需使用@import url()来引入css文件，可以用在*.css文件中，也可用在<style>标签中。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>@import式</title>
    <style>
        @import url("css/style.css");

        p {
            color: yellow;
        }
    </style>
</head>
<body>
    <p>@import式</p>
</body>
</html>
```



### **2.4、样式的优先级**

我们将上面的三种样式都在html页面中使用，看一下页面会优先使用哪个样式。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>样式</title>
    <link rel="stylesheet" href="css/style.css"/>
    <style>
        p {
            color: yellow;
            font-size: 30px;
        }
    </style>
</head>
<body>
    <p style="color: green;font-size: 10px;">样式</p>
</body>
</html>
```

发现首先使用的是行内样式。然后再把行内样式p标签中的属性去掉，这样就可以得出三个样式的优先级为：

**行内样式 > 内部样式 > 外部样式** ；CSS样式优先级从高到低排序，其中内部样式和外部样式是就近原则。

我们也可以自己设定某个CSS样式为最高优先级，只需在样式的属性后加上“**！important”**就可以了。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>样式</title>
    <link rel="stylesheet" href="css/style.css"/>
    <style>
        p {
            color: yellow !important;
            font-size: 30px !important;
        }
    </style>
</head>
<body>
    <p style="color: green;font-size: 10px;">样式</p>
</body>
</html>
```



### **2.5、样式继承**

CSS样式继承是指被包在内部的标签将拥有外部标签的样式。但并不是所有的属性会传递给子孙元素，例如：boder、padding、margin、background等。

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>继承</title>
    <style>
        div {
            color: red;
            margin: 100px;
        }
    </style>
</head>
<body>
    <div>
        <p>这里面的也继承了,但又有些没有继承</p>
    </div>
</body>
</html>
```

其中的margin属性在p标签中是没有继承的，开通Google的开发工具查看(F12)。

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12421159.html#_labelTop)

## 3、CSS选择器

CSS的选择器的基本语法如下：

```
css选择器{
  　　　css属性：值;
　　　  css属性：值;
}
```

CSS的选择器可分为：基本选择器和复合选择器。



### 3.1、基本选择器

基本选择器分为：通配选择器、元素选择器、ID选择器、类选择器。

**①、通配选择器**

通配选择器就像是一个通配符，它可以与任何元素匹配。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>通配选择器</title>
    <style>
        * {
            color: red;
            font-size: 30px;
        }
    </style>
</head>
<body>
    <h1>h中内容</h1>
    <p>p中的内容</p>
    <div>div中的内容</div>
</body>
</html>
```

**②、元素选择器**

元素选择器用来选择html中最基本的标签元素的选择器。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>元素选择器</title>
    <style>
        h1 {
            color: red;
        }

        p {
            font-size: 20px;
        }
    </style>
</head>
<body>
    <h1>h中的内容</h1>
    <p>p中的内容</p>
</body>
</html>
```

**③、ID选择器**

ID选择器针对某一个特定的标签来使，它只能使用一次，CSS中的ID选择器以”#”来定义。

```
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>ID选择器</title>
        <style>
            #d {
                color: red ;
            }
            #p {
                color: green;
            }
        }
        </style>
    </head>
    <body>
        <div id="d">div中的内容</div>
        <p id="p">p中的内容</p>
    </body>
</html>
```

注意：ID选择器只能使用一次，这个意思是不能出现两个同名的ID（如：<div id="div1"></div> <div id="div1"></div>），而不是只能使用一次。在CSS样式中可以无限次使用，不过它会遵循就近原则。其实ID选择器中ID出现同名是可以的。但如果页面涉及到JS，那就就不好了。因为JS里获取DOM是通过getElementById，而如果页面出现同一个id几次，这样就获取不到了。所以id要有唯一性。一般在成熟网站里，你很少看到CSS里用ID选择器的，都是用class，ID选择器留给写JS的人用，这样避免冲突。

**结论：最好把class属性交给CSS使用，id属性交给js使用。**

**④、类选择器（常用）**

类选择器通过标签中的class属性值来选中指定标签，CSS中用 “**.**”来定义。多个标签可以有相同的class值。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>类选择器</title>
    <style>
        .d {
            color: red;
        }
        .p {
            color: green;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <div class="d">div中的内容</div>
    <p class="p">p中的内容</p>
    <p class="p">p中的内容</p>
</body>
</html>
```

多个类选择器，就是一个class中有多个名字，它们用空格隔开。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>多类选择器</title>
    <style>
        .div2 {
            color: yellow;
        }

        .div1 {
            color: red;
        }

    </style>
</head>
<body>
    <div class="div1 div2">div中的内容</div>
</body>
</html>
```

当一个元素用于多个类选择器，同一css属性的优先级取决于CSS样式表中的先后顺序，与类列表的先后顺序无关。



### 3.2、复合选择器

复合选择器分为：交集选择器、并集选择器、后代选择器、子元素选择器、相邻兄弟选择器、属性选择器、伪类选择器。

**①、交集选择器**

交集选择器由两个选择器构成，其中第一个为标签选择器，第二个为class选择器，两个选择器之间通过 **.** 隔开，不能有空格。**语法：选择器1.选择器2{ }**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>交集选择器</title>
    <style>

        p.special{
            color: red;
        }

        .special{
            color: green;
        }

    </style>
</head>
<body>
    <p class="special">文本内容</p>
    <h3 class="special">标题内容</h3>
</body>
</html>
```

**②、并集选择器**

用于处理定义的样式相同，或部分相同，就可以利用并集选择器，可以让代码更简洁，各个选择器之间通过 `**,**`隔开。**语法：选择器1,选择器2{ }**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>并集选择器</title>
    <style>

        p,.special, div {
            color: red;
        }

    </style>
</head>
<body>
    <p>文本内容</p>
    <p class="special">文本内容</p>
    <h3 class="special">标题内容</h3>
    <div>容器内容</div>
</body>
</html>
```

**③、后代选择器**

后代选择器用于选择元素或元素组的子孙后代。各个选择器之间通过空格隔开。**语法：选择器1 选择器2{ }**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>后代选择器</title>
    <style>

        div p{
            color: red;
        }

    </style>
</head>
<body>
    <div>
        <h3>H3标题内容</h3>
        <div>
            <p>P标签内容</p>
            <div>
                <p>P标签内容</p>
            </div>
        </div>
    </div>
</body>
</html>
```

div中的子孙p标签都变成了红色。

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307155748245-81142114.png)

**④、子元素选择器**

子元素选择器用于选择元素或元素组的直接后代，就是”亲儿子”，各个选择器之间通过>隔开。**语法：选择器1>选择器2{ }**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>子元素选择器</title>
    <style>

        div > p {
            color: red;
        }

    </style>
</head>
<body>
    <div>
        <P>P标题内容</P>
        <div>
            <P>P标题内容</P>
            <span>
                <p>P标题内容</p>
            </span>
        </div>
    </div>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307160719375-348057130.png)

**⑤、相邻兄弟选择器**

相邻兄弟选择器用于选择目标元素紧跟在它后面的同级元素，各个选择器之间通过+隔开。**语法：选择器1+选择器2{ }**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>相邻兄弟选择器</title>
    <style>

        p+h3 {
            color: red;
        }

    </style>
</head>
<body>
    <P>P标题内容</P>
    <h3>h3标题内容</h3>
    <a href="http://www.sina.com">新浪</a>
    <h3>h3标题内容</h3>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307164726931-518615631.png)

**⑥、普通兄弟选择器**

普通兄弟选择器用于选择目标元素跟在它后面的同级元素，各个选择器之间通过~隔开。**语法：选择器1~选择器2{ }**

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>普通兄弟选择器</title>
    <style>

        p~h3 {
            color: red;
        }

    </style>
</head>
<body>
    <P>P标题内容</P>
    <h3>h3标题内容</h3>
    <P>P标题内容</P>
    <a href="http://www.sina.com">新浪</a>
    <h3>h3标题内容</h3>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307164754572-597720207.png)

**⑦、属性选择器**

属性选择器就是根据样式的属性来选择。

**a、[attribute]**： 用于选取带有指定属性的元素，忽略属性值。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [class] {
            color: red;
        }

        h3[id] {
            color: green;
        }

    </style>
</head>
<body>
    <P class="p">P标题内容</P>
    <h3 class="h">h3标题内容</h3>
    <h3 id="h3">h3标题内容</h3>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307171733330-2014165873.png)

**b、[attribute="value"]**：用于选取带有指定属性和值的元素。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [class="p"] {
            color: red;
        }

        h3[id="h3"] {
            color: green;
        }

    </style>
</head>
<body>
    <P class="p">P标题内容</P>
    <h3 class="h">h3标题内容</h3>
    <h3 id="h3">h3标题内容</h3>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307171733817-822495200.png)

**c、[attribute~=value]：**用于选取带有指定属性、属性值具有多个值且其中一个值是value的元素。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [class~="class1"] {
            color: red;
        }

        h3[id] {
            color: green;
        }

    </style>
</head>
<body>
    <p>P标题内容</p>
    <p class="class1 class2">P标题内容</p>
    <div title="a1">div容器内容</div>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307171734221-1484900504.png)

**d、[attribute|="value"]：**用于选取带有以指定值开头的属性值的元素，该值必须是整个单词，如果被符号 - 分割的第一个value恰好是要选择的值，那么也会被选中。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [title|="abc"] {
            color: red;
        }

    </style>
</head>
<body>
    <p>P段落内容</p>
    <p title="abc">P段落内容</p>
    <div title="abc-def">div容器内容</div>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307173940234-1085943035.png)

**e、[attribute^="value"]：**用于选取带有指定属性且属性值以value字符开头的元素。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [title^="a"] {
            color: red;
        }

    </style>
</head>
<body>
    <p title="b">P段落内容</p>
    <p title="abc">P段落内容</p>
    <div title="a-abc">div容器内容</div>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307173940709-693531561.png)

**f、[attribute$="value"]：**用于选取带有指定属性且属性值以value字符结尾的元素。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [title$="c"] {
            color: red;
        }

    </style>
</head>
<body>
    <p title="b">P段落内容</p>
    <p title="abc">P段落内容</p>
    <div title="abc-c">div容器内容</div>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307173941031-1441623342.png)

**g、[attribute\*="value"]：**用于选取带有指定属性和属性值包含value字符的元素。

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>属性选择器</title>
    <style>

        [title*="b"] {
            color: red;
        }

    </style>
</head>
<body>
    <p title="b">P段落内容</p>
    <p title="abc">P段落内容</p>
    <div title="abc-c">div容器内容</div>
</body>
</html>
```

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200307173941359-1858955876.png)

**⑧、伪类选择器**

伪类选择器就是用来给指定元素设置不同的状态样式。比如给链接添加特殊效果， 比如可以选择第1个，第n个元素。因为伪类选择器很多，比如链接伪类，结构伪类等等。所以这里只给大家讲解链接伪类选择器。

链接伪类选择器分为：静态伪类和动态伪类。其中静态伪类只能用于链接，动态伪类适用任何标签。

  静态伪类：

- link：超链接点击之前 。
- visited：超链接点击之后 。

  动态伪类：

- focus：是某个标签获得焦点的时候（比如某个输入框获得焦点） 。
- hover：鼠标放到某个标签上的时候 。
- active：点击某个标签没有松鼠标时。

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>伪类选择器</title>
    <style>
        a{
            font-size: 20px;
        }

        a:link {
            color: red;
        }
        a:visited {
            color: blue;
        }
        a:hover {
            color: green;
        }
        a:active {
            color: yellow;
        }

    </style>
</head>
<body>
    <a href="https://www.sina.com.cn/">新浪</a>
</body>
</html>
```

**注意:**

- 在CSS中，他们之间的顺序不要颠倒，要按照lvha(四类的首字母) 的顺序(不过在开发中一般都不会全部使用它们)。否则可能引起错误。
- 如果出现a:link失效，是因为它是超链接点击之前显示，只要你有过一次点击，那么它显示的就是点击之后的颜色了（a:visited{color: blue;}）。处理它最好的办法就是把a:link和a:visited设置在一起来（a:link,a:visited{color: red;}）。

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12421159.html#_labelTop)

## 4、CSS常用属性



### **4.1、字体属性：font **

font-family：设置字体系列。

```
p{
    font-family: 微软雅黑;
}
```

font-size：设置字体的大小。属性值(一般用数值就可以，单位：PX、PD)：数值|inherit| medium| large|larger| x-large| xx-large| small| smaller| x-small| xx-small。

```
p{
    font-size: 20px;
}
```

font-style:设置字体风格。属性值：normal(默认值);oblique(偏斜体); italic(斜体);inherit(继承父类)。

```
p{
    font-style: italic;
}
```

font-variant:以小型大写字体或者正常字体显示文本。

```
p{
    font-variant: small-caps;
}
```

font-weight：设置字体的粗细。属性值：normal(默认值)；bold(粗体)；bolder(更粗);lighter(更细);数值100-900。

```
p{
    font-weight: bold;
}
```



### **4**.**2、文本：text**

color：设置文本颜色。

```
p{
    color: red;
    color: #FF0000;
    color: rgb(255,0,0);
}
```

direction：设置文本方向。属性值：rtl(从右到左);ltr(从左到右);(inhreit)继承。

```
p{
    direction:rtl;
}
```

letter-spacing：设置字符间距。

```
p{
    letter-spacing: 10px ;
}
```

line-height：设置行高。

```
p{
    line-height: 200px;
}
```

text-align:对齐元素中的文本。属性值：justify;(两端对齐) left;(左对齐) right;(右对齐) center;(居中)。

```
p{
    text-align:center;
}
```

text-decoration:向文本添加修饰。属性值：overline(上划线)；line-through(删除线);underline(下划线);none(无)。

```
p{
    text-decoration: underline;
}
```

text-transform : 设置英文大小写。capitalize(首字大写);uppercase(英文大写)lowercase(英文小写)。 

```
p{
    text-transform: capitalize;
}
```

text-shadow：首行缩进多少。

```
p {
    text-indent: 2em;
}
```



### **4.3、字体样式：font-style**

font-family：设置字体类型。例如：宋体、黑体、楷体等

```
p {
    font-family: "黑体";
}
```

font-style：规定斜体文本。

```
p {
    font-style: italic;
}
```

font-size：设置文本的字体大小。

```
p {
    font-size: 50px;
}
```



### **4.****4、列表：list-style**

list-style-type：指定列表项标记的类型。属性值：无序列表（disc 默认实心圆；circle空心圆；square实心方块）。有序列表（decimal数字；decimal-leading-zero是0开头的数字(01, 02, 03…)；lower-roman小写罗马数字(i, ii, iii, iv,…)；upper-roman大写罗马数字(I, II, III, IV,…)）。

```
ul li{
    list-style-type: square;
}
```

list-style-image：指定列表项标记的图像。

```
ul li{
    list-style-image: url("0.jpg");
}
```

list-style-position：指定列表项标记的位置。属性值：inside(缩进);outside(凸排)。

```
ul li{
    list-style-position: outside;
}
```



### **4.5、边框：border**

边框有三个要素：像素（粗细）、线型、颜色。颜色如果不写，默认是黑色。而另外两个属性不写，边框是显示不出来的。

border的写法一般都是简写：**border：width style color。**注意各个属性之间是用空格隔开的。其中style边框的样式有 dotted(点线)； dashed(虚线)； solid(实线); double(双线)； groove;(槽线)； ridge(脊状)； inset(凹陷)等。

```
div{
    width: 200px;
    height: 100px;
    border: 5px solid red;
}
```

border既然可以简写那么也是能够被拆开的，有两大种拆开的方式：

- 按三要素拆开：border-width、border-style、border-color。（一个border属性是由三个小属性综合而成的）
- 按方向拆开：border-top、border-right、border-bottom、border-left。

现在我们明白了：**一个border属性，是由三个小属性综合而成的**。如果某一个小属性后面是空格隔开的多个值，那么就是**上右下左**的顺序。举例如下：

```
div{
    width: 200px;
    height: 100px;
    border-width: 5px;
    border-style: dashed solid double dotted;
    border-color: orange red yellow blue;
}
div{
     width: 200px;
     height: 100px;
     border-top:5px solid red;
     border-right:5px dotted green;
     border-bottom:5px dashed blue;
     border-left:5px ridge yellow;
 }
```

但是这种就不能转换为border的简写了，因为简写只能为各属性相同的一个值。

注意：在平时开发中使用border的简写方式就够了。



### **4.6、背景：background**

background-color：设置背景颜色。背景颜色的表示方法有三种：单词、rgb表示法、十六进制表示法。

```
div{
    width: 300px;
    height: 200px;
    background-color: red;
    background-color: rgb(255,0,0);
    background-color: #FF0000;
 }
```

background-image：设置背景图片。

```
div{
    width: 300px;
    height: 200px;
    background-image: url("0.jpg");
 }
```

background-attachment：设置背景图片在当前容器中的位置。属性值：scroll(默认值，背景图像会随着页面其余部分的滚动而移动。)；fixed（当页面的其余部分滚动时，背景图像不会移动）。

```
body{
    background: url("0.jpg");
    background-attachment: fixed;
 }
```

效果如下：

[![12](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200308171122947-1101273419.gif)](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200308171122031-847351625.gif)

background-repeat：设置背景图片如何重复平铺（重要）。 属性值：repeat(默认平铺)；no-repeat(不要平铺)；repeat-x(横向平铺)；repeat-y(``纵向平铺)。

```
body{
    background-image: url("2.jpg");
    background-repeat: repeat-y;
}
```

``纵向平铺效果：

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200308173229597-1766042239.png)

backgroun-position:设置背景图片在当前容器中的位置。属性值：像素；left、center、right（左中右）；top 、center、bottom（上中下）。

```
div{
    height: 500px;
    background-image: url("1.jpg");
    background-repeat: no-repeat;
    background-position: center top;
}
```

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12421159.html#_labelTop)

##  5、CSS盒子模型

CSS的盒子模型是CSS中最核心的基础知识，理解了这个重要的概念才能更好的排版，进行页面布局。下面的图片是整个标准盒子模型的构成：

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200306163013257-1200559254.png)

从上面的图片可以发现CSS盒子模型包含了内容（content）、内边距（padding）、边框（border）、外边距（margin）、宽度（width）、高度（height）这几个要素，

下面依次来说明各部分：

- Content：就是盒子的内容，可以显示文本和图像。
- Padding：边框内的距离。如果没有特意指出左还是右，那设置时默认是上右下左的顺序，例如padding:10px(上)，20px(右)，30px(下)，40px(左)；padding:10px (上下左右都是10)。
- Border：将内容框起来的一个边框。border有三个属性，粗细（width），样式（style），颜色（color）。width可以设置为thin、thick和length，length为具体数值。style可以设置为none、hidden、solid等，none是不显示border，hidden可以解决边框中多余的内容，solid表示是实线。例如上图的边框可表示为border:10px solid red，“10px”表示的是边框的粗细。
- Margin ：盒子与盒子之间的距离（或者说是边框与边框之间的距离）。
- Width ：宽度（这是标准盒子的宽度，IE的宽度与标准盒子的宽度不同）。而元素框的总宽度 = 元素（element）的width + padding的左边距和右边距的值 + margin的左边距和右边距的值 + border的左右宽度。
- Height ：高度（同上）。元素框的总高度 = 元素（element）的height + padding的上下边距的值 + margin的上下边距的值 ＋ border的上下宽度。

我们可以这么来理解一下盒子模型，你把一些鸡蛋（content）放在盒子里，为了不让鸡蛋打碎就放了一些填充物（padding），而且选择的这个盒子需要厚一点（border），然后再把他放到车子的后背箱，你不能把其它的东西压在放鸡蛋的盒子上，所以要留一些空隙（margin）。

 

这里我们主要来认识一下padding和margin这两个属性。

**padding介绍：**

padding就是内边距。它有两种表示方法：综合属性、拆分属性。

综合属性写法：

```
padding:100px 50px 40px 200px;
```

如果padding属性指定四个值，那么顺序分别是上、右、下、左的内边距。

如果padding属性指定三个值，那么左边的内边距默认使用右边的，其他不变。

如果padding属性指定两个值，那么下边的内边距默认使用上边的，左边的内边距默认使用右边的。

如果padding属性指定一个值，那么上、右、下、左的内边距都是这个值。

 

拆分属性写法：

```
    padding-top: 100px;
    padding-right: 50px;
    padding-bottom: 40px;
    padding-left: 200px;
```

等价于：

```
padding:100px 50px 40px 200px;
```

由于padding和margin属性相似，margin就是外边距，所以这里就不介绍margin的使用了。