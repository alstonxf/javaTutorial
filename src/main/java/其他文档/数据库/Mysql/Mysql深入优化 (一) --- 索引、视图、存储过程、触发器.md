# Mysql深入优化 (一) --- 索引、视图、存储过程、触发器
|序列号|内容|链接|
| :-----| :-----| :-----|
|1|Mysql深入优化 (一) ----- 索引、视图、存储过程、触发器|https://blog.csdn.net/qq_43061290/article/details/125410648|
|2|Mysql深入优化 (二) ----- 体系结构、存储引擎、SQL优化|https://blog.csdn.net/qq_43061290/article/details/125410728|
|3|Mysql深入优化 (三) ----- 查询缓存、内存管理及优化、MySQL锁问题|https://blog.csdn.net/qq_43061290/article/details/125410781|
|4|Mysql深入优化 (四) ----- MySQL常用工具、日志、主从复制、综合案例|https://blog.csdn.net/qq_43061290/article/details/125410813|


 # 目录标题
Mysql深入优化-day01
MySQL高级课程简介
1. Linux 系统安装MySQL
1.1 下载Linux 安装包
1.2 安装MySQL
1.3 启动 MySQL 服务
1.4 登录MySQL
2. 索引
2.1 索引概述
2.2 索引优势劣势
2.3 索引结构
2.3.1 BTREE 结构
2.3.3 B+TREE 结构
2.3.3 MySQL中的B+Tree
2.4 索引分类
2.5 索引语法
2.5.1 创建索引
2.5.2 查看索引
2.5.3 删除索引
2.5.4 ALTER命令
2.6 索引设计原则
3. 视图
3.1 视图概述
3.2 创建或者修改视图
3.3 查看视图
3.4 删除视图
4. 存储过程和函数
4.1 存储过程和函数概述
4.2 创建存储过程
4.3 调用存储过程
4.4 查看存储过程
4.5 删除存储过程
4.6 语法
4.6.1 变量
4.6.2 if条件判断
4.6.3 传递参数
4.6.4 case结构
4.6.5 while循环
4.6.6 repeat结构
4.6.7 loop语句
4.6.8 leave语句
4.6.9 游标/光标
4.7 存储函数
5. 触发器
5.1 介绍
5.2 创建触发器
5.3 删除触发器
5.4 查看触发器
5.3 删除触发器
5.4 查看触发器

# Mysql深入优化-day01

### MySQL高级课程简介

|序号|第一部分|第二部分|第三部分|第四部分|
| :-----| :-----| :-----| :-----| :-----|
|1|Linux系统安装MySQL|体系结构|应用优化|MySQL 常用工具|
|2|索引|存储引擎|查询缓存优化|MySQL 日志|
|3|视图|优化SQL步骤|内存管理及优化|MySQL 主从复制|
|4|存储过程和函数|索引使用|MySQL锁问题|综合案例|
|5|触发器|SQL优化|常用SQL技巧||


### 1. Linux 系统安装MySQL

#### 1.1 下载Linux 安装包

```
https://dev.mysql.com/downloads/mysql/5.7.html#downloads

```

<img src="https://img-blog.csdnimg.cn/2dda502348004f998fee00de72aa2628.png" alt="在这里插入图片描述"/>

#### 1.2 安装MySQL

```
1). 卸载 centos 中预安装的 mysql
	
	rpm -qa | grep -i mysql
	
	rpm -e mysql-libs-5.1.71-1.el6.x86_64 --nodeps
	
2). 上传 mysql 的安装包
	
	alt + p -------> put  E:/test/MySQL-5.6.22-1.el6.i686.rpm-bundle.tar

3). 解压 mysql 的安装包 
	
	mkdir mysql
	
	tar -xvf MySQL-5.6.22-1.el6.i686.rpm-bundle.tar -C /root/mysql
	
4). 安装依赖包 
	
	yum -y install libaio.so.1 libgcc_s.so.1 libstdc++.so.6 libncurses.so.5 --setopt=protected_multilib=false
	
	yum  update libstdc++-4.4.7-4.el6.x86_64
	
5). 安装 mysql-client
	
	rpm -ivh MySQL-client-5.6.22-1.el6.i686.rpm

6). 安装 mysql-server
	
	rpm -ivh MySQL-server-5.6.22-1.el6.i686.rpm
	

```

#### 1.3 启动 MySQL 服务

```
service mysql start

service mysql stop

service mysql status

service mysql restart

```

#### 1.4 登录MySQL

```
mysql 安装完成之后, 会自动生成一个随机的密码, 并且保存在一个密码文件中 : /root/.mysql_secret

mysql -u root -p 

登录之后, 修改密码 :

set password = password('itcast');

授权远程访问 : 

grant all privileges on *.* to 'root' @'%' identified by 'itcast';
flush privileges;


```

### 2. 索引

#### 2.1 索引概述

MySQL官方对索引的定义为：索引（index）是帮助MySQL高效获取数据的数据结构（有序）。在数据之外，数据库系统还维护者满足特定查找算法的数据结构，这些数据结构以某种方式引用（指向）数据， 这样就可以在这些数据结构上实现高级查找算法，这种数据结构就是索引。如下面的<mark>示意图</mark>所示 :

<img src="https://img-blog.csdnimg.cn/5fd37078b7554326b3ba1c1ef7ae0ecb.png#pic_center" alt="在这里插入图片描述"/>

左边是数据表，一共有两列七条记录，最左边的是数据记录的物理地址（注意逻辑上相邻的记录在磁盘上也并不是一定物理相邻的）。为了加快Col2的查找，可以维护一个右边所示的二叉查找树，每个节点分别包含索引键值和一个指向对应数据记录物理地址的指针，这样就可以运用二叉查找快速获取到相应数据。

一般来说索引本身也很大，不可能全部存储在内存中，因此索引往往以索引文件的形式存储在磁盘上。索引是数据库中用来提高性能的最常用的工具。

#### 2.2 索引优势劣势

优势

1） 类似于书籍的目录索引，提高数据检索的效率，降低数据库的IO成本。

2） 通过索引列对数据进行排序，降低数据排序的成本，降低CPU的消耗。

劣势

1） 实际上索引也是一张表，该表中保存了主键与索引字段，并指向实体类的记录，所以索引列也是要占用空间的。

2） 虽然索引大大提高了查询效率，同时却也降低更新表的速度，如对表进行INSERT、UPDATE、DELETE。因为更新表时，MySQL 不仅要保存数据，还要保存一下索引文件每次更新添加了索引列的字段，都会调整因为更新所带来的键值变化后的索引信息。

#### 2.3 索引结构

索引是在MySQL的存储引擎层中实现的，而不是在服务器层实现的。所以每种存储引擎的索引都不一定完全相同，也不是所有的存储引擎都支持所有的索引类型的。MySQL目前提供了以下4种索引：
- BTREE 索引 ： 最常见的索引类型，大部分索引都支持 B 树索引。- HASH 索引：只有Memory引擎支持 ， 使用场景简单 。- R-tree 索引（空间索引）：空间索引是MyISAM引擎的一个特殊索引类型，主要用于地理空间数据类型，通常使用较少，不做特别介绍。- Full-text （全文索引） ：全文索引也是MyISAM的一个特殊索引类型，主要用于全文索引，InnoDB从Mysql5.6版本开始支持全文索引。
|索引|InnoDB引擎|MyISAM引擎|Memory引擎|
| :-----| :-----| :-----| :-----|
|BTREE索引|支持|支持|支持|
|HASH 索引|不支持|不支持|支持|
|R-tree 索引|不支持|支持|不支持|
|Full-text|5.6版本之后支持|支持|不支持|


我们平常所说的索引，如果没有特别指明，都是指B+树（多路搜索树，并不一定是二叉的）结构组织的索引。其中聚集索引、复合索引、前缀索引、唯一索引默认都是使用 B+tree 索引，统称为 索引。

##### 2.3.1 BTREE 结构

BTree又叫多路平衡搜索树，一颗m叉的BTree特性如下：
- 树中每个节点最多包含m个孩子。- 除根节点与叶子节点外，每个节点至少有[ceil(m/2)]个孩子。- 若根节点不是叶子节点，则至少有两个孩子。- 所有的叶子节点都在同一层。- 每个非叶子节点由n个key与n+1个指针组成，其中[ceil(m/2)-1] <= n <= m-1
以5叉BTree为例，key的数量：公式推导[ceil(m/2)-1] <= n <= m-1。所以 2 <= n <=4 。当n>4时，中间节点分裂到父节点，两边节点分裂。

插入 C N G A H E K Q M F W L T Z D P R X Y S 数据为例。

演变过程如下：

1). 插入前4个字母 C N G A

<img src="https://img-blog.csdnimg.cn/f248f7ae411e401bb1bcb1d8400ca124.png#pic_center" alt="在这里插入图片描述"/>

2). 插入H，n>4，中间元素G字母向上分裂到新的节点

<img src="https://img-blog.csdnimg.cn/981db950652a460aabc213ac223cd0cf.png" alt="在这里插入图片描述"/>

3). 插入E，K，Q不需要分裂 <img src="https://img-blog.csdnimg.cn/3d9de4e8a65b44e7a2813000a9046823.png#pic_center" alt="在这里插入图片描述"/>

4). 插入M，中间元素M字母向上分裂到父节点G

<img src="https://img-blog.csdnimg.cn/036aeb67b53b41a1b8e63fb2718239cd.png" alt="在这里插入图片描述"/>

5). 插入F，W，L，T不需要分裂

<img src="https://img-blog.csdnimg.cn/93c53cafdc5a4074a1bb6c148b70b631.png" alt="在这里插入图片描述"/>

6). 插入Z，中间元素T向上分裂到父节点中

<img src="https://img-blog.csdnimg.cn/4b53bbc79e41414f886497429d4bdeb8.png" alt="在这里插入图片描述"/>

7). 插入D，中间元素D向上分裂到父节点中。然后插入P，R，X，Y不需要分裂

<img src="https://img-blog.csdnimg.cn/aaa4e52f66854d91a84774e7b68cdfc5.png" alt="在这里插入图片描述"/>

8). 最后插入S，NPQR节点n>5，中间节点Q向上分裂，但分裂后父节点DGMT的n>5，中间节点M向上分裂

<img src="https://img-blog.csdnimg.cn/7237b7d1c5e54c6e81bd42ddfef49728.png" alt="在这里插入图片描述"/>

到此，该BTREE树就已经构建完成了， BTREE树 和 二叉树 相比， 查询数据的效率更高， 因为对于相同的数据量来说，BTREE的层级结构比二叉树小，因此搜索速度快。

##### 2.3.3 B+TREE 结构

B+Tree为BTree的变种，B+Tree与BTree的区别为：

1). n叉B+Tree最多含有n个key，而BTree最多含有n-1个key。

2). B+Tree的叶子节点保存所有的key信息，依key大小顺序排列。

3). 所有的非叶子节点都可以看作是key的索引部分。

<img src="https://img-blog.csdnimg.cn/0d206de6e08e46adaaea06de54994256.png#pic_center" alt="在这里插入图片描述"/>

由于B+Tree只有叶子节点保存key信息，查询任何key都要从root走到叶子。所以B+Tree的查询效率更加稳定。

##### 2.3.3 MySQL中的B+Tree

MySql索引数据结构对经典的B+Tree进行了优化。在原B+Tree的基础上，增加一个指向相邻叶子节点的链表指针，就形成了带有顺序指针的B+Tree，提高区间访问的性能。

MySQL中的 B+Tree 索引结构示意图:

<img src="https://img-blog.csdnimg.cn/027646bf6e1a416caa137e97875d7569.png#pic_center" alt="在这里插入图片描述"/>

#### 2.4 索引分类

1） 单值索引 ：即一个索引只包含单个列，一个表可以有多个单列索引

2） 唯一索引 ：索引列的值必须唯一，但允许有空值

3） 复合索引 ：即一个索引包含多个列

#### 2.5 索引语法

索引在创建表的时候，可以同时创建， 也可以随时增加新的索引。

准备环境:

```mysql
SELECT * from db1.t1;
SET GLOBAL tidb_multi_statement_mode='ON';
create database demo_01 if not EXISTS default charset=utf8mb4;

CREATE database mydb1

show databases;
use demo_01;

  
CREATE TABLE city (
  city_id int(11) NOT NULL AUTO_INCREMENT,
  city_name varchar(50) NOT NULL,
  country_id int(11) NOT NULL,
  PRIMARY KEY (city_id)
)

ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE country (
  country_id int(11) NOT NULL AUTO_INCREMENT,
  country_name varchar(100) NOT NULL,
  PRIMARY KEY (country_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into city (city_id, city_name, country_id) values(1,'西安',1);
insert into city (city_id, city_name, country_id) values(2,'NewYork',2);
insert into city (city_id, city_name, country_id) values(3,'北京',1);
insert into city (city_id, city_name, country_id) values(4,'上海',1);

insert into country (country_id, country_name) values(1,'China');
insert into country (country_id, country_name) values(2,'America');
insert into country (country_id, country_name) values(3,'Japan');
insert into country (country_id, country_name) values(4,'UK');
```

##### 2.5.1 创建索引

语法 ：

```
CREATE 	[UNIQUE|FULLTEXT|SPATIAL]  INDEX index_name 
[USING  index_type]
ON tbl_name(index_col_name,...)


index_col_name : column_name[(length)][ASC | DESC]

```

示例 ： 为city表中的city_name字段创建索引 ； <img src="https://img-blog.csdnimg.cn/d88eccb9351b4e698752249d5e90369f.png#pic_center" alt="在这里插入图片描述"/>



##### 2.5.2 查看索引

语法：

```
show index  from  table_name;

```

示例：查看city表中的索引信息；

<img src="https://img-blog.csdnimg.cn/f849251cde9943c8a1eee002c24f02f6.png#pic_center" alt="在这里插入图片描述"/>

<img src="https://img-blog.csdnimg.cn/7e79c2c9504948a7b5b1522f8785f488.png#pic_center" alt="在这里插入图片描述"/>

##### 2.5.3 删除索引

语法 ：

```
DROP  INDEX  index_name  ON  tbl_name;

```

示例 ： 想要删除city表上的索引idx_city_name，可以操作如下：

<img src="https://img-blog.csdnimg.cn/2cbfb8ec1f784fdba3a954079cf26979.png#pic_center" alt="在这里插入图片描述"/>

##### 2.5.4 ALTER命令

```
1). alter  table  tb_name  add  primary  key(column_list); 

	该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL
	
2). alter  table  tb_name  add  unique index_name(column_list);
	
	这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次）
	
3). alter  table  tb_name  add  index index_name(column_list);

	添加普通索引， 索引值可以出现多次。
	
4). alter  table  tb_name  add  fulltext  index_name(column_list);
	
	该语句指定了索引为FULLTEXT， 用于全文索引
	

```

#### 2.6 索引设计原则

 索引的设计可以遵循一些已有的原则，创建索引的时候请尽量考虑符合这些原则，便于提升索引的使用效率，更高效的使用索引。
-  对查询频次较高，且数据量比较大的表建立索引。 

-  - 索引字段的选择，最佳候选列应当从where子句的条件中提取，如果where子句中的组合比较多，那么应当挑选最常用、过滤效果最好的列的组合。 
	- - 使用唯一索引，区分度越高，使用索引的效率越高。 -  索引可以有效的提升查询数据的效率，但索引数量不是多多益善，索引越多，维护索引的代价自然也就水涨船高。对于插入、更新、删除等DML操作比较频繁的表来说，索引过多，会引入相当高的维护代价，降低DML操作的效率，增加相应操作的时间消耗。另外索引过多的话，MySQL也会犯选择困难病，虽然最终仍然会找到一个可用的索引，但无疑提高了选择的代价。 
	  - -  使用短索引，索引创建之后也是使用硬盘来存储的，因此提升索引访问的I/O效率，也可以提升总体的访问效率。假如构成索引的字段总长度比较短，那么在给定大小的存储块内可以存储更多的索引值，相应的可以有效的提升MySQL访问索引的I/O效率。  利用最左前缀，N个列组合而成的组合索引，那么相当于是创建了N个索引，如果查询时where子句中使用了组成该索引的前几个字段，那么这条查询SQL可以利用组合索引来提升查询效率。
	
-  创建复合索引:

	CREATE INDEX idx_name_email_status ON tb_seller(NAME,email,STATUS);

就相当于
	对name 创建索引 ;
	对name , email 创建了索引 ;
	对name , email, status 创建了索引 ;

### 3. 视图

#### 3.1 视图概述

 视图（View）是一种虚拟存在的表。视图并不在数据库中实际存在，行和列数据来自定义视图的查询中使用的表，并且是在使用视图时动态生成的。通俗的讲，视图就是一条SELECT语句执行后返回的结果集。所以我们在创建视图的时候，主要的工作就落在创建这条SQL查询语句上。

视图相对于普通的表的优势主要包括以下几项。
- 简单：使用视图的用户完全不需要关心后面对应的表的结构、关联条件和筛选条件，对用户来说已经是过滤好的复合条件的结果集。
- 安全：使用视图的用户只能访问他们被允许查询的结果集，对表的权限管理并不能限制到某个行某个列，但是通过视图就可以简单的实现。
- 数据独立：一旦视图的结构确定了，可以屏蔽表结构变化对用户的影响，源表增加列对视图没有影响；源表修改列名，则可以通过修改视图来解决，不会造成对访问者的影响。
#### 3.2 创建或者修改视图

创建视图的语法为：

```
CREATE [OR REPLACE] [ALGORITHM = {<!-- -->UNDEFINED | MERGE | TEMPTABLE}]

VIEW view_name [(column_list)]

AS select_statement

[WITH [CASCADED | LOCAL] CHECK OPTION]

```

修改视图的语法为：

```
ALTER [ALGORITHM = {<!-- -->UNDEFINED | MERGE | TEMPTABLE}]

VIEW view_name [(column_list)]

AS select_statement

[WITH [CASCADED | LOCAL] CHECK OPTION]

```

```
选项 : 
	WITH [CASCADED | LOCAL] CHECK OPTION 决定了是否允许更新数据使记录不再满足视图的条件。
	
	LOCAL ： 只要满足本视图的条件就可以更新。
	CASCADED ： 必须满足所有针对该视图的所有视图的条件才可以更新。 默认值.

```

示例 , 创建city_country_view视图 , 执行如下SQL :

```MYSQL
create or replace view city_country_view 
as 
select t.*,c.country_name from country c , city t where c.country_id = t.country_id;


```

查询视图 :

<img src="https://img-blog.csdnimg.cn/6678797659c4438d85cab2511bb229cb.png#pic_center" alt="在这里插入图片描述"/>

#### 3.3 查看视图

 从 MySQL 5.1 版本开始，使用 SHOW TABLES 命令的时候不仅显示表的名字，同时也会显示视图的名字，而不存在单独显示视图的 SHOW VIEWS 命令。

<img src="https://img-blog.csdnimg.cn/a302e8efe25742a9bfe45248bd80aefa.png#pic_center" alt="在这里插入图片描述"/>

同样，在使用 SHOW TABLE STATUS 命令的时候，不但可以显示表的信息，同时也可以显示视图的信息。

<img src="https://img-blog.csdnimg.cn/0c84299e6c4f4419942901c08ed9838a.png#pic_center" alt="在这里插入图片描述"/>

如果需要查询某个视图的定义，可以使用 SHOW CREATE VIEW 命令进行查看 ：

<img src="https://img-blog.csdnimg.cn/817f287a9ec44bb78647238c3ef3a2cb.png#pic_center" alt="在这里插入图片描述"/>

#### 3.4 删除视图

语法 :

```
DROP VIEW [IF EXISTS] view_name [, view_name] ...[RESTRICT | CASCADE]	

```

示例 , 删除视图city_country_view :

```MYSQL
DROP VIEW city_country_view ;

```

### 4. 存储过程和函数

#### 4.1 存储过程和函数概述

 存储过程和函数是 事先经过编译并存储在数据库中的一段 SQL 语句的集合，调用存储过程和函数可以简化应用开发人员的很多工作，减少数据在数据库和应用服务器之间的传输，对于提高数据处理的效率是有好处的。

 存储过程和函数的区别在于函数必须有返回值，而存储过程没有。

 函数 ： 是一个有返回值的过程 ；

 过程 ： 是一个没有返回值的函数 ；

#### 4.2 创建存储过程

```mysql
CREATE PROCEDURE procedure_name ([proc_parameter[,...]])
begin
	-- SQL语句
end ;

```

示例 ：

```mysql
delimiter $

create procedure pro_test1()
begin
	select 'Hello Mysql' ;
end$

delimiter ;

```

**<font color="red">知识小贴士</font>**

DELIMITER

 该关键字用来声明SQL语句的分隔符 , 告诉 MySQL 解释器，该段命令是否已经结束了，mysql是否可以执行了。默认情况下，delimiter是分号;。在命令行客户端中，如果有一行命令以分号结束，那么回车后，mysql将会执行该命令。

#### 4.3 调用存储过程

```mysql
call procedure_name() ;	

```

#### 4.4 查看存储过程

```mysql
-- 查询db_name数据库中的所有的存储过程
select name from mysql.proc where db='db_name';


-- 查询存储过程的状态信息
show procedure status;


-- 查询某个存储过程的定义
show create procedure test.pro_test1 \G;

```

#### 4.5 删除存储过程

```mysql
DROP PROCEDURE  [IF EXISTS] sp_name ；

```

#### 4.6 语法

存储过程是可以编程的，意味着可以使用变量，表达式，控制结构 ， 来完成比较复杂的功能。

##### 4.6.1 变量
- DECLARE
通过 DECLARE 可以定义一个局部变量，该变量的作用范围只能在 BEGIN…END 块中。

```mysql
DECLARE var_name[,...] type [DEFAULT value]

```

示例 :

```mysql
 delimiter $

 create procedure pro_test2() 
 begin 
 	declare num int default 5;
 	select num+ 10; 
 end$

 delimiter ; 

```
- SET
直接赋值使用 SET，可以赋常量或者赋表达式，具体语法如下：

```mysql
  SET var_name = expr [, var_name = expr] ...

```

示例 :

```mysql
  DELIMITER $
  
  CREATE  PROCEDURE pro_test3()
  BEGIN
  	DECLARE NAME VARCHAR(20);
  	SET NAME = 'MYSQL';
  	SELECT NAME ;
  END$
  
  DELIMITER ;

```

也可以通过select … into 方式进行赋值操作 :

```mysql
DELIMITER $

CREATE  PROCEDURE pro_test5()
BEGIN
	declare  countnum int;
	select count(*) into countnum from city;
	select countnum;
END$

DELIMITER ;

```

##### 4.6.2 if条件判断

语法结构 :

```mysql
if search_condition then statement_list

	[elseif search_condition then statement_list] ...
	
	[else statement_list]
	
end if;

```

需求：

```
根据定义的身高变量，判定当前身高的所属的身材类型 

	180 及以上 ----------> 身材高挑

	170 - 180  ---------> 标准身材

	170 以下  ----------> 一般身材

```

示例 :

```mysql
delimiter $

create procedure pro_test6()
begin
  declare  height  int  default  175; 
  declare  description  varchar(50);
  
  if  height >= 180  then
    set description = '身材高挑';
  elseif height >= 170 and height < 180  then
    set description = '标准身材';
  else
    set description = '一般身材';
  end if;
  
  select description ;
end$

delimiter ;

```

调用结果为 :

<img src="https://img-blog.csdnimg.cn/97db0b7d52ba4a5bb90dded8568ada00.png#pic_center" alt="在这里插入图片描述"/>

##### 4.6.3 传递参数

语法格式 :

```mysql
create procedure procedure_name([in/out/inout] 参数名   参数类型)
...


IN :   该参数可以作为输入，也就是需要调用方传入值 , 默认
OUT:   该参数作为输出，也就是该参数可以作为返回值
INOUT: 既可以作为输入参数，也可以作为输出参数

```

**IN - 输入**

需求 :

```
根据定义的身高变量，判定当前身高的所属的身材类型 

```

示例 :

```mysql
delimiter $

create procedure pro_test5(in height int)
begin
    declare description varchar(50) default '';
  if height >= 180 then
    set description='身材高挑';
  elseif height >= 170 and height < 180 then
    set description='标准身材';
  else
    set description='一般身材';
  end if;
  select concat('身高 ', height , '对应的身材类型为:',description);
end$

delimiter ;

```

**OUT-输出**

需求 :

```
根据传入的身高变量，获取当前身高的所属的身材类型  

```

示例:

```mysql
create procedure pro_test5(in height int , out description varchar(100))
begin
  if height >= 180 then
    set description='身材高挑';
  elseif height >= 170 and height < 180 then
    set description='标准身材';
  else
    set description='一般身材';
  end if;
end$	

```

调用:

```mysql
call pro_test5(168, @description)$

select @description$

```

<font color="red">**小知识** </font>

<font color="red">@description : 这种变量要在变量名称前面加上“@”符号，叫做用户会话变量，代表整个会话过程他都是有作用的，这个类似于全局变量一样。 </font>

<font color="red">@@global.sort_buffer_size : 这种在变量前加上 “@@” 符号, 叫做 系统变量 </font>

##### 4.6.4 case结构

语法结构 :

```mysql
方式一 : 

CASE case_value

  WHEN when_value THEN statement_list
  
  [WHEN when_value THEN statement_list] ...
  
  [ELSE statement_list]
  
END CASE;


方式二 : 

CASE

  WHEN search_condition THEN statement_list
  
  [WHEN search_condition THEN statement_list] ...
  
  [ELSE statement_list]
  
END CASE;


```

需求:

```
给定一个月份, 然后计算出所在的季度

```

示例 :

```mysql
delimiter $


create procedure pro_test9(month int)
begin
  declare result varchar(20);
  case 
    when month >= 1 and month <=3 then 
      set result = '第一季度';
    when month >= 4 and month <=6 then 
      set result = '第二季度';
    when month >= 7 and month <=9 then 
      set result = '第三季度';
    when month >= 10 and month <=12 then 
      set result = '第四季度';
  end case;
  
  select concat('您输入的月份为 :', month , ' , 该月份为 : ' , result) as content ;
  
end$


delimiter ;

```

##### 4.6.5 while循环

语法结构:

```mysql
while search_condition do

	statement_list
	
end while;

```

需求:

```
计算从1加到n的值

```

示例 :

```mysql
delimiter $

create procedure pro_test8(n int)
begin
  declare total int default 0;
  declare num int default 1;
  while num<=n do
    set total = total + num;
	set num = num + 1;
  end while;
  select total;
end$

delimiter ;

```

##### 4.6.6 repeat结构

有条件的循环控制语句, 当满足条件的时候退出循环 。while 是满足条件才执行，repeat 是满足条件就退出循环。

语法结构 :

```mysql
REPEAT

  statement_list

  UNTIL search_condition

END REPEAT;

```

需求:

```
计算从1加到n的值

```

示例 :

```mysql
delimiter $

create procedure pro_test10(n int)
begin
  declare total int default 0;
  
  repeat 
    set total = total + n;
    set n = n - 1;
    until n=0  
  end repeat;
  
  select total ;
  
end$


delimiter ;

```

##### 4.6.7 loop语句

LOOP 实现简单的循环，退出循环的条件需要使用其他的语句定义，通常可以使用 LEAVE 语句实现，具体语法如下：

```mysql
[begin_label:] LOOP

  statement_list

END LOOP [end_label]

```

如果不在 statement_list 中增加退出循环的语句，那么 LOOP 语句可以用来实现简单的死循环。

##### 4.6.8 leave语句

用来从标注的流程构造中退出，通常和 BEGIN … END 或者循环一起使用。下面是一个使用 LOOP 和 LEAVE 的简单例子 , 退出循环：

```mysql
delimiter $

CREATE PROCEDURE pro_test11(n int)
BEGIN
  declare total int default 0;
  
  ins: LOOP
    
    IF n <= 0 then
      leave ins;
    END IF;
    
    set total = total + n;
    set n = n - 1;
  	
  END LOOP ins;
  
  select total;
END$

delimiter ;

```

##### 4.6.9 游标/光标

游标是用来存储查询结果集的数据类型 , 在存储过程和函数中可以使用光标对结果集进行循环的处理。光标的使用包括光标的声明、OPEN、FETCH 和 CLOSE，其语法分别如下。

声明光标：

```mysql
DECLARE cursor_name CURSOR FOR select_statement ;

```

OPEN 光标：

```mysql
OPEN cursor_name ;

```

FETCH 光标：

```mysql
FETCH cursor_name INTO var_name [, var_name] ...

```

CLOSE 光标：

```mysql
CLOSE cursor_name ;

```

示例 :

初始化脚本:

```mysql
create table emp(
  id int(11) not null auto_increment ,
  name varchar(50) not null comment '姓名',
  age int(11) comment '年龄',
  salary int(11) comment '薪水',
  primary key(`id`)
)engine=innodb default charset=utf8 ;

insert into emp(id,name,age,salary) values(null,'金毛狮王',55,3800),(null,'白眉鹰王',60,4000),(null,'青翼蝠王',38,2800),(null,'紫衫龙王',42,1800);


```

```mysql
-- 查询emp表中数据, 并逐行获取进行展示
create procedure pro_test11()
begin
  declare e_id int(11);
  declare e_name varchar(50);
  declare e_age int(11);
  declare e_salary int(11);
  declare emp_result cursor for select * from emp;
  
  open emp_result;
  
  fetch emp_result into e_id,e_name,e_age,e_salary;
  select concat('id=',e_id , ', name=',e_name, ', age=', e_age, ', 薪资为: ',e_salary);
  
  fetch emp_result into e_id,e_name,e_age,e_salary;
  select concat('id=',e_id , ', name=',e_name, ', age=', e_age, ', 薪资为: ',e_salary);
  
  fetch emp_result into e_id,e_name,e_age,e_salary;
  select concat('id=',e_id , ', name=',e_name, ', age=', e_age, ', 薪资为: ',e_salary);
  
  fetch emp_result into e_id,e_name,e_age,e_salary;
  select concat('id=',e_id , ', name=',e_name, ', age=', e_age, ', 薪资为: ',e_salary);
  
  fetch emp_result into e_id,e_name,e_age,e_salary;
  select concat('id=',e_id , ', name=',e_name, ', age=', e_age, ', 薪资为: ',e_salary);
  
  close emp_result;
end$


```

通过循环结构 , 获取游标中的数据 :

```mysql
DELIMITER $

create procedure pro_test12()
begin
  DECLARE id int(11);
  DECLARE name varchar(50);
  DECLARE age int(11);
  DECLARE salary int(11);
  DECLARE has_data int default 1;
  
  DECLARE emp_result CURSOR FOR select * from emp;
  DECLARE EXIT HANDLER FOR NOT FOUND set has_data = 0;
  
  open emp_result;
  
  repeat
    fetch emp_result into id , name , age , salary;
    select concat('id为',id, ', name 为' ,name , ', age为 ' ,age , ', 薪水为: ', salary);
    until has_data = 0
  end repeat;
  
  close emp_result;
end$

DELIMITER ; 

```

#### 4.7 存储函数

语法结构:

```mysql
CREATE FUNCTION function_name([param type ... ]) 
RETURNS type 
BEGIN
	...
END;

```

mysql案例 :

定义一个存储过程, 请求满足条件的总记录数 ;

```mysql

delimiter $

create function count_city(countryId int)
returns int
begin
  declare cnum int ;
  
  select count(*) into cnum from city where country_id = countryId;
  
  return cnum;
end$

delimiter ;

```

调用:

```mysql
select count_city(1);
select count_city(2);
```

#### 4.8 存储函数 vs 存储过程

MySQL存储函数（自定义函数），函数一般用于计算和返回一个值，可以将经常需要使用的计算或功能写成一个函数。

存储函数和存储过程一样，都是在数据库中定义一些 SQL 语句的集合。

存储函数与存储过程的区别

**1.存储函数有且只有一个返回值，而存储过程可以有多个返回值，也可以没有返回值。**

**2.存储函数只能有输入参数，而且不能带in, 而存储过程可以有多个in,out,inout参数。**

**3.存储过程中的语句功能更强大，存储过程可以实现很复杂的业务逻辑，而函数有很多限制，如不能在函数中使用insert,update,delete,create等语句；**

**4.存储函数只完成查询的工作，可接受输入参数并返回一个结果，也就是函数实现的功能针对性比较强。**

**5.存储过程可以调用存储函数、但函数不能调用存储过程。**

**6.存储过程一般是作为一个独立的部分来执行(call调用)。而函数可以作为查询语句的一个部分来调用.**

```mysql
create function func_name ([param_name type[,...]])
returns type
[characteristic ...] 
begin
    routine_body
end;
```

参数说明：
（1）func_name ：存储函数的名称。
（2）param_name type：可选项，指定存储函数的参数。type参数用于指定存储函数的参数类型，该类型可以是MySQL数据库中所有支持的类型。
（3）RETURNS type：指定返回值的类型。
（4）characteristic：可选项，指定存储函数的特性。
（5）routine_body：SQL代码内容。

```mysql
create database mydb9_function;
-- 导入测试数据
use mydb9_function;
set global log_bin_trust_function_creators=TRUE; -- 信任子程序的创建者
 
-- 创建存储函数-没有输输入参数
drop function if exists myfunc1_emp;
 
delimiter $$
create function myfunc1_emp() returns int
begin
  declare cnt int default 0;
    select count(*) into  cnt from emp;
  return cnt;
end $$
delimiter ;
-- 调用存储函数
select myfunc1_emp();

```

```mysql

-- 创建存储过程-有输入参数
drop function if exists myfunc2_emp;
delimiter $$
create function myfunc2_emp(in_empno int) returns varchar(50)
begin
    declare out_name varchar(50);
    select ename into out_name from emp where  empno = in_empno;
    return out_name;
end $$
delimiter ;
select myfunc2_emp(1008);
```



### 5. 触发器

#### 5.1 介绍

触发器是与表有关的数据库对象，指在 insert/update/delete 之前或之后，触发并执行触发器中定义的SQL语句集合。触发器的这种特性可以协助应用在数据库端确保数据的完整性 , 日志记录 , 数据校验等操作 。

使用别名 OLD 和 NEW 来引用触发器中发生变化的记录内容，这与其他的数据库是相似的。现在触发器还只支持行级触发，不支持语句级触发。

|触发器类型|NEW 和 OLD的使用|
| :-----| :-----|
|INSERT 型触发器|NEW 表示将要或者已经新增的数据|
|UPDATE 型触发器|OLD 表示修改之前的数据 , NEW 表示将要或已经修改后的数据|
|DELETE 型触发器|OLD 表示将要或者已经删除的数据|


#### 5.2 创建触发器

语法结构 :

```mysql
create trigger trigger_name 

before/after insert/update/delete

on tbl_name 

[ for each row ]  -- 行级触发器

begin

	trigger_stmt ;

end;

```

示例

需求

```
通过触发器记录 emp 表的数据变更日志 , 包含增加, 修改 , 删除 ;

```

首先创建一张日志表 :

```mysql
create table emp_logs(
  id int(11) not null auto_increment,
  operation varchar(20) not null comment '操作类型, insert/update/delete',
  operate_time datetime not null comment '操作时间',
  operate_id int(11) not null comment '操作表的ID',
  operate_params varchar(500) comment '操作参数',
  primary key(`id`)
)engine=innodb default charset=utf8;

```

创建 insert 型触发器，完成插入数据时的日志记录 :

```mysql
DELIMITER $

create trigger emp_logs_insert_trigger
after insert 
on emp 
for each row 
begin
  insert into emp_logs (id,operation,operate_time,operate_id,operate_params) values(null,'insert',now(),new.id,concat('插入后(id:',new.id,', name:',new.name,', age:',new.age,', salary:',new.salary,')'));	
end $

DELIMITER ;

```

创建 update 型触发器，完成更新数据时的日志记录 :

```mysql
DELIMITER $

create trigger emp_logs_update_trigger
after update 
on emp 
for each row 
begin
  insert into emp_logs (id,operation,operate_time,operate_id,operate_params) values(null,'update',now(),new.id,concat('修改前(id:',old.id,', name:',old.name,', age:',old.age,', salary:',old.salary,') , 修改后(id',new.id, 'name:',new.name,', age:',new.age,', salary:',new.salary,')'));                                                                      
end $

DELIMITER ;

```

创建delete 行的触发器 , 完成删除数据时的日志记录 :

```mysql
DELIMITER $

create trigger emp_logs_delete_trigger
after delete 
on emp 
for each row 
begin
  insert into emp_logs (id,operation,operate_time,operate_id,operate_params) values(null,'delete',now(),old.id,concat('删除前(id:',old.id,', name:',old.name,', age:',old.age,', salary:',old.salary,')'));                                                                      
end $

DELIMITER ;

```

测试：

```mysql
insert into emp(id,name,age,salary) values(null, '光明左使',30,3500);
insert into emp(id,name,age,salary) values(null, '光明右使',33,3200);

update emp set age = 39 where id = 3;

delete from emp where id = 5;

```

#### 5.3 删除触发器

语法结构 :

```mysql
drop trigger [schema_name.]trigger_name

```

如果没有指定 schema_name，默认为当前数据库 。

#### 5.4 查看触发器

可以通过执行 SHOW TRIGGERS 命令查看触发器的状态、语法等信息。

语法结构 ：

```mysql
show triggers ；

```

operation,operate_time,operate_id,operate_params) values(null,‘update’,now(),new.id,concat(‘修改前(id:’,old.id,‘, name:’,old.name,‘, age:’,old.age,‘, salary:’,old.salary,‘) , 修改后(id’,new.id, ‘name:’,new.name,‘, age:’,new.age,‘, salary:’,new.salary,‘)’)); end $

DELIMITER ;

```mysql

创建delete 行的触发器 , 完成删除数据时的日志记录 : 

```sql
DELIMITER $

create trigger emp_logs_delete_trigger
after delete 
on emp 
for each row 
begin
  insert into emp_logs (id,operation,operate_time,operate_id,operate_params) values(null,'delete',now(),old.id,concat('删除前(id:',old.id,', name:',old.name,', age:',old.age,', salary:',old.salary,')'));                                                                      
end $

DELIMITER ;

```

测试：

```mysql
insert into emp(id,name,age,salary) values(null, '光明左使',30,3500);
insert into emp(id,name,age,salary) values(null, '光明右使',33,3200);

update emp set age = 39 where id = 3;

delete from emp where id = 5;

```

### 触发器笔记2

（Trigger）是 MySQL 中非常实用的一个功能，它可以在操作者对表进行「增删改」 之前（或之后）被触发，自动执行一段事先写好的 SQL 代码。

本教程带领大家在实践中学习，你将学到触发器在实际应用场景中的重要应用。

在这个教程中，你是「卡拉云银行」的程序员，你正在搭建一套银行客户管理系统。在这套系统中，你需要设置在`INSERT` 表之前检测操作者是否输入错误数据、在 `UPDATE` 时，记录操作者的行为 log ，以及在`DELETE` 时，判断删除的信息是否符合删除规则。 这三类操作都可以使用 MySQL 触发器来实现。

如果你正在数据库的基础上搭建一套数据库管理工具或企业内部工具，推荐你试试我开发的**[卡拉云](https://link.zhihu.com/?target=https%3A//kalacloud.com/)**，详情见后文。

本教程将带你一起实践的案例

- `BEFORE INSERT` ： 在插入数据前，检测插入数据是否符合业务逻辑，如不符合返回错误信息。
- `AFTER INSERT` ： 在表 A 创建新账户后，将创建成功信息自动写入表 B 中。
- `BEFORE UPDATE` ：在更新数据前，检测更新数据是否符合业务逻辑，如不符合返回错误信息。
- `AFTER INSERT` ：在更新数据后，将操作行为记录在 log 中
- `BEFORE DELETE` ：在删除数据前，检查是否有关联数据，如有，停止删除操作。
- `AFTER DELETE` ：删除表 A 信息后，自动删除表 B 中与表 A 相关联的信息。

#### **测试准备**

在开始之前，请确保您具备以下条件：

- 一台配置好的 Ubuntu 服务器，root 账号。
- 服务器上配置好 MySQL Server（配置 MySQL 请看**[MySQL安装](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-install-linux-apache-mysql-php-lamp-stack/)**及**[连接MySQL教程](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-allow-remote-access-to-mysql/)**）
- MySQL root 账号

#### **创建示例数据库**

我们先创建一个干净的示例数据库，方便大家可以跟随本教程一起实践。我们会在这个数据库中演示 MySQL 触发器的多种工作方式。

首先，以 root 身份登录到你的 MySQL 服务器：

```text
mysql -u root -p
```

出现提示时，请输入你 MySQL root 账号的密码，然后点击 `ENTER` 继续。看到 `mysql>` 提示后，运行以下命令，创建 `demo_kalacloud` 数据库：

```text
CREATE database demo_kalacloud;

Output
Query OK, 1 row affected (0.00 sec)
```

接下来，切换到新建的 `demo_kalacloud` 数据库：

```text
USE demo_kalacloud;

Output
Database changed
```

接着创建一个 `customers` 表。我们使用这个表记录银行客户的信息。这个表包括 `customer_id`，`customer_name`，和`level`。咱们先把客户分为两个级别：`BASIC`和`VIP`。

```text
create table customers(
customer_id BIGINT PRIMARY KEY, 
customer_name VARCHAR(50), 
level VARCHAR(50) 
) ENGINE=INNODB;

Output
Query OK, 0 rows affected (0.01 sec)
```

接着，我们向 `customers` 表中添加一些客户记录。

```text
Insert into customers (customer_id, customer_name, level )values('1','Jack Ma','BASIC');
Insert into customers (customer_id, customer_name, level )values('2','Robin Li','BASIC');
Insert into customers (customer_id, customer_name, level )values('3','Pony Ma','VIP');
```

分别运行三个 `INSERT` 命令后，命令行输出成功信息。

```text
Output
Query OK, 1 row affected (0.01 sec)
```

我们使用 `SELECT` 检查一下三条信息是否已经写入表中：

```text
Select * from customers;
```

![img](https://pic4.zhimg.com/80/v2-bab55cbb480ea4c27b3f99a7194b163b_1440w.jpg)

下面我们创建另一个表`customer_status`，用于保存 `customers` 表中客户的备注信息。

这个表包含 `customer_id` 和 `status_notes` 字段：

```text
Create table customer_status(customer_id BIGINT PRIMARY KEY, status_notes VARCHAR(50)) ENGINE=INNODB;
```

然后，我们再创建一个 `sales` 表，这个表与 `customer_id` 关联。保存与客户有关的销售数据。

```text
Create table sales(sales_id BIGINT PRIMARY KEY, customer_id BIGINT, sales_amount DOUBLE ) ENGINE=INNODB;

Output
Query OK, 0 rows affected (0.01 sec)
```

最后一步，我们再建一个 `audit_log` 表，用来记录操作员操作「卡拉云银行」客户管理系统时的操作行为。方便管理员在发生问题时，有 log 可查。

```text
Create table audit_log(log_id BIGINT PRIMARY KEY AUTO_INCREMENT, sales_id BIGINT, previous_amount DOUBLE, new_amount DOUBLE, updated_by VARCHAR(50), updated_on DATETIME ) ENGINE=INNODB;

Output
Query OK, 0 rows affected (0.02 sec)
```

至此，你作为「卡拉云银行」的程序员，已经把客户管理系统的`demo_kalacloud` 数据库和四张表建立完成。接下来，我们将对这个管理系统的关键节点增加对应的触发器。

扩展阅读：《**[如何使用 MySQL 慢查询日志进行性能优化 - Profiling、mysqldumpslow 实例详解](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-use-mysql-slow-query-log-profiling-mysqldumpslow/)**》

#### **1.`BEFORE INSERT`触发器创建和使用方法**

作为严谨的银行客户管理系统，对任何写入系统的数据都应该提前检测，以防止错误的信息被写进去。

在写入前检测数据这个功能，我们可以使用`BEFORE INSERT` 触发器来实现。

在操作者对 `sales` 表中的`sales_amount` 字段进行写操作时，系统将在写入（`INSERT`）前检查数据是否符合规范。

我们先来看一下，**创建触发器的基本语法**。

```mysql
DELIMITER //
CREATE TRIGGER [触发器的名字]
[触发器执行时机] [触发器监测的对象]
ON [表名]
FOR EACH ROW [触发器主体代码]//
DELIMITER ;
```

触发器的结构包括：

- `DELIMITER //`：MySQL 默认分隔符是`;` 但在触发器中，我们使用 `//` 表示触发器的开始与结束。
- `[触发器的名字]`：这里填写触发器的名字
- `[触发器执行时机]`：这里设置触发器是在关键动作执行之前触发，还是执行之后触发。
- `[触发器监测的对象]`：触发器可以监测 `INSERT`、`UPDATE`、`DELETE` 的操作，当监测的命令对触发器关联的表进行操作时，触发器就被激活了。
- `[表名]`：将这个触发器与数据库中的表进行关联，触发器定义在表上，也附着在表上，如果这个表被删除了，那么这个触发器也随之被删除。
- `FOR EACH ROW`：这句表示只要满足触发器触发条件，触发器都会被执行，也就是说带上这个参数后，触发器将监测每一行对关联表操作的代码，一旦符合条件，触发器就会被触发。
- `[触发器主体代码]`：这里是当满足触发条件后，被触发执行的代码主体。这里可以是一句 SQL 语句，也可以是多行命令。如果是多行命令，那么这些命令要写在 `BEGIN...END` 之间。

**注：**在创建触发器主体时，还可以使用`OLD`和`NEW` 来获取 SQL 执行`INSERT`，`UPDATE`和`DELETE` 操作前后的写入数据。这里没看明白没关系，我们将会在接下来的实践中，展开讲解。



现在，我们来创建第一个触发器，`BEFORE INSERT` （在执行 `insert` 之前，执行触发器）。这个触发器用于监测操作者在写入 `sales` 表中的 `sales_amount` 值时，这个值是否大于 `10000` ，如果大于，那么返回错误信息进行报错。

登录 MySQL Server 后，我们创建一个触发器：

```mysql
DELIMITER //
CREATE TRIGGER validate_sales_amount
BEFORE INSERT
ON sales
FOR EACH ROW
IF NEW.sales_amount>10000 THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = "你输入的销售总额超过 10000 元。";
END IF//
DELIMITER ;
```

上面这段代码中，我们使用`IF...THEN...END IF` 来创建一个监测 `INSERT` 语句写入的值是否在限定的范围内的触发器。

这个触发器的功能时监测 `INSERT` 在写入`sales_amount` 值时，这个新增的（`NEW`）值是否符合条件（ `> 10000`）。

当操作员录入一个超过 10000 的数字，会返回如下错误信息：

```mysql
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = '你输入的销售总额超过 10000 元。';
```

我们来试试看，看看触发器是否已启用。

我们向 `sales_amount` 中插入一条 `11000` 的值。

```mysql
Insert into sales(sales_id, customer_id, sales_amount) values('1','1','11000');
```

![img](https://pic1.zhimg.com/80/v2-5beebf9986f2afb2cbe6076fa12a075c_1440w.jpg)

命令行返回错误信息，这就是我们刚刚创建触发器时，填入的错误信息。与我们的设置一致。

下面我们 `insert` 一个值小于 `10000` 的数字：

```mysql
Insert into  sales(sales_id, customer_id, sales_amount) values('1','1','7700');
```

输入值为 `7700` 小于设定的 `10000` ，`insert` 命令执行成功。

```mysql
Output
Query OK, 1 row affected (0.01 sec)
```

我们调出 `sales` 表，看看是否插入成功：

```mysql
Select * from sales;
```

输出确认数据在表中：

![确认数据在表中]]([https://img-blog.csdnimg.cn/img_convert/41b92d1198e5f7d227e108a25ff144c2.png](https://link.zhihu.com/?target=https%3A//img-blog.csdnimg.cn/img_convert/41b92d1198e5f7d227e108a25ff144c2.png))

通过这张表，我们可以看到，7700 已经插入到表中。

刚刚我们演示了在执行 `insert` 命令前，检测某个值是否符合设定，接着我们来看在执行 `insert` 之后，使用触发器将不同的值保存到不同的表中。

**扩展阅读：**《**[如何在两台服务器之间迁移 MySQL / MariaDB 数据库 阿里云腾讯云迁移案例](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-migrate-a-mysql-database-between-two-servers-aliyun-tencentyun/)**》

#### **2.`AFTER INSERT`触发器创建和使用方法**

接着我们讲解 `AFTER INSERT` ，触发器在监测到我们成功执行了 `INSERT` 命令后，再执行触发器中设置好的代码。

例如：在银行账户系统中，当我们新建一个账户后，我们将创建成功信息写入对应的 `customer_status` 表中。

在这个案例中，你作为「卡拉云银行」的程序员，现在要创建一个`AFTER INSERT`触发器，在创建新客户账户后，将成功信息写入`customer_status` 表中

要创建`AFTER INSERT`触发器，请输入以下命令：

```mysql
DELIMITER //
CREATE TRIGGER customer_status_records
AFTER INSERT
ON customers
FOR EACH ROW
Insert into customer_status(customer_id, status_notes) VALUES(NEW.customer_id, '账户创建成功')//
DELIMITER ;

Output
Query OK, 0 rows affected (0.00 sec)
```

这个触发器在操作者向 `customers` 表中 `INSERT` 新客户信息后，再向 `customer_status` 表对应的行中写入成功信息。

现在我们 `INSERT` 一条信息，看看触发器是否已启用：

```mysql
Insert into customers (customer_id, customer_name, level )values('4','Xing Wang','VIP');

Output
Query OK, 1 row affected (0.01 sec)
```

记录 `INSERT` 成功，接着我们来检查`customer_status`表中是否写入了对应的成功数据。

```mysql
Select * from customer_status;
```

![img](https://pic1.zhimg.com/80/v2-916edfe532ee20dbfac494378fc23044_1440w.jpg)

这里可以看到，我们向 `customers` 表插入了一个`customer_id` 为 `4` 的新用户 ，随后，触发器根据代码自动向`customer_status` 表中也插入了一个 `customer_id` 为 `4` 的开户成功信息。

`AFTER INSERT` 特别适合这种状态变更的关联写入操作。比如开户、暂停、注销等各类状态变更。

到这里，触发器在`INSERT`执行前、后的应用，我们已经讲完了，接着我们来讲 `UPDATE` 触发器。

**扩展阅读：**《**[MySQL 配置文件 my.cnf / my.ini 逐行详解](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-edit-mysql-configuration-file-my-cnf-ini/)**》

#### **3.`BEFORE UPDATE`触发器创建和使用方法**

`BEFORE UPDATE`触发器与`BEFORE INSERT` 触发器非常类似，我们可以使用`BEFORE UPDATE` 触发器在更新数据之前，先做一次业务逻辑检测，避免发生误操作。

刚刚我们创建示例数据库时，创建了两个级别的客户，VIP 和 BASIC 级别。卡拉云银行的客户一旦升级至 VIP，就不能再降级至 BASIC 级别了。

我们使用 `BEFORE UPDATE` 来贯彻这一规则，这个触发器将在 `UPDATE` 语句执行之前，先判断是否为降级行为，如果是，则输出报错信息。

我们来创建这个触发器：

```mysql
DELIMITER //
CREATE TRIGGER validate_customer_level
BEFORE UPDATE
ON customers
FOR EACH ROW
IF OLD.level='VIP' THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'VIP 级别客户不能降级为普通级别客户';
END IF //
DELIMITER ;
```

我们可以使用 `OLD` 来获取执行 `UPDATE` 命令前，客户的 `level` 值。同样，我们使用该`IF...THEN...END IF`语句来对 `level` 值是否符合规则进行判断。

我们先来查看一下 `customers` 表中的数据。

```text
select * from customers;
```

![img](https://pic4.zhimg.com/80/v2-067a17493e002ea391528a3b62ce72cf_1440w.jpg)

好，我们选一个已经是 VIP 级别的客户，对他进行降级操作，看看我们的触发器是否能够正确执行。

接下来，运行以下 SQL 命令，试试能不能将 `customer_id` 为 `3` 的 VIP 客户降级成 `BASIC` 客户：

```mysql
Update customers set level='BASIC' where customer_id='3';
```

执行代码后，命令行返回错误信息：

![img](https://pic3.zhimg.com/80/v2-b7461bd38259720f54b9b496c01b7436_1440w.jpg)

这说明我们刚刚设置的触发器已经起作用了。

接着我们来试试，对一个`BASIC`级别的客户运行相同的命令，看看能不能把他升级到`VIP`级别：

```mysql
Update customers set level='VIP' where customer_id='2';
```

执行成功：

```text
Output
Rows matched: 1  Changed: 1  Warnings: 0
```

我们再来看一下 `customers` 表中的数据情况：

```mysql
select * from customers;
```

![img](https://pic2.zhimg.com/80/v2-dc29a179942afcb01350ea4db61db3e9_1440w.jpg)

可以看到刚才 `customer_id` 为 `2` 的 `BASIC` 客户已经升级为 `VIP` 客户。

`BEFORE UPDATE` 触发器用于在更新数据前进行确认，很好的守护了系统的业务规则。接着我们来看看 `AFTER UPDATE` 在客户管理系统中的应用。

扩展阅读：《**[MySQL Workbench 操作 MySQL / MariaDB 数据库中文指南](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/mysql-workbench-tutorial/)**》

#### **4.`AFTER INSERT`触发器创建和使用方法**

本节我们来演示 `AFTER UPDATE` 在实际中的应用。`AFTER UPDATE` **多用于 log 记录**，在管理系统多操作者使用的环境中，管理员需要设置操作 log 记录，以便在出问题时，可以查看操作者对表编辑的操作，可追根溯源。

我们先来创建一个对 `sales` 表操作的 log 记录触发器。

当操作者对 `sales` 表进行修改后，操作记录会被写入 `audit_log` 表中。

触发器将监测用户 ID 、更新前的销售总额、更新后的销售总额、操作者 ID、修改时间等信息，作为 log 存入 `audit_log` 表中。

使用以下命令建立这个 log 记录触发器：

```mysql
DELIMITER //
CREATE TRIGGER log_sales_updates
AFTER UPDATE
ON sales
FOR EACH ROW
Insert into audit_log(sales_id, previous_amount, new_amount, updated_by, updated_on) VALUES (NEW.sales_id,OLD.sales_amount, NEW.sales_amount,(SELECT USER()), NOW() )//
DELIMITER ;
```

当操作者对 `sales` 表中的一条客户信息进行 `UPDATE` 操作时，触发器会在`UPDATE`操作之后，将操作行为记录在 `audit_log` 中。包括 `sales_id` ，修改 `sales_amount` 值的前后变化。

销售总额的变化是审计的关键数据，所以要把它记录在 `audit_log` 中。使用`OLD` 来获取更新前的 `sales_amount` 值，使用 `NEW` 来获取更新后的值。

另外我们还要记录修改 `sales` 表的操作者信息及操作时间。

你可以使用 `SELECT USER()` 来检测当前操作用户的账号，用 `NOW()` 语句抓去当前服务器日期和时间。

为了测试这个触发器，我们先在 `sales` 表中创建一条信息记录：

```mysql
Insert into sales(sales_id, customer_id, sales_amount) values('5', '2','8000');

Output
Query OK, 1 row affected (0.00 sec)
```

接下来，我们来更新这条记录：

```mysql
Update sales set sales_amount='9000' where sales_id='5';
```

您将看到以下输出：

```mysql
Output
Rows matched: 1  Changed: 1  Warnings: 0
```

理论上，我们更新了 `sales` 表后，触发器应该触发了操作，将我们刚刚的修改记录到了`audit_log` 表中。我们用以下命令，看看`audit_log` 表中是否已经有记录了。

```mysql
Select * from audit_log;
```

如下表，触发器更新了`audit_log` 表，表中包含了`sales_amount` 更新前的旧值和更新后的新值。

![img](https://pic1.zhimg.com/80/v2-bf68f2c26786b920cd2dad738f05c6ac_1440w.jpg)

至此，使用 `AFTER UPDATE` 制作的 log 自动记录触发器就完成了。

下一节，我们来学习 `DELETE` 相关的触发器。

扩展阅读：《**[如何查看 MySQL 数据库、表、索引容量大小？找到占用空间最大的表](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-get-the-sizes-of-the-tables-of-a-mysql-database/)**》

#### **5.`BEFORE DELETE`触发器创建和使用方法**

`BEFORE DELETE`触发器会在`DELETE`语句执行之前调用。

这些类型的触发器通常用于在不同的相关表上强制执行参照完整性。

`BEFORE DELETE` 的应用场景通常是确保有关联的数据不被错误的误删除掉。

例如：`sales` 表通过`customer_id` 与`customers`表相关联。如果操作者删除了`customers` 表中的一条数据，那么 `sales` 表中某些数据就失去了关联线索。

为了避免这种情况的发生，我们需要创建一个 `BEFORE DELETE`触发器，防止记录被误删除。

```mysql
DELIMITER //
CREATE TRIGGER validate_related_records
BEFORE DELETE
ON customers
FOR EACH ROW
IF OLD.customer_id in (select customer_id from sales) THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = '这位客户有相关联的销售记录，不能删除。';
END IF//
DELIMITER ;
```

现在，我们试着删除有销售关联信息的客户：

```mysql
Delete from customers where customer_id='2';
```

所以，你会看到以下输出：

![img](https://pic3.zhimg.com/80/v2-f2700b4a9abd51ab60d1e08ba7cf63c2_1440w.jpg)

这个触发器做到了先检测 `sales` 是否与正要被删除的 `customers` 表中的数据有关联，防止有关联信息的数据被误删除。

不过有时候，我们需要删除主数据后，再让系统自动帮我们删除与之相关联的其他所有数据。这时，我们就要用到 `AFTER DELETE` 这个触发器了。

扩展阅读：《**[在 MySQL 中 DATETIME 和 TIMESTAMP 时间类型的区别及使用场景 - 实战案例讲解](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/difference-between-mysql-datetime-and-timestamp-datatypes/)**》

#### **6.`AFTER DELETE`触发器创建和使用方法**

接着说说 `AFTER DELETE` ，一旦记录被成功删除，这个触发器就会被激活。

这个触发器在实际场景用的应用也比较广泛。比如银行系统中的升级降级操作，当客户花掉自己的账户积分后，激活触发器，触发器可以判断剩余积分是否满足客户当前等级，如果不满足，自动做降级操作。

`AFTER DELETE`触发器的另一个用途是在删除主表中的数据后，与这个主表关联的数据，一起自动删除。

我们来看一下这个触发器如何创建：

```mysql
DELIMITER //
CREATE TRIGGER delete_related_info
AFTER DELETE
ON sales
FOR EACH ROW
Delete from customers where customer_id=OLD.customer_id;//
DELIMITER ;
```

接下来，我们来试试这个触发器。删除销售记录中 `customer_id` 为 `2` 的销售记录：

```mysql
Delete from sales where customer_id='2';

Output
Query OK, 1 row affected (0.00 sec)
```

接着我们检查以下 `customers` 表中的关联信息是否一起自动删除：

```mysql
Select * from customers where customer_id='2';
```

命令行会返回 `Empty Set` 的结果，我们刚刚删除了 `sales` 表中的信息后，`customers` 表中的关联信息也被一起删除了。

![img](https://pic4.zhimg.com/80/v2-7eb78fbd301fd943f751455c740c59c7_1440w.jpg)

以上就是 MySQL 触发器的六种使用方式和对应的场景。

扩展阅读：《**[最好用的 10 款 MySQL / MariaDB 管理工具横向测评 - 免费和付费到底怎么选?](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/best-mysql-gui-tools/)**》

#### **7.查看触发器**

##### **（1）直接查看触发器**

当我们想查看数据库中的触发器有哪些时，可用以下命令：

```mysql
SHOW TRIGGERS;
```

后面加上 `\G` 是触发器列表竖排列：

```mysql
SHOW TRIGGERS \G
```

![img](https://pic2.zhimg.com/80/v2-1c1e6bcf43e4937f21eeb1f3f04ce005_1440w.jpg)

刚刚我们创建的触发器都罗列在这个列表当中了。

##### **（2）在 triggers 表中查看触发器信息**

在 MySQL Server 中，数据库 `information_schema` 的 `triggers` 表中存着所有触发器的信息。所有我们可以通过 `SELECT` 来查看。

```mysql
SELECT * FROM information_schema.triggers WHERE trigger_name= '触发器名称';
```

当然，也可以不指定触发器名称，来查看所有。

```mysql
SELECT * FROM information_schema.triggers \G
```

扩展阅读：《**[如何在 MySQL / MariaDB 中查询数据库中带有某个字段/列名的所有表名](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/find-all-tables-with-specific-column-names-in-mysql/)**》

#### **8.删除触发器**

最后，咱们来说说如何删除触发器。删除命令也很简单，`Drop trigger 触发器名字` 即可。

```mysql
Drop trigger [触发器名称];
```

例如，咱们把刚刚创建的最后一个触发器删掉：

```mysql
Drop trigger delete_related_info;

Output
Query OK, 0 rows affected (0.00 sec)
```

特别提示：我们不能对已经创建好的触发器进行修改。如果你想修改，只能先删除，再重新创建。

扩展阅读：《**[MySQL / MariaDB 中如何存储图片 BLOB 数据类型详解](https://link.zhihu.com/?target=https%3A//kalacloud.com/blog/how-to-use-the-mysql-blob-data-type-to-store-images-with-php-or-kalacloud/)**》

#### **9.总结**

在本教程中，我们展示了触发器的六种形式，即在`INSERT`、`DELETE`、`UPDATE` 执行前或后执行触发器，以及对应的六个实战案例。

- `BEFORE INSERT` ： 在插入数据前，检测插入数据是否符合业务逻辑，如不符合返回错误信息。
- `AFTER INSERT` ： 在表 A 创建新账户后，将创建成功信息自动写入表 B 中。
- `BEFORE UPDATE` ：在更新数据前，检测更新数据是否符合业务逻辑，如不符合返回错误信息。
- `AFTER INSERT` ：在更新数据后，将操作行为记录在 log 中
- `BEFORE DELETE` ：在删除数据前，检查是否有关联数据，如有，停止删除操作。
- `AFTER DELETE` ：删除表 A 信息后，自动删除表 B 中与表 A 相关联的信息。

# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125410648