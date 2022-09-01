# MySQL安装

系统：Mac OS 10.12

MySQL：5.7.15

**前言：**

安装mysql有两种方式：1为官方下载dmg安装包。2为使用brew进行安装。

**安装步骤：**

## 一、官方下载dmg安装包进行安装

1、登陆官网下载

https://downloads.mysql.com/archives/community/

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112015632369-658606563.png)

2、解压出pkg文件

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112021126760-1316383060.png)

3、安装

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112021149244-449090488.png)

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112021158306-295705897.png)

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112021223244-1952874972.png)

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112021313572-1471559485.png)

安装完成。

4、测试

▲验证是否安装成功，进入/usr/local/mysql/bin/，用ls命令查看是否有mysql

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112024504994-564741458.png)

▲启动mysql的服务，输入以下命令：

```
sudo /usr/local/mysql/support-files/mysql.server start 
```

注意，start为启动，那么stop就是停止，全命令如下：

```
sudo /usr/local/mysql/support-files/mysql.server stop
```

▲修改mysql初始化密码

避免服务已经启动，所以第一步先关闭mysql的服务

```
sudo /usr/local/mysql/support-files/mysql.server stop
```

开启安全模式启动mysql，这种方式可以不用输入默认密码

```
sudo /usr/local/mysql/bin/mysqld_safe --skip-grant-tables
```

出现如下画面是启动成功

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112025955556-157240274.png)

此时，关闭这个终端窗口，再重新启动一个

进入/usr/local/mysql/bin/，以root身份启动mysql

```
./mysql -u root
```

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112030211369-1233431346.png)

输入以下命令修改密码，其中PASSWORD里面就是要设置的密码

```
UPDATE mysql.user SET authentication_string=PASSWORD('root') WHERE User='root';
```

```
FLUSH PRIVILEGES;
```

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112030451228-1201238217.png)

退出mysql，输入

```
quit;
```

停止mysql服务

```
sudo /usr/local/mysql/support-files/mysql.server stop
```

再启动mysql服务

```
sudo /usr/local/mysql/support-files/mysql.server start
```

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112030702056-743541117.png)

使用以下命令进入mysql

```
./mysql -u root -p
```

 此时，要求输入密码，就是前面设置的root密码

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112030827510-208344692.png)

再测试是否正常

```
show databases; 
```

此时，如果出现：ERROR 1820 (HY000): You must reset your password using ALTER USER statement before executing this statement.的错误提示时，需要再设置一次密码

```
SET PASSWORD = PASSWORD('root');
```

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112031309525-2077952956.png)

再测试是否正常

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112031351291-2053243565.png)

一切正常。

5、优化使用

▲通过上面的操作，每次都要进入到mysql的目录上进行启动，那么有两种方式alias和ln进行设置，ln其实不推荐，路径时很大的问题。推荐使用alias。

```
alias mysql=/usr/local/mysql/bin/mysql
```

6、后话

其实对比发现，用dmg安转包安装的有很多好处，比如开机自动启动这些，全部都有gui工具使用

在系统的偏好设置上如下：

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112034645494-1582023783.png)

![img](https://images2015.cnblogs.com/blog/417876/201701/417876-20170112034707525-1143536342.png)

所以，非常推荐使用此方法安装在开发环境。

而对于linux下，命令行操作时首选。



## 二、使用brew命令安装mysql

1、直接输入

```
brew install mysql
```

2、其余步骤参考上述，基本一致。

参考：[http://blog.neten.de/posts/2014/01/27/install-mysql-using-homebrew/ ](http://blog.neten.de/posts/2014/01/27/install-mysql-using-homebrew/)

# MySQL实战常用命令大总结

## 一、MySQL 常用命令

### 1.查看当前所有的数据库

```
show databases;
```

### 2.打开指定的库

```
use 库名;
```

### 3.查看当前库的所有表

```
show tables;
```

### 4.查看指定库的所有数据表

show tables from 库名;

### 5.创建表

create table 表名(
   列名 列类型,
   列名 列类型
   ...
);

### 6.查看表结构

desc 表名;

### 7.查看服务器版本

（1）方式一：登录到mysql服务端

select version();
（2）没有登录到mysql服务端

mysql --version
或

mysql -V

## 二、MySQL语法规范

1.不区分大小写，但是建议关键字大写，表名、列表小写

2.每条命令用分号结尾

3.每天命令根据需要可以缩进或换行

 

## 三、基础查询

### 1.查询表中单个字段

SELECT last_name FROM employees;

### 2.查询表中多个字段

SELECT last_name,salary,email FROM employees;

### 3.查询表中所有字段

SELECT * FROM employees;

### 4.查询常量值

SELECT 100;
SELECT 'john';

### 5.查询表达式

SELECT 100%98;

### 6.取别名

（1）方式一：使用AS

SELECT last_name AS xing, first_name AS ming FROM employees;
（2）方式二：使用空格

SELECT last_name xing, first_name ming FROM employees;

### 7.去重

SELECT DISTINCT department_id FROM employees;

### 8.加号+

（1）两个操作数都为数值型，则做加法运算

SELECT 100+90;
（2）一方为数值型，一方为字符型，则试图将字符型转为数值型，如果转成功，则做加法运算，否则将字符型转为0

SELECT '123'+90;
SELECT 'john'+90;
（3）有一方为null, 结果肯定为null

SELECT null+90;
9. ### 拼接 

#SELECT CONCAT(a, b, c) AS 结果
SELECT CONCAT(last_name, ',', first_name, ',' IF_NULL(comission_pct, 0)) as result FROM employees;

## 四、条件查询

语法：

select 查询列表 from 表名 where 筛选条件;

### 1.条件运算符：>、<、= 、!= 、<>、>=、<=

（1）查询工资大于12000的员工信息

SELECT * FROM employees WHERE salary > 12000;
（2）查询部门编号不等于90号的员工名和部门编号

SELECT last_name, department_id FROM employees WHERE department_id != 90;
或

SELECT last_name, department_id FROM employees WHERE department_id <> 90;

### 2.逻辑运算符：&&、 ||、 !、and、or 、not

（1）查询工资在1000到2000之间的员工名、工资以及奖金

SELECT last_name, salary, commision_pct FROM employees WHERE salary >= 10000 and salary <= 20000;
（2）查询部门编号不是在90到110 之间，或者工资高于15000的员工信息 

SELECT * FROM employees WHERE department_id < 90 OR department_id > 110 OR salary >15000;
或

SELECT * FROM employees WHERE NOT (department_id >= 90 AND department_id <= 110) OR salary > 15000;

### 3.模糊查询：like、between and、in、is null、is not null

（1）查询员工名中包含字符a的员工信息（百分号）

SELECT * FROM employees WHERE last_name LIKE '%a%';
（2）查询员工名中第三个字符为e，第五个字符为a的员工名和工资（下划线）

SELECT last_name, salary FROM employees WHERE last_name LIKE '__e_a%';
（3）查询员工名中第二个字符为下划线的员工名（斜杠转义）

SELECT last_name FROM employees WHERE last_name LIKE '_\_%';
或

SELECT last_name FROM employees WHERE LIKE '_$_%' ESCAPE '$';
（4）查询员工编号在100-120之间的员工信息

SELECT * FROM employees WHERE employee_id BETWEEN 100 and 200;
#等价于下面的
SELECT * FROM employees WHERE employee_id >= 100 AND employee_id <= 200;
（5）查询工资不在8000-17000的员工的姓名和工资， 按工资降序

SELECT last_name, salary FROM employees WHERE salary NOT BETWEEN 8000 AND 17000;
（6）查询员工的工种编号是IT_PROG、AD_VP、AD_PRES中的一个员工名和工种编号

SELECT last_name, job_id FROM employees WHERE job_id IN (IT_PROG, AD_VP, AD_PRES);
#等同于下面
SELECT last_name, job_id FROM employees WHERE job_id = 'IT_PROG' OR job_id = 'AD_VP' OR job_id = 'AD_PRES';


（7） 查询没有奖金的员工名和奖金率

SELECT last_name, commision_pct FROM employees WHERE commission_pct IS NULL;

## 五、排序查询

语法：select 查询列表 from 表名 【where筛选条件】order by 排序列表 【asc|desc】

1.查询员工工资信息，要求从高到低排序

SELECT * FROM employees ORDER BY salary DESC;
2.查询部门编号>=90的员工信息，按入职时间的先后顺序【添加筛选条件】

 SELECT * FROM employees WHERE department_id >= 90 ORDER BY hiredate ASC;
3.按年薪的高低显示员工的信息和年薪【按表达式排序】

SELECT *, salary*12(1+IFNULL(commission_pct, 0)) AS year_salary ORDER BY year_salary DESC;
4.按姓名的长度显示员工的姓名和工资【按函数排序】

SELECT LENGTH(last_name) AS name_length, last_name, salary FROM employees ORDER BY name_length DESC;

## 六、常见函数

### 1.字符函数

（1）length 获取参数值的字节个数

SELECT LENGTH('john'); #4
SELECT LENGTH('张三丰hahaha')； #15，一个中文占3个字节
 （2）concat 拼接字符串

SELECT concat(last_name, '_', first_name) AS name FROM employees;
（3）upper、lower

SELECT UPPER('john');
SELECT LOWER('JOHN');
SELECT CONCAT(UPPER(last_name), LOWER(first_name)) AS name FROM employees;
 （4）substr、substring，索引从1开始

SELECT SUBSTR('李莫愁爱上了陆展元', 7) AS out_put; # 陆展元
SELECT SUBSTR('李莫愁爱上了陆展元', 0, 3) AS out_put; #李莫愁
#姓名中首字符大写，其它字符小写，然后用下划线_拼接
SELECT CONCAT(UPPER(SUBSTR(last_name, 1, 2)), '_', LOWER(SUBSTR(SUBSTR(last_name, 2))) AS out_put FROM employees;
（5）instr 返回子串第一次出现的索引，如果找不到返回0

SELECT INSTR('杨不悔爱上了殷六侠', '殷六侠') AS out_put; #7
（6）trim

SELECT LENGTH(TRIM('   张翠山    ')) AS out_put;
SELECT TRIM('a' FROM 'aaaaaa张翠山aaaaa') AS out_put; #张翠山
 （7）lpad 用指定的字符实现左填充指定长度， lpad 用指定的字符实现右填充指定长度

SELECT LPAD('殷素素', 16, '*') AS out_put; #*************殷素素
SELECT LPAD('殷素素', 12, 'ab') AS out_put;#殷素素ababababa
（8）replace 替换

SELECT REPLACE('周芷若爱上了张无忌周芷若', '周芷若', '赵敏') AS out_put; #赵敏爱上了张无忌赵敏

### 2.数学函数

（1）round四舍五入

SELECT ROUND(-1.55); #-2
SELECT ROUND(1.567, 2); #1.57
（2）ceil 向上取整，返回>=该参数的最小整数 

SELECT CEIL(1.2); #2
（3）floor 向上取整，返回<=该参数的最小整数

SELECT FLOOR(9.99); #9
SELECT FLOOR(-10); #-10
（4）truncate 截断

SELECT TRUNCATE(1.79999, 1); #1.7
（5）mod 取余

SELECT MOD(10, 3); #1

### 3.日期函数

（1）now 返回系统当前日期+时间

SELECT NOW(); #2021-05-22 15:01:54
（2）curdate 返回系统当前日期, 不包含时间

SELECT CURDATE();#2021-05-22
 （3）curtime 返回系统当前时间，不包含日期

SELECT CURTIME();#15:04:46
（4）获取指定的部分，年月日时分秒

SELECT YEAR(NOW()) AS year; #2021
SELECT YEAR(hiredate) AS year FROM employees;


SELECT YEAR('2021-05-22') AS year;
SELECT STR_TO_DATE('2021-5-22', '%Y-%c-%d') AS out_put; #2021-05-22
 （5）date_format 将日期转化为字符

SELECT DATE_FORMAT(NOW(), '%Y年%m月%d日') AS ymd; #2021年05月22日

### 4.其它函数

（1）当前数据库版本号

SELECT VERSION();
（2）当前数据库 

SELECT DATABASE();
（3）当前用户

SELECT USER();
（4）返回字符的密码形式

SELECT PASSWORD('mysql');
（5）返回字符的md5加密结果

SELECT MD5('mysql');

### 5.流程控制函数

（1）if 函数，能实现if else 效果

SELECT IF(10 > 5, '大', '小'); #大
SELECT last_name, commission_pct IF(commission IS NULL, '没奖金', '有奖金') AS bonus FROM employees;
（2）case函数，类似switch case的效果

语法：case 要判断的字段或表达式

when 常量1 then 要显示的值1 或语句1

when 常量2 then 要显示的值2 或语句2

...

else 要显示的值或语句

end

SELECT salary AS original_salary, department_id,
CASE department_id
WHEN 30 THEN salary*1.1
WHEN 40 THEN salary*1.2
WHEN 50 THEN salary*1.3
ELSE salary
END AS new_salary
FROM employees;
SELECT salary,
CASE salary
WHEN salary > 20000 THEN 'A'
WHEN salary > 15000 THEN 'B'
WHEN salary > 10000 THEN 'C'
ELSE 'D'
END AS salary_level FROM employees;

### 6.分组函数（聚合函数或统计函数）

（1）sum求和

SELECT SUM(salary) FROM employees;
（2）avg平均值

SELECT AVG(salary) FROM employees;
（3）max最大值

SELECT MAX(salary) FROM employees;
（4）min最小值

SELECT MIN(salary) FROM employees;
（5）count个数

SELECT COUNT(salary) FROM employees;
SELECT SUM(salary) AS sum, MAX(salary) AS max, MIN(salary) AS min, ROUND(AVG(salary), 2) AS avg, COUNT(salary) AS count FROM employees;
SELECT COUNT(*) FROM employees;

## 七、分组查询

语法：select 分组函数，列 from 表 【where筛选条件】 group by 分组的列表 【order by 子句】

注意：

①查询列表必须是分组函数和group by 后出现的字段

②分组函数做条件肯定放在having子句中

③能用分组前筛选的，优先考虑使用分组前筛选

特点：分组查询的筛选条件分为2类：

![image-20220817203245032](/Users/lixiaofeng/Library/Application Support/typora-user-images/image-20220817203245032.png)

1.查询每个工种的最高工资

SELECT MAX(salary), job_id FROM employees GROUP BY job_id;
2.查询每个位置上的部门个数

SELECT COUNT(*), location_id FROM departments GROUP BY location_id;
3.查询邮箱中包含a字符的，每个部门的平均工资

SELECT AVG(salary), department_id FROM employees WHERE email LIKE '%a%' GROUP BY department_id;
4.查询有奖金的每个领导手下员工的最高工资

SELECT MAX(salary), manager_id FROM employees WHERE commission_pct IS NOT NULL GROUP BY manager_id;
5.查询哪个部门的员工数大于2

（1）查询每个部门的员工数

SELECT COUNT(*), department_id FROM employees GROUP BY department_id;
（2）根据（1）的结果进行筛选，查询哪个部门的员工数大于2（添加分组后的筛选条件，having）

SELECT COUNT(*), department_id FROM employees GROUP BY department_id HAVING count(*) > 2;
6.查询每个工种有奖金的员工的最高工资>12000的工种编号和最高工资

（1）查询每个工种有奖金的员工的最高工资

SELECT MAX(salary),job_id FROM employees WHERE commission_pct IS NOT NULL GROUP BY job_id;
（2）根据（1）结果进行筛选，最高工资>12000

SELECT MAX(salary), job_id FROM employees WHERE commission_pct is NOT NULL GROUP BY job_id HAVING MAX(salary) > 12000;
7.查询领导编号>102的每个领导手下的最低工资>5000的领导编号是哪个，以及其最低工资

（1）查询领导编号>102的每个领导手下的最低工资

SELECT MIN(salary),manager_id FROM employees WHERE manager_id > 102 GROUP BY manager_id;
（2）最低工资大于5000

SELECT MIN(salary),manager_id FROM employees WHERE manager_id > 102 GROUP BY manager_id HAVING MIN(salary) > 5000;
8.按员工姓名的长度分组，查询每一组的员工个数，筛选员工个数大于5的有哪些

SELECT COUNT(*) C, LENGTH(last_name) len_name FROM employees  GROUP BY len_name HAVING c > 5;
9.查询每个部门每个工种的员工的平均工资（多多个字段分组）

SELECT AVG(salary), department_id, job_id FROM employees GROUP BY department_id, job_id;
10.查询每个部门每个工种的员工的平均工资，按照平均工资从高到低排序 

SELECT AVG(salary) asa, department_id, job_id FROM employees GROUP BY department_id, job_id ORDER BY asa DESC;

## 八、连接查询（SQL1992）

又称多表查询，当查询的字段来自于多个表时，就会用到连接查询

笛卡尔积现象：表1有m行，表2有n行，结果m*n行

如何避免：添加有效的连接

按功能分类：

内连接：等值连接、非等值连接、自连接

外连接：左外连接、右外连接、全连接

交叉连接

 

### 等值连接示例

注意事项：结果为多表的交集部分；n表连接至少需要n-1个连接条件；多表的顺序没有要求；一般需要为表起别名；可以搭配子句使用，如排序、分组、筛选

1.查询员工名对应的部门名（等值连接）

SELECT last_name, department_name FROM employees, departments WHERE employees.`department_id` = departments.`department_id`;
2.查询员工名、工种号、工种名

SELECT last_name, .job_id, job_title
FROM employees e, jobs j
WHERE e.`job_id` = j.`job_id`;
3.查询有奖金的员工名、部门名

SELECT last_name, department_name 
FROM employees e, departments d
WHERE e.`department_id` = d.`department_id`
AND e.commission_pct IS NOT NULL;
3.查询城市名中第二个字符为o的部门名和城市名

SELECT department_name, city
FROM departmens d, locations l
WHERE d.`location_id` = l.`location_id`
AND city LIKE '%a%';
4. 查询每个城市的部门个数（添加分组）

SELECT COUNT(*) 个数, city
FROM departments d, locations l
GROUP BY city;
5.查询有奖金的每个部门的部门名和部门的领导编号和该部门的最低工资

SELECT department_name, d.manager_id, MIN(salary)
FROM departments d, employees d
WHERE d.`department_id` = e.`department_id`
AND commission_pct is NOT NULL
GROUP BY department_name, d.manager_id;
6.查询每个工种的工种名和员工的个数，并且按员工个数降序（添加排序）

SELECT job_title, COUNT(*)
FROM employees e, jobs j
WHERE e.`job_id` = j.`job_id`
GROUP BY job_title
ORDER BY COUNT(*) DESC;
7.查询员工名、部门名和所在的城市（三表连接）

SELECT last_name, department_name, city
FROM employees e, departments d, locations l
WHERE e.`department_id` = d.`department_id`
AND d.`location_id` = l.`location_id`
AND city LIKE '%s%'
ORDER BY department_name DESC;

### 非等值连接示例

查询员工的工资和工资级别

SELECT salary, grade_level
FROM employees e, job_grades g
WHERE salary BETWEEN g.`lowest_sal` AND g.`highest_sal`
AND g.`grade_level` = 'A';

### 自连接示例

查询员工名和上级的名称

SELECT e.employee_id, e.last_name, m.employee_id, m.last_name
FROM employees e, employees m
WHERE e.`manager_id` = m.`employee_id`;

## 九、连接查询（SQL1999）

![img](https://img-blog.csdnimg.cn/20210524222441286.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxMjk0MjcxMzY1OA==,size_16,color_FFFFFF,t_70)



语法：

SELECT   查询列表
FROM 表1 别名 【连接类型】
JOIN 表2 别名
ON 连接条件
【WHERE 筛选条件】
【GROUP BY 分组】
【HAVING 筛选条件】
【ORDER BY 排序列表】

### 1.内连接（inner）：等值连接、非等值连接、自连接

（1）等值连接

   ① 查询员工名、部门名

SELECT last_name, department_name
FROM employees e
INNER JOIN departments d
ON e.`department_id` = d.`department_id`;
   ② 查询名字中包含e的员工名和工种名（添加筛选）

SELECT last_name, job_title
FROM employees e
INNER JOIN jobs j
ON e.`job_id` = j.`job_id`
WHERE last_name LIKE '%e%';
   ③ 查询部门数大于3的城市名和部门个数（添加分组+筛选）

SELECT city, COUNT(*)
FROM departments d
INNER JOIN locations l
ON d.`location_id` = l.`location_id`
GROUP BY city
HAVING COUNT(*) > 3;
   ④ 查询哪个部门的员工数大于3的部门名和员工个数，并按个数降序（添加排序）

SELECT COUNT(*), department_name
FROM employees e
INNER JOIN departments d
ON e.`department_id` = d.`department_id`
GROUP BY department_name
ORDER BY COUNT(*);
   ⑤ 查询员工名、部门名、工种名并按照部门名降序

SELECT last_name, department_name, job_title
FROM employees e
INNER JOIN departments d
ON e.`department_id` = d.`department_id`
INNER JOIN jobs j
ON e.`job_id` = j.`job_id`
ORDER BY department_name DESC;
（2）非等值连接

 ① 查询员工的工资级别

SELECT salary, grade_level
FROM emplyees e
JOIN job_grades g
ON e.`salary` BETWEEN g.`lowest_sal` ADN g.`highest_sal`;
   ②查询工资级别大于2的个数，并且按工资级别降序

SELECT COUNT(*), grade_level
FROM employees e
JOIN job_grades g
ON e.`salary` BETWEEN g.`lowest_sal` AND g.`highest_sal`
GROUP BY grade_level
HAVING COUNT(*) > 20
ORDER BY grade_level DESC;
（3）自连接

 查询姓名中包含字符k员工的名字，上级的名称

SELECT e.last_name, m.last_name
FROM employees e
JOIN employees m
ON e.`manager_id` = m.`employee_id`
WHERE E.`last_name` LIKE '%k%';

### 2.外连接

特点：查询结果为主表中的所有记录；如果从表中有和它匹配的，则显示匹配的值，如果从表中没有和它匹配的，则显示null；外连接查询结果=内连接结果+主表中有而从表中没有的记录；左外连接，left join左边的是主表，right join右边的是主表

（1）左外连接（left【outer】）

（2）右外连接（right【outer】）

（3）全外连接（full【outer】）

查询哪个部门没有员工

#左外
SELECT d.*, e.employee_id
FROM departments d
LEFT OUTER JOIN employees e
ON d.`department_id` = e.`department_id`
WHERE e.`employee_id` IS NULL;

#右外
SELECT d.*, e.employee_id
FROM employees e
RIGHT OUTER JOIN departments d
ON d.`department_id` = e.`department_id`
WHERE e.`employee_id` IS NULL;

## 十、子查询

含义：出现在其他语句内部的select语句，称为子查询或内查询。

分类：

 按查询出现的位置：

 select后面：仅支持标量子查询
 from后面：支持表子查询
  where或having后面：标量子查询、列子查询、行子查询
  exists后面：表子查询
按结果集的行列数不同：

标量子查询：结果集只有一行一列
列子查询：结果集只有一列多行
行子查询：结果集只有一行多列
表子查询：一般为多行多列 

# MySQL查看用户权限及权限管理

## 一、 MySQL权限级别介绍

全局——可以管理整个MySQL
库——可以管理指定的数据库
表——可以管理指定数据库的指定表
字段——可以管理指定数据库的指定表的指定字段
权限存储在mysql库的user, db, tables_priv, columns_priv, procs_priv这几个系统表中，待MySQL实例启动后就加载到内存中

## 二、查看用户权限

### 0、查看所有用户的授权情况（具体）；

```
SELECT * FROM mysql.user;
```



### 1、查看所有用户（用户名、给谁授权）

```sql
SELECT user,host FROM mysql.user;
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210521175705136.png)

### 2、查看单个用户所有情况

```sql
SELECT * FROM mysql.user WHERE user='root'\G
```

\g 相当于’;’
\G使每个字段打印到单独的行，也有’;'的作用

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210521175739504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ljaGVuODIw,size_16,color_FFFFFF,t_70)

加粗样式用户信息：授权对象，连接用户名，用户密码

```sql
Host: %		# 授权用户，% 代表所有
User: root	# 用户名
authentication_string: *6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9	#密码，MD5加密

```



### 授权信息

Select_priv：确定用户是否可以通过SELECT命令选择数据 
Insert_priv：确定用户是否可以通过INSERT命令插入数据 
Update_priv：确定用户是否可以通过UPDATE命令修改现有数据 
Delete_priv：确定用户是否可以通过DELETE命令删除现有数据 
Create_priv：确定用户是否可以创建新的数据库和表 
Drop_priv：确定用户是否可以删除现有数据库和表 
Reload_priv：确定用户是否可以执行刷新和重新加载MySQL所用各种内部缓存的特定命令，包括日志、权限、主机、查询和表 
Shutdown_priv：确定用户是否可以关闭MySQL服务器，将此权限提供给root账户之外的任何用户时，都应当非常谨慎 
Process_priv：确定用户是否可以通过SHOW 
File_priv：确定用户是否可以执行SELECT INTO OUTFILE和LOAD DATA INFILE命令 
Grant_priv：确定用户是否可以将已经授予给该用户自己的权限再授予其他用户，例如，如果用户可以插入、选择和删除foo数据库中的信息，并且授予了GRANT权限，则该用户就可以将其任何或全部权限授予系统中的任何其他用户 
References_priv：目前只是某些未来功能的占位符，现在没有作用 
Index_priv：确定用户是否可以创建和删除表索引 
Alter_priv：确定用户是否可以重命名和修改表结构 
Show_db_priv：确定用户是否可以查看服务器上所有数据库的名字，包括用户拥有足够访问权限的数据库，可以考虑对所有用户禁用这个权限，除非有特别不可抗拒的原因 
Super_priv：确定用户是否可以执行某些强大的管理功能，例如通过KILL命令删除用户进程，使用SET GLOBAL修改全局MySQL变量，执行关于复制和日志的各种命令 
Create_tmp_table_priv：确定用户是否可以创建临时表 
Lock_tables_priv：确定用户是否可以使用LOCK 
Execute_priv：确定用户是否可以执行存储过程，此权限只在MySQL 5.0及更高版本中有意义 
Repl_slave_priv：确定用户是否可以读取用于维护复制数据库环境的二进制日志文件，此用户位于主系统中，有利于主机和客户机之间的通信 
Repl_client_priv：确定用户是否可以确定复制从服务器和主服务器的位置 
Create_view_priv：确定用户是否可以创建视图，此权限只在MySQL 5.0及更高版本中有意义 
Show_view_priv：确定用户是否可以查看视图或了解视图如何执行，此权限只在MySQL 5.0及更高版本中有意义 Create_routine_priv：确定用户是否可以更改或放弃存储过程和函数，此权限是在MySQL 5.0中引入的 Alter_routine_priv：确定用户是否可以修改或删除存储函数及函数，此权限是在MySQL 5.0中引入的 Create_user_priv：确定用户是否可以执行CREATE 
Event_priv：确定用户能否创建、修改和删除事件，这个权限是MySQL 5.1.6新增的 
Trigger_priv：确定用户能否创建和删除触发器，这个权限是MySQL 5.1.6新增的
Create_tablespace_priv: 创建表的空间

### 权限表

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210521175825153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ljaGVuODIw,size_16,color_FFFFFF,t_70)

## 三、授权

每次更新权限后记得刷新权限

```sql
FLUSH PRIVILEGES;
```



### 格式：

```sql
GRANT
  [权限] 
ON [库.表] 
TO [用户名]@[IP] 
IDENTIFIED BY [密码] 

# WITH GRANT OPTION;
```

### GRANT命令说明：

(1) <font color = Tomato size=3 face="楷书"> </font><font color = Tomato size=3 face="楷书"> ALL PRIVILEGES </font>表示所有权限，你也可以使用select、update等权限。
(2) ON 用来指定权限针对哪些库和表。
(3)*.* 中前面的号用来指定数据库名，后面的号用来指定表名。
(4) TO 表示将权限赋予某个用户。
(5) @ 前面表示用户，@后面接限制的主机，可以是IP、IP段、域名以及%，%表示任何地方。
(6) IDENTIFIED BY 指定用户的登录密码。
(7) WITH GRANT OPTION 这个选项表示该用户可以将自己拥有的权限授权给别人。

注意：经常有人在创建操作用户的时候不指定WITH GRANT OPTION选项导致后来该用户不能使用GRANT命令创建用户或者给其它用户授权。
备注：可以使用GRANT重复给用户添加权限，权限叠加，比如你先给用户添加一个select权限，然后又给用户添加一个insert权限，那么该用户就同时拥有了select和insert权限。

### 1、全局授权（直接把 root 限制主机改为 %，任意主机）

因为 root 是数据库用户默认最高权限

```sql
UPDATE mysql.user SET user.Host='%' where user.User='root';
FLUSH PRIVILEGES;
```


当然也可以新建一个用户，给与全部权限

```sql
GRANT
  ALL PRIVILEGES
ON *.*
TO admin@'175.155.59.133'
IDENTIFIED BY 'admin';
```

这样你就可以在远程连接到该数据库，且获取全部权限。

### 2、单个数据库授权

只给175.155.59.133这个 IP 赋给 ctrip 数据库 查询的权限，用户：ctrip，密码：ctrip

```sql
GRANT
  select
ON ctrip.*
TO ctrip@'175.155.59.133'
IDENTIFIED BY 'ctrip';
```

声明：
（1）用ipconfig查询出来的IP，那是局域网的，这么设置只能局域网内使用，
（2）要想服务器和本机连接，IP 必须是网关的IP，推荐使用 https://www.ipip.net/ 查询自己的 IP。

可以看到本机连接有 ctrip 数据库和一个数据库本身库，而看不到其他库。注意此时可以看到两个表。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210521180125289.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ljaGVuODIw,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20210521180142265.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2ljaGVuODIw,size_16,color_FFFFFF,t_70)

### 3、单个数据库单个表授权

```sql
GRANT
  select
ON ctrip.t_plane
TO ctrip@'175.155.59.133'
IDENTIFIED BY 'ctrip';
```


可以看到只有一个表显示出来，注意现在字段



### 4、单个数据库单个表授权某些字段授权

```sql
GRANT
  select(id,EN)
ON ctrip.t_plane
TO ctrip@'175.155.59.133'
IDENTIFIED BY 'ctrip';
```



## 四、收回权限、删除用户

### 1、收回权限

格式：

```sql
REVOKE
  [权限] 
ON [库.表] 
FROM [用户名]@[IP];
```


操作：

```sql
REVOKE
  select(id,EN)
ON ctrip.t_plane
FROM ctrip@'175.155.59.133';
```



### 2、删除用户

格式：

```sql
DROP USER [用户名]@[IP];
```


操作：

```sql
DROP USER ctrip@'175.155.59.133';
```



# MySQL中的show variables like xxx 详解

1、首先确认你日志是否启用了mysql>show variables like 'log_bin'。

2、如果启用了，即ON，那日志文件就在mysql的安装目录的data目录下。

3、怎样知道当前的日志mysql> show master status。

4、看[二进制](https://www.baidu.com/s?wd=二进制&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1dWPHfsP19-nhcvnhcvnjKW0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EnHTvrHf3nWDvPHDLPH61rHcvn0)日志文件用mysqlbinlog，shell>mysqlbinlog mail-bin.000001或者shell>mysqlbinlog mail-bin.000001 | tail，Windows 下用类似的。



1, 查看MySQL服务器配置信息
mysql> show variables;

mysql>show variables like 'version'\G

2, 查看MySQL服务器运行的各种状态值
mysql> show global status;

3, 慢查询

```sql
1.  mysql> show variables like '%slow%';  
2.  +------------------+-------+  
3.  | Variable_name    | Value |  
4.  +------------------+-------+  
5.  | log_slow_queries | OFF   | 
6.  | slow_launch_time | 2     |  
7.  +------------------+-------+  
8.  mysql> show global status like '%slow%';  
9.  +---------------------+-------+  
10. | Variable_name       | Value |  
11. +---------------------+-------+  
12. | Slow_launch_threads | 0     |  
13. | Slow_queries        | 279   |  
14. +---------------------+-------+  
```

配置中关闭了记录慢查询（最好是打开，方便优化），超过2秒即为慢查询，一共有279条慢查询

4, 连接数

```sql
1.  mysql> show variables like 'max_connections';  



2.  +-----------------+-------+  



3.  | Variable_name   | Value |  



4.  +-----------------+-------+  



5.  | max_connections | 500   |  



6.  +-----------------+-------+  



7.    



8.  mysql> show global status like 'max_used_connections';  



9.  +----------------------+-------+  



10. | Variable_name        | Value |  



11. +----------------------+-------+  



12. | Max_used_connections | 498   |  



13. +----------------------+-------+  
```

设置的最大连接数是500，而响应的连接数是498

max_used_connections / max_connections * 100% = 99.6% （理想值 ≈ 85%）

5, key_buffer_size
key_buffer_size是对MyISAM表性能影响最大的一个参数, 不过数据库中多为Innodb

```sql
1.  mysql> show variables like 'key_buffer_size';  



2.  +-----------------+----------+  



3.  | Variable_name   | Value    |  



4.  +-----------------+----------+  



5.  | key_buffer_size | 67108864 |  



6.  +-----------------+----------+  



7.    



8.  mysql> show global status like 'key_read%';  



9.  +-------------------+----------+  



10. | Variable_name     | Value    |  



11. +-------------------+----------+  



12. | Key_read_requests | 25629497 |  



13. | Key_reads         | 66071    |  



14. +-------------------+----------+  
```

一共有25629497个索引读取请求，有66071个请求在内存中没有找到直接从硬盘读取索引，计算索引未命中缓存的概率：
key_cache_miss_rate ＝ Key_reads / Key_read_requests * 100% =0.27%
需要适当加大key_buffer_size

```sql
1.  mysql> show global status like 'key_blocks_u%';  



2.  +-------------------+-------+  



3.  | Variable_name     | Value |  



4.  +-------------------+-------+  



5.  | Key_blocks_unused | 10285 |  



6.  | Key_blocks_used   | 47705 |  



7.  +-------------------+-------+  
```

Key_blocks_unused表示未使用的缓存簇(blocks)数，Key_blocks_used表示曾经用到的最大的blocks数
Key_blocks_used / (Key_blocks_unused + Key_blocks_used) * 100% ≈ 18% （理想值 ≈ 80%）

6， 临时表

```sql
1.  mysql> show global status like 'created_tmp%';  



2.  +-------------------------+---------+  



3.  | Variable_name           | Value   |  



4.  +-------------------------+---------+  



5.  | Created_tmp_disk_tables | 4184337 |  



6.  | Created_tmp_files       | 4124    |  



7.  | Created_tmp_tables      | 4215028 |  



8.  +-------------------------+---------+  
```

每次创建临时表，Created_tmp_tables增加，如果是在磁盘上创建临时表，Created_tmp_disk_tables也增加,Created_tmp_files表示MySQL服务创建的临时文件文件数：
Created_tmp_disk_tables / Created_tmp_tables * 100% ＝ 99% （理想值<= 25%）

```sql
1.  mysql> show variables where Variable_name in ('tmp_table_size', 'max_heap_table_size');  



2.  +---------------------+-----------+  



3.  | Variable_name       | Value     |  



4.  +---------------------+-----------+  



5.  | max_heap_table_size | 134217728 |  



6.  | tmp_table_size      | 134217728 |  



7.  +---------------------+-----------+  
```

需要增加tmp_table_size

7,open table 的情况

```sql
1.  mysql> show global status like 'open%tables%';  



2.  +---------------+-------+  



3.  | Variable_name | Value |  



4.  +---------------+-------+  



5.  | Open_tables   | 1024  |  



6.  | Opened_tables | 1465  |  



7.  +---------------+-------+  
```

Open_tables 表示打开表的数量，Opened_tables表示打开过的表数量，如果Opened_tables数量过大，说明配置中 table_cache(5.1.3之后这个值叫做table_open_cache)值可能太小，我们查询一下服务器table_cache值

```sql
1.  mysql> show variables like 'table_cache';  



2.  +---------------+-------+  



3.  | Variable_name | Value |  



4.  +---------------+-------+  



5.  | table_cache   | 1024  |  



6.  +---------------+-------+  
```

Open_tables / Opened_tables *100% =69% 理想值 （>= 85%）
Open_tables / table_cache* 100% = 100% 理想值 (<= 95%)

8, 进程使用情况

```sql
1.  mysql> show global status like 'Thread%';  



2.  +-------------------+-------+  



3.  | Variable_name     | Value |  



4.  +-------------------+-------+  



5.  | Threads_cached    | 31    |  



6.  | Threads_connected | 239   |  



7.  | Threads_created   | 2914  |  



8.  | Threads_running   | 4     |  



9.  +-------------------+-------+  
```

如果我们在MySQL服务器配置文件中设置了thread_cache_size，当客户端断开之后，服务器处理此客户的线程将会缓存起来以响应下一个客户而不是销毁（前提是缓存数未达上限）。Threads_created表示创建过的线程数，如果发现Threads_created值过大的话，表明 MySQL服务器一直在创建线程，这也是比较耗资源，可以适当增加配置文件中thread_cache_size值，查询服务器 thread_cache_size配置：

```sql
1.  mysql> show variables like 'thread_cache_size';  



2.  +-------------------+-------+  



3.  | Variable_name     | Value |  



4.  +-------------------+-------+  



5.  | thread_cache_size | 32    |  



6.  +-------------------+-------+  
```

9, 查询缓存(query cache)

```sql
1.  mysql> show global status like 'qcache%';  



2.  +-------------------------+----------+  



3.  | Variable_name           | Value    |  



4.  +-------------------------+----------+  



5.  | Qcache_free_blocks      | 2226     |  



6.  | Qcache_free_memory      | 10794944 |  



7.  | Qcache_hits             | 5385458  |  



8.  | Qcache_inserts          | 1806301  |  



9.  | Qcache_lowmem_prunes    | 433101   |  



10. | Qcache_not_cached       | 4429464  |  



11. | Qcache_queries_in_cache | 7168     |  



12. | Qcache_total_blocks     | 16820    |  



13. +-------------------------+----------+  
```

Qcache_free_blocks：缓存中相邻内存块的个数。数目大说明可能有碎片。FLUSH QUERY CACHE会对缓存中的碎片进行整理，从而得到一个空闲块。
Qcache_free_memory：缓存中的空闲内存。
Qcache_hits：每次查询在缓存中命中时就增大
Qcache_inserts：每次插入一个查询时就增大。命中次数除以插入次数就是不中比率。
Qcache_lowmem_prunes：缓存出现内存不足并且必须要进行清理以便为更多查询提供空间的次数。这个数字最好长时间来看；如果这个数字在不断增长，就表示可能碎片非常严重，或者内存很少。（上面的 free_blocks和free_memory可以告诉您属于哪种情况）
Qcache_not_cached：不适合进行缓存的查询的数量，通常是由于这些查询不是 SELECT 语句或者用了now()之类的函数。
Qcache_queries_in_cache：当前缓存的查询（和响应）的数量。
Qcache_total_blocks：缓存中块的数量。

我们再查询一下服务器关于query_cache的配置：

```markdown
1.  mysql> show variables like 'query_cache%';  



2.  +------------------------------+----------+  



3.  | Variable_name                | Value    |  



4.  +------------------------------+----------+  



5.  | query_cache_limit            | 33554432 |  



6.  | query_cache_min_res_unit     | 4096     |  



7.  | query_cache_size             | 33554432 |  



8.  | query_cache_type             | ON       |  



9.  | query_cache_wlock_invalidate | OFF      |  



10. +------------------------------+----------+  
```

各字段的解释：

query_cache_limit：超过此大小的查询将不缓存
query_cache_min_res_unit：缓存块的最小大小
query_cache_size：查询缓存大小
query_cache_type：缓存类型，决定缓存什么样的查询，示例中表示不缓存 select sql_no_cache 查询
query_cache_wlock_invalidate：当有其他客户端正在对MyISAM表进行写操作时，如果查询在query cache中，是否返回cache结果还是等写操作完成再读表获取结果。

query_cache_min_res_unit的配置是一柄”双刃剑”，默认是4KB，设置值大对大数据查询有好处，但如果你的查询都是小数据查询，就容易造成内存碎片和浪费。

查询缓存碎片率 = Qcache_free_blocks / Qcache_total_blocks * 100%

如果查询缓存碎片率超过20%，可以用FLUSH QUERY CACHE整理缓存碎片，或者试试减小query_cache_min_res_unit，如果你的查询都是小数据量的话。

查询缓存利用率 = (query_cache_size – Qcache_free_memory) / query_cache_size * 100%

查询缓存利用率在25%以下的话说明query_cache_size设置的过大，可适当减小；查询缓存利用率在80％以上而且Qcache_lowmem_prunes > 50的话说明query_cache_size可能有点小，要不就是碎片太多。

查询缓存命中率 = (Qcache_hits – Qcache_inserts) / Qcache_hits * 100%

示例服务器 查询缓存碎片率 ＝ 20.46％，查询缓存利用率 ＝ 62.26％，查询缓存命中率 ＝ 1.94％，命中率很差，可能写操作比较频繁吧，而且可能有些碎片。

10,排序使用情况

```sql
1.  mysql> show global status like 'sort%';  



2.  +-------------------+----------+  



3.  | Variable_name     | Value    |  



4.  +-------------------+----------+  



5.  | Sort_merge_passes | 2136     |  



6.  | Sort_range        | 81888    |  



7.  | Sort_rows         | 35918141 |  



8.  | Sort_scan         | 55269    |  



9.  +-------------------+----------+  
```

Sort_merge_passes 包括两步。MySQL 首先会尝试在内存中做排序，使用的内存大小由系统变量 Sort_buffer_size 决定，如果它的大小不够把所有的记录都读到内存中，MySQL 就会把每次在内存中排序的结果存到临时文件中，等 MySQL 找到所有记录之后，再把临时文件中的记录做一次排序。这再次排序就会增加 Sort_merge_passes。实际上，MySQL 会用另一个临时文件来存再次排序的结果，所以通常会看到 Sort_merge_passes 增加的数值是建临时文件数的两倍。因为用到了临时文件，所以速度可能会比较慢，增加 Sort_buffer_size 会减少 Sort_merge_passes 和 创建临时文件的次数。但盲目的增加 Sort_buffer_size 并不一定能提高速度，见 How fast can you sort data with MySQL?（引自http://qroom.blogspot.com/2007/09/mysql-select-sort.html）

另外，增加read_rnd_buffer_size(3.2.3是record_rnd_buffer_size)的值对排序的操作也有一点的好处，参见：http://www.mysqlperformanceblog.com/2007/07/24/what-exactly-is- read_rnd_buffer_size/

11.文件打开数(open_files)

```sql
1.  mysql> show global status like 'open_files';  



2.  +---------------+-------+  



3.  | Variable_name | Value |  



4.  +---------------+-------+  



5.  | Open_files    | 821   |  



6.  +---------------+-------+  



7.    



8.  mysql> show variables like 'open_files_limit';  



9.  +------------------+-------+  



10. | Variable_name    | Value |  



11. +------------------+-------+  



12. | open_files_limit | 65535 |  



13. +------------------+-------+  
```

比较合适的设置：Open_files / open_files_limit * 100% <= 75％

正常

12。 表锁情况

```sql
1.  mysql> show global status like 'table_locks%';  



2.  +-----------------------+---------+  



3.  | Variable_name         | Value   |  



4.  +-----------------------+---------+  



5.  | Table_locks_immediate | 4257944 |  



6.  | Table_locks_waited    | 25182   |  



7.  +-----------------------+---------+  
```

Table_locks_immediate 表示立即释放表锁数，Table_locks_waited表示需要等待的表锁数，如果 Table_locks_immediate / Table_locks_waited > 5000，最好采用InnoDB引擎，因为InnoDB是行锁而MyISAM是表锁，对于高并发写入的应用InnoDB效果会好些.

1. 表扫描情况

```markdown
1.  mysql> show global status like 'handler_read%';  



2.  +-----------------------+-----------+  



3.  | Variable_name         | Value     |  



4.  +-----------------------+-----------+  



5.  | Handler_read_first    | 108763    |  



6.  | Handler_read_key      | 92813521  |  



7.  | Handler_read_next     | 486650793 |  



8.  | Handler_read_prev     | 688726    |  



9.  | Handler_read_rnd      | 9321362   |  



10. | Handler_read_rnd_next | 153086384 |  



11. +-----------------------+-----------+  
```

调出服务器完成的查询请求次数：

1. mysql> show global status like 'com_select';

2. +---------------+---------+

3. | Variable_name | Value |

4. +---------------+---------+

5. | Com_select | 2693147 |

6. +---------------+---------+

   ```undefined
   
   ```

计算表扫描率：

表扫描率 ＝ Handler_read_rnd_next / Com_select

如果表扫描率超过4000，说明进行了太多表扫描，很有可能索引没有建好，增加read_buffer_size值会有一些好处，但最好不要超过8MB。