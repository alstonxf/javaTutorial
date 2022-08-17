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



# 