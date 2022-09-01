# tidb 做lighting 导入需要的权限

使用 TiDB Lightning 导入数据前，先检查环境是否满足要求，这有助于减少导入过程的错误，避免导入失败的情况。

## 下游数据库权限要求

TiDB Lightning 导入数据时，根据导入方式和启用特性等，需要下游数据库用户具备不同的权限，可参考下表：

|               | 特性                                               | 作用域                                        | 所需权限                                      | 备注                                                         |
| ------------- | -------------------------------------------------- | --------------------------------------------- | --------------------------------------------- | ------------------------------------------------------------ |
| 必需          | 基本功能                                           | 目标 table                                    | CREATE,SELECT,INSERT,UPDATE,DELETE,DROP,ALTER | DROP 仅 tidb-lightning-ctl 在执行 checkpoint-destroy-all 时需要 |
| 目标 database | CREATE                                             |                                               |                                               |                                                              |
| 必需          | tidb-backend                                       | information_schema.columns                    | SELECT                                        |                                                              |
| local-backend | mysql.tidb                                         | SELECT                                        |                                               |                                                              |
| -             | SUPER                                              |                                               |                                               |                                                              |
| -             | RESTRICTED_VARIABLES_ADMIN,RESTRICTED_TABLES_ADMIN | 当目标 TiDB 开启 SEM                          |                                               |                                                              |
| 推荐          | 冲突检测，max-error                                | lightning.task-info-schema-name 配置的 schema | SELECT,INSERT,UPDATE,DELETE,CREATE,DROP       | 如不需要，该值必须设为""                                     |
| 可选          | 并行导入                                           | lightning.meta-schema-name 配置的 schema      | SELECT,INSERT,UPDATE,DELETE,CREATE,DROP       | 如不需要，该值必须设为""                                     |
| 可选          | checkpoint.driver = “mysql”                        | checkpoint.schema 设置                        | SELECT,INSERT,UPDATE,DELETE,CREATE,DROP       | 使用数据库而非文件形式存放 checkpoint 信息时需要             |

## 下游数据库所需空间

目标 TiKV 集群必须有足够空间接收新导入的数据。除了[标准硬件配置](https://docs.pingcap.com/zh/tidb/v5.4/hardware-and-software-requirements)以外，目标 TiKV 集群的总存储空间必须大于 **数据源大小 × [副本数量](https://docs.pingcap.com/zh/tidb/v5.4/manage-cluster-faq#每个-region-的-replica-数量可配置吗调整的方法是) × 2**。例如集群默认使用 3 副本，那么总存储空间需为数据源大小的 6 倍以上。公式中的 2 倍可能难以理解，其依据是以下因素的估算空间占用：

- 索引会占据额外的空间
- RocksDB 的空间放大效应

目前无法精确计算 Dumpling 从 MySQL 导出的数据大小，但你可以用下面 SQL 语句统计信息表的 data_length 字段估算数据量：

统计所有 schema 大小，单位 MiB，注意修改 ${schema_name}

```sql
select table_schema,sum(data_length)/1024/1024 as data_length,sum(index_length)/1024/1024 as index_length,sum(data_length+index_length)/1024/1024 as sum from information_schema.tables where table_schema = "${schema_name}" group by table_schema;
```

统计最大单表，单位 MiB，注意修改 ${schema_name}

```sql
select table_name,table_schema,sum(data_length)/1024/1024 as data_length,sum(index_length)/1024/1024 as index_length,sum(data_length+index_length)/1024/1024 as sum from information_schema.tables where table_schema = "${schema_name}" group by table_name,table_schema order by sum  desc limit 5;
```

## TiDB Lightning 运行时资源要求

**操作系统**：本文档示例使用的是若干新的、纯净版 CentOS 7 实例，你可以在本地虚拟化一台主机，或在供应商提供的平台上部署一台小型的云虚拟主机。TiDB Lightning 运行过程中，默认会占满 CPU，建议单独部署在一台主机上。如果条件不允许，你可以将 TiDB Lightning 和其他组件（比如`tikv-server`）部署在同一台机器上，然后设置`region-concurrency` 配置项的值为逻辑 CPU 数的 75%，以限制 TiDB Lightning 对 CPU 资源的使用。

**内存和 CPU**：因为 TiDB Lightning 对计算机资源消耗较高，建议分配 64 GB 以上的内存以及 32 核以上的 CPU，而且确保 CPU 核数和内存（GB）比为 1:2 以上，以获取最佳性能。

**存储空间**：配置项 `sorted-kv-dir` 设置排序的键值对的临时存放地址，目标路径必须是一个空目录，目录空间须大于待导入数据集的大小。建议与 `data-source-dir` 使用不同的存储设备，独占 IO 会获得更好的导入性能，且建议优先考虑配置闪存等高性能存储介质。

**网络**：建议使用带宽 >=10Gbps 的网卡。



# TIDB 日志查询

慢日志视图

# ADMIN SHOW DDL [JOBS|QUERIES]

[ 来源:TiDB](https://docs.pingcap.com/zh/tidb/stable) 浏览 *176* [ 扫码](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#) [ 分享](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#) *2021-12-04 21:37:47*

- ADMIN SHOW DDL [JOBS|QUERIES]
  - [语法图](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#语法图)
  - 示例
    - [`ADMIN SHOW DDL`](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#ADMIN SHOW DDL)
    - [`ADMIN SHOW DDL JOBS`](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#ADMIN SHOW DDL JOBS)
    - [`ADMIN SHOW DDL JOB QUERIES`](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#ADMIN SHOW DDL JOB QUERIES)
  - [MySQL 兼容性](https://www.bookstack.cn/read/TiDB-5.3-zh/sql-statements-sql-statement-admin-show-ddl.md#MySQL 兼容性)
  - 另请参阅

title: ADMIN SHOW DDL [JOBS|QUERIES] summary: TiDB 数据库中 ADMIN SHOW DDL [JOBS|QUERIES] 的使用概况。

# ADMIN SHOW DDL [JOBS|QUERIES]

`ADMIN SHOW DDL [JOBS|QUERIES]` 语句显示了正在运行和最近完成的 DDL 作业的信息。

## 语法图

```
AdminStmt ::=    'ADMIN' ( 'SHOW' ( 'DDL' ( 'JOBS' Int64Num? WhereClauseOptional | 'JOB' 'QUERIES' NumList )? | TableName 'NEXT_ROW_ID' | 'SLOW' AdminShowSlow ) | 'CHECK' ( 'TABLE' TableNameList | 'INDEX' TableName Identifier ( HandleRange ( ',' HandleRange )* )? ) | 'RECOVER' 'INDEX' TableName Identifier | 'CLEANUP' ( 'INDEX' TableName Identifier | 'TABLE' 'LOCK' TableNameList ) | 'CHECKSUM' 'TABLE' TableNameList | 'CANCEL' 'DDL' 'JOBS' NumList | 'RELOAD' ( 'EXPR_PUSHDOWN_BLACKLIST' | 'OPT_RULE_BLACKLIST' | 'BINDINGS' ) | 'PLUGINS' ( 'ENABLE' | 'DISABLE' ) PluginNameList | 'REPAIR' 'TABLE' TableName CreateTableStmt | ( 'FLUSH' | 'CAPTURE' | 'EVOLVE' ) 'BINDINGS' )NumList ::=    Int64Num ( ',' Int64Num )*WhereClauseOptional ::=    WhereClause?
```

## 示例

### `ADMIN SHOW DDL`

可以通过 `ADMIN SHOW DDL` 语句查看当前正在运行的 DDL 作业：

```
ADMIN SHOW DDL;
ADMIN SHOW DDL;+------------+--------------------------------------+---------------+--------------+--------------------------------------+-------+| SCHEMA_VER | OWNER_ID                             | OWNER_ADDRESS | RUNNING_JOBS | SELF_ID                              | QUERY |+------------+--------------------------------------+---------------+--------------+--------------------------------------+-------+|         26 | 2d1982af-fa63-43ad-a3d5-73710683cc63 | 0.0.0.0:4000  |              | 2d1982af-fa63-43ad-a3d5-73710683cc63 |       |+------------+--------------------------------------+---------------+--------------+--------------------------------------+-------+1 row in set (0.00 sec)
```

### `ADMIN SHOW DDL JOBS`

`ADMIN SHOW DDL JOBS` 语句用于查看当前 DDL 作业队列中的所有结果（包括正在运行以及等待运行的任务）以及已执行完成的 DDL 作业队列中的最近十条结果。

```
ADMIN SHOW DDL JOBS;
ADMIN SHOW DDL JOBS;+--------+---------+--------------------+--------------+----------------------+-----------+----------+-----------+---------------------+---------------------+---------+| JOB_ID | DB_NAME | TABLE_NAME         | JOB_TYPE     | SCHEMA_STATE         | SCHEMA_ID | TABLE_ID | ROW_COUNT | START_TIME          | END_TIME            | STATE   |+--------+---------+--------------------+--------------+----------------------+-----------+----------+-----------+---------------------+---------------------+---------+|     59 | test    | t1                 | add index    | write reorganization |         1 |       55 |     88576 | 2020-08-17 07:51:58 | NULL                | running ||     60 | test    | t2                 | add index    | none                 |         1 |       57 |         0 | 2020-08-17 07:51:59 | NULL                | none    ||     58 | test    | t2                 | create table | public               |         1 |       57 |         0 | 2020-08-17 07:41:28 | 2020-08-17 07:41:28 | synced  ||     56 | test    | t1                 | create table | public               |         1 |       55 |         0 | 2020-08-17 07:41:02 | 2020-08-17 07:41:02 | synced  ||     54 | test    | t1                 | drop table   | none                 |         1 |       50 |         0 | 2020-08-17 07:41:02 | 2020-08-17 07:41:02 | synced  ||     53 | test    | t1                 | drop index   | none                 |         1 |       50 |         0 | 2020-08-17 07:35:44 | 2020-08-17 07:35:44 | synced  ||     52 | test    | t1                 | add index    | public               |         1 |       50 |    451010 | 2020-08-17 07:34:43 | 2020-08-17 07:35:16 | synced  ||     51 | test    | t1                 | create table | public               |         1 |       50 |         0 | 2020-08-17 07:34:02 | 2020-08-17 07:34:02 | synced  ||     49 | test    | t1                 | drop table   | none                 |         1 |       47 |         0 | 2020-08-17 07:34:02 | 2020-08-17 07:34:02 | synced  ||     48 | test    | t1                 | create table | public               |         1 |       47 |         0 | 2020-08-17 07:33:37 | 2020-08-17 07:33:37 | synced  ||     46 | mysql   | stats_extended     | create table | public               |         3 |       45 |         0 | 2020-08-17 06:42:38 | 2020-08-17 06:42:38 | synced  ||     44 | mysql   | opt_rule_blacklist | create table | public               |         3 |       43 |         0 | 2020-08-17 06:42:38 | 2020-08-17 06:42:38 | synced  |+--------+---------+--------------------+--------------+----------------------+-----------+----------+-----------+---------------------+---------------------+---------+12 rows in set (0.00 sec)
```

由上述 `ADMIN` 查询结果可知：

- `job_id` 为 59 的 DDL 作业当前正在进行中（`STATE` 列显示为 `running`）。`SCHEMA_STATE` 列显示了表当前处于 `write reorganization` 状态，一旦任务完成，将更改为 `public`，以便用户会话可以公开观察到状态变更。`end_time` 列显示为 `NULL`，表明当前作业的完成时间未知。
- `job_id` 为 60 的 `JOB_TYPE` 显示为 `add index`，表明正在排队等待 `job_id` 为 59 的作业完成。当作业 59 完成时，作业 60 的 `STATE` 将更改为 `running`。
- 对于破坏性的更改（例如删除索引或删除表），当作业完成时，`SCHEMA_STATE` 将变为 `none`。对于附加更改，`SCHEMA_STATE` 将变为 `public`。

若要限制表中显示的行数，可以指定 `NUM` 和 `WHERE` 条件：

```
ADMIN SHOW DDL JOBS [NUM] [WHERE where_condition];
```

- `NUM`：用于查看已经执行完成的 DDL 作业队列中最近 `NUM` 条结果；未指定时，默认值为 10。
- `WHERE`：`WHERE` 子句，用于添加过滤条件。

### `ADMIN SHOW DDL JOB QUERIES`

`ADMIN SHOW DDL JOB QUERIES` 语句用于查看 `job_id` 对应的 DDL 任务的原始 SQL 语句：

```
ADMIN SHOW DDL JOBS;ADMIN SHOW DDL JOB QUERIES 51;
ADMIN SHOW DDL JOB QUERIES 51;+--------------------------------------------------------------+| QUERY                                                        |+--------------------------------------------------------------+| CREATE TABLE t1 (id INT NOT NULL PRIMARY KEY auto_increment) |+--------------------------------------------------------------+1 row in set (0.02 sec)
```

只能在 DDL 历史作业队列中最近十条结果中搜索与 `job_id` 对应的正在运行中的 DDL 作业。

```
AdminStmt ::=
    'ADMIN' ( 'SHOW' ( 'DDL' ( 'JOBS' Int64Num? WhereClauseOptional | 'JOB' 'QUERIES' NumList )? | TableName 'NEXT_ROW_ID' | 'SLOW' AdminShowSlow ) | 'CHECK' ( 'TABLE' TableNameList | 'INDEX' TableName Identifier ( HandleRange ( ',' HandleRange )* )? ) | 'RECOVER' 'INDEX' TableName Identifier | 'CLEANUP' ( 'INDEX' TableName Identifier | 'TABLE' 'LOCK' TableNameList ) | 'CHECKSUM' 'TABLE' TableNameList | 'CANCEL' 'DDL' 'JOBS' NumList | 'RELOAD' ( 'EXPR_PUSHDOWN_BLACKLIST' | 'OPT_RULE_BLACKLIST' | 'BINDINGS' ) | 'PLUGINS' ( 'ENABLE' | 'DISABLE' ) PluginNameList | 'REPAIR' 'TABLE' TableName CreateTableStmt | ( 'FLUSH' | 'CAPTURE' | 'EVOLVE' ) 'BINDINGS' )

NumList ::=
    Int64Num ( ',' Int64Num )*

WhereClauseOptional ::=
    WhereClause?
```



