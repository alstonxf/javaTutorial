# 浅析-vue.js
# 学习目标
- 会创建Vue实例，知道Vue的常见属性- 会使用Vue的生命周期的钩子函数- 会使用vue常见指令- 会使用vue计算属性和watch监控- 会编写Vue组件- 掌握组件间通信- 了解vue-router使用- 了解webpack使用- 会使用vue-cli搭建项目
# 0.前言

前几天我们已经对后端的技术栈有了初步的了解、并且已经搭建了整个后端微服务的平台。接下来要做的事情就是功能开发了。但是没有前端页面，我们肯定无从下手，因此今天我们就要来了解一下前端的一些技术，完成前端页面搭建。

先聊一下前端开发模式的发展。

>  
 静态页面 


最初的网页以HTML为主，是纯静态的网页。网页是只读的，信息流只能从服务端到客户端单向流通。**开发人员也只关心页面的样式和内容**即可。

>  
 异步刷新，操作DOM 


1995年，网景工程师Brendan Eich 花了10天时间设计了JavaScript语言.

随着JavaScript的诞生，我们可以操作页面的DOM元素及样式，页面有了一些动态的效果，但是依然是以静态为主。

ajax盛行：
- 2005年开始，ajax逐渐被前端开发人员所重视，因为不用刷新页面就可以更新页面的数据和渲染效果。- 此时的**开发人员不仅仅要编写HTML样式，还要懂ajax与后端交互，然后通过JS操作Dom元素来实现页面动态效果**。比较流行的框架如Jquery就是典型代表。
>  
 MVVM，关注模型和视图 


2008年，google的Chrome发布，随后就以极快的速度占领市场，超过IE成为浏览器市场的主导者。

2009年，Ryan Dahl在谷歌的Chrome V8引擎基础上，打造了基于事件循环的异步IO框架：Node.js。
- 基于事件循环的异步IO- 单线程运行，避免多线程的变量同步问题- JS可以编写后台代码，前后台统一编程语言
node.js的伟大之处不在于让JS迈向了后端开发，而是构建了一个庞大的生态系统。

2010年，NPM作为node.js的包管理系统首次发布，开发人员可以遵循Common.js规范来编写Node.js模块，然后发布到NPM上供其他开发人员使用。目前已经是世界最大的包模块管理系统。

随后，在node的基础上，涌现出了一大批的前端框架：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-W08v6Ev8-1591000574682)(assets/1525825983230.png)] <img src="https://img-blog.csdnimg.cn/20200603184147683.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

>  
 MVVM模式 

- M：即Model，模型，包括数据和一些基本操作- V：即View，视图，页面渲染结果- VM：即View-Model，模型与视图间的双向操作（无需开发人员干涉）
在MVVM之前，开发人员从后端获取需要的数据模型，然后要通过DOM操作Model渲染到View中。而后当用户操作视图，我们还需要通过DOM获取View中的数据，然后同步到Model中。

而MVVM中的VM要做的事情就是把DOM操作完全封装起来，开发人员不用再关心Model和View之间是如何互相影响的：
- 只要我们Model发生了改变，View上自然就会表现出来。- 当用户修改了View，Model中的数据也会跟着改变。
把开发人员从繁琐的DOM操作中解放出来，把关注点放在如何操作Model上。

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-JeNeP8i3-1591000574691)(assets/1525828854056.png)]

<img src="https://img-blog.csdnimg.cn/20200603184217723.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

而我们今天要学习的，就是一款MVVM模式的框架：Vue

# 1.认识Vue

Vue (读音 /vjuː/，类似于 **view**) 是一套用于构建用户界面的**渐进式框架**。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。另一方面，当与现代化的工具链</a>以及各种支持类库</a>结合使用时，Vue 也完全能够为复杂的单页应用提供驱动。

​ 前端框架三巨头：Vue.js、React.js、AngularJS，vue.js以其轻量易用著称，vue.js和React.js发展速度最快，AngularJS还是老大。

官网：https://cn.vuejs.org/

参考：https://cn.vuejs.org/v2/guide/

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-IfutqndL-1591000574695)(assets/1525829249048.png)] <img src="https://img-blog.csdnimg.cn/20200603184433626.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> Git地址：https://github.com/vuejs

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-CDzrdAT3-1591000574701)(assets/1525829030730.png)] <img src="https://img-blog.csdnimg.cn/2020060318445640.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> **尤雨溪**，Vue.js 创作者，Vue Technology创始人，致力于Vue的研究开发。

# 2.Node和NPM

前面说过，NPM是Node提供的模块管理工具，可以非常方便的下载安装很多前端框架，包括Jquery、AngularJS、VueJs都有。为了后面学习方便，我们先安装node及NPM工具。

## 2.1.下载Node.js

下载地址：https://nodejs.org/en/

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-UaFAwCqz-1591000574705)(assets/1529594451775.png)] <img src="https://img-blog.csdnimg.cn/20200603184521634.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 推荐下载LTS版本。

课程中采用的是8.11.3版本。也是目前最新的。大家自行下载或者使用课前资料中提供的安装包。然后下一步安装即可。

完成以后，在控制台输入：

```
node -v

```

看到版本信息：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-TUSzmHwq-1591000574708)(assets/1529595770482.png)]

<img src="https://img-blog.csdnimg.cn/20200603184544507.png" alt="在这里插入图片描述"/>

## 2.2.NPM

Node自带了NPM了，在控制台输入`npm -v`查看：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-kWyq7HZE-1591000574710)(assets/1529595810923.png)]

<img src="https://img-blog.csdnimg.cn/20200603184612553.png" alt="在这里插入图片描述"/>

npm默认的仓库地址是在国外网站，速度较慢，建议大家设置到淘宝镜像。但是切换镜像是比较麻烦的。推荐一款切换镜像的工具：nrm

我们首先安装nrm，这里`-g`代表全局安装。可能需要一点儿时间

```
npm install nrm -g

```

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-mdG8jCvK-1591000574712)(assets/1529596099952.png)] <img src="https://img-blog.csdnimg.cn/20200603184635574.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 然后通过`nrm ls`命令查看npm的仓库列表,带*的就是当前选中的镜像仓库：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-FDnvdKLy-1591000574712)(assets/1529596219439.png)] <img src="https://img-blog.csdnimg.cn/2020060318471515.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 通过`nrm use taobao`来指定要使用的镜像源：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-G9tOFmbN-1591000574714)(assets/1529596312671.png)] <img src="https://img-blog.csdnimg.cn/20200603184742926.png" alt="在这里插入图片描述"/> 然后通过`nrm test npm`来测试速度：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-xaHcts2o-1591000574716)(assets/1529596566134.png)] <img src="https://img-blog.csdnimg.cn/20200603184804601.png" alt="在这里插入图片描述"/>

注意：
- 有教程推荐大家使用cnpm命令，但是使用发现cnpm有时会有bug，不推荐。- 安装完成请一定要重启下电脑！！！- 安装完成请一定要重启下电脑！！！- 安装完成请一定要重启下电脑！！！
# 3.快速入门

接下来，我们快速领略下vue的魅力

## 3.1.创建工程

创建一个新的空工程：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-lmmq6dEE-1591000574716)(assets/1529596874127.png)] <img src="https://img-blog.csdnimg.cn/20200603184906612.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> [外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-riNExsJW-1591000574718)(assets/1529597228506.png)] <img src="https://img-blog.csdnimg.cn/20200603184926472.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 然后新建一个module：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-SQR4IAmi-1591000574720)(assets/1529597325121.png)] <img src="https://img-blog.csdnimg.cn/20200603184948271.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 选中static web，静态web项目：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-M6bsK1va-1591000574721)(assets/1529597573453.png)] <img src="https://img-blog.csdnimg.cn/20200603185106435.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 位置信息：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-3jsr69Mn-1591000574722)(assets/1529597672429.png)]

<img src="https://img-blog.csdnimg.cn/20200603185132460.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 3.2.安装vue

### 3.2.1.下载安装

下载地址：https://github.com/vuejs/vue

可以下载2.5.16版本https://github.com/vuejs/vue/archive/v2.5.16.zip

下载解压，得到vue.js文件。

### 3.2.2.使用CDN

或者也可以直接使用公共的CDN服务：

```
<!-- 开发环境版本，包含了用帮助的命令行警告 -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

```

或者：

```
<!-- 生产环境版本，优化了尺寸和速度 -->
<script src="https://cdn.jsdelivr.net/npm/vue"></script>

```

### 3.2.3.推荐npm安装

在idea的左下角，有个Terminal按钮，点击打开控制台：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-WKe1CSzC-1591000574723)(assets/1529598030268.png)] <img src="https://img-blog.csdnimg.cn/20200603185214345.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 进入hello-vue目录，先输入：`npm init -y` 进行初始化

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-VxSmPrEb-1591000574725)(assets/1529598244471.png)] <img src="https://img-blog.csdnimg.cn/20200603185239618.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 安装Vue，输入命令：`npm install vue --save`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-jl8S0zb9-1591000574727)(assets/1529598444504.png)] <img src="https://img-blog.csdnimg.cn/20200603185306269.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 然后就会在hello-vue目录发现一个node_modules目录，并且在下面有一个vue目录。

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-AEDJhSTJ-1591000574728)(assets/1529602488684.png)] <img src="https://img-blog.csdnimg.cn/20200603185327261.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> node_modules是通过npm安装的所有模块的默认位置。

## 3.3.vue入门案例

### 3.3.1.HTML模板

在hello-vue目录新建一个HTML

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-7oVApznn-1591000574729)(assets/1529719572523.png)] <img src="https://img-blog.csdnimg.cn/20200603185357134.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 在hello.html中，我们编写一段简单的代码：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-HGh1jrTL-1591000574730)(assets/1529719673944.png)] <img src="https://img-blog.csdnimg.cn/20200603185417904.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> h2中要输出一句话：xx 非常帅。前面的xx是要渲染的数据。

### 3.3.2.vue声明式渲染

然后我们通过Vue进行渲染：

```
<body>
    
        <h2>{<!-- -->{name}}，非常帅！！！</h2>
    
</body>
<script src="node_modules/vue/dist/vue.js" ></script>
<script>
    // 创建vue实例
    var app = new Vue({<!-- -->
        el:"#app", // el即element，该vue实例要渲染的页面元素
        data:{<!-- --> // 渲染页面需要的数据
            name: "峰哥"
        }
    });

</script>

```
- 首先通过 new Vue()来创建Vue实例<li>然后构造函数接收一个对象，对象中有一些属性： 
  <ul>- el：是element的缩写，通过id选中要渲染的页面元素，本例中是一个div<li>data：数据，数据是一个对象，里面有很多属性，都可以渲染到视图中 
    <ul>- name：这里我们指定了一个name属性
打开页面查看效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-mIrnSC0Q-1591000574731)(assets/1529722898366.png)] <img src="https://img-blog.csdnimg.cn/20200603185454659.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 更神奇的在于，当你修改name属性时，页面会跟着变化：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-EuBKlxSo-1591000574732)(assets/1529723206508.png)]

<img src="https://img-blog.csdnimg.cn/20200603185526972.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 3.3.3.双向绑定

我们对刚才的案例进行简单修改：

```
<body>
    
        <input type="text" v-model="num">
        <h2>
            {<!-- -->{name}}，非常帅！！！有{<!-- -->{num}}位女神为他着迷。
        </h2>
    
</body>
<script src="node_modules/vue/dist/vue.js" ></script>
<script>
    // 创建vue实例
    var app = new Vue({<!-- -->
        el: "#app", // el即element，该vue实例要渲染的页面元素
        data: {<!-- --> // 渲染页面需要的数据
            name: "峰哥",
            num: 5
        }
    });

</script>

```
- 我们在data添加了新的属性：`num`- 在页面中有一个`input`元素，通过`v-model`与`num`进行绑定。- 同时通过`{<!-- -->{num}}`在页面输出
效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-BjHYcxJ1-1591000574733)(assets/52.gif)] <img src="https://img-blog.csdnimg.cn/20200603203007373.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 我们可以观察到，输入框的变化引起了data中的num的变化，同时页面输出也跟着变化。
- input与num绑定，input的value值变化，影响到了data中的num值- 页面`{<!-- -->{num}}`与数据num绑定，因此num值变化，引起了页面效果变化。
没有任何dom操作，这就是双向绑定的魅力。

### 3.3.4.事件处理

我们在页面添加一个按钮：

```
<button v-on:click="num++">点我</button>

```
- 这里用`v-on`指令绑定点击事件，而不是普通的`onclick`，然后直接操作num- 普通click是无法直接操作num的。
效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-Nj6bdJdr-1591000574735)(assets/53.gif)]

<img src="https://img-blog.csdnimg.cn/20200603203036564.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

# 4.Vue实例

## 4.1.创建Vue实例

每个 Vue 应用都是通过用 `Vue` 函数创建一个新的 **Vue 实例**开始的：

```
var vm = new Vue({<!-- -->
  // 选项
})

```

在构造函数中传入一个对象，并且在对象中声明各种Vue需要的数据和方法，包括：
- el- data- methods
等等

接下来我们一 一介绍。

## 4.2.模板或元素

每个Vue实例都需要关联一段Html模板，Vue会基于此模板进行视图渲染。

我们可以通过el属性来指定。

例如一段html模板：

```

    


```

然后创建Vue实例，关联这个div

```
var vm = new Vue({<!-- -->
	el:"#app"
})

```

这样，Vue就可以基于id为`app`的div元素作为模板进行渲染了。在这个div范围以外的部分是无法使用vue特性的。

## 4.3.数据

当Vue实例被创建时，它会尝试获取在data中定义的所有属性，用于视图的渲染，并且监视data中的属性变化，当data发生改变，所有相关的视图都将重新渲染，这就是“响应式“系统。

html：

```

    <input type="text" v-model="name"/>


```

js:

```
var vm = new Vue({<!-- -->
    el:"#app",
    data:{<!-- -->
        name:"刘德华"
    }
})

```
- name的变化会影响到`input`的值- input中输入的值，也会导致vm中的name发生改变
## 4.4.方法

Vue实例中除了可以定义data属性，也可以定义方法，并且在Vue实例的作用范围内使用。

html:

```

    {<!-- -->{num}}
    <button v-on:click="add">加</button>


```

js:

```
var vm = new Vue({<!-- -->
    el:"#app",
    data:{<!-- -->
        num: 0
    },
    methods:{<!-- -->
        add:function(){<!-- -->
            // this代表的当前vue实例
            this.num++;
        }
    }
})

```

## 4.5.生命周期钩子

### 4.5.1.生命周期

每个 Vue 实例在被创建时都要经过一系列的初始化过程 ：创建实例，装载模板，渲染模板等等。Vue为生命周期中的每个状态都设置了钩子函数（监听函数）。每当Vue实例处于不同的生命周期时，对应的函数就会被触发调用。

生命周期：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-Lu2watO2-1591000574737)(assets/lifecycle.png)] <img src="https://img-blog.csdnimg.cn/20200603203118557.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 4.5.2.钩子函数

beforeCreated：我们在用Vue时都要进行实例化，因此，该函数就是在Vue实例化是调用，也可以将他理解为初始化函数比较方便一点，在Vue1.0时，这个函数的名字就是init。

created：在创建实例之后进行调用。

beforeMount：页面加载完成，没有渲染。如：此时页面还是{<!-- -->{name}}

mounted：我们可以将他理解为原生js中的window.οnlοad=function({.,.}),或许大家也在用jquery，所以也可以理解为jquery中的$(document).ready(function(){….})，他的功能就是：在dom文档渲染完毕之后将要执行的函数，该函数在Vue1.0版本中名字为compiled。 此时页面中的{<!-- -->{name}}已被渲染成峰哥

beforeDestroy：该函数将在销毁实例前进行调用 。

destroyed：改函数将在销毁实例时进行调用。

beforeUpdate：组件更新之前。

updated：组件更新之后。

例如：created代表在vue实例创建后；

我们可以在Vue中定义一个created函数，代表这个时期的钩子函数：

```
    // 创建vue实例
    var app = new Vue({<!-- -->
        el: "#app", // el即element，该vue实例要渲染的页面元素
        data: {<!-- --> // 渲染页面需要的数据
            name: "峰哥",
            num: 5
        },
        methods: {<!-- -->
            add: function(){<!-- -->
                this.num--;
            }
        },
        created: function () {<!-- -->
            this.num = 100;
        }
    });

```

结果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-QBxq5QwC-1591000574739)(assets/1529835200236.png)]

<img src="https://img-blog.csdnimg.cn/20200603203205102.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 4.5.3.this

我们可以看下在vue内部的this变量是谁，我们在created的时候，打印this

```
        methods: {<!-- -->
            add: function(){<!-- -->
                this.num--;
                console.log(this);
            }
        },

```

控制台的输出：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-3g8DZqtp-1591000574740)(assets/1529835379275.png)]

<img src="https://img-blog.csdnimg.cn/20200603203300220.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

# 5.指令

什么是指令？

指令 (Directives) 是带有 `v-` 前缀的特殊特性。指令特性的预期值是：**单个 JavaScript 表达式**。指令的职责是，当表达式的值改变时，将其产生的连带影响，响应式地作用于 DOM。

例如我们在入门案例中的v-on，代表绑定事件。

## 5.1.插值表达式

### 5.1.1.花括号

格式：

```
{<!-- -->{表达式}}

```

说明：
- 该表达式支持JS语法，可以调用js内置函数（必须有返回值）- 表达式必须有返回结果。例如 1 + 1，没有结果的表达式不允许使用，如：var a = 1 + 1;- 可以直接获取Vue实例中定义的数据或函数
示例：

HTML：

```
{<!-- -->{name}}

```

JS:

```
var app = new Vue({<!-- -->
    el:"#app",
    data:{<!-- -->
        name:"Jack"
    }
})

```

### 5.1.2.插值闪烁

使用{<!-- -->{}}方式在网速较慢时会出现问题。在数据未加载完成时，页面会显示出原始的`{<!-- -->{}}`，加载完毕后才显示正确数据，我们称为插值闪烁。

我们将网速调慢一些，然后试试看刚才的案例：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-OIuKnezI-1591000574743)(assets/1529836021593.png)] <img src="https://img-blog.csdnimg.cn/20200603203328558.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 刷新页面：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-60TTsKbF-1591000574743)(assets/54.gif)]

<img src="https://img-blog.csdnimg.cn/20200603203354855.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.1.3.v-text和v-html

使用v-text和v-html指令来替代`{<!-- -->{}}`

说明：
- v-text：将数据输出到元素内部，如果输出的数据有HTML代码，会作为普通文本输出- v-html：将数据输出到元素内部，如果输出的数据有HTML代码，会被渲染
示例：

HTML:

```

    v-text: v-text="hello"></span> <br/>
    v-html: v-html="hello"></span>


```

JS:

```
var vm = new Vue({<!-- -->
    el:"#app",
    data:{<!-- -->
        hello: "<h1>大家好，我是峰哥</h1>"
    }
})

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-cPNLQUom-1591000574745)(assets/1529836688083.png)] <img src="https://img-blog.csdnimg.cn/20200603203431541.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 并且不会出现插值闪烁，当没有数据时，会显示空白。

## 5.2.v-model

刚才的v-text和v-html可以看做是单向绑定，数据影响了视图渲染，但是反过来就不行。接下来学习的v-model是双向绑定，视图（View）和模型（Model）之间会互相影响。

既然是双向绑定，一定是在视图中可以修改数据，这样就限定了视图的元素类型。目前v-model的可使用元素有：
- input- select- textarea- checkbox- radio- components（Vue中的自定义组件）
基本上除了最后一项，其它都是表单的输入项。

举例：

html：

```

    <input type="checkbox" v-model="language" value="Java" />Java<br/>
    <input type="checkbox" v-model="language" value="PHP" />PHP<br/>
    <input type="checkbox" v-model="language" value="Swift" />Swift<br/>
    <h1>
        你选择了：{<!-- -->{language.join(',')}}
    </h1>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var vm = new Vue({<!-- -->
        el:"#app",
        data:{<!-- -->
            language: []
        }
    })
</script>

```
- 多个`CheckBox`对应一个model时，model的类型是一个数组，单个checkbox值默认是boolean类型- radio对应的值是input的value值- `input` 和`textarea` 默认对应的model是字符串- `select`单选对应字符串，多选对应也是数组
效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-5ny5q6OV-1591000574746)(assets/1529837541201.png)]

<img src="https://img-blog.csdnimg.cn/20200603203502999.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 5.3.v-on

### 5.3.1.基本用法

v-on指令用于给页面元素绑定事件。

语法：

```
v-on:事件名="js片段或函数名"

```

示例：

```

    <!--事件中直接写js片段-->
    <button v-on:click="num++">增加一个</button><br/>
    <!--事件指定一个回调函数，必须是Vue实例中定义的函数-->
    <button v-on:click="decrement">减少一个</button><br/>
    <h1>有{<!-- -->{num}}个女神迷恋峰哥</h1>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el:"#app",
        data:{<!-- -->
            num:100
        },
        methods:{<!-- -->
            decrement(){<!-- -->
                this.num--;
            }
        }
    })
</script>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-O1CZBu1c-1591000574748)(assets/55.gif)] <img src="https://img-blog.csdnimg.cn/20200603203805556.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 另外，事件绑定可以简写，例如`v-on:click='add'`可以简写为`@click='add'`

### 5.3.2.事件修饰符

在事件处理程序中调用 `event.preventDefault()` 或 `event.stopPropagation()` 是非常常见的需求。尽管我们可以在方法中轻松实现这点，但更好的方式是：方法只有纯粹的数据逻辑，而不是去处理 DOM 事件细节。

为了解决这个问题，Vue.js 为 `v-on` 提供了**事件修饰符**。修饰符是由点开头的指令后缀来表示的。
- `.stop` ：阻止事件冒泡到父元素- `.prevent`：阻止默认事件发生- `.capture`：使用事件捕获模式- `.self`：只有元素自身触发事件才执行。（冒泡或捕获的都不执行）- `.once`：只执行一次
阻止默认事件

```

    <!--右击事件，并阻止默认事件发生-->
    <button v-on:contextmenu.prevent="num++">增加一个</button>
    <br/>
    <!--右击事件，不阻止默认事件发生-->
    <button v-on:contextmenu="decrement($event)">减少一个</button>
    <br/>
    <h1>有{<!-- -->{num}}个女神迷恋峰哥</h1>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el: "#app",
        data: {<!-- -->
            num: 100
        },
        methods: {<!-- -->
            decrement(ev) {<!-- -->
                // ev.preventDefault();
                this.num--;
            }
        }
    })
</script>

```

效果：（右键“增加一个”，不会触发默认的浏览器右击事件；右键“减少一个”，会触发默认的浏览器右击事件）

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-GhtzmRUS-1591000574749)(assets/56.gif)]

<img src="https://img-blog.csdnimg.cn/20200603203832658.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.3.3.按键修饰符

在监听键盘事件时，我们经常需要检查常见的键值。Vue 允许为 `v-on` 在监听键盘事件时添加按键修饰符：

```
<!-- 只有在 `keyCode` 是 13 时调用 `vm.submit()` -->
<input v-on:keyup.13="submit">

```

记住所有的 `keyCode` 比较困难，所以 Vue 为最常用的按键提供了别名：

```
<!-- 同上 -->
<input v-on:keyup.enter="submit">

<!-- 缩写语法 -->
<input @keyup.enter="submit">

```

全部的按键别名：
- `.enter`- `.tab`- `.delete` (捕获“删除”和“退格”键)- `.esc`- `.space`- `.up`- `.down`- `.left`- `.right`
### 5.3.4.组合按钮

可以用如下修饰符来实现仅在按下相应按键时才触发鼠标或键盘事件的监听器。
- `.ctrl`- `.alt`- `.shift`
例如：

```
<!-- Alt + C -->
<input @keyup.alt.67="clear">

<!-- Ctrl + Click -->
Do something

```

## 5.4.v-for

遍历数据渲染页面是非常常用的需求，Vue中通过v-for指令来实现。

### 5.4.1.遍历数组

>  
 语法： 


```
v-for="item in items"

```
- items：要遍历的数组，需要在vue的data中定义好。- item：迭代得到的数组元素的别名
>  
 示例 


```

    <ul>
        <li v-for="user in users">
            {<!-- -->{user.name}} - {<!-- -->{user.gender}} - {<!-- -->{user.age}}
        </li>
    </ul>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el: "#app",
        data: {<!-- -->
            users:[
                {<!-- -->name:'柳岩', gender:'女', age: 21},
                {<!-- -->name:'峰哥', gender:'男', age: 18},
                {<!-- -->name:'范冰冰', gender:'女', age: 24},
                {<!-- -->name:'刘亦菲', gender:'女', age: 18},
                {<!-- -->name:'古力娜扎', gender:'女', age: 25}
            ]
        },
    })
</script>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-peecbkd3-1591000574751)(assets/1530006198953.png)]

<img src="https://img-blog.csdnimg.cn/20200603203904921.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.4.2.数组角标

在遍历的过程中，如果我们需要知道数组角标，可以指定第二个参数：

>  
 语法 


```
v-for="(item,index) in items"

```
- items：要迭代的数组- item：迭代得到的数组元素别名- index：迭代到的当前元素索引，从0开始。
>  
 示例 


```
    <ul>
        <li v-for="(user, index) in users">
            {<!-- -->{index + 1}}. {<!-- -->{user.name}} - {<!-- -->{user.gender}} - {<!-- -->{user.age}}
        </li>
    </ul>

```

>  
 效果： 


[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-rrB1zjQG-1591000574752)(assets/1530006094601.png)]

<img src="https://img-blog.csdnimg.cn/2020060320393036.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.4.3.遍历对象

v-for除了可以迭代数组，也可以迭代对象。语法基本类似

>  
 语法： 


```
v-for="value in object"
v-for="(value,key) in object"
v-for="(value,key,index) in object"

```
- 1个参数时，得到的是对象的属性- 2个参数时，第一个是属性，第二个是键- 3个参数时，第三个是索引，从0开始
>  
 示例： 


```

    <ul>
        <li v-for="(value, key, index) in user">
            {<!-- -->{index + 1}}. {<!-- -->{key}} - {<!-- -->{value}}
        </li>
    </ul>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var vm = new Vue({<!-- -->
        el:"#app",
        data:{<!-- -->
            user:{<!-- -->name:'峰哥', gender:'男', age: 18}
        }
    })
</script>

```

>  
 效果： 


[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-cLpgjxsO-1591000574753)(assets/1530006251975.png)]

<img src="https://img-blog.csdnimg.cn/20200603203959506.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.4.4.key

当 Vue.js 用 `v-for` 正在更新已渲染过的元素列表时，它默认用“就地复用”策略。如果数据项的顺序被改变，Vue 将不会移动 DOM 元素来匹配数据项的顺序， 而是简单复用此处每个元素，并且确保它在特定索引下显示已被渲染过的每个元素。

这个功能可以有效的提高渲染的效率。

但是要实现这个功能，你需要给Vue一些提示，以便它能跟踪每个节点的身份，从而重用和重新排序现有元素，你需要为每项提供一个唯一 `key` 属性。理想的 `key` 值是每项都有的且唯一的 id。

示例：

```
<ul>
    <li v-for="(item,index) in items" :key=index></li>
</ul>

```
- 这里使用了一个特殊语法：`:key=""` 我们后面会讲到，它可以让你读取vue中的属性，并赋值给key属性- 这里我们绑定的key是数组的索引，应该是唯一的
## 5.5.v-if和v-show

### 5.5.1.基本使用

v-if，顾名思义，条件判断。当得到结果为true时，所在的元素才会被渲染。

>  
 语法： 


```
v-if="布尔表达式"

```

>  
 示例： 


```

    <button v-on:click="show = !show">点我呀</button>
    <br>
    <h1 v-if="show">
        看到我啦？！
    </h1>
    <h1 v-show="show">
        看到我啦？！show
    </h1>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el: "#app",
        data: {<!-- -->
            show: true
        }
    })
</script>

```

>  
 效果： 


[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-Tjpb7cpw-1591000574757)(assets/57.gif)] <img src="https://img-blog.csdnimg.cn/2020060320423658.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.5.2.与v-for结合

当v-if和v-for出现在一起时，v-for优先级更高。也就是说，会先遍历，再判断条件。

修改v-for中的案例，添加v-if：

```
    <ul>
        <li v-for="(user, index) in users" v-if="user.gender == '女'">
            {<!-- -->{index + 1}}. {<!-- -->{user.name}} - {<!-- -->{user.gender}} - {<!-- -->{user.age}}
        </li>
    </ul>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-i2u1GpsD-1591000574759)(assets/1530013415911.png)] <img src="https://img-blog.csdnimg.cn/20200603204625322.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 只显示女性用户信息

### 5.5.3.v-else

你可以使用 `v-else` 指令来表示 `v-if` 的“else 块”：

```

    <h1 v-if="Math.random() > 0.5">
        看到我啦？！if
    </h1>
    <h1 v-else>
        看到我啦？！else
    </h1>


```

`v-else` 元素必须紧跟在带 `v-if` 或者 `v-else-if` 的元素的后面，否则它将不会被识别。

`v-else-if`，顾名思义，充当 `v-if` 的“else-if 块”，可以连续使用：

```

    <button v-on:click="random=Math.random()">点我呀</button>>{<!-- -->{random}}</span>
    <h1 v-if="random >= 0.75">
        看到我啦？！if
    </h1>
    <h1 v-else-if="random > 0.5">
        看到我啦？！if 0.5
    </h1>
    <h1 v-else-if="random > 0.25">
        看到我啦？！if 0.25
    </h1>
    <h1 v-else>
        看到我啦？！else
    </h1>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el: "#app",
        data: {<!-- -->
            random: 1
        }
    })
</script>

```

类似于 `v-else`，`v-else-if` 也必须紧跟在带 `v-if` 或者 `v-else-if` 的元素之后。

演示：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-8ePFcTd8-1591000574760)(assets/58.gif)]

<img src="https://img-blog.csdnimg.cn/20200603204727781.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 5.5.4.v-show

另一个用于根据条件展示元素的选项是 `v-show` 指令。用法大致一样：

```
<h1 v-show="ok">Hello!</h1>

```

不同的是带有 `v-show` 的元素始终会被渲染并保留在 DOM 中。`v-show` 只是简单地切换元素的 CSS 属性 `display`。

示例：

```
    
        <!--事件中直接写js片段-->
        <button v-on:click="show = !show">点击切换</button><br/>
        <h1 v-if="show">
            你好
        </h1>
    
    <script src="./node_modules/vue/dist/vue.js"></script>
    <script type="text/javascript">
        var app = new Vue({<!-- -->
            el:"#app",
            data:{<!-- -->
                show:true
            }
        })
    </script>

```

代码：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-AXlB6kji-1591000574763)(assets/59.gif)]

<img src="https://img-blog.csdnimg.cn/20200603213900457.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 5.6.v-bind

html属性不能使用双大括号形式绑定，只能使用v-bind指令。

在将 `v-bind` 用于 `class` 和 `style` 时，Vue.js 做了专门的增强。表达式结果的类型除了字符串之外，还可以是对象或数组。

```

    <!--可以是数据模型，可以是具有返回值的js代码块或者函数-->
    

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el: "#app",
        data: {<!-- -->
            title: "title",
        }
    })
</script>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-ZHa49Op2-1591000574764)(assets/1530025378843.png)] <img src="https://img-blog.csdnimg.cn/20200603213927689.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 在将 `v-bind` 用于 `class` 和 `style` 时，Vue.js 做了专门的增强。表达式结果的类型除了字符串之外，还可以是对象或数组。

### 5.6.1.绑定class样式

>  
 数组语法 


我们可以借助于`v-bind`指令来实现：

HTML：

```

    
    
    

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var app = new Vue({<!-- -->
        el: "#app",
        data: {<!-- -->
            activeClass: 'active',
            errorClass: ['text-danger', 'text-error']
        }
    })
</script>

```

渲染后的效果：（具有active和hasError的样式）

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-JdZqvy7Q-1591000574765)(assets/1530026818515.png)] <img src="https://img-blog.csdnimg.cn/20200603214052787.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

>  
 对象语法 


我们可以传给 `v-bind:class` 一个对象，以动态地切换 class：

```


```

上面的语法表示 `active` 这个 **class 存在与否将取决于数据属性 `isActive`** 的 truthiness</a>（所有的值都是真实的，除了false,0,“”,null,undefined和NaN）。

你可以在对象中传入更多属性来动态切换多个 class。此外，`v-bind:class` 指令也可以与普通的 class 属性共存。如下模板:

```
<div class="static"
     v-bind:class="{ active: isActive, 'text-danger': hasError }">


```

和如下 data：

```
data: {<!-- -->
  isActive: true,
  hasError: false
}

```

结果渲染为：

```


```

active样式和text-danger样式的存在与否，取决于isActive和hasError的值。本例中isActive为true，hasError为false，所以active样式存在，text-danger不存在。

**通常情况下，绑定的数据对象不必内联定义在模板里**：

```


```

数据：

```
data: {<!-- -->
  classObject: {<!-- -->
    active: true,
    'text-danger': false
  }
}

```

效果和之前一样：

```


```

### 5.6.2.绑定style样式

>  
 数组语法 


数组语法可以将多个样式对象应用到同一个元素上：

```


```

数据：

```
data: {<!-- -->
    baseStyles: {<!-- -->'background-color': 'red'},
    overridingStyles: {<!-- -->border: '1px solid black'}
}

```

渲染后的结果：

```


```

>  
 对象语法 


`v-bind:style` 的对象语法十分直观——看着非常像 CSS，但其实是一个 JavaScript 对象。CSS 属性名可以用驼峰式 (camelCase) 或短横线分隔 (kebab-case，记得用单引号括起来) 来命名：

```
 color: activeColor, fontSize: fontSize + 'px' }">

```

数据：

```
data: {
  activeColor: 'red',
  fontSize: 30
}

```

效果：

```


```

**直接绑定到一个样式对象通常更好，这会让模板更清晰**：

```


```

```
data: {<!-- -->
  styleObject: {<!-- -->
    color: 'red',
    fontSize: '13px'
  }
}

```

效果同上。

### 5.6.3.简写

`v-bind:class`可以简写为`:class`

## 5.7.计算属性

在插值表达式中使用js表达式是非常方便的，而且也经常被用到。

但是如果表达式的内容很长，就会显得不够优雅，而且后期维护起来也不方便，例如下面的场景，我们有一个日期的数据，但是是毫秒值：

```
data:{<!-- -->
    birthday:1529032123201 // 毫秒值
}

```

我们在页面渲染，希望得到yyyy-MM-dd的样式：

```
<h1>您的生日是：{<!-- -->{
    new Date(birthday).getFullYear() + '-'+ new Date(birthday).getMonth()+ '-' + new Date(birthday).getDay()
    }}
</h1>

```

虽然能得到结果，但是非常麻烦。

Vue中提供了计算属性，来替代复杂的表达式：

```
var vm = new Vue({<!-- -->
    el:"#app",
    data:{<!-- -->
        birthday:1429032123201 // 毫秒值
    },
    computed:{<!-- -->
        birth(){<!-- -->// 计算属性本质是一个方法，但是必须返回结果
            const d = new Date(this.birthday);
            return d.getFullYear() + "-" + d.getMonth() + "-" + d.getDay();
        }
    }
})

```
- 计算属性本质就是方法，但是一定要返回数据。然后页面渲染时，可以把这个方法当成一个变量来使用。
页面使用：

```
    
       <h1>您的生日是：{<!-- -->{birth}} </h1>
    

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-3z4HjQPA-1591000574770)(assets/1530029950644.png)]

<img src="https://img-blog.csdnimg.cn/20200603214203548.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

我们可以将同一函数定义为一个方法而不是一个计算属性。两种方式的最终结果确实是完全相同的。然而，不同的是**计算属性是基于它们的依赖进行缓存的**。计算属性只有在它的相关依赖发生改变时才会重新求值。这就意味着只要`birthday`还没有发生改变，多次访问 `birthday` 计算属性会立即返回之前的计算结果，而不必再次执行函数。

## 5.8.watch

watch可以让我们监控一个值的变化。从而做出相应的反应。

示例：

```

    <input type="text" v-model="message">

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var vm = new Vue({<!-- -->
        el:"#app",
        data:{<!-- -->
            message:""
        },
        watch:{<!-- -->
            message(newVal, oldVal){<!-- -->
                console.log(newVal, oldVal);
            }
        }
    })
</script>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-LA8krVVi-1591000574771)(assets/1530030506879.png)]

<img src="https://img-blog.csdnimg.cn/20200603214239800.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

# 6.组件化

在大型应用开发的时候，页面可以划分成很多部分。往往不同的页面，也会有相同的部分。例如可能会有相同的头部导航。

但是如果每个页面都独自开发，这无疑增加了我们开发的成本。所以我们会把页面的不同部分拆分成独立的组件，然后在不同页面就可以共享这些组件，避免重复开发。

## 6.1.全局组件

我们通过Vue的component方法来定义一个全局组件。

```

    <!--使用定义好的全局组件-->
    <counter></counter>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    // 定义全局组件，两个参数：1，组件名称。2，组件参数
    Vue.component("counter",{<!-- -->
        template:'<button v-on:click="count++">你点了我 {<!-- -->{ count }} 次，我记住了.</button>',
        data(){<!-- -->
            return {<!-- -->
                count:0
            }
        }
    })
    var app = new Vue({<!-- -->
        el:"#app"
    })
</script>

```
- 组件其实也是一个Vue实例，因此它在定义时也会接收：data、methods、生命周期函数等- 不同的是组件不会与页面的元素绑定，否则就无法复用了，因此没有el属性。- 但是组件渲染需要html模板，所以增加了template属性，值就是HTML模板- 全局组件定义完毕，任何vue实例都可以直接在HTML中通过组件名称来使用组件了。- data必须是一个函数，不再是一个对象。
效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-BnnL41Ix-1591000574771)(assets/60.gif)]

<img src="https://img-blog.csdnimg.cn/20200603214306934.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 6.2.组件的复用

定义好的组件，可以任意复用多次：

```

    <!--使用定义好的全局组件-->
    <counter></counter>
    <counter></counter>
    <counter></counter>


```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-soBzXjkh-1591000574773)(assets/1530084943778.png)] <img src="https://img-blog.csdnimg.cn/20200603214328873.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 你会发现每个组件互不干扰，都有自己的count值。怎么实现的？

>  
 **组件的data属性必须是函数**！ 


当我们定义这个 `<counter>` 组件时，它的data 并不是像这样直接提供一个对象：

```
data: {<!-- -->
  count: 0
}


```

取而代之的是，一个组件的 data 选项必须是一个函数，因此每个实例可以维护一份被返回对象的独立的拷贝：

```
data: function () {<!-- -->
  return {<!-- -->
    count: 0
  }
}

```

如果 Vue 没有这条规则，点击一个按钮就会影响到其它所有实例！

## 6.3.局部注册

一旦全局注册，就意味着即便以后你不再使用这个组件，它依然会随着Vue的加载而加载。

因此，对于一些并不频繁使用的组件，我们会采用局部注册。

我们先在外部定义一个对象，结构与创建组件时传递的第二个参数一致：

```
const counter = {<!-- -->
    template:'<button v-on:click="count++">你点了我 {<!-- -->{ count }} 次，我记住了.</button>',
    data(){<!-- -->
        return {<!-- -->
            count:0
        }
    }
};

```

然后在Vue中使用它：

```
var app = new Vue({<!-- -->
    el:"#app",
    components:{<!-- -->
        counter:counter // 将定义的对象注册为组件
    }
})

```
<li>components就是当前vue对象子组件集合。 
  <ul>- 其key就是子组件名称- 其值就是组件对象的属性
## 6.4.组件通信

通常一个单页应用会以一棵嵌套的组件树的形式来组织：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-hyoBHoFc-1591000574775)(assets/1525855149491.png)] <img src="https://img-blog.csdnimg.cn/20200603214400483.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>
- 页面首先分成了顶部导航、左侧内容区、右侧边栏三部分- 左侧内容区又分为上下两个组件- 右侧边栏中又包含了3个子组件
各个组件之间以嵌套的关系组合在一起，那么这个时候不可避免的会有组件间通信的需求。

### 6.4.1.props（父向子传递）
1. 父组件使用子组件时，自定义属性（属性名任意，属性值为要传递的数据）1. 子组件通过props接收父组件属性
父组件使用子组件，并自定义了title属性：

```

    <h1>打个招呼：</h1>
    <!--使用子组件，同时传递title属性-->
    <introduce title="大家好，我是锋哥"/>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    Vue.component("introduce",{<!-- -->
        // 直接使用props接收到的属性来渲染页面
        template:'<h1>{<!-- -->{title}}</h1>',
        props:['title'] // 通过props来接收一个父组件传递的属性
    })
    var app = new Vue({<!-- -->
        el:"#app"
    })
</script>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-y1ytxCUy-1591000574777)(assets/1530093525973.png)]

<img src="https://img-blog.csdnimg.cn/20200603214441195.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 6.4.2.props验证

我们定义一个子组件，并接受复杂数据：

```
    const myList = {<!-- -->
        template: '\
        <ul>\
        	- {<!-- -->{item.id}} : {<!-- -->{item.name}}\
        </ul>\
        ',
        props: {<!-- -->
            items: {<!-- -->
                type: Array,
                default: [],
                required: true
            }
        }
    };

```
- 这个子组件可以对 items 进行迭代，并输出到页面。<li>props：定义需要从父组件中接收的属性 
  <ul><li>items：是要接收的属性名称 
    <ul>- type：限定父组件传递来的必须是数组- default：默认值- required：是否必须
**当 prop 验证失败的时候，(开发环境构建版本的) Vue 将会产生一个控制台的警告。**

我们在父组件中使用它：

```

    <h2>传智播客已开设如下课程：</h2>
    <!-- 使用子组件的同时，传递属性，这里使用了v-bind，指向了父组件自己的属性lessons -->
    <my-list :items="lessons"/>


```

```
var app = new Vue({<!-- -->
    el:"#app",
    components:{<!-- -->
        myList // 当key和value一样时，可以只写一个
    },
    data:{<!-- -->
        lessons:[
            {<!-- -->id:1, name: 'java'},
            {<!-- -->id:2, name: 'php'},
            {<!-- -->id:3, name: 'ios'},
        ]
    }
})

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-pTnRLzTo-1591000574777)(assets/1530107338625.png)]

<img src="https://img-blog.csdnimg.cn/20200603214516468.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

type类型，可以有：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-xoz69FQx-1591000574778)(assets/1530108427358.png)]

<img src="https://img-blog.csdnimg.cn/20200603214542569.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 6.4.3.动态静态传递

给 prop 传入一个静态的值：

```
<introduce title="大家好，我是锋哥"/>

```

给 prop 传入一个动态的值： （通过v-bind从数据模型中，获取title的值）

```
<introduce :title="title"/>

```

静态传递时，我们传入的值都是字符串类型的，但实际上**任何类型**的值都可以传给一个 props。

```
<!-- 即便 `42` 是静态的，我们仍然需要 `v-bind` 来告诉 Vue -->
<!-- 这是一个JavaScript表达式而不是一个字符串。-->
<blog-post v-bind:likes="42"></blog-post>

<!-- 用一个变量进行动态赋值。-->
<blog-post v-bind:likes="post.likes"></blog-post>

```

### 6.4.4.子向父的通信

来看这样的一个案例：

```

    <h2>num: {<!-- -->{num}}</h2>
    <!--使用子组件的时候，传递num到子组件中-->
    <counter :num="num"></counter>

<script src="./node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    Vue.component("counter", {<!-- -->// 子组件，定义了两个按钮，点击数字num会加或减
        template:'\
            \
                <button @click="num++">加</button>  \
                <button @click="num--">减</button>  \
            ',
        props:['num']// count是从父组件获取的。
    })
    var app = new Vue({<!-- -->
        el:"#app",
        data:{<!-- -->
            num:0
        }
    })
</script>

```
- 子组件接收父组件的num属性- 子组件定义点击按钮，点击后对num进行加或减操作
我们尝试运行，好像没问题，点击按钮试试：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-J7OM3iaj-1591000574779)(assets/1530115066496.png)] <img src="https://img-blog.csdnimg.cn/20200603214608165.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 子组件接收到父组件属性后，默认是不允许修改的。怎么办？

既然只有父组件能修改，那么加和减的操作一定是放在父组件：

```
var app = new Vue({<!-- -->
    el:"#app",
    data:{<!-- -->
        num:0
    },
    methods:{<!-- --> // 父组件中定义操作num的方法
        increment(){<!-- -->
            this.num++;
        },
        decrement(){<!-- -->
            this.num--;
        }
    }
})

```

但是，点击按钮是在子组件中，那就是说需要子组件来调用父组件的函数，怎么做？

我们可以**通过v-on指令将父组件的函数绑定到子组件**上：

```

    <h2>num: {<!-- -->{num}}</h2>
    <counter :count="num" @inc="increment" @dec="decrement"></counter>


```

在子组件中定义函数，函数的具体实现调用父组件的实现，并在子组件中调用这些函数。当子组件中按钮被点击时，调用绑定的函数：

```
        Vue.component("counter", {<!-- -->
            template:'\
                \
                    <button @click="plus">加</button>  \
                    <button @click="reduce">减</button>  \
                ',
            props:['count'],
            methods:{<!-- -->
                plus(){<!-- -->
                    this.$emit("inc");
                },
                reduce(){<!-- -->
                    this.$emit("dec");
                }
            }
        })

```
- vue提供了一个内置的this.$emit()函数，用来调用父组件绑定的函数
效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-s33oY5Gu-1591000574781)(assets/61.gif)]

<img src="https://img-blog.csdnimg.cn/2020060321463663.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

# 7.路由vue-router

## 7.1.场景模拟

现在我们来实现这样一个功能：

一个页面，包含登录和注册，点击不同按钮，实现登录和注册页切换：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-DCvEKhaE-1591000574782)(H:/%E4%B9%90%E4%BC%98/day05-Vue/assets/8.gif)]

### 7.1.1.编写父组件

为了让接下来的功能比较清晰，我们先新建一个文件夹：src

然后新建一个HTML文件，作为入口：index.html

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-P7Z3AJVO-1591000574783)(assets/1530148321175.png)] <img src="https://img-blog.csdnimg.cn/20200603214716176.png" alt="在这里插入图片描述"/> 然后编写页面的基本结构：

```

    >登录</span>
    >注册</span>
    <hr/>
    
        登录页/注册页
    

<script src="../node_modules/vue/dist/vue.js"></script>
<script type="text/javascript">
    var vm = new Vue({<!-- -->
        el:"#app"
    })
</script>

```

样式：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-CMCnRPCD-1591000574784)(assets/1530149363817.png)]

<img src="https://img-blog.csdnimg.cn/20200603214744811.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 7.1.2.编写登录及注册组件

接下来我们来实现登录组件，以前我们都是写在一个文件中，但是为了复用性，开发中都会把组件放入独立的JS文件中，我们新建一个user目录以及login.js及register.js：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-sSP04mJh-1591000574786)(assets/1530156389366.png)] <img src="https://img-blog.csdnimg.cn/20200603214809296.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 编写组件，这里我们只写模板，不写功能。

login.js内容如下：

```
const loginForm = {<!-- -->
    template:'\
    \
    <h2>登录页</h2> \
    用户名：<input type="text"><br/>\
    密码：<input type="password"><br/>\
    \
    '
}

```

register.js内容：

```
const registerForm = {<!-- -->
    template:'\
    \
    <h2>注册页</h2> \
    用&amp;ensp;户&amp;ensp;名：<input type="text"><br/>\
    密&amp;emsp;&amp;emsp;码：<input type="password"><br/>\
    确认密码：<input type="password"><br/>\
    \
    '
}

```

### 7.1.3.在父组件中引用

```

    >登录</span>
    >注册</span>
    <hr/>
    
        <!--<loginForm></loginForm>-->
        <!--
            疑问：为什么不采用上面的写法？
            由于html是大小写不敏感的，如果采用上面的写法，则被认为是<loginform></loginform>
            所以，如果是驼峰形式的组件，需要把驼峰转化为“-”的形式
         -->
        <login-form></login-form>
        <register-form></register-form>
    

<script src="../node_modules/vue/dist/vue.js"></script>
<script src="user/login.js"></script>
<script src="user/register.js"></script>
<script type="text/javascript">
    var vm = new Vue({<!-- -->
        el: "#app",
        components: {<!-- -->
            loginForm,
            registerForm
        }
    })
</script>

```

效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-NNYrAgl3-1591000574788)(assets/1530157389501.png)]

<img src="https://img-blog.csdnimg.cn/20200603214840589.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 7.1.5.问题

我们期待的是，当点击登录或注册按钮，分别显示登录页或注册页，而不是一起显示。

但是，如何才能动态加载组件，实现组件切换呢？

虽然使用原生的Html5和JS也能实现，但是官方推荐我们使用vue-router模块。

## 7.2.vue-router简介和安装

使用vue-router和vue可以非常方便的实现 复杂单页应用的动态路由功能。

官网：https://router.vuejs.org/zh-cn/

使用npm安装：`npm install vue-router --save`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-RSDOX6Ge-1591000574790)(assets/1530161293338.png)] <img src="https://img-blog.csdnimg.cn/20200603214910723.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 在index.html中引入依赖：

```
<script src="../node_modules/vue-router/dist/vue-router.js"></script>

```

## 7.3.快速入门

新建vue-router对象，并且指定路由规则：

```
// 创建VueRouter对象
const router = new VueRouter({<!-- -->
    routes:[ // 编写路由规则
        {<!-- -->
            path:"/login", // 请求路径
            component:loginForm // 组件名称
        },
        {<!-- -->path:"/register",component:registerForm},
    ]
})

```
- 创建VueRouter对象，并指定路由参数<li>routes：路由规则的数组，可以指定多个对象，每个对象是一条路由规则，包含以下属性： 
  <ul>- path：路由的路径- component：组件名称
在父组件中引入router对象：

```
var vm = new Vue({<!-- -->
    el:"#app",
    components:{<!-- -->// 引用登录和注册组件
        loginForm,
        registerForm
    },
    router // 引用上面定义的router对象
})

```

页面跳转控制：

```

    <!--router-link来指定跳转的路径-->
    ><router-link to="/login">登录</router-link></span>
    ><router-link to="/register">注册</router-link></span>
    <hr/>
    
        <!--vue-router的锚点-->
        <router-view></router-view>
    


```
- 通过`<router-view>`来指定一个锚点，当路由的路径匹配时，vue-router会自动把对应组件放到锚点位置进行渲染- 通过`<router-link>`指定一个跳转链接，当点击时，会触发vue-router的路由功能，路径中的hash值会随之改变
效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-UUKBY91e-1591000574792)(assets/62.gif)] <img src="https://img-blog.csdnimg.cn/2020060321493856.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> **注意**：单页应用中，页面的切换并不是页面的跳转。仅仅是地址最后的hash值变化。

事实上，我们总共就一个HTML：index.html

# 8.webpack

Webpack 是一个前端资源的打包工具，它可以将js、image、css等资源当成一个模块进行打包。

中文官方网站：https://www.webpackjs.com/

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-iZch8jry-1591000574793)(assets/1530168661348.png)] <img src="https://img-blog.csdnimg.cn/20200603215003120.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 官网给出的解释：

>  
 本质上，**webpack** 是一个现代 JavaScript 应用程序的**静态模块打包器(module bundler)**。当 webpack 处理应用程序时，它会递归地构建一个**依赖关系图(dependency graph)**，其中包含应用程序需要的每个模块，然后将所有这些模块打包成一个或多个 **bundle**。 


为什么需要打包？
- 将许多碎小文件打包成一个整体，减少单页面内的衍生请求次数，提高网站效率。- 将ES6的高级语法进行转换编译，以兼容老版本的浏览器。- 将代码打包的同时进行混淆，提高代码的安全性。
## 8.1.安装

webpack支持全局安装和本地安装，官方推荐是本地安装，我们按照官方的来。

安装最新版本webpack，输入命令：`npm install --save-dev webpack`

webpack 4+ 版本，你还需要安装 CLI ，输入命令：`npm install webpack webpack-cli --save-dev`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-FIP2IMms-1591000574795)(assets/1530187524815.png)] <img src="https://img-blog.csdnimg.cn/20200603215037583.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 此时，我们注意下项目中文件夹下，会有一个package.json文件。（其实早就有了）

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-6lLEMU7I-1591000574796)(assets/1530187744149.png)] <img src="https://img-blog.csdnimg.cn/20200603215059443.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 打开文件，可以看到我们之前用npm安装过的文件都会出现在这里：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-RchKnvIU-1591000574799)(assets/1525873343908.png)]

<img src="https://img-blog.csdnimg.cn/20200603215125471.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 8.2.核心概念

学习Webpack，你需要先理解四个**核心概念**：
-  入口(entry) webpack打包的起点，可以有一个或多个，一般是js文件。webpack会从启点文件开始，寻找启点直接或间接依赖的其它所有的依赖，包括JS、CSS、图片资源等，作为将来打包的原始数据 -  输出(output) 出口一般包含两个属性：path和filename。用来告诉webpack打包的目标文件夹，以及文件的名称。目的地也可以有多个。 -  加载器（loader） webpack本身只识别Js文件，如果要加载非JS文件，必须指定一些额外的加载器（loader），例如css-loader。然后将这些文件转为webpack能处理的有效模块，最后利用webpack的打包能力去处理。 -  插件(plugins) 插件可以扩展webpack的功能，让webpack不仅仅是完成打包，甚至各种更复杂的功能，或者是对打包功能进行优化、压缩，提高效率。 
## 8.3.编写webpack配置

接下来，我们编写一个webpack的配置，来指定一些打包的配置项。配置文件的名称，默认就是webpack.config.js，我们放到hello-vue的根目录：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-gU4JYncJ-1591000574801)(assets/1530199761226.png)] <img src="https://img-blog.csdnimg.cn/20200603215155814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 配置文件中就是要指定上面说的四个核心概念，入口、出口、加载器、插件。

不过，加载器和插件是可选的。我们先编写入口和出口

### 8.3.1.入口entry

webpack打包的启点，可以有一个或多个，一般是js文件。现在思考一下我们有没有一个入口？貌似没有，我们所有的东西都集中在index.html，不是一个js，那怎么办？

我们新建一个js，把index.html中的部分内容进行集中，然后在index.html中引用这个js不就OK了！

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-5CRSQC3A-1591000574802)(assets/1530200787599.png)] <img src="https://img-blog.csdnimg.cn/20200603215219350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 然后把原来index.html中的js代码全部移动到index.js中

```
// 使用es6的语法导入js模块
import Vue from '../node_modules/vue/dist/vue';
import VueRouter from '../node_modules/vue-router/dist/vue-router';
import loginForm from './user/login';
import registerForm from './user/register';

Vue.use(VueRouter);

// 创建vue对象
const router = new VueRouter({<!-- -->
    routes: [ // 编写路由规则
        // path: 路由请求路径；component：组件名称
        {<!-- -->path: "/login", component: loginForm},
        {<!-- -->path: "/register", component: registerForm}
    ]
});
var vm = new Vue({<!-- -->
    el: "#app",
    components: {<!-- -->
        loginForm,
        registerForm
    },
    router
});

```
-  原来的index.html中引入了很多其它js，在这里我们使用es6的import语法进行导入。 <li> ​ 注意，要使用import，就需要在login.js和register.js中添加export导出语句： <pre><code class="prism language-js">const loginForm={<!-- -->
    template: '\
       \
            <h2>登陆页</h2>\
            用户名：<input type="text"><br>\
            密&amp;emsp;码：<input type="password">\
       ',
}
export default loginForm;
</code></pre> register.js: <pre><code class="prism language-js">const registerForm = {<!-- -->
    template:'\
    \
    <h2>注册页</h2> \
    用&amp;ensp;户&amp;ensp;名：<input type="text"><br/>\
    密&amp;emsp;&amp;emsp;码：<input type="password"><br/>\
    确认密码：<input type="password"><br/>\
    \
    '
}
export default registerForm;
</code></pre> </li>-  vue-router使用模块化加载后，必须增加一句：Vue.use(VueRouter) 
这样，index.js就成了我们整个配置的入口了。

我们在webpack.config.js中添加以下内容：

```
module.exports={<!-- -->
    entry:'./src/index.js',  //指定打包的入口文件
}

```

### 8.3.2.出口output

出口，就是输出的目的地。一般我们会用一个dist目录，作为打包输出的文件夹：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-MuKZXLRH-1591000574804)(assets/1530201612391.png)] <img src="https://img-blog.csdnimg.cn/20200603215327176.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 然后，编写webpack.config.js，添加出口配置：

```
module.exports={<!-- -->
    entry:'./src/main.js',  //指定打包的入口文件
    output:{<!-- -->
        // path: 输出的目录，__dirname是相对于webpack.config.js配置文件的绝对路径
        path : __dirname+'/dist',  
        filename:'build.js'	 //输出的js文件名
    }
}

```

## 8.4.执行打包

在控制台输入以下命令：

```
npx webpack --config webpack.config.js

```

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-OyXFJZAr-1591000574805)(assets/1530203361613.png)] <img src="https://img-blog.csdnimg.cn/20200603215350880.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 随后，查看dist目录：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-MzDQnNDI-1591000574807)(assets/1530203406462.png)] <img src="https://img-blog.csdnimg.cn/20200603215447720.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 尝试打开build.js，你根本看不懂：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-cdyxC8Ra-1591000574808)(assets/1530203465737.png)] <img src="https://img-blog.csdnimg.cn/20200603215514559.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 所有的js合并为1个，并且对变量名进行了随机打乱，这样就起到了 压缩、混淆的作用。

## 8.5.测试运行

在index.html中引入刚刚生成的build.js文件，

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    
        <!--router-link来指定跳转的路径-->
        ><router-link to="/login">登录</router-link></span>
        ><router-link to="/register">注册</router-link></span>
        <hr/>
        
            <!--vue-router的锚点-->
            <router-view></router-view>
        
    
    <script src="../dist/build.js"></script>
</body>
</html>

```

然后运行：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-ts3tx6Gm-1591000574810)(assets/1530203553915.png)]

<img src="https://img-blog.csdnimg.cn/20200603215540911.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 8.6.打包CSS

我们来编写一段CSS代码，对index的样式做一些美化：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-D7QYZbjN-1591000574812)(assets/1530203880056.png)] <img src="https://img-blog.csdnimg.cn/20200603215602393.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 内容：

```
#app a{<!-- -->
    display: inline-block;
    width: 150px;
    line-height: 30px;
    background-color: dodgerblue;
    color: white;
    font-size: 16px;
    text-decoration: none;
}
#app a:hover{<!-- -->
    background-color: whitesmoke;
    color: dodgerblue;
}
#app div{<!-- -->
    width: 300px;
    height: 150px;
}
#app{<!-- -->
    width: 305px;
    border: 1px solid dodgerblue;
}

```

### 8.6.1.安装加载器

前面说过，webpack默认只支持js加载。要加载CSS文件，必须安装加载器：

命令：`npm install style-loader css-loader --save-dev`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-oAbtP4fv-1591000574813)(assets/1530204068192.png)] <img src="https://img-blog.csdnimg.cn/20200603215629755.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 此时，在package.json中能看到新安装的：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-wXxfx9EK-1591000574814)(assets/1530204160848.png)] <img src="https://img-blog.csdnimg.cn/20200603215650164.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

### 8.6.3.index.js引入css文件

因为入口在index.js，因此css文件也要在这里引入。依然使用ES6 的模块语法：

```
import './css/main.css'

```

### 8.6.4.配置加载器

在webpack.config.js配置文件中配置css的加载器

```
module.exports = {<!-- -->
    entry: './src/main.js',  //指定打包的入口文件
    output: {<!-- -->
        path: __dirname + '/dist', // 注意：__dirname表示webpack.config.js所在目录的绝对路径
        filename: 'build.js'  //输出文件
    },
    module: {<!-- -->
        rules: [
            {<!-- -->
                test: /\.css$/, // 通过正则表达式匹配所有以.css后缀的文件
                use: [ // 要使用的加载器，这两个顺序一定不要乱
                    'style-loader',
                    'css-loader'
                ]
            }
        ]
    }
}

```

### 8.6.5.重新打包

再次输入打包指令：`npx webpack --config webpack.config.js`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-frGiRjaT-1591000574816)(assets/1530204780240.png)] <img src="https://img-blog.csdnimg.cn/20200603215729929.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-ah6ScORW-1591000574816)(assets/1530204813013.png)]

<img src="https://img-blog.csdnimg.cn/20200603215804776.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 8.7.script脚本

我们每次使用npm安装，都会在package.json中留下痕迹，事实上，package.json中不仅可以记录安装的内容，还可编写脚本，让我们运行命令更加快捷。

我们可以把webpack的命令编入其中：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-z7gnlZq7-1591000574818)(assets/1530205423730.png)] <img src="https://img-blog.csdnimg.cn/2020060321583154.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 以后，如果要打包，就可以直接输入：`npm run build`即可。

`npm run` ：执行npm脚本，后面跟的是配置脚本的名称`build`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-rVoGGBtI-1591000574819)(assets/1530205504104.png)]

<img src="https://img-blog.csdnimg.cn/20200603215858384.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 8.8.打包HTML

之前的打包过程中，除了HTML文件外的其它文件都被打包了，当在线上部署时，我们还得自己复制HTML到dist，然后手动添加生成的js到HTML中，这非常不友好。

webpack中的一个插件：html-webpack-plugin，可以解决这个问题。

1）安装插件：`npm install --save-dev html-webpack-plugin`

需要在webpack.config.js中添加插件：

```
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {<!-- -->
    entry: './src/main.js',  //指定打包的入口文件
    output: {<!-- -->
        path: __dirname + '/dist',  // 注意：__dirname表示webpack.config.js所在目录的绝对路径
        filename: 'build.js'		   //输出文件
    },
    module: {<!-- -->
        rules: [
            {<!-- -->
                test: /\.css$/, // 通过正则表达式匹配所有以.css后缀的文件
                use: [ // 要使用的加载器，这两个顺序一定不要乱
                    'style-loader',
                    'css-loader'
                ]
            }
        ]
    },
    plugins:[
        new HtmlWebpackPlugin({<!-- -->
            title: '首页',  //生成的页面标题<head><title>首页</title></head>
            filename: 'index.html', // dist目录下生成的文件名
            template: './src/index.html' // 我们原来的index.html，作为模板
        })
    ]
}

```

2）将原来HTML中的引入js代码删除：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-EqLT9wIS-1591000574819)(assets/1530207035782.png)] <img src="https://img-blog.csdnimg.cn/20200603215922725.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 3）再次打包：`npm run build`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-2z2TM9j8-1591000574822)(assets/1530206990349.png)] <img src="https://img-blog.csdnimg.cn/20200603215942584.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 4）查看dist目录：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-yrAJE8H1-1591000574823)(assets/1530207132261.png)] <img src="https://img-blog.csdnimg.cn/20200603220002468.png" alt="在这里插入图片描述"/> 打开index.html，发现已经自动添加了当前目录下的build.js

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    
        <!--router-link来指定跳转的路径-->
        ><router-link to="/login">登录</router-link></span>
        ><router-link to="/register">注册</router-link></span>
        
            <!--vue-router的锚点-->
            <router-view></router-view>
        
    
<script type="text/javascript" src="build.js"></script></body>
</html>

```

## 8.9.热更新的web服务

刚才的案例中，每次修改任何js或css内容，都必须重新打包，非常麻烦。

webpack给我们提供了一个插件，可以帮我们运行一个web服务，加载页面内容，并且修改js后不需要重新加载就能看到最新结果：

1）安装插件：`npm install webpack-dev-server --save-dev`

2）添加启动脚本

在package.json中配置script

```
  "scripts": {<!-- -->
    "dev": "webpack-dev-server --inline --hot --open --port 8080 --host 127.0.0.1"
  },

```

–inline：自动刷新

–hot：热加载

–port：指定端口

–open：自动在默认浏览器打开

–host：可以指定服务器的 ip，不指定则为127.0.0.1

3）运行脚本：`npm run dev`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-oTctP9Zp-1591000574825)(assets/1530207667660.png)] <img src="https://img-blog.csdnimg.cn/20200603220027516.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 4）效果：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-6sN6aB8Q-1591000574826)(assets/1530207505226.png)]

<img src="https://img-blog.csdnimg.cn/20200603220049149.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

# 9.vue-cli

## 9.1.介绍和安装

在开发中，需要打包的东西不止是js、css、html。还有更多的东西要处理，这些插件和加载器如果我们一一去添加就会比较麻烦。

幸好，vue官方提供了一个快速搭建vue项目的脚手架：vue-cli

使用它能快速的构建一个web工程模板。

官网：https://github.com/vuejs/vue-cli

安装命令：`npm install -g vue-cli`

## 9.2.快速上手

我们新建一个module：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-1qlPUzS3-1591000574827)(assets/1530208068828.png)] <img src="https://img-blog.csdnimg.cn/20200603220113497.png" alt="在这里插入图片描述"/> 切换到该目录：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-3ROSSgTB-1591000574828)(assets/1530208139922.png)] <img src="https://img-blog.csdnimg.cn/20200603220134118.png" alt="在这里插入图片描述"/> 用vue-cli命令，快速搭建一个webpack的项目：`vue init webpack`

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-hsAoA8u4-1591000574829)(assets/1530208650256.png)] <img src="https://img-blog.csdnimg.cn/20200603220157803.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> [外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-E75iPa6M-1591000574830)(assets/1530208708000.png)] <img src="https://img-blog.csdnimg.cn/2020060322021957.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 前面几项都走默认或yes

下面这些我们选no

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-uGJ6ljWg-1591000574832)(assets/1530208850418.png)] <img src="https://img-blog.csdnimg.cn/2020060322024131.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 最后，再选yes，使用 npm安装

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-iyK4VyyK-1591000574834)(assets/1530208897063.png)] <img src="https://img-blog.csdnimg.cn/20200603220303548.png" alt="在这里插入图片描述"/> 开始初始化项目，并安装依赖，可能需要

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-xi1kGXDl-1591000574836)(assets/1530208932814.png)] <img src="https://img-blog.csdnimg.cn/20200603220325785.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 安装成功！

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-WHcZ839Q-1591000574839)(assets/1530209062090.png)] <img src="https://img-blog.csdnimg.cn/20200603220345598.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 可以使用`npm run dev`命令启动。

## 9.3.项目结构

安装好的项目结构：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-qBfd6Jbm-1591000574842)(assets/1530209146349.png)]

<img src="https://img-blog.csdnimg.cn/20200603220407960.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

入口文件：main.js

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-xmZwUXjz-1591000574846)(assets/1530209503007.png)]

<img src="https://img-blog.csdnimg.cn/20200603220429839.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 9.4.单文件组件

需要注意的是，我们看到有一类后缀名为.vue的文件，我们称为单文件组件

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-2bhfz76n-1591000574846)(assets/1530209769323.png)] <img src="https://img-blog.csdnimg.cn/20200603220452620.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/> 每一个.vue文件，就是一个独立的vue组件。类似于我们刚才写的login.js和register.js

只不过，我们在js中编写 html模板和样式非常的不友好，而且没有语法提示和高亮。

而单文件组件中包含三部分内容：
- template：模板，支持html语法高亮和提示- script：js脚本，这里编写的就是vue的组件对象，还可以有data(){}等- style：样式，支持CSS语法高亮和提示
每个组件都有自己独立的html、JS、CSS，互不干扰，真正做到可独立复用。

## 9.5.运行

看看生成的package.json：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-Roo7ym3c-1591000574849)(assets/1530210016103.png)] <img src="https://img-blog.csdnimg.cn/20200603220519772.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>
- 可以看到这引入了非常多的依赖，绝大多数都是开发期依赖，比如大量的加载器。- 运行时依赖只有vue和vue-router<li>脚本有三个： 
  <ul>- dev：使用了webpack-dev-server命令，开发时热部署使用- start：使用了npm run dev命令，与上面的dev效果完全一样，当脚本名为“start”时，可以省略“run”。- build：等同于webpack的打包功能，会打包到dist目录下。
我们执行`npm run dev` 或者 `npm start` 都可以启动项目：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-sWykB9k1-1591000574850)(assets/1530210411076.png)]

<img src="https://img-blog.csdnimg.cn/20200603220539947.png" alt="在这里插入图片描述"/> 页面：

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-1YCzoWQQ-1591000574852)(assets/1530210349704.png)]

<img src="https://img-blog.csdnimg.cn/20200603220600965.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/106475728