# TIDB慢日志查看
### 1.直接去慢日志文件查看，可以通过show variables like 'tidb_slow_query_file';

查看慢日志路径。可以和mysql一样，用 pt-query-digest 直接分析慢日志文件。

MySQL [(none)]> show variables like 'tidb_slow_query_file';
+----------------------+-----------------------------------------------+
| Variable_name        | Value                                         |
+----------------------+-----------------------------------------------+
| tidb_slow_query_file | /data/tidb-deploy/tidb-4000/log/tidb_slow_query.log |
+----------------------+-----------------------------------------------+

**在logs文件夹里面有各种日志以供排查。**

### 2.tidb会将慢日志文件内容解析后存在INFORMATION_SCHEMA.SLOW_QUERY表中，可以直接select该表查看慢日志。

Select * from INFORMATION_SCHEMA.SLOW_QUERY where query like "insert into%"

### 3.查询 TopN 的慢查询
例如查看top 10的慢日志

admin show slow top 10；
或select `Query_time`, query from INFORMATION_SCHEMA.`SLOW_QUERY` where `Is_internal`=false order by `Query_time` desc limit 10;

### 4.查看近N条慢日志
例如查看近10条慢日志

admin show slow recent 10

