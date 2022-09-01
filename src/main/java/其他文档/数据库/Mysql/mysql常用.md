## MySQL实现Oracle的trunc(date)函数

MySQL对于截取日期可以用date_format()+str_to_date()函数实现：

```sql
-- 返回当年第一天
oracle> SELECT trunc(sysdate, 'yyyy') FROM dual;
oracle> SELECT trunc(sysdate, 'yy') FROM dual;
mysql>  SELECT str_to_date(date_format(now(), '%Y0101'), '%Y%m%d');

-- 返回当月第一天
oracle> SELECT trunc(sysdate, 'mm') FROM dual;
mysql>  SELECT str_to_date(date_format(now(), '%Y%m01'), '%Y%m%d');

-- 返回当前年月日
oracle> SELECT trunc(sysdate, 'dd') FROM dual;
mysql>  SELECT str_to_date(date_format(now(), '%Y%m%d'), '%Y%m%d');

-- 返回当前星期的第一天(星期日) 
oracle> SELECT trunc(sysdate, 'd') FROM dual;
mysql>  select case when WEEKDAY(now()) = 6 
		then str_to_date(date_format(now(), '%Y%m%d'), '%Y%m%d') 
		else str_to_date(date_format(date_sub(now(),INTERVAL WEEKDAY(now()) + 1 DAY), '%Y%m%d'), '%Y%m%d')  end;

-- 返回当前日期截取到小时,分秒补0
oracle> SELECT trunc(sysdate, 'hh') FROM dual;
mysql>  SELECT str_to_date(date_format(now(), '%Y%m%d%H'), '%Y%m%d%H');

-- 返回当前日期截取到分,秒补0
oracle> SELECT trunc(sysdate, 'mi') FROM dual;
mysql>  SELECT str_to_date(date_format(now(), '%Y%m%d%H%i'), '%Y%m%d%H%i');


```

## **mysql日期相减的天数**

*万次阅读*

2018-07-24 10:17:02

DATEDIFF() 函数返回两个日期之间的天数。

### 语法

```
DATEDIFF(date1,date2)
```

*date1* 和 *date2* 参数是合法的日期或日期/时间表达式。

注释：只有值的日期部分参与计算。

### 实例

#### 例子 1

使用如下 SELECT 语句：

```
SELECT DATEDIFF('2008-12-30','2008-12-29') AS DiffDate
```

结果：

| DiffDate |
| :------- |
| 1        |

#### 例子 2

使用如下 SELECT 语句：

```
SELECT DATEDIFF('2008-12-29','2008-12-30') AS DiffDate
```

结果：

| DiffDate |
| :------- |
| -1       |



## **查看mysql 的驱动版本号**

*千次阅读*

2017-12-26 17:33:30

1 查看客户端的版本号

mysql --version



2 查看服务的版本号

mysqld --version

或者登陆数据库

select version();