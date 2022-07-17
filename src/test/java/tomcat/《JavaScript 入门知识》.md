<div id="article_content" class="article_content clearfix">
        <link rel="stylesheet" href="https://csdnimg.cn/release/blogv2/dist/mdeditor/css/editerView/ck_htmledit_views-bbac9290cd.css">
                <div id="content_views" class="markdown_views prism-atom-one-light">
                    <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                        <path stroke-linecap="round" d="M5,0 0,2.5 5,5z" id="raphael-marker-block" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                    </svg>
                    <p></p>
<div class="toc">
 <h3><a name="t0"></a>文章目录</h3>
 <ul><li><a href="#1__JavaScript_1" target="_self">1. 认识 JavaScript</a></li><li><ul><li><a href="#11_JavaScript__3" target="_self">1.1 JavaScript 基本介绍</a></li><li><a href="#12_JavaScriptHTML__CSS__14" target="_self">1.2 JavaScript、HTML 和 CSS 之间的关系</a></li><li><a href="#13_JavaScript__24" target="_self">1.3 JavaScript 的运行过程</a></li><li><a href="#14_JavaScript__42" target="_self">1.4 JavaScript 的组成</a></li></ul>
  </li><li><a href="#2_JavaScript__57" target="_self">2. JavaScript 前置知识</a></li><li><ul><li><a href="#21_JavaScript__59" target="_self">2.1 JavaScript 的书写形式</a></li><li><ul><li><a href="#211__61" target="_self">2.1.1 行内式</a></li><li><a href="#212__77" target="_self">2.1.2 内嵌式</a></li><li><a href="#213__91" target="_self">2.1.3 外部式</a></li></ul>
   </li><li><a href="#22_JavaScript__127" target="_self">2.2 JavaScript 的注释</a></li><li><a href="#23_JavaScript__153" target="_self">2.3 JavaScript 的输入输出方式</a></li><li><ul><li><a href="#231__prompt_155" target="_self">2.3.1 输入 prompt</a></li><li><a href="#232__alert_167" target="_self">2.3.2 输出 alert</a></li><li><a href="#233__consolelog_179" target="_self">2.3.3 输出 console.log</a></li></ul>
  </li></ul>
  </li><li><a href="#3__199" target="_self">3. 变量</a></li><li><ul><li><a href="#31__201" target="_self">3.1 基本用法</a></li><li><a href="#32__235" target="_self">3.2 动态类型</a></li></ul>
  </li><li><a href="#4__251" target="_self">4. 基本数据类型</a></li><li><ul><li><a href="#41_number__263" target="_self">4.1 number 数字类型</a></li><li><a href="#42_string__320" target="_self">4.2 string 字符类型</a></li><li><a href="#43_boolean__394" target="_self">4.3 boolean 布尔类型</a></li><li><a href="#44_undefined__409" target="_self">4.4 undefined 未定义数据类型</a></li><li><a href="#45_null__454" target="_self">4.5 null 空值类型</a></li></ul>
  </li><li><a href="#5__489" target="_self">5. 运算符</a></li><li><a href="#6__557" target="_self">6. 逻辑语句</a></li><li><ul><li><a href="#61__561" target="_self">6.1 条件语句</a></li><li><ul><li><a href="#611_if__563" target="_self">6.1.1 if 语句</a></li><li><a href="#612__590" target="_self">6.1.2 三元表达式</a></li><li><a href="#613_switch__598" target="_self">6.1.3 switch 语句</a></li></ul>
   </li><li><a href="#62__615" target="_self">6.2 循环语句</a></li><li><ul><li><a href="#621_while__617" target="_self">6.2.1 while 循环</a></li><li><a href="#622_do_while__627" target="_self">6.2.2 do while 循环</a></li><li><a href="#623_for__637" target="_self">6.2.3 for 循环</a></li></ul>
  </li></ul>
 </li></ul>
</div>
<p></p> 
<h1><a name="t1"></a><a id="1__JavaScript_1"></a>1. 认识 JavaScript</h1> 
<h2><a name="t2"></a><a id="11_JavaScript__3"></a>1.1 JavaScript 基本介绍</h2> 
<p><strong>JavaScript（简称 JS）：</strong></p> 
<ul><li>JS 是一个脚本语言，是一种解释型语言，通过解释器运行</li><li>JS 和 Java 类似，都需要运行在一个“虚拟机“上。JS 运行的虚拟机统称为”JS 引擎”，是 JS 运行过程中的解释器，当今最主流使用的版本是 Google 开发的 V8 Worker</li><li>JS 主要在客户端（浏览器）上运行，现在也可以基于 node.js 在服务器端运行</li><li>JS 能够进行网页开发、网页游戏开发、服务器开发、桌面程序开发、手机 app 开发等等</li></ul> 
<h2><a name="t3"></a><a id="12_JavaScriptHTML__CSS__14"></a>1.2 JavaScript、HTML 和 CSS 之间的关系</h2> 
<div class="table-box"><table><thead><tr><th>语言</th><th>用处</th><th>关系</th></tr></thead><tbody><tr><td>HTML</td><td>用来定义网页的内容</td><td>是网页的结构（骨）</td></tr><tr><td>CSS</td><td>用来规定网页的布局</td><td>是网页的样式（皮）</td></tr><tr><td>JavaScript</td><td>用来对网页进行编程</td><td>是网页的行为（魂）</td></tr></tbody></table></div>
<h2><a name="t4"></a><a id="13_JavaScript__24"></a>1.3 JavaScript 的运行过程</h2> 
<ol><li>首先我们编写的的代码是保存在文件中的，也就是存储在硬盘上</li><li>当我们双击 html 文件后，浏览器应用就会读取文件，把文件内容加载到内存中</li><li>之后浏览器会解析用户编写的代码，将代码翻译成二进制指令，使得计算机能够识别（<mark>解释器的作用</mark>）</li><li>最后得到的二进制指令会被 CPU 加载并执行</li></ol> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/6e44a6de9114bfa491072a5923e05999.png" alt="image-20220205165732554"></p> 
<p><strong>补充：</strong></p> 
<ul><li>上述一系列的<mark>加载、翻译、执行的过程都是由浏览器来执行的</mark></li><li>浏览器分成<mark>渲染引擎</mark>和 <mark>JS 引擎</mark> 
  <ul><li>渲染引擎：用来解析 HTML 和 CSS，俗称<mark>内核</mark></li><li>JS 引擎：是 JS 的解释器，典型的就是 Chrome 中的 V8</li></ul> </li></ul> 
<h2><a name="t5"></a><a id="14_JavaScript__42"></a>1.4 JavaScript 的组成</h2> 
<p>在<mark>浏览器端运行</mark>的 JavaScript 由以下三部分组成：</p> 
<ul><li>ECMAScript（简称 ES）：是 JavaScript 的语法</li><li>DOM（Document Object Modle）：文档对象模型，用于对页面中的元素进行操作。（页面上的每个 HTML 元素，都在 JS 中对应到一个对象，通过 JS 操作这些对象，就可以达到控制页面表现形式的效果）</li><li>BOM（Browser Object Model）：浏览器对象模型，用于对浏览器窗口进行操作。（浏览器也在 JS 中提供了一些对象，例如：刷新页面、控制浏览器窗口大小、前进、后退等等，通过 JS 操作这些对象就可以控制浏览的一些行为）</li></ul> 
<p><strong>注意：</strong></p> 
<ul><li>光有 JS 的语法，只能写一些基础的逻辑流程，但是想要完成更复杂的任务，完成浏览器以及页面的交互，则需要 DOM API 和 BOM API</li><li>上述描述的 JS 的组成主要指在浏览器端运行的 JS，如果是在服务器端的 JS，则需要使用 node.js 的 API</li></ul> 
<h1><a name="t6"></a><a id="2_JavaScript__57"></a>2. JavaScript 前置知识</h1> 
<h2><a name="t7"></a><a id="21_JavaScript__59"></a>2.1 JavaScript 的书写形式</h2> 
<h3><a name="t8"></a><a id="211__61"></a>2.1.1 行内式</h3> 
<p><strong>基本介绍：</strong> 直接嵌入到 html 元素内部</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="0" class="prettyprint"><code class="prism language-html has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>input</span> <span class="token attr-name">type</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>button<span class="token punctuation">"</span></span> <span class="token attr-name">value</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>我是按钮<span class="token punctuation">"</span></span> <span class="token attr-name">onclick</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>alert(<span class="token punctuation">'</span>我出现啦！<span class="token punctuation">'</span>)<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/f214b94f3bb995ea3dcdad74fbecf69d.gif" alt=""></p> 
<p><strong>补充：</strong> alert 的功能就是弹出一个对话框，参数就是对话框显示的内容</p> 
<h3><a name="t9"></a><a id="212__77"></a>2.1.2 内嵌式</h3> 
<p><strong>基本介绍：</strong> 放到 script 标签中</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="1" class="prettyprint"><code class="prism language-html has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>script</span><span class="token punctuation">&gt;</span></span><span class="token script"><span class="token language-javascript">
    <span class="token function">alert</span><span class="token punctuation">(</span><span class="token string">"haha"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</span></span><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>script</span><span class="token punctuation">&gt;</span></span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/ec92d1793ad14e1c53dd87687f3699f6.gif" alt=""></p> 
<h3><a name="t10"></a><a id="213__91"></a>2.1.3 外部式</h3> 
<p><strong>基本介绍：</strong> 写到单独的 js 文件中</p> 
<p><strong>注意：</strong> 这种情况下，script 标签中间不能写代码，写了也不会执行</p> 
<p><strong>示例代码：</strong></p> 
<ul><li> <p>js 文件代码（与下方 html 文件为同级文件，且文件名为 haha.js）</p> <pre data-index="2" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token function">alert</span><span class="token punctuation">(</span><span class="token string">"haha"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> </li><li> <p>html 文件代码</p> <pre data-index="3" class="prettyprint"><code class="prism language-html has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token doctype"><span class="token punctuation">&lt;!</span><span class="token doctype-tag">DOCTYPE</span> <span class="token name">html</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>html</span> <span class="token attr-name">lang</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>en<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>head</span><span class="token punctuation">&gt;</span></span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>meta</span> <span class="token attr-name">charset</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>UTF-8<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>meta</span> <span class="token attr-name">http-equiv</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>X-UA-Compatible<span class="token punctuation">"</span></span> <span class="token attr-name">content</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>IE=edge<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>meta</span> <span class="token attr-name">name</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>viewport<span class="token punctuation">"</span></span> <span class="token attr-name">content</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>width=device-width, initial-scale=1.0<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>title</span><span class="token punctuation">&gt;</span></span>test1<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>title</span><span class="token punctuation">&gt;</span></span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>script</span> <span class="token attr-name">src</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">"</span>haha.js<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span><span class="token script"></span><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>script</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>head</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>body</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>body</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>html</span><span class="token punctuation">&gt;</span></span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li><li style="color: rgb(153, 153, 153);">6</li><li style="color: rgb(153, 153, 153);">7</li><li style="color: rgb(153, 153, 153);">8</li><li style="color: rgb(153, 153, 153);">9</li><li style="color: rgb(153, 153, 153);">10</li><li style="color: rgb(153, 153, 153);">11</li><li style="color: rgb(153, 153, 153);">12</li><li style="color: rgb(153, 153, 153);">13</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/0efcc39f216b8026f8d336675452a4fc.gif" alt=""></p> </li></ul> 
<h2><a name="t11"></a><a id="22_JavaScript__127"></a>2.2 JavaScript 的注释</h2> 
<p><strong>基本介绍：</strong></p> 
<ul><li> <p>单行注释：<code>//</code></p> <pre data-index="4" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// 我是单行注释</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> </li><li> <p>多行注释：<code>/* */</code></p> <pre data-index="5" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">/*
 我是多行注释
 我是多行注释
*/</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li></ul></pre> </li></ul> 
<p><strong>注意：</strong></p> 
<ul><li>可以使用 <code>Ctrl + /</code> 快速注释</li><li>多行注释不能嵌套</li></ul> 
<h2><a name="t12"></a><a id="23_JavaScript__153"></a>2.3 JavaScript 的输入输出方式</h2> 
<h3><a name="t13"></a><a id="231__prompt_155"></a>2.3.1 输入 prompt</h3> 
<p><strong>基本介绍：</strong> 弹出一个输入框</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="6" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token function">prompt</span><span class="token punctuation">(</span><span class="token string">"请输入你的姓名:"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/ad50a4e7d321e026ff411621e0fb0735.gif" alt=""></p> 
<h3><a name="t14"></a><a id="232__alert_167"></a>2.3.2 输出 alert</h3> 
<p><strong>基本介绍：</strong> 弹出一个警示对话框，输出结果</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="7" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token function">alert</span><span class="token punctuation">(</span><span class="token string">"我是大魔王"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/c7682f63d29708a93f7172d48a89d1a6.gif" alt=""></p> 
<h3><a name="t15"></a><a id="233__consolelog_179"></a>2.3.3 输出 console.log</h3> 
<p><strong>基本介绍：</strong> 在控制台打印一个日志</p> 
<p><strong>注意：</strong> 需要打开浏览器的开发者工具（F12），在 Console 标签页才能看到结果</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="8" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;">console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token string">"这是一条日志"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/27811f56bf7145c76e7eab2d2c0f1e25.png" alt="image-20220205194957213"></p> 
<ul><li><code>console</code> 是 js 中的一个对象，表示控制台</li><li><code>.</code> 表示取对象中的某个属性或者方法</li><li><code>console.log</code> 可以理解为使用控制台对象的 log 方法</li></ul> 
<h1><a name="t16"></a><a id="3__199"></a>3. 变量</h1> 
<h2><a name="t17"></a><a id="31__201"></a>3.1 基本用法</h2> 
<p><strong>基本介绍：</strong> 变量的创建有两个关键字可以使用，分别是 var 和 let</p> 
<ul><li> <p>使用 var 创建变量</p> <pre data-index="9" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">var</span> name <span class="token operator">=</span> <span class="token string">'大魔王'</span><span class="token punctuation">;</span>
<span class="token keyword">var</span> name <span class="token operator">=</span> <span class="token number">20</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> </li><li> <p>使用 let 创建变量（推荐使用）</p> <pre data-index="10" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> name <span class="token operator">=</span> <span class="token string">'abc'</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> money<span class="token operator">=</span><span class="token number">30.5</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> </li></ul> 
<p><strong>注意：</strong></p> 
<ul><li>在 js 中，创建变量的时候并不需要指定变量的类型，但是并不意味着这些变量没有类型，= 后面赋的值是什么类型，该变量就是什么类型</li><li>使用 var 定义变量时，一个变量名可以被重复定义，并且不会报错，该变量的值为最后一次定义的变量值</li><li>使用 let 定义变量时，一个变量名不可以被重复定义</li></ul> 
<p><strong>示例代码：</strong></p> 
<pre data-index="11" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> name <span class="token operator">=</span> <span class="token function">prompt</span><span class="token punctuation">(</span><span class="token string">"请输入你的姓名:"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> age <span class="token operator">=</span> <span class="token function">prompt</span><span class="token punctuation">(</span><span class="token string">"请输入你的年龄:"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token function">alert</span><span class="token punctuation">(</span><span class="token string">"你的姓名是:"</span> <span class="token operator">+</span> name <span class="token operator">+</span> <span class="token string">"\n"</span> <span class="token operator">+</span> <span class="token string">"你的年龄是:"</span> <span class="token operator">+</span> age<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/21822cf205f725c19a6508e7646d6087.gif" alt=""></p> 
<h2><a name="t18"></a><a id="32__235"></a>3.2 动态类型</h2> 
<p>JS 中的变量是动态类型的，意思是<mark>变量的类型可以在程序运行过程中发生改变</mark>，例如</p> 
<pre data-index="12" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// 将字符串类型的 name 变量改为 数字类型</span>
<span class="token keyword">let</span> name <span class="token operator">=</span> <span class="token string">'大魔王'</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>name<span class="token punctuation">)</span><span class="token punctuation">;</span>
name <span class="token operator">=</span> <span class="token number">13</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>name<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/4214a479fd1adfaf61da680dc1e7707b.png" alt="image-20220205211117792"></p> 
<h1><a name="t19"></a><a id="4__251"></a>4. 基本数据类型</h1> 
<p>JS 中内置了几种数据类型类型：</p> 
<ul><li>number：数字类型，不区分整数和浮点数</li><li>boolean：布尔类型，true 是真，false 是假</li><li>string：字符串类型</li><li>undefined：未定义类型，表示变量的值还没有定义</li><li>null：空值类型，值唯一为 null</li></ul> 
<h2><a name="t20"></a><a id="41_number__263"></a>4.1 number 数字类型</h2> 
<p><strong>基本介绍：</strong> JS 中不区分整数和浮点数，统一用数字类型 number 来表示</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="13" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token number">10</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> b <span class="token operator">=</span> <span class="token number">2.5</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a<span class="token punctuation">)</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>b<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/575402148681b322229fa480f8e341dc.png" alt="image-20220205224214059"></p> 
<p><strong>数字进制表示：</strong></p> 
<ul><li> <p>八进制：以 0 开头</p> <pre data-index="14" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// a的十进制值为7</span>
<span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token number">07</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> </li><li> <p>十六进制：以 0x 开头</p> <pre data-index="15" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// a的十进制为7</span>
<span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token number">0x7</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> </li><li> <p>二进制：以 0b 开头</p> <pre data-index="16" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// a的十进制为7</span>
<span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token number">0b111</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> </li></ul> 
<p><strong>特殊的数字值：</strong> 当变量的值为浮点数时，会有以下三个特殊的值</p> 
<ul><li>Infinity：表示无穷大，即数字已经超过了 JS 能表示最大的范围</li><li>-Infinity：表示负无穷大，即数字已经超过了 JS 能表示的最小范围，注意负无穷大不是无穷小</li><li>NaN：表示当前的结果不是一个数字 not a number</li></ul> 
<pre data-index="17" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// Number.MAX_VALUE 为 JS 的最大值</span>
<span class="token keyword">let</span> max <span class="token operator">=</span> Number<span class="token punctuation">.</span><span class="token constant">MAX_VALUE</span><span class="token punctuation">;</span>
<span class="token comment">// 得到 Infinity</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token number">2</span><span class="token operator">*</span>max<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 得到 -Infinity</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token operator">-</span><span class="token number">2</span><span class="token operator">*</span>max<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 得到 NaN</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token string">'abc'</span> <span class="token operator">+</span> <span class="token number">10</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li><li style="color: rgb(153, 153, 153);">6</li><li style="color: rgb(153, 153, 153);">7</li><li style="color: rgb(153, 153, 153);">8</li></ul></pre> 
<h2><a name="t21"></a><a id="42_string__320"></a>4.2 string 字符类型</h2> 
<p><strong>基本介绍：</strong> 将字面值用单引号或者双引号括起来表示字符类型</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="18" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token string">"abc"</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> b <span class="token operator">=</span> <span class="token string">'大魔王'</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> 
<p><strong>字符串本身包含引号的写法：</strong></p> 
<ul><li> <p>方式一：本身带有双引号，外面就用单引号括起来</p> <pre data-index="19" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> s1 <span class="token operator">=</span> <span class="token string">'I am "Peter"'</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> </li><li> <p>方式二：本身带有单引号，外面就用双引号括起来</p> <pre data-index="20" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> s2 <span class="token operator">=</span> <span class="token string">"I am 'Peter''"</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> </li><li> <p>方式三：使用转义字符 <code>\"</code> 或 <code>\'</code></p> <pre data-index="21" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> s3 <span class="token operator">=</span> <span class="token string">"I am \'Peter\'"</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> s4 <span class="token operator">=</span> <span class="token string">"I am \"Peter\""</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> </li></ul> 
<p><strong>转义字符：</strong> 有些字符不方便直接输入，需要通过一些特殊方式来表示</p> 
<ul><li>换行符：<code>\n</code></li><li>反斜杠：<code>\\</code></li><li>单引号：<code>\'</code></li><li>双引号：<code>\"</code></li><li>Tab 符：<code>\t</code></li></ul> 
<p><strong>字符串常用方法：</strong></p> 
<ul><li> <p>求长度：使用 string 的 length 属性</p> <pre data-index="22" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> s1 <span class="token operator">=</span> <span class="token string">"abc"</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>s1<span class="token punctuation">.</span>length<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> s2 <span class="token operator">=</span> <span class="token string">"吞吞吐吐大魔王"</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>s2<span class="token punctuation">.</span>length<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/3cbddbff571a6b6a09fe4d429e7ec5a8.png" alt="image-20220205232649586"></p> </li><li> <p>字符串拼接：使用 + 进行拼接（和 Java 的字符串拼接相同）</p> <pre data-index="23" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> s1 <span class="token operator">=</span> <span class="token string">"abc"</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> s2 <span class="token operator">=</span> <span class="token string">"吞吞吐吐大魔王"</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>s1 <span class="token operator">+</span> s2<span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">let</span> s3 <span class="token operator">=</span> <span class="token string">"50"</span><span class="token punctuation">;</span>
<span class="token keyword">let</span> s4 <span class="token operator">=</span> <span class="token number">100</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>s3 <span class="token operator">+</span> s4<span class="token punctuation">)</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token keyword">typeof</span><span class="token punctuation">(</span>s3 <span class="token operator">+</span> s4<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">let</span> s5 <span class="token operator">=</span> <span class="token number">50</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>s5 <span class="token operator">+</span> s4<span class="token punctuation">)</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token keyword">typeof</span><span class="token punctuation">(</span>s5 <span class="token operator">+</span> s4<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li><li style="color: rgb(153, 153, 153);">6</li><li style="color: rgb(153, 153, 153);">7</li><li style="color: rgb(153, 153, 153);">8</li><li style="color: rgb(153, 153, 153);">9</li><li style="color: rgb(153, 153, 153);">10</li><li style="color: rgb(153, 153, 153);">11</li><li style="color: rgb(153, 153, 153);">12</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/3c87a746e9b31fd968aca3e894e768c3.png" alt="image-20220205233238959"></p> </li></ul> 
<h2><a name="t22"></a><a id="43_boolean__394"></a>4.3 boolean 布尔类型</h2> 
<p><strong>基本介绍：</strong> true 表示真，false 表示假，并且在 JS 中，<mark>boolean 类型可以参与算术运算，true 表示1，false 表示0</mark></p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="24" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;">console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token boolean">true</span> <span class="token operator">+</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span>	<span class="token comment">// 2</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span><span class="token boolean">false</span> <span class="token operator">-</span> <span class="token number">1</span><span class="token punctuation">)</span><span class="token punctuation">;</span>	<span class="token comment">// -1</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> 
<p>JS 的布尔类型可以进行算术运算，证明了该语言是<mark>弱类型</mark>语言</p> 
<h2><a name="t23"></a><a id="44_undefined__409"></a>4.4 undefined 未定义数据类型</h2> 
<p><strong>基本介绍：</strong> 如果一个变量没有被初始化过，结果就是 undefined</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="25" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a<span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/f74e78828908b83bf4b5548e7bcc5cc0.png" alt="image-20220205233838150"></p> 
<p><strong>undefined 的两种加法运算：</strong></p> 
<ul><li> <p>与字符串进行相加，结果为字符串</p> <pre data-index="26" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a<span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a <span class="token operator">+</span> <span class="token string">"吞吞吐吐大魔王"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/13cee2df39974d5f17d297a0592cba1a.png" alt="image-20220205234339058"></p> </li><li> <p>与数字进行相加，结果为 NaN</p> <pre data-index="27" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a<span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a <span class="token operator">+</span> <span class="token number">10</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/d048025e81d94a5cf7dc557636685d91.png" alt="image-20220205234411298"></p> </li></ul> 
<p>如果参与运算的变量为 undefined，通过上述示例，那么可能会对于接下来的运算结果产生影响，因此，为了防止这种现象，可以让 undefined 的值变为0</p> 
<pre data-index="28" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a<span class="token punctuation">;</span>
a <span class="token operator">=</span> a <span class="token operator">||</span> <span class="token number">0</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/a5f4719b6e750e4bb6fe0a716f05e231.png" alt="image-20220205235500494"></p> 
<h2><a name="t24"></a><a id="45_null__454"></a>4.5 null 空值类型</h2> 
<p><strong>基本介绍：</strong> null 表示当前的变量是一个空值</p> 
<p><strong>示例代码：</strong></p> 
<pre data-index="29" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token keyword">null</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> 
<p><img src="https://img-blog.csdnimg.cn/img_convert/983d3f5f7f7664cd0069a2544f20148d.png" alt="image-20220205235647686"></p> 
<p><strong>null 的两种加法运算：</strong></p> 
<ul><li> <p>与字符串进行相加，结果为字符串</p> <pre data-index="30" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token keyword">null</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a <span class="token operator">+</span> <span class="token string">"吞吞吐吐大魔王"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/9e413642b5c31432e30d059efc5837f7.png" alt="image-20220205235923111"></p> </li><li> <p>与数字进行相加时，null 视为0</p> <pre data-index="31" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">let</span> a <span class="token operator">=</span> <span class="token keyword">null</span><span class="token punctuation">;</span>
console<span class="token punctuation">.</span><span class="token function">log</span><span class="token punctuation">(</span>a <span class="token operator">+</span> <span class="token number">50</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li></ul></pre> <p><img src="https://img-blog.csdnimg.cn/img_convert/102bb22e8908deee491ba3056bc90672.png" alt="image-20220205235943862"></p> </li></ul> 
<h1><a name="t25"></a><a id="5__489"></a>5. 运算符</h1> 
<div class="table-box"><table><thead><tr><th>算术运算符</th><th>描述</th></tr></thead><tbody><tr><td>+</td><td>加</td></tr><tr><td>-</td><td>减</td></tr><tr><td>*</td><td>乘</td></tr><tr><td>/</td><td>除</td></tr><tr><td>%</td><td>取余</td></tr></tbody></table></div> 
<div class="table-box"><table><thead><tr><th>赋值运算符</th><th>描述</th></tr></thead><tbody><tr><td>=</td><td>赋值</td></tr><tr><td>+=</td><td>加等</td></tr><tr><td>-=</td><td>减等</td></tr><tr><td>*=</td><td>乘等</td></tr><tr><td>/=</td><td>除等</td></tr><tr><td>%=</td><td>取余等</td></tr></tbody></table></div> 
<div class="table-box"><table><thead><tr><th>自增自减运算符</th><th>描述</th></tr></thead><tbody><tr><td>++</td><td>自增1</td></tr><tr><td>–</td><td>自减1</td></tr></tbody></table></div> 
<div class="table-box"><table><thead><tr><th>比较运算符</th><th>描述</th></tr></thead><tbody><tr><td>&lt;</td><td>小于</td></tr><tr><td>&gt;</td><td>大于</td></tr><tr><td>&lt;=</td><td>小于等于</td></tr><tr><td>&gt;=</td><td>大于等于</td></tr><tr><td>==</td><td>等于</td></tr><tr><td>!=</td><td>不等于</td></tr><tr><td>===</td><td>等于</td></tr><tr><td>!==</td><td>不等于</td></tr></tbody></table></div> 
<div class="table-box"><table><thead><tr><th>逻辑运算符</th><th>描述</th></tr></thead><tbody><tr><td>&amp;&amp;</td><td>与</td></tr><tr><td>||</td><td>或</td></tr><tr><td>!</td><td>非</td></tr></tbody></table></div> 
<div class="table-box"><table><thead><tr><th>位运算符</th><th>描述</th></tr></thead><tbody><tr><td>&amp;</td><td>按位与</td></tr><tr><td>|</td><td>按位或</td></tr><tr><td>~</td><td>按位取反</td></tr><tr><td>^</td><td>按位异或</td></tr></tbody></table></div> 
<div class="table-box"><table><thead><tr><th>移位运算符</th><th>描述</th></tr></thead><tbody><tr><td>&lt;&lt;</td><td>左移</td></tr><tr><td>&gt;&gt;</td><td>有符号右移（算术右移）</td></tr><tr><td>&gt;&gt;&gt;</td><td>无符号右移（逻辑右移）</td></tr></tbody></table></div>
<p><strong>注意：</strong> 以上运算符和 Java 中的大部分都相同，下面介绍几处不同点</p> 
<ul><li> <p>==：在 JS 中， 等号可以用来直接比较字符串内容（不像 Java，需要使用 equals），并且比较的时候，只比较值，不比较类型（会触发隐式类型转换，比较的是转换后的结果）</p> </li><li> <p>===：JS 独有的运算符，比较的时候，会同时比较值和类型（不会触发隐式类型转换，如果类型不同，直接返回 false）</p> </li><li> <p>!==：比较的时候，会同时比较值和类型</p> </li><li> <p>&amp;&amp;：一假则假，<mark>如果 <code>a&amp;&amp;b</code> 表达式中，a的值为真，则表达式的值就是b的值；如果a的值为假，则表达式的值就是a的值</mark></p> </li><li> <p>||：一真则真，<mark>如果 <code>a||b</code> 表达式中，a的值为真，则表达式的值就是a的值；如果a的值为假，则表达式的值就是b的值</mark></p> </li><li> <p>JS 中的位运算，不区分整数和小数，但是数据在进行位运算之前会先隐式转换成一个4个字节的整数，然后再进行运算</p> </li></ul> 
<h1><a name="t26"></a><a id="6__557"></a>6. 逻辑语句</h1> 
<p>JS 中的逻辑语句和 Java 中的类似，其中这些语句中的条件表达式的值可以不仅仅是布尔类型，还可以是字符串类型等等。</p> 
<h2><a name="t27"></a><a id="61__561"></a>6.1 条件语句</h2> 
<h3><a name="t28"></a><a id="611_if__563"></a>6.1.1 if 语句</h3> 
<pre data-index="32" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token comment">// 形式1</span>
<span class="token keyword">if</span> <span class="token punctuation">(</span>条件<span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	语句
<span class="token punctuation">}</span>
<span class="token comment">// 形式2</span>
<span class="token keyword">if</span> <span class="token punctuation">(</span>条件<span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	语句<span class="token number">1</span>
<span class="token punctuation">}</span> <span class="token keyword">else</span> <span class="token punctuation">{<!-- --></span>
	语句<span class="token number">2</span>
<span class="token punctuation">}</span>
<span class="token comment">// 形式3</span>
<span class="token keyword">if</span> <span class="token punctuation">(</span>条件<span class="token number">1</span><span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	语句<span class="token number">1</span>
<span class="token punctuation">}</span> <span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token punctuation">(</span>条件<span class="token number">2</span><span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	语句<span class="token number">2</span>
<span class="token punctuation">}</span> <span class="token keyword">else</span> <span class="token keyword">if</span> <span class="token operator">...</span><span class="token punctuation">.</span> <span class="token punctuation">{<!-- --></span>
	语句<span class="token operator">...</span>
<span class="token punctuation">}</span> <span class="token keyword">else</span> <span class="token punctuation">{<!-- --></span>
	语句<span class="token constant">N</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li><li style="color: rgb(153, 153, 153);">6</li><li style="color: rgb(153, 153, 153);">7</li><li style="color: rgb(153, 153, 153);">8</li><li style="color: rgb(153, 153, 153);">9</li><li style="color: rgb(153, 153, 153);">10</li><li style="color: rgb(153, 153, 153);">11</li><li style="color: rgb(153, 153, 153);">12</li><li style="color: rgb(153, 153, 153);">13</li><li style="color: rgb(153, 153, 153);">14</li><li style="color: rgb(153, 153, 153);">15</li><li style="color: rgb(153, 153, 153);">16</li><li style="color: rgb(153, 153, 153);">17</li><li style="color: rgb(153, 153, 153);">18</li><li style="color: rgb(153, 153, 153);">19</li><li style="color: rgb(153, 153, 153);">20</li></ul></pre> 
<h3><a name="t29"></a><a id="612__590"></a>6.1.2 三元表达式</h3> 
<pre data-index="33" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;">条件 <span class="token operator">?</span> 表达式<span class="token number">1</span> <span class="token operator">:</span> 表达式<span class="token number">2</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li></ul></pre> 
<h3><a name="t30"></a><a id="613_switch__598"></a>6.1.3 switch 语句</h3> 
<pre data-index="34" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">switch</span> <span class="token punctuation">(</span>表达式<span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	<span class="token keyword">case</span> 值<span class="token number">1</span><span class="token operator">:</span>
		语句<span class="token number">1</span><span class="token punctuation">;</span>
		<span class="token keyword">break</span><span class="token punctuation">;</span>
	<span class="token keyword">case</span> 值<span class="token number">2</span><span class="token operator">:</span>
		语句<span class="token number">2</span><span class="token punctuation">;</span>
		<span class="token keyword">break</span><span class="token punctuation">;</span>
	<span class="token keyword">default</span><span class="token operator">:</span>
		语句<span class="token constant">N</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li><li style="color: rgb(153, 153, 153);">6</li><li style="color: rgb(153, 153, 153);">7</li><li style="color: rgb(153, 153, 153);">8</li><li style="color: rgb(153, 153, 153);">9</li><li style="color: rgb(153, 153, 153);">10</li></ul></pre> 
<h2><a name="t31"></a><a id="62__615"></a>6.2 循环语句</h2> 
<h3><a name="t32"></a><a id="621_while__617"></a>6.2.1 while 循环</h3> 
<pre data-index="35" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">while</span> <span class="token punctuation">(</span>条件<span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	循环体<span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li></ul></pre> 
<h3><a name="t33"></a><a id="622_do_while__627"></a>6.2.2 do while 循环</h3> 
<pre data-index="36" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">do</span> <span class="token punctuation">{<!-- --></span>
    循环体<span class="token punctuation">;</span>
<span class="token punctuation">}</span> <span class="token keyword">while</span><span class="token punctuation">(</span>条件<span class="token punctuation">)</span><span class="token punctuation">;</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li></ul></pre> 
<h3><a name="t34"></a><a id="623_for__637"></a>6.2.3 for 循环</h3> 
<pre data-index="37" class="prettyprint"><code class="prism language-js has-numbering" onclick="mdcp.copyCode(event)" style="position: unset;"><span class="token keyword">for</span> <span class="token punctuation">(</span>表达式<span class="token number">1</span><span class="token punctuation">;</span> 表达式<span class="token number">2</span><span class="token punctuation">;</span> 表达式<span class="token number">3</span><span class="token punctuation">)</span> <span class="token punctuation">{<!-- --></span>
	循环体
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></code><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li></ul></pre>
                </div><div data-report-view="{&quot;mod&quot;:&quot;1585297308_001&quot;,&quot;spm&quot;:&quot;1001.2101.3001.6548&quot;,&quot;dest&quot;:&quot;https://t4dmw.blog.csdn.net/article/details/122799231&quot;,&quot;extend1&quot;:&quot;pc&quot;,&quot;ab&quot;:&quot;new&quot;}"><div></div></div>
                <link href="https://csdnimg.cn/release/blogv2/dist/mdeditor/css/editerView/markdown_views-3fd7f7a902.css" rel="stylesheet">
                <link href="https://csdnimg.cn/release/blogv2/dist/mdeditor/css/style-49037e4d27.css" rel="stylesheet">
        </div>