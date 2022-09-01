# 大数据知识面试题-Azkaban（2022版）
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
1.1 什么是 Azkaban
1.2 为什么需要工作流调度系统
1.3 Azkaban 特点
1.4 常见工作流调度系统
1.5 Azkaban 的架构
1.6 Azkaban 下载地址
2.1 安装前准备
2.2 安装 Azkaban
2.3 生成密钥对和证书
2.4 时间同步配置
2.5 配置文件
2.5.1 Web 服务器配置
2.5.2 执行服务器配置
2.6 启动 Executor 服务器
2.7 启动 Web 服务器
三 Azkaban 实战
3.1 单一 job 案例
3.2 邮件通知配置案例
3.3 多 job 工作流案例
3.4 Java 操作任务
3.5 HDFS 操作任务
3.6 MapReduce 任务
3.7 Hive 脚本任务

## 1.1 什么是 Azkaban

<img src="https://img-blog.csdnimg.cn/0a099afd0cb441fb8a022f8f3f4ef7f6.png" alt="在这里插入图片描述"/>

Azkaban 是由Linkedin 公司推出的一个批量工作流任务调度器，主要用于在一个工作流 内以一个特定的顺序运行一组工作和流程，它的配置是通过简单的 key:value 对的方式， 通 过配置中的 Dependencies 来设置依赖关系。Azkaban 使用job 配置文件建立任务之间的依赖 关系， 并提供一个易于使用的 web 用户界面维护和跟踪你的工作流。

## 1.2 为什么需要工作流调度系统

(1) 一个完整的数据分析系统通常都是由大量任务单元组成： Shell 脚本程序，Java 程序，MapReduce 程序、 Hive 脚本等

(2) 各任务单元之间存在时间先后及前后依赖关系

(3) 为了很好地组织起这样的复杂执行计划，需要一个工作流调度系统来调度执行； 例如， 我们可能有这样一个需求，某个业务系统每天产生 20G 原始数据，我们每天都 要对其进行处理，处理步骤如下所示：
1. 通过 Hadoop 先将原始数据上传到 HDFS 上 (HDFS 的操作)；1. 使用 MapReduce 对原始数据进行清洗 (MapReduce 的操作)；1. 将清洗后的数据导入到 hive 表中 (hive 的导入操作) ；1. 对 Hive 中多个表的数据进行 JOIN 处理， 得到一张 hive 的明细表 (创建中间表) ；1. 通过对明细表的统计和分析，得到结果报表信息 (hive 的查询操作) ；
<img src="https://img-blog.csdnimg.cn/3dc8ae615ea54252abcf7d4e2078b3fa.png" alt="在这里插入图片描述"/>

## 1.3 Azkaban 特点
1. 兼容任何版本的 hadoop1. 易于使用的 Web 用户界面1. 简单的工作流的上传1. 方便设置任务之间的关系1. 调度工作流1. 模块化和可插拔的插件机制1. 认证/授权(权限的工作)1. 能够杀死并重新启动工作流1. 有关失败和成功的电子邮件提醒
## 1.4 常见工作流调度系统

1)简单的任务调度：直接使用 crontab 实现； 2)复杂的任务调度：开发调度平台或使用现成的开源调度系统，比如 ooize 、azkaban 等

## 1.5 Azkaban 的架构

<img src="https://img-blog.csdnimg.cn/64866b27d6cb4119ab02b1247da7198b.png" alt="在这里插入图片描述"/>

Azkaban 由三个关键组件构成：
1. AzkabanWebServer：AzkabanWebServer 是整个Azkaban 工作流系统的主要管理者， 它用户登录认证、负责 project 管理、 定时执行工作流、跟踪工作流执行进度等一 系列任务。1. AzkabanExecutorServer：负责具体的工作流的提交、执行，它们通过 mysql 数据库 来协调任务的执行。1. 关系型数据库(MySQL ) ：存储大部分执行流状态， AzkabanWebServer 和 AzkabanExecutorServer 都需要访问数据库。
## 1.6 Azkaban 下载地址

下载地址:http://azkaban.github.io/downloads.html

## 2.1 安装前准备
1. 将 Azkaban Web 服务器、Azkaban 执行服务器、Azkaban 的 sql 执行脚本及 MySQL 安 装包拷贝到 hadoop102 虚拟机/opt/software 目录下 a) azkaban-web-server-2.5.0.tar.gz b) azkaban-executor-server-2.5.0.tar.gz c) azkaban-sql-script-2.5.0.tar.gz d) mysql-libs.zip1. 选择 Mysql 作为 Azkaban 数据库，因为 Azkaban 建立了一些 Mysql 连接增强功能， 以 方便 Azkaban 设置。并增强服务可靠性。 (参见 hive 文档2.4)
## 2.2 安装 Azkaban
1.  在/opt/module/目录下创建 azkaban 目录 1.  解压 azkaban-web-server-2.5.0.tar.gz 、azkaban-executor-server-2.5.0.tar.gz 、azkaban-sql-script-2.5.0.tar.gz 到/opt/module/azkaban 目录下 
```
[atguigu@hadoop102 software]$ tar -zxvf azkaban-web-server-2.5.0.tar.gz
-C /opt/module/azkaban/
[atguigu@hadoop102  software]$  tar  -zxvf  azkaban-executor-server-
2.5.0.tar.gz -C /opt/module/azkaban/                                   	
[atguigu@hadoop102 software]$ tar -zxvf azkaban-sql-script-2.5.0.tar.gz -C /opt/module/azkaban/      

```
1.  对解压后的文件重新命名 [atguigu@hadoop102 azkaban]$ mv azkaban-web-2.5.0/ server [atguigu@hadoop102 azkaban]$ mv azkaban-executor-2.5.0/ executor 1.  azkaban 脚本导入 进入 mysql ，创建 azkaban 数据库，并将解压的脚本导入到 azkaban 数据库。 
```
[atguigu@hadoop102 azkaban]$ mysql -uroot -p000000                     
mysql> create database azkaban;                                         
mysql> use azkaban;                                                    
mysql>     source     /opt/module/azkaban/azkaban-2.5.0/create-all-sql-
2.5.0.sql   

```

注： source 后跟.sql 文件， 用于批量处理.sql 文件中的 sql 语句。

## 2.3 生成密钥对和证书

Keytool 是java 数据证书的管理工具，使用户能够管理自己的公/私钥对及相关证书。 -keystore 指定密钥库的名称及位置(产生的各类信息将存在.keystore 文件中) -genkey(或者-genkeypair) 生成密钥对 -alias 为生成的密钥对指定别名，如果没有默认是 mykey -keyalg 指定密钥的算法 RSA/DSA 默认是 DSA

1)生成 keystore 的密码及相应信息的密钥库 [atguigu@hadoop102 azkaban]$ keytool -keystore keystore -alias jetty - genkey -keyalg RSA 输入密钥库口令 : 再次输入新口令 : 您的名字与姓氏是什么?  [Unknown]: 您的组织单位名称是什么?  [Unknown]: 您的组织名称是什么?  [Unknown]: 您所在的城市或区域名称是什么? [Unknown]: 您所在的省/市/自治区名称是什么?  [Unknown]: 该单位的双字母国家/地区代码是什么?  [Unknown]: CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown 是否 正确?  [否]: y

输入 的密钥口令 (如果和密钥库口令相同, 按回车):

**注意：** 密钥库的密码至少必须 6 个字符，可以是纯数字或者字母或者数字和字母的组合等等 密钥库的密码最好和 的密钥相同，方便记忆 2)将 keystore 拷贝到 azkaban web 服务器根目录中 [atguigu@hadoop102 azkaban]$ mv keystore /opt/module/azkaban/server/

## 2.4 时间同步配置

先配置好服务器节点上的时区
1. 如果在/usr/share/zoneinfo/这个目录下不存在时区配置文件 Asia/Shanghai ，就要用 tzselect 生成。
```
		[atguigu@hadoop102 azkaban]$ tzselect                                   Please identify a location so that time zone rules can be set correctly. Please select a continent or ocean.                                     	1) Africa                                                              				
			2) Americas                                                            					
			3) Antarctica                                                          						
			4) Arctic Ocean                                                        	
			5) Asia                                                                							
			6) Atlantic Ocean                                                      	
			7) Australia                                                           	
			8) Europe                                                              	
			9) Indian Ocean                                                        		
			10) Pacific Ocean                                                       	
			11) none - I want to specify the time zone using the Posix TZ format.   	#? 5                                                                    Please select a country.                                               
35) Palestine
36) Philippines
37) Qatar
38) Russia
39) Saudi Arabia
40) Singapore
41) Sri Lanka
42) Syria
43) Taiwan
44) Tajikistan
45) Thailand
46) Turkmenistan
47) United Arab Emirates
48) Uzbekistan
49) Vietnam
50) Yemen
17) Iraq
#? 9
Please select one of the following time zone regions.
1) Beijing Time
2) Xinjiang Time
#? 1

```
1. 拷贝该时区文件，覆盖系统本地时区配置 [atguigu@hadoop102 azkaban]$ cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
3)集群时间同步 (同时发给三个窗口) [atguigu@hadoop102 azkaban]$ sudo date -s ‘2018-10-18 16:39:30’

## 2.5 配置文件

## 2.5.1 Web 服务器配置

1)进入 azkaban web 服务器安装目录 conf 目录，打开 azkaban.properties 文件

```
[atguigu@hadoop102 conf]$ pwd  
  /opt/module/azkaban/server/conf                                        
[atguigu@hadoop102 conf]$ vim azkaban.properties     

```
1. 按照如下配置修改 azkaban.properties 文件。
```
#Azkaban Personalization Settings                                      
#服务器 UI 名称,用于服务器上方显示的名字                                                         
azkaban.name=Test                                                      
#描述                                                                                            
azkaban.label=My Local Azkaban                                         
#UI 颜色                                                                                         
azkaban.color=#FF3601                                                   
azkaban.default.servlet.path=/index                                    

#默认 web server 存放 web 文件的目录                                                            
web.resource.dir=/opt/module/azkaban/server/web/                       

 #默认时区,已改为亚洲/上海 默认为美国                                                            
 default.timezone.id=Asia/Shanghai                                      	
#Azkaban UserManager class                                              

user.manager.class=azkaban.user.XmlUserManager                         
#用户权限管理默认类(绝对路径)                                                                   
user.manager.xml.file=/opt/module/azkaban/server/conf/azkaban-users.xml
	
#Loader for projects                                                   
#global 配置文件所在位置(绝对路径)                                                             
executor.global.properties=/opt/module/azkaban/executor/conf/global.pro perties                                                                
azkaban.project.dir=projects             

```
1. web 服务器用户配置 在 azkaban web 服务器安装目录 conf 目录，按照如下配置修改 azkaban-users.xml 文件， 增加管理员用户。
```
<role name="metrics" permissions="METRICS"/>                          
</azkaban-users>      

```

## 2.5.2 执行服务器配置
1. 进入执行服务器安装目录 conf，打开 azkaban.properties
```
[atguigu@hadoop102 conf]$ pwd                                           	
/opt/module/azkaban/executor/conf                                      
[atguigu@hadoop102 conf]$ vim azkaban.properties                       

 2) 按照如下配置修改 azkaban.properties 文件。
#Azkaban                                                               

 #时区                                                                                

default.timezone.id=Asia/Shanghai 
# Azkaban JobTypes Plugins                                              
#jobtype 插件所在位置                                                                
azkaban.jobtype.plugin.dir=plugins/jobtypes                            
	
#Loader for projects                                                   

executor.global.properties=/opt/module/azkaban/executor/conf/global.pro 
perties                                                                

azkaban.project.dir=projects                                           
	

database.type=mysql                                                     
mysql.port=3306                                                        

mysql.host=hadoop102                                                    
mysql.database=azkaban                                                  

mysql.user=root                                                         
mysql.password=000000                                                  

mysql.numconnections=100                                               
	
# Azkaban Executor settings                                            
 #最大线程数                                                                           
 executor.maxThreads=50                                                 
  #端口号(如修改,请与 web 服务中一致)                                          
  executor.port=12321                                                     
  #线程数                                                                               
  executor.flow.threads=30         

```

## 2.6 启动 Executor 服务器

在 executor 服务器目录下执行启动命令

```
[atguigu@hadoop102 executor]$ pwd                                       	
/opt/module/azkaban/executor  
[atguigu@hadoop102 executor]$ bin/azkaban-executor-start.sh       

```

## 2.7 启动 Web 服务器

在 azkaban web 服务器目录下执行启动命令

```
[atguigu@hadoop102 server]$ pwd                                         	
/opt/module/azkaban/server                                             
[atguigu@hadoop102 server]$ bin/azkaban-web-start.sh     

```

注意： 先执行 executor，再执行 web，避免 Web Server 会因为找不到执行器启动失败。 jps 查看进程

启动完成后，在浏览器(建议使用谷歌浏览器)中输入 https://服务器 IP 地址:8443 ，即可访问 azkaban 服务了。 在登录中输入刚才在 azkaban-users.xml 文件中新添加的户用名及密码，点击 login。 <img src="https://img-blog.csdnimg.cn/2e5f4818ea414c1e907ea2c7687acbb1.png" alt="在这里插入图片描述"/>

<img src="https://img-blog.csdnimg.cn/c45b8fd3b2f649f5a817f4d1c1371dd8.png" alt="在这里插入图片描述"/>

# 三 Azkaban 实战

Azkaban 内置的任务类型支持 command、java

## 3.1 单一 job 案例

1.案例实操
1. 创建job 描述文件 [atguigu@hadoop102 jobs]$ vim first.job #first.job type=command command=echo ‘this is my first job’1. 将job 资源文件打包成 zip 文件 [atguigu@hadoop102 jobs]$ zip adding: first.job (deflated [atguigu@hadoop102 jobs]$ ll 总用量 8
**注意：** 目前， Azkaban 上传的工作流文件只支持 xxx.zip 文件。 zip 应包含 xxx.job 运行作业所需的 文件和任何文件 (文件名后缀必须以.job 结尾， 否则无法识别) 。作业名称在项目中必须是 唯一的。 3) 通过 azkaban 的 web 管理平台创建 project 并上传job 的 zip 包 首先创建 project <img src="https://img-blog.csdnimg.cn/4a0118be01c045a3b688b81a0cdc7779.png" alt="在这里插入图片描述"/>

上传 zip 包 <img src="https://img-blog.csdnimg.cn/b6715bc6239c48cdbf413e26d8ee6bb9.png" alt="在这里插入图片描述"/>
1. 启动执行该job <img src="https://img-blog.csdnimg.cn/56cadad840714982ba3a7497a93af600.png" alt="在这里插入图片描述"/>
点击执行工作流 <img src="https://img-blog.csdnimg.cn/e8832d6a21ce40df8211844c14098b19.png" alt="在这里插入图片描述"/>

点击继续 <img src="https://img-blog.csdnimg.cn/1c59585a9fc74930902127db05113b2a.png" alt="在这里插入图片描述"/>
1.  Job 执行成功 <img src="https://img-blog.csdnimg.cn/1f6167648cf441268a389698028e318a.png" alt="在这里插入图片描述"/> 1.  点击查看job 日志 <img src="https://img-blog.csdnimg.cn/7846b0b884cb4785a70a9c10c25d30b9.png" alt="在这里插入图片描述"/> 
## 3.2 邮件通知配置案例
1. 修改配置文件 修改 server 的 conf 下的 azkaban.properties 文件
<img src="https://img-blog.csdnimg.cn/6a901111a2094851b5a0d28c008a4f8a.png" alt="在这里插入图片描述"/>

2)在网页上进行配置

<img src="https://img-blog.csdnimg.cn/54c4acacfa7e476eb35c35f00aa63c43.png" alt="在这里插入图片描述"/>

<img src="https://img-blog.csdnimg.cn/48f9314570e4408ca9b5ad521afba872.png" alt="在这里插入图片描述"/>

## 3.3 多 job 工作流案例
1. 创建有依赖关系的多个job 描述 第一个job：start.job
```
第一个job：start.job
[atguigu@hadoop102 jobs]$ vim start.job
#start.job
type=command
command=touch /opt/module/kangkang.txt

第二个job：step1.job 依赖 start.job
[atguigu@hadoop102 jobs]$ vim step1 .job
#step1 .job
type=command
dependencies=start
command=echo "this is step1 job"
第三个job：step2.job 依赖 start.job
[atguigu@hadoop102 jobs]$ vim step2.job
#step2.job
type=command
dependencies=start
command=echo "this is step2 job"
第四个job：finish.job 依赖 step1.job 和 step2.job

```

```
[atguigu@hadoop102 jobs]$ vim finish.job
#finish.job
type=command
dependencies=step1,step2
command=echo "this is finish job"

```
1. 将所有job 资源文件打到一个 zip 包中
```
[atguigu@hadoop102 jobs]$ zip jobs.zip start.job step1.job step2.job
finish.job
updating: start.job (deflated 16%)
adding: step1.job (deflated 12%)
adding: step2.job (deflated 12%)
adding: finish.job (deflated 14%)

```
1.  在 azkaban 的 web 管理界面创建工程并上传 zip 包 <img src="https://img-blog.csdnimg.cn/64fd922b8d4e43ff9ceca91bde10b4a5.png" alt="在这里插入图片描述"/> 1.  启动工作流 flow 
<img src="https://img-blog.csdnimg.cn/4c5eb98fe13e4e26a9b16097f127d55e.png" alt="在这里插入图片描述"/>
1. 查看结果 <img src="https://img-blog.csdnimg.cn/b29d9dbcf82a451489d16f79a2d565df.png" alt="在这里插入图片描述"/>
思考： 将 student.txt 文件上传到 hdfs，根据所传文件创建外部表， 再将表中查询到的结果写入到本 地文件

## 3.4 Java 操作任务

使用 Azkaban 调度java 程序
1. 编写java 程序
```
import java.io.IOException;                                            
	

public class AzkabanTest {<!-- -->                                              	
public void run() throws IOException {<!-- -->                                			
// 根据需求编写具体代码                                                       		
FileOutputStream   fos   =  new
FileOutputStream("/opt/module/azkaban/output.txt");                    

fos.write("this is a java progress".getBytes());

fos.close();                                                     	}                                                                    
	

public static void main(String[] args) throws IOException {<!-- -->           
AzkabanTest azkabanTest = new AzkabanTest();

azkabanTest.run ();
}
}

```
1. 将java 程序打成jar 包，创建 lib 目录，将 jar 放入 lib 内
```
[atguigu@hadoop102 azkaban]$ mkdir lib                                  
[atguigu@hadoop102 azkaban]$ cd lib/                                    
[atguigu@hadoop102 lib]$ ll                                             
总用量 4                                                                
 -rw-rw-r--.  1 atguigu atguigu  3355  10 月  18 20:55 azkaban-0.0.1- SNAPSHOT.jar                                                           
 

```
1. 编写job 文件
```
[atguigu@hadoop102 jobs]$ vim azkabanJava.job                           
#azkabanJava.job                                                       

type=javaprocess                                                        
java.class=com.atguigu.azkaban.AzkabanTest                              
classpath=/opt/module/azkaban/lib/*     

```

4)将 job 文件打成 zip 包

```
[atguigu@hadoop102 jobs]$ zip azkabanJava.zip azkabanJava.job           		
adding: azkabanJava.job (deflated 19%)      

```
1. 通过 azkaban 的 web 管理平台创建 project 并上传job 压缩包，启动执行该job
<img src="https://img-blog.csdnimg.cn/58d9a9b40873470fb9482b12a3101b9e.png" alt="在这里插入图片描述"/>

```
atguigu@hadoop102 azkaban]$ pwd
/opt/module/azkaban
[atguigu@hadoop102 azkaban]$ ll
总用量 24
drwxrwxr-x. 2 atguigu atguigu 4096 10 月 17 17:14 azkaban-2.5.0 drwxrwxr-x. 10 atguigu atguigu 4096 10 月 18 17:17 executor    drwxrwxr-x. 2 atguigu atguigu 4096 10 月 18 20:35 jobs         drwxrwxr-x. 2 atguigu atguigu 4096 10 月 18 20:54 lib
-rw-rw-r--. 1 atguigu atguigu  23 10 月 18 20:55 output
drwxrwxr-x. 9 atguigu atguigu 4096 10 月 18 17:17 server
[atguigu@hadoop102 azkaban]$ cat output
this is a java progress


```

## 3.5 HDFS 操作任务

1)创建job 描述文件

```
[atguigu@hadoop102 jobs]$ vim fs.job                                    
#hdfs job                                                               
type=command                                                            
command=/opt/module/hadoop-2.7.2/bin/hadoop fs -mkdir /azkaban   

```

2)将 job 资源文件打包成 zip 文件

```
[atguigu@hadoop102 jobs]$ zip fs.zip fs.job                             		
adding: fs.job (deflated 12%)   

```

3)通过 azkaban 的 web 管理平台创建 project 并上传job 压缩包 4)启动执行该job 5)查看结果 <img src="https://img-blog.csdnimg.cn/ea692b9c028e4d40b6ae276c35a97026.png" alt="在这里插入图片描述"/>

<img src="https://img-blog.csdnimg.cn/d75e65f70d334d03aa4e5c93afb2873a.png" alt="在这里插入图片描述"/>

## 3.6 MapReduce 任务

MapReduce 任务依然可以使用Azkaban 进行调度
1. 创建job 描述文件，及 mr 程序jar 包
```
 [atguigu@hadoop102 jobs]$ vim mapreduce.job
#mapreduce job
type=command
command=/opt/module/hadoop-2.7.2/bin/hadoop   jar   /opt/module/hadoop-
2.7.2/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar
wordcount /wordcount/input /wordcount/output

```
1. 将所有job 资源文件打到一个 zip 包中
```
[atguigu@hadoop102 jobs]$ zip mapreduce.zip mapreduce.job
adding: mapreduce.job (deflated 43%)


```
1. 在 azkaban 的 web 管理界面创建工程并上传 zip 包1. 启动job
5)查看结果

<img src="https://img-blog.csdnimg.cn/a98538e27cd54625b6ecb562a35c3e73.png" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/4e85fea7e82f43d09679bed247bdf059.png" alt="在这里插入图片描述"/>

## 3.7 Hive 脚本任务
1. 创建job 描述文件和 hive 脚本 (1) Hive 脚本：student.sql
```
[atguigu@hadoop102 jobs]$ vim student.sql
use default;
drop table student;
create table student(id int, name string)
row format delimited fields terminated by '\t';
load  data  local  inpath  '/opt/module/datas/student.txt'  into  table
student;
insert overwrite local directory '/opt/module/datas/student'
row format delimited fields terminated by '\t'
select * from student;



```

(2) Job 描述文件： hive.job

```
[atguigu@hadoop102 jobs]$ vim hive.job
#hive job
type=command
command=/opt/module/hive/bin/hive
/opt/module/azkaban/jobs/student.sql

```
1. 将所有job 资源文件打到一个 zip 包中
```
[atguigu@hadoop102 jobs]$ zip hive.zip hive.job
adding: hive.job (deflated 21%)

```
1.  在 azkaban 的 web 管理界面创建工程并上传 zip 包 1.  启动job 
5)查看结果

```
[atguigu@hadoop102 student]$ cat /opt/module/datas/student/000000_0
yangyang
huihui
banzhang
pengpeng

```

<img src="https://img-blog.csdnimg.cn/69ce8194f566416db78680d089ae387b.png" alt="在这里插入图片描述"/>
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125146859