# TiDB 用户账户管理

本文档主要介绍如何管理 TiDB 用户账户。

要快速了解 TiDB 如何进行认证与赋权并创建与管理用户账户，建议先观看下面的培训视频（时长 22 分钟）。注意本视频只作为学习参考，如需了解具体的用户账户管理方法，请参考本文档的内容。

<video src="https://download.pingcap.com/docs-cn%2FLesson11_security.mp4" width="600px" height="450px" controls="" poster="https://tidb-docs.s3.us-east-2.amazonaws.com/thumbnail+-+lesson+11.png" style="box-sizing: inherit; color: rgb(36, 41, 47); font-family: -apple-system, &quot;system-ui&quot;, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;"></video>

## 查看当前登录的用户



```sql
select current_user();
```



## 用户名和密码

TiDB 将用户账户存储在 `mysql.user` 系统表里面。每个账户由用户名和 host 作为标识。每个账户可以设置一个密码。

通过 MySQL 客户端连接到 TiDB 服务器，通过指定的账户和密码登录：

```brainfuck
mysql --port 4000 --user xxx --password
```

使用缩写的命令行参数则是：

```css
mysql -P 4000 -u xxx -p
```

## 添加用户

添加用户有两种方式：

- 通过标准的用户管理的 SQL 语句创建用户以及授予权限，比如 `CREATE USER` 和 `GRANT`。
- 直接通过 `INSERT`、`UPDATE` 和 `DELETE` 操作授权表。

推荐使用第一种方式。第二种方式修改容易导致一些不完整的修改，因此不推荐。还有另一种可选方式是使用第三方工具的图形化界面工具。

```sql
CREATE USER [IF NOT EXISTS] user [IDENTIFIED BY 'auth_string'];
```

设置登录密码后，`auth_string` 会被 TiDB 经过加密存储在 `mysql.user` 表中。

```sql
CREATE USER 'test'@'127.0.0.1' IDENTIFIED BY 'xxx';
```

TiDB 的用户账户名由一个用户名和一个主机名组成。账户名的语法为 `'user_name'@'host_name'`。

- `user_name` 大小写敏感。
- `host_name` 可以是一个主机名或 IP 地址。主机名或 IP 地址中允许使用通配符 `%` 和 `_`。例如，名为 `'%'` 的主机名可以匹配所有主机，`'192.168.1.%'` 可以匹配子网中的所有主机。

host 支持模糊匹配，比如：

```sql
CREATE USER 'test'@'192.168.10.%';
```

允许 `test` 用户从 `192.168.10` 子网的任何一个主机登录。

如果没有指定 host，则默认是所有 IP 均可登录。如果没有指定密码，默认为空：

```sql
CREATE USER 'test';
```

等价于：

```sql
CREATE USER 'test'@'%' IDENTIFIED BY '';
```

为一个不存在的用户授权时，是否会自动创建用户的行为受 `sql_mode` 影响。如果 `sql_mode` 中包含 `NO_AUTO_CREATE_USER`，则 `GRANT` 不会自动创建用户并报错。

假设 `sql_mode` 不包含 `NO_AUTO_CREATE_USER`，下面的例子用 `CREATE USER` 和 `GRANT` 语句创建了四个账户：

```sql
CREATE USER 'finley'@'localhost' IDENTIFIED BY 'some_pass';
GRANT ALL PRIVILEGES ON *.* TO 'finley'@'localhost' WITH GRANT OPTION;
CREATE USER 'finley'@'%' IDENTIFIED BY 'some_pass';
GRANT ALL PRIVILEGES ON *.* TO 'finley'@'%' WITH GRANT OPTION;
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin_pass';
GRANT RELOAD,PROCESS ON *.* TO 'admin'@'localhost';
CREATE USER 'dummy'@'localhost';
```

使用 `SHOW GRANTS` 可以看到为一个用户授予的权限：

```sql
SHOW GRANTS FOR 'admin'@'localhost';
+-----------------------------------------------------+
| Grants for admin@localhost                          |
+-----------------------------------------------------+
| GRANT RELOAD, PROCESS ON *.* TO 'admin'@'localhost' |
+-----------------------------------------------------+
```

## 删除用户

使用 `DROP USER` 语句可以删除用户，例如：

```sql
DROP USER 'test'@'localhost';
```

这个操作会清除用户在 `mysql.user` 表里面的记录项，并且清除在授权表里面的相关记录。

## 保留用户账户

TiDB 在数据库初始化时会生成一个 `'root'@'%'` 的默认账户。

## 设置资源限制

暂不支持。

## 设置（修改）密码

TiDB 将密码存在 `mysql.user` 系统数据库里面。只有拥有 `CREATE USER` 权限，或者拥有 `mysql` 数据库权限（`INSERT` 权限用于创建，`UPDATE` 权限用于更新）的用户才能够设置或修改密码。

- 在 `CREATE USER` 创建用户时通过 `IDENTIFIED BY` 指定密码：

  ```sql
  CREATE USER 'test'@'localhost' IDENTIFIED BY 'mypass';
  ```

- 为一个已存在的账户修改密码，可以通过 `SET PASSWORD FOR` 或者 `ALTER USER` 语句完成：

  ```sql
  SET PASSWORD FOR 'root'@'%' = 'xxx';
  ```

  或者：

  ```sql
  ALTER USER 'test'@'localhost' IDENTIFIED BY 'mypass';
  ```

## 忘记 `root` 密码

1. 修改配置文件，在 `security` 部分添加 `skip-grant-table`：

   ```ini
   [security]
   skip-grant-table = true
   ```

2. 使用修改之后的配置启动 TiDB，然后使用 `root` 登录后修改密码：

   ```bash
   mysql -h 127.0.0.1 -P 4000 -u root
   ```

设置 `skip-grant-table` 之后，启动 TiDB 进程会增加操作系统用户检查，只有操作系统的 `root` 用户才能启动 TiDB 进程。

## `FLUSH PRIVILEGES`

用户以及权限相关的信息都存储在 TiKV 服务器中，TiDB 在进程内部会缓存这些信息。一般通过 `CREATE USER`，`GRANT` 等语句来修改相关信息时，可在整个集群迅速生效。如果遇到网络或者其它因素影响，由于 TiDB 会周期性地更新缓存信息，正常情况下，最多 15 分钟左右生效。

如果授权表已被直接修改，则不会通知 TiDB 节点更新缓存，运行如下命令可使改动立即生效：

```sql
FLUSH PRIVILEGES;
```

详情参见[权限管理](https://docs.pingcap.com/zh/tidb/stable/privilege-management)。



# 权限管理

TiDB 支持 MySQL 5.7 的权限管理系统，包括 MySQL 的语法和权限类型。同时 TiDB 还支持 MySQL 8.0 的以下特性：

- 从 TiDB 3.0 开始，支持 SQL 角色。
- 从 TiDB 5.1 开始，支持动态权限。

本文档主要介绍 TiDB 权限相关操作、各项操作需要的权限以及权限系统的实现。

## 权限相关操作

### 授予权限

授予 `xxx` 用户对数据库 `test` 的读权限：

```sql
GRANT SELECT ON test.* TO 'xxx'@'%';
```

为 `xxx` 用户授予所有数据库，全部权限：

```sql
GRANT ALL PRIVILEGES ON *.* TO 'xxx'@'%';
```

默认情况下，如果指定的用户不存在，`GRANT` 语句将报错。该行为受 SQL Mode 中的 `NO_AUTO_CREATE_USER` 控制。

```sql
SET sql_mode=DEFAULT;
Query OK, 0 rows affected (0.00 sec)

SELECT @@sql_mode;
+-------------------------------------------------------------------------------------------------------------------------------------------+
| @@sql_mode                                                                                                                                |
+-------------------------------------------------------------------------------------------------------------------------------------------+
| ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION |
+-------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)

SELECT * FROM mysql.user WHERE user='idontexist';
Empty set (0.00 sec)

GRANT ALL PRIVILEGES ON test.* TO 'idontexist';
ERROR 1105 (HY000): You are not allowed to create a user with GRANT

SELECT user,host,authentication_string FROM mysql.user WHERE user='idontexist';
Empty set (0.00 sec)
```

在下面的例子中，由于没有将 SQL Mode 设置为 `NO_AUTO_CREATE_USER`，用户 `idontexist` 会被自动创建且密码为空。**不推荐**使用这种方式，因为会带来安全风险：如果用户名拼写错误，会导致新用户被创建且密码为空。

```sql
SET @@sql_mode='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
Query OK, 0 rows affected (0.00 sec)

SELECT @@sql_mode;
+-----------------------------------------------------------------------------------------------------------------------+
| @@sql_mode                                                                                                            |
+-----------------------------------------------------------------------------------------------------------------------+
| ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION |
+-----------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)

SELECT * FROM mysql.user WHERE user='idontexist';
Empty set (0.00 sec)

GRANT ALL PRIVILEGES ON test.* TO 'idontexist';
Query OK, 1 row affected (0.05 sec)

SELECT user,host,authentication_string FROM mysql.user WHERE user='idontexist';
+------------+------+-----------------------+
| user       | host | authentication_string |
+------------+------+-----------------------+
| idontexist | %    |                       |
+------------+------+-----------------------+
1 row in set (0.01 sec)
```

`GRANT` 还可以模糊匹配地授予用户数据库的权限：

```sql
GRANT ALL PRIVILEGES ON `te%`.* TO genius;
Query OK, 0 rows affected (0.00 sec)
SELECT user,host,db FROM mysql.db WHERE user='genius';
+--------|------|-----+
| user   | host | db  |
+--------|------|-----+
| genius | %    | te% |
+--------|------|-----+
1 row in set (0.00 sec)
```

这个例子中通过 `%` 模糊匹配，所有 `te` 开头的数据库，都被授予了权限。

### 收回权限

`REVOKE` 语句与 `GRANT` 对应：

```sql
REVOKE ALL PRIVILEGES ON `test`.* FROM 'genius'@'localhost';
```

注意

`REVOKE` 收回权限时只做精确匹配，若找不到记录则报错。而 `GRANT` 授予权限时可以使用模糊匹配。

```sql
REVOKE ALL PRIVILEGES ON `te%`.* FROM 'genius'@'%';
ERROR 1141 (42000): There is no such grant defined for user 'genius' on host '%'
```

关于模糊匹配和转义，字符串和 identifier：

```sql
GRANT ALL PRIVILEGES ON `te\%`.* TO 'genius'@'localhost';
Query OK, 0 rows affected (0.00 sec)
```

上述例子是精确匹配名为 `te%` 的数据库，注意使用 `\` 转义字符。

以单引号包含的部分，是一个字符串。以反引号包含的部分，是一个 identifier。注意下面的区别：

```sql
GRANT ALL PRIVILEGES ON 'test'.* TO 'genius'@'localhost';
ERROR 1064 (42000): You have an error in your SQL syntax; check the
manual that corresponds to your MySQL server version for the right
syntax to use near ''test'.* to 'genius'@'localhost'' at line 1
GRANT ALL PRIVILEGES ON `test`.* TO 'genius'@'localhost';
Query OK, 0 rows affected (0.00 sec)
```

如果想将一些特殊的关键字做为表名，可以用反引号包含起来。比如：

```sql
CREATE TABLE `select` (id int);
Query OK, 0 rows affected (0.27 sec)
```

### 查看为用户分配的权限

`SHOW GRANTS` 语句可以查看为用户分配了哪些权限。例如：

查看当前用户的权限：

```sql
SHOW GRANTS;
+-------------------------------------------------------------+
| Grants for User                                             |
+-------------------------------------------------------------+
| GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION |
+-------------------------------------------------------------+
```

或者：

```sql
SHOW GRANTS FOR CURRENT_USER();
```

查看某个特定用户的权限：

```sql
SHOW GRANTS FOR 'user'@'host';
```

例如，创建一个用户 `rw_user@192.168.%` 并为其授予 `test.write_table` 表的写权限，和全局读权限。

```sql
CREATE USER `rw_user`@`192.168.%`;
GRANT SELECT ON *.* TO `rw_user`@`192.168.%`;
GRANT INSERT, UPDATE ON `test`.`write_table` TO `rw_user`@`192.168.%`;
```

查看用户 `rw_user@192.168.%` 的权限。

```sql
SHOW GRANTS FOR `rw_user`@`192.168.%`;
+------------------------------------------------------------------+
| Grants for rw_user@192.168.%                                     |
+------------------------------------------------------------------+
| GRANT Select ON *.* TO 'rw_user'@'192.168.%'                     |
| GRANT Insert,Update ON test.write_table TO 'rw_user'@'192.168.%' |
+------------------------------------------------------------------+
```

### 动态权限

从 v5.1 开始，TiDB 支持 MySQL 8.0 中的动态权限特性。动态权限用于限制 `SUPER` 权限，实现对某些操作更细粒度的访问。例如，系统管理员可以使用动态权限来创建一个只能执行 `BACKUP` 和 `RESTORE` 操作的用户帐户。

动态权限包括：

- `BACKUP_ADMIN`
- `RESTORE_ADMIN`
- `ROLE_ADMIN`
- `CONNECTION_ADMIN`
- `SYSTEM_VARIABLES_ADMIN`
- `RESTRICTED_REPLICA_WRITER_ADMIN` 允许权限拥有者在 TiDB 集群开启了只读模式的情况下不受影响地执行写入或更新操作，详见 [`tidb_restricted_read_only` 配置项](https://docs.pingcap.com/zh/tidb/stable/system-variables#tidb_restricted_read_only-从-v520-版本开始引入)。

若要查看全部的动态权限，请执行 `SHOW PRIVILEGES` 语句。由于用户可使用插件来添加新的权限，因此可分配的权限列表可能因用户的 TiDB 安装情况而异。

## TiDB 各操作需要的权限

TiDB 用户目前拥有的权限可以在 `INFORMATION_SCHEMA.USER_PRIVILEGES` 表中查找到。例如：

```sql
SELECT * FROM INFORMATION_SCHEMA.USER_PRIVILEGES WHERE grantee = "'root'@'%'";
+------------+---------------+-------------------------+--------------+
| GRANTEE    | TABLE_CATALOG | PRIVILEGE_TYPE          | IS_GRANTABLE |
+------------+---------------+-------------------------+--------------+
| 'root'@'%' | def           | Select                  | YES          |
| 'root'@'%' | def           | Insert                  | YES          |
| 'root'@'%' | def           | Update                  | YES          |
| 'root'@'%' | def           | Delete                  | YES          |
| 'root'@'%' | def           | Create                  | YES          |
| 'root'@'%' | def           | Drop                    | YES          |
| 'root'@'%' | def           | Process                 | YES          |
| 'root'@'%' | def           | References              | YES          |
| 'root'@'%' | def           | Alter                   | YES          |
| 'root'@'%' | def           | Show Databases          | YES          |
| 'root'@'%' | def           | Super                   | YES          |
| 'root'@'%' | def           | Execute                 | YES          |
| 'root'@'%' | def           | Index                   | YES          |
| 'root'@'%' | def           | Create User             | YES          |
| 'root'@'%' | def           | Create Tablespace       | YES          |
| 'root'@'%' | def           | Trigger                 | YES          |
| 'root'@'%' | def           | Create View             | YES          |
| 'root'@'%' | def           | Show View               | YES          |
| 'root'@'%' | def           | Create Role             | YES          |
| 'root'@'%' | def           | Drop Role               | YES          |
| 'root'@'%' | def           | CREATE TEMPORARY TABLES | YES          |
| 'root'@'%' | def           | LOCK TABLES             | YES          |
| 'root'@'%' | def           | CREATE ROUTINE          | YES          |
| 'root'@'%' | def           | ALTER ROUTINE           | YES          |
| 'root'@'%' | def           | EVENT                   | YES          |
| 'root'@'%' | def           | SHUTDOWN                | YES          |
| 'root'@'%' | def           | RELOAD                  | YES          |
| 'root'@'%' | def           | FILE                    | YES          |
| 'root'@'%' | def           | CONFIG                  | YES          |
| 'root'@'%' | def           | REPLICATION CLIENT      | YES          |
| 'root'@'%' | def           | REPLICATION SLAVE       | YES          |
+------------+---------------+-------------------------+--------------+
31 rows in set (0.00 sec)
```

### ALTER

- 对于所有的 `ALTER` 语句，均需要用户对所操作的表拥有 `ALTER` 权限。
- 除 `ALTER...DROP` 和 `ALTER...RENAME TO` 外，均需要对所操作表拥有 `INSERT` 和 `CREATE` 权限。
- 对于 `ALTER...DROP` 语句，需要对表拥有 `DROP` 权限。
- 对于 `ALTER...RENAME TO` 语句，需要对重命名前的表拥有 `DROP` 权限，对重命名后的表拥有 `CREATE` 和 `INSERT` 权限。

注意

根据 MySQL 5.7 文档中的说明，对表进行 `ALTER` 操作需要 `INSERT` 和 `CREATE` 权限，但在 MySQL 5.7.25 版本实际情况中，该操作仅需要 `ALTER` 权限。目前，TiDB 中的 `ALTER` 权限与 MySQL 实际行为保持一致。

### BACKUP

需要拥有 `SUPER` 或者 `BACKUP_ADMIN` 权限。

### CREATE DATABASE

需要拥有全局 `CREATE` 权限。

### CREATE INDEX

需要对所操作的表拥有 `INDEX` 权限。

### CREATE TABLE

需要对要创建的表所在的数据库拥有 `CREATE` 权限；若使用 `CREATE TABLE...LIKE...` 需要对相关的表拥有 `SELECT` 权限。

### CREATE VIEW

需要拥有 `CREATE VIEW` 权限。

注意

如果当前登录用户与创建视图的用户不同，除需要 `CREATE VIEW` 权限外，还需要 `SUPER` 权限。

### DROP DATABASE

需要对数据库拥有 `DROP` 权限。

### DROP INDEX

需要对所操作的表拥有 `INDEX` 权限。

### DROP TABLES

需要对所操作的表拥有 `DROP` 权限。

### LOAD DATA

`LOAD DATA` 需要对所操作的表拥有 `INSERT` 权限。执行 `REPLACE INTO` 语句还需要对所操作的表拥有 `DELETE` 权限。

### TRUNCATE TABLE

需要对所操作的表拥有 `DROP` 权限。

### RENAME TABLE

需要对重命名前的表拥有 `ALTER` 和 `DROP` 权限，对重命名后的表拥有 `CREATE` 和 `INSERT` 权限。

### ANALYZE TABLE

需要对所操作的表拥有 `INSERT` 和 `SELECT` 权限。

### SHOW

`SHOW CREATE TABLE` 需要任意一种权限。

`SHOW CREATE VIEW` 需要 `SHOW VIEW` 权限。

`SHOW GRANTS` 需要拥有对 `mysql` 数据库的 `SELECT` 权限。如果是使用 `SHOW GRANTS` 查看当前用户权限，则不需要任何权限。

`SHOW PROCESSLIST` 需要 `SUPER` 权限来显示属于其他用户的连接。

### CREATE ROLE/USER

`CREATE ROLE` 需要 `CREATE ROLE` 权限。

`CREATE USER` 需要 `CREATE USER` 权限

### DROP ROLE/USER

`DROP ROLE` 需要 `DROP ROLE` 权限。

`DROP USER` 需要 `CREATE USER` 权限

### ALTER USER

`ALTER USER` 需要 `CREATE USER` 权限。

### GRANT

`GRANT` 需要 `GRANT` 权限并且拥有 `GRANT` 所赋予的权限。

如果在 `GRANTS` 语句中创建用户，需要有 `CREATE USER` 权限。

`GRANT ROLE` 操作需要拥有 `SUPER` 或者 `ROLE_ADMIN` 权限。

### REVOKE

`REVOKE` 需要 `GRANT` 权限并且拥有 `REVOKE` 所指定要撤销的权限。

`REVOKE ROLE` 操作需要拥有 `SUPER` 或者 `ROLE_ADMIN` 权限。

### SET GLOBAL

使用 `SET GLOBAL` 设置全局变量需要拥有 `SUPER` 或者 `SYSTEM_VARIABLES_ADMIN` 权限。

### ADMIN

需要拥有 `SUPER` 权限。

### SET DEFAULT ROLE

需要拥有 `SUPER` 权限。

### KILL

使用 `KILL` 终止其他用户的会话需要拥有 `SUPER` 或者 `CONNECTION_ADMIN` 权限。

## 权限系统的实现

### 授权表

以下几张系统表是非常特殊的表，权限相关的数据全部存储在这几张表内。

- `mysql.user`：用户账户，全局权限
- `mysql.db`：数据库级别的权限
- `mysql.tables_priv`：表级别的权限
- `mysql.columns_priv`：列级别的权限，当前暂不支持

这几张表包含了数据的生效范围和权限信息。例如，`mysql.user` 表的部分数据：

```sql
SELECT User,Host,Select_priv,Insert_priv FROM mysql.user LIMIT 1;
+------|------|-------------|-------------+
| User | Host | Select_priv | Insert_priv |
+------|------|-------------|-------------+
| root | %    | Y           | Y           |
+------|------|-------------|-------------+
1 row in set (0.00 sec)
```

这条记录中，`Host` 和 `User` 决定了 root 用户从任意主机 (%) 发送过来的连接请求可以被接受，而 `Select_priv` 和 `Insert_priv` 表示用户拥有全局的 `Select` 和 `Insert` 权限。`mysql.user` 这张表里面的生效范围是全局的。

`mysql.db` 表里面包含的 `Host` 和 `User` 决定了用户可以访问哪些数据库，权限列的生效范围是数据库。

理论上，所有权限管理相关的操作，都可以通过直接对授权表的 CRUD 操作完成。

实现层面其实也只是包装了一层语法糖。例如删除用户会执行：

```sql
DELETE FROM mysql.user WHERE user='test';
```

但是，不推荐手动修改授权表，建议使用 `DROP USER` 语句：

```sql
DROP USER 'test';
```

### 连接验证

当客户端发送连接请求时，TiDB 服务器会对登录操作进行验证。验证过程先检查 `mysql.user` 表，当某条记录的 `User` 和 `Host` 和连接请求匹配上了，再去验证 `authentication_string`。用户身份基于两部分信息，发起连接的客户端的 `Host`，以及用户名 `User`。如果 `User` 不为空，则用户名必须精确匹配。

User+Host 可能会匹配 `user` 表里面多行，为了处理这种情况，`user` 表的行是排序过的，客户端连接时会依次去匹配，并使用首次匹配到的那一行做权限验证。排序是按 `Host` 在前，`User` 在后。

### 请求验证

连接成功之后，请求验证会检测执行操作是否拥有足够的权限。

对于数据库相关请求 (`INSERT`, `UPDATE`)，先检查 `mysql.user` 表里面的用户全局权限，如果权限够，则直接可以访问。如果全局权限不足，则再检查 `mysql.db` 表。

`user` 表的权限是全局的，并且不管默认数据库是哪一个。比如 `user` 里面有 `DELETE` 权限，任何一行，任何的表，任何的数据库。

`db`表里面，User 为空是匹配匿名用户，User 里面不能有通配符。Host 和 Db 列里面可以有 `%` 和 `_`，可以模式匹配。

`user` 和 `db` 读到内存也是排序的。

`tables_priv` 和 `columns_priv` 中使用 `%` 是类似的，但是在`Db`, `Table_name`, `Column_name` 这些列不能包含 `%`。加载进来时排序也是类似的。

### 生效时机

TiDB 启动时，将一些权限检查的表加载到内存，之后使用缓存的数据来验证权限。系统会周期性的将授权表从数据库同步到缓存，生效则是由同步的周期决定，目前这个值设定的是 5 分钟。

修改了授权表，如果需要立即生效，可以手动调用：

```sql
FLUSH PRIVILEGES;
```