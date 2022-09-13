# sqlplus 命令登录 Oracle数据库的多种方法

 更新时间：2021年09月22日 11:13:55  作者：越努力越幸运再努力  

这篇文章主要介绍了sqlplus 命令登录 Oracle数据库的两种方法,方式一通过sql*plus 命令窗口，方式2：通过 cmd 窗口，每种方式给大家介绍的非常详细，需要的朋友可以参考下

# 目录

- [1 概述](https://www.jb51.net/article/223573.htm#_label0)

- [2 三种登录方法](https://www.jb51.net/article/223573.htm#_label1)

- - [2.1 sqlplus / as sysdba](https://www.jb51.net/article/223573.htm#_lab2_1_0)
  - [2.2 sqlplus username/password@ip:port/sid](https://www.jb51.net/article/223573.htm#_lab2_1_1)
  - [2.3 sqlplus /nolog](https://www.jb51.net/article/223573.htm#_lab2_1_2)

- [3 备选命令](https://www.jb51.net/article/223573.htm#_label2)




## 1 概述

- sql*plus 通过命令行登录 Oracle 数据库有两种方法
- 方式1：通过 **sql\*plus 命令窗口**（下载 Oracle 时，自带）

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202109/2021092211093933.png)

- 方式2：通过 **cmd 窗口**（`即使忘记了密码，也可以登录`）

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202109/2021092211093934.png) 



## 2 三种登录方法

```
方式1：``> sqlplus / ``as` `sysdba``方式2：``> sqlplus username/``password``@ip:port/sid``> sqlplus username/``password``@orcl ``-- 简写（前提：配置了 TNS），以下同``方式3：（推荐）``> sqlplus /nolog``> conn username/``password``@ip:port/sid
```

若配置了 **“TNS 监听”**，也可有简写的方式

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202109/2021092211094035.png)



### 2.1 sqlplus / as sysdba

```
> sqlplus / ``as` `sysdba
```

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202109/2021092211094036.png)



### 2.2 sqlplus username/password@ip:port/sid

```
> sqlplus scott/scott@orcl ``-- 简写``>``> sqlplus username/``password``@ip:port/sid ``-- 全称
```

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202109/2021092211094037.png)



### 2.3 sqlplus /nolog

- 先以 **无日志** 的方式登录，暂不连接 **数据库**
- 好处：避免登录的 **用户名、密码 泄露**

```
> sqlplus /nolog``> conn scott/scott@orcl
```

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202109/2021092211094038.png)



## 3 备选命令