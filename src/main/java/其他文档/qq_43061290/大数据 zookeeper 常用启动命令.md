# 大数据 zookeeper 常用启动命令
（1）分别启动Zookeeper

[atguigu@hadoop102 zookeeper-3.4.10]$ bin/zkServer.sh start

（2）查看状态

[atguigu@hadoop102 zookeeper-3.4.10]# bin/zkServer.sh status

 

1．启动客户端

[atguigu@hadoop103 zookeeper-3.4.10]$ bin/zkCli.sh

 

2．显示所有操作命令

[zk: localhost:2181(CONNECTED) 1] help

3．查看当前znode中所包含的内容

[zk: localhost:2181(CONNECTED) 0] ls /

[zookeeper]

4．查看当前节点详细数据

[zk: localhost:2181(CONNECTED) 1] ls2 /

[zookeeper]

cZxid = 0x0

ctime = Thu Jan 01 08:00:00 CST 1970

mZxid = 0x0

mtime = Thu Jan 01 08:00:00 CST 1970

pZxid = 0x0

cversion = -1

dataVersion = 0

aclVersion = 0

ephemeralOwner = 0x0

dataLength = 0

numChildren = 1

5．分别创建2个普通节点

[zk: localhost:2181(CONNECTED) 3] create /sanguo "jinlian"

Created /sanguo

[zk: localhost:2181(CONNECTED) 4] create /sanguo/shuguo "liubei"

Created /sanguo/shuguo

 

 
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/91466747