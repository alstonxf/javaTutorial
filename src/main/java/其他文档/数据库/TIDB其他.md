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