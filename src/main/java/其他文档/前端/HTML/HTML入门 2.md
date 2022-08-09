# [H5入门这一篇就够了](https://www.cnblogs.com/tanghaorong/p/12401991.html)



**目录**

- [一、什么是HTML](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label0)
- [二、创建HTML](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label1)
- 三、常用的标签
  - [①、标题 h](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_0)
  - [②、段落 p](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_1)
  - [③、超链接 a](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_2)
  - [④、图片 img](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_3)
  - [⑤、列表ol-li、ul-li、dl-dt-dd](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_4)
  - [⑥、选择框select-option](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_5)
  - [⑦、容器 div、span(重要)](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_6)
  - [⑧、表格 table](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_7)
  - [⑨、输入框 input](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_8)
  - [⑩、表单 form](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_9)
  - [⑪、文本域 textarea](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_10)
  - [⑫、HTML常用的字符实体](https://www.cnblogs.com/tanghaorong/p/12401991.html#_label2_11)

 

------

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200303164723680-1283623154.png)

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12401991.html#_labelTop)

## 一、什么是HTML

HTML的全称是 Hyper Text Markup Language，翻译过来就是超文本标记语言，是目前网络上应用最为广泛的语言，它不是编程语言，而是一种标记语言。HTML文本是由HTML命令组成的描述性文本，HTML命令可以说明文字、图形、动画、声音、表格、链接等。HTML的结构包括头部（Head）、主体（Body）两大部分，其中头部描述浏览器所需的信息，而主体则包含所要说明的具体内容。

说白了它就是用来构建网页的，所以话不多说，先来创建一个HTML文件吧。

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12401991.html#_labelTop)

## 二、创建HTML

创建HTML总得使用一款工具吧，那么它来啦。这里推荐一款非常好用而且是国产的前端Web开发工具HBuilder，当然还有比它更加好用的Web开发工具：[webstorm](https://www.jetbrains.com/webstorm/)、[VS code](https://code.visualstudio.com/)等。这里只是入门的简单示例，所以就不用这种专门开发大前端的工具了。HBuilder毕竟它也是国产的开发工具嘛，有时候我们还是需要支持一下国产的开发工具滴。个人认为Hbuilder还是比较适合新手入门的。

![1583218946-18834[3]](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200303151053159-1355361072.gif)

 

官网地址：https://www.dcloud.io/

下载地址：https://www.dcloud.io/hbuilderx.html

 

 

下载完成后解压打开，创建一个项目。

![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200303151659906-1028489984.png)

此种方式创建开发工具会自动创建一个默认的 index.html文件，打开index.html的内容是：

```
<!DOCTYPE html>``<html>``  ``<head>``    ``<meta charset=``"utf-8"` `/>``    ``<title></title>``  ``</head>``  ``<body>``    ` `  ``</body>``</html>
```

上面就是HTML的整体骨架。这是HTML5的定义方式。所以肯定会与HTML4有区别，具体的区别还请自行去问[度娘](http://www.baidu.com/)。现在基本都是以H5标准开发前端。

来认识一下上面的的标签：

①、***\*<!DOCTYPE html>\****：必须在文档开头第一行，表示标记为文档类型。（必不可少）

②、**<html>**:HTML页面的根元素，告知浏览器其自身是一个HTML文档。（必不可少）

③、**<head>**:用于表述网页的元数据，及网页的基本信息。（必不可少）

④、**<meta>**：表示元数据，是描述文件类型和编码，搜索关键字和描述。它永远位于<head>元素内部。元数据它不会显示在页面上，但是对于机器是可读的。

<meta>标签有两种格式如下：

```
<meta name=``"keywords"` `content=``"html入门"` `charset=``"utf-8"``>` `<meta http-equiv=``"Content-Type"` `content=``"text/html; charset=utf-8"``>
```

简单介绍其中的属性：

- charseat属性：设置文档字符集编码格式，如UTF-8，GBK。它是必要属性，因为要防止页面出现乱码。而下面的是可选属性。
- name属性:写给搜索引擎看。需掌握的属性值author（作者）keywords（关键字）description（网页描述），配合content使用。
- http-equiv属性：将我们的信息写给浏览器看，让浏览器按里边的要求执行。属性值：Content-type（文档类型）Refresh（网页定时刷新）set-cookie（设置缓存），必须配合content使用，因为http-equiv属性只表明设置哪部分，具体的设置内容放在content属性中。
- content属性配合其他使用。

meta标签的详细使用可以参考这篇博客：[meta标签的作用及整理](https://blog.csdn.net/yc123h/article/details/51356143)  我认为的meta标签一方面是设置浏览器的一些功能，另一方面是方便搜索引擎的收录关键词。

⑤、**<title>**：表示网页的标题。例如:![image](https://img2018.cnblogs.com/blog/1745215/202003/1745215-20200303163938513-1614046467.png)

⑥、**<body>**：表示显示在网页上的内容。（它是最重要也是最主要的部分）

HTML的基本整体骨架就介绍这么多了，接下来我们学习常用标签。

[回到顶部](https://www.cnblogs.com/tanghaorong/p/12401991.html#_labelTop)

## 三、常用的标签

标签参考：https://www.runoob.com/tags/html-reference.html   

HTML中的注释： <!--注释的内容-->  



### **①、标题 h**

HTML的标题通过<h1>—<h6>标签来定义，<h1> 定义重要等级最高的标题。<h6> 定义重要等级最低的标题。

```
<!DOCTYPE html>
<html>
  <head>
	<meta  charset="utf-8"/>
	<title>标题h</title>
  </head>
  <body>
	<h1>这是标题1</h1>
	<h2>这是标题2</h2>
	<h3>这是标题3</h3>
	<h4>这是标题4</h4>
	<h5>这是标题5</h5>
	<h6>这是标题6</h6>
  </body>

</html>
```



### **②、段落 p**

HTML段落通过标签 <p> 来定义。

```
<!DOCTYPE html>
<html>
  <head>
	<meta  charset="utf-8"/>
	<title>网页标题</title>
  </head>
  <body>
        <p>这一个是段落</p>
	<p>这一个是段落</p>
	<p>这一个是段落</p>
	<p>这一个是段落</p>
  </body>

</html>
```



### **③、超链接 a**

HTML超链接通过标签 <a> 来定义，用于从一个页面链接到另一个页面。

```
<!DOCTYPE html>
<html>
  <head>
	<meta  charset="utf-8"/>
	<title>网页标题</title>
  </head>
  <body>
        <a href = "http://www.baidu.com" title="百度">这是一个超链接</a><br/>
        <a href = "http://www.baidu.com"target="_blank">这是一个超链接，在另一个窗口打</a><br/>
  </body>

</html>
```

a标签中的相关属性：

- href:规定链接的目标 URL，可以是网络连接，也可以是本地文件。
- target：超链接打开的位置，_self自身页面（默认）_blank 新页面。
- title：鼠标放在上面之上后显示的文字。
- rel:指定当前文档与被连接文档的关系。"chapter":文档的章，"section"：文档的节，”subsection”：文档的子段。



### **④、图片 img**

HTML图片通过标签 <img> 来定义。

```
<!DOCTYPE html>
<html>
  <head>
	<meta  charset="utf-8"/>
	<title>网页标题</title>
  </head>
  <body>
        <img src = https://www.baidu.com/img/bd_logo1.png /><br>
        <img src = https://www.baidu.com/img/bd_logo1.png align="right"/><br>
        <img src = https://www.baidu.com/img/bd_logo1.png title="百度图片" alt="百度图片"/><br>
        <a href="https://www.baidu.com"><img src = https://www.baidu.com/img/bd_logo1.png height="100px" width="100px"/></a>

  </body>

</html>
```

img标签中的相关属性：

- src:规定显示图像的 URL。
- align:设置图片周围文字相对于图片的位置，top center botton left right。
- title:图片的标题，鼠标指上后显示的文字。
- alt:当图片无法加载时显示的文字。
- height="100px"width="100px"图片的宽度高度，可以用css样式（style=""）代替。



### **⑤、列表ol-li、ul-li、dl-dt-dd**

HTML列表通过标签<ol>-<li>、<ul>-<li>、<dl>-<dt>-<dd>，分为有序和无序列表。

```
<!DOCTYPE html>
<html>
  <head>
	<meta  charset="utf-8"/>
	<title>网页标题</title>
  </head>
  <body>
	<!-- 定义列表 -->
	<dl>
	    <dt>第一行</dt>
	      <dd>描述第一行</dd>
	    <dt>第二行</dt>
	      <dd>描述第二行</dd>
	 </dl>
	<!-- 无序列表 -->
    <ul>
        <li>ul第一行</li>
        <li>ul第二行</li>
        <li>ul第三行</li>
        <li>ul第四行</li>
    </ul>
	<!-- 无序列表 -->
    <ol>
        <li>ol第一行</li>
        <li>ol第二行</li>
        <li>ol第三行</li>
        <li>ol第四行</li>
    </ol>

  </body>

</html>
```



### **⑥、选择框select-option**

HTML的选择框通过标签<select>-<option>来定义。其中<option>是可以单独使用的，但是不和<select>配合使用那么这个标签无任何意义。

```
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>网页标题</title>
	</head>
	<body>
            <select name="select" autofocus disabled size="5">
                <option value="1">1</option>
	        <option value="2">2</option>
	        <option value="3">3</option>
	        <option value="4">4</option>
	        <option value="5">5</option>
	        <option value="6">6</option>
            </select>
	</body>
</html>
```

select-option标签的相关属性：

- autofocus：在页面加载时下拉列表自动获得焦点。
- disabled：禁止使用下拉列表。
- name：下拉列表的名称。
- required：用户在提交表单前必须选择一个下拉列表中的选项。
- size：下拉列表中可见选项的数目。
- value：选择框提交至服务器的值。
- select：select="selected"表示默认选中。



### **⑦、容器 div、span(重要)**

div和span是HTML中两个非常重要的标签，会在后面的编码中大量使用，尤其是div标签。

```
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>网页标题</title>
	</head>
	<body>
	    <!-- 换行 -->
	    <div>1</div>
	    <div>2</div>
            <div>3</div>
	    <div>4</div>

	    <!-- 不换行 -->
	    <span>5</span>
	    <span>6</span>
	    <span>7</span>
	    <span>8</span>
	</body>
</html>
```

**div和span区别在于，div是一个块级元素，它包含的元素会自动换行，因为div的默认宽度为页面的100%，高度由内容决定。而span是行内元素，在它的前后不会换行，因为span的宽高由内容决定。**span没有结构上的意义，只是单纯的应用样式，其他元素不适合时，就可以使用span元素。span可以作为div的子元素，但div不能是span的子元素，如果出现span中出现div不符合ws3c的页面标准。div和span一般都是用CSS来控制它们的样式。



### **⑧、表格 table**

HTML表格通过<table>标签来定义。表格的行用<tr>表示；表格的表头用<th>表示，默认加粗，单元格居中；表格的列用<td>表示。

```
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
        <table border="1"cellpadding="0" cellspacing="0">
            <tr>
                <th>标题1</th>
                <th>标题2</th>
                <th>标题3</th>
            </tr>
            <tr>
                <td>第一行数据</td>
                <td>第一行数据</td>
                <td>第一行数据</td>
            </tr>
            <tr>
                <td>第二行数据</td>
                <td>第二行数据</td>
                <td>第二行数据</td>
            </tr>
        </table>
    </body>
</html>
```

table标签中的相关属性：

- border：表格边框大小。当border属性增大时，只有外围框线增粗，中间框线不变。
- cellspacing：单元格与单元格之间的距离当，HTML5已经不支持了，建议用CSS代替。
- cellpadding：每个单元格内文字与边缘之间的距离，HTML5已经不支持了，建议用CSS代替。



### **⑨、输入框 input**

HTML输入框通过<input>标签来定义。

```
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
        输入框：<input type="text" name="input" value="默认值"/><br/>
        密码：<input type="password" name="password" value="123456"/><br/>
        提交按钮：<input type="submit" name="submit" value="提交"/><br/>
        单选框：<input type="radio" name="radio" />
               <input type="radio" name="radio" />
               <input type="radio" name="radio" /><br/>
        复选框：<input type="checkbox" name="checkbox"/>
               <input type="checkbox" name="checkbox"/>
               <input type="checkbox" name="checkbox"/><br/>
        重置按钮：<input type="reset" /><br/>
        普通按钮：<input type="button" /><br/>
        文件：<input type="file" name="file"/><br/>
        图片：<input type="image" src="C:\Users\Administrator\Pictures\头像\0.jpg" name="image"/><br/>
        日期时间：<input type="datetime" name="datetime"/><br/>
    </body>
</html>
```

input标签中的相关属性：

- type:表示输入框的类型。下面有详细介绍
- name：输入框的名字，一般情况下必填。因为：传递数据的时候使用name=value 的方式传递。
- value：输入框的默认值。
- placeholder:提示内容，不能指定默认值，当提示框有内容时，提示内容消失。
- autofocus：当页面加载时 <input> 元素应该自动获得焦点。
- pattern：用于验证 <input> 元素的值的正则表达式。
- disabled：是否禁用输入框。
- readonly：输入框字段是只读的。
- checked：默认选中。(只针对 type="radio" 或者 type="checkbox")
- hidden：hidden="hidden"隐藏输入框，用于隐藏域传值。等同于type="hidden"隐藏域传值。
- step：输入框合法数字的间隔。

input标签中的type属性详解：

- text：文本输入框
- password：密码输入框，浏览器显示为点。
- submit：提交按钮：提交表单数据。
- radio：单选按钮。name和value属性值必须全部存在，提交时，提交的是value中的属性。radio凭借name中的属性区别是否为同一组，name相同为同一组，同组中只能选一个。
- checkbox：复选按钮
- reset：将表单数据重置为初始状态。
- file：文件上传按钮。
- image：图形提交按钮。
- bottom：普通按钮。
- date、datetime：日期和日期时间。



### **⑩、表单 form**

HTML表单通过<form>标签来定义。

```
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
	<body>
        <form method="post" action="login" name="form">
            输入框：<input type="text" name="input" value="默认值"/><br/>
            密码：<input type="password" name="password" value="123456"/><br/>
            提交按钮：<input type="submit" name="submit" value="提交"/><br/>

        </form>
	</body>
</html>
```

form标签中的相关属性：

- method：发送表单数据的方法。get和post方式。
- action：表单数据发送地址。
- name：表单的名称。
- enctype：发送表单数据之前如何对其进行编码（适用于 method="post" 的情况）。application/x-www-form-urlencoded是默认编码。multipart/form-data以二进制的形式传送，用于上传文件。text/plain以纯文本形式进行编码，其中不含任何控件或格式字符。

其中get和poet方式的区别在于：

- get传参使用URL：http://服务器地址?name1=value1&name2=value2。使用get方式所有信息可在地址栏看到，并且可以通过地址栏随意传递数据，对数据来说非常不安全。
- post使用HTTP请求传输协议，地址栏不可见，比较安全，且传递数据无限制。所以一般使用post，尽量不要用get。



### **⑪、文本域 textarea**

HTML文本域通过<textarea>标签来定义。

```
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
	</head>
    <body>
        <textarea rows="10" cols="10" name="name">文本域</textarea>
        <textarea rows="10" cols="10" autofocus="autofocus">文本域</textarea>
        <textarea rows="10" cols="10" maxlength="100">文本域</textarea>
        <textarea rows="10" cols="15" placeholder="请输入一段文字"></textarea>
        <textarea rows="10" cols="10" readonly="readonly" required="required">文本域</textarea>
    </body>
</html>
```

textarea标签中的相关属性：

- autofocus：当页面加载时，文本区域自动获得焦点。
- cols：本区域内可见的宽度。
- rows：文本区域内可见的行数。
- disabled：禁用文本区域。
- maxlength：规定文本区域允许的最大字符数。
- name：文本区域的名称。
- placeholder：规定一个简短的提示，描述文本区域期望的输入值。
- readonly：本区域为只读，不可写。
- required：规定文本区域是必需的/必填的。



### **⑫、HTML常用的字符实体**

```
&nbsp; —— 空格
&lt; —— <
&gt; —— >
&amp; —— &
&copy; —— © 版权（copyright）
```

那么H5的入门就介绍怎么多了，正所谓学习是一个在于坚持，在于积累的过程，只要每天学习一点点，每天就会进步一点点，那么今天你学习了吗？