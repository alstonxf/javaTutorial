# @linux查看用户操作的记录
 # 文章目录
1.使用history命令
2.自定义格式化history

## 1.使用history命令

>  
 history如下所示： 存储的日志文件 ~/.bash_History 


```bash

[root@server ~]# history | head -20
    1  passwd root
    2  crontab -l
    3  crontab -e
    4  sed -i 's#SELINUX=enforcing#SELINUX=disabled#g' /etc/selinux/config
    5  setenforce 0
    6  getenforce 0
    7  setenforce 0
    8  getenforce 0
    9  rpm -Uvh https://mirrors.aliyun.com/zabbix/zabbix/5.0/rhel/7/x86_64/zabbix-release-5.0-1.el7.noarch.rpm
   10  rpm -e zabbix-release-5.3-1.el8.noarch
   11  rpm -Uvh https://mirrors.aliyun.com/zabbix/zabbix/5.0/rhel/7/x86_64/zabbix-release-5.0-1.el7.noarch.rpm
   12  sed -i 's#http://repo.zabbix.com#https://mirrors.aliyun.com/zabbix#' /etc/yum.repos.d/zabbix.repo
   13  yum clean all &amp;&amp; yum makecache
   14  yum install zabbix-agent2 -y
   15  systemctl enable --now zabbix-agent2.service
   16  netstat -lntp |grep zabbix
   17  vim  /etc/zabbix/zabbix_agent2.conf
   18  mv /etc/zabbix/zabbix_agent2.conf /etc/zabbix/zabbix_agent2.conf.bak
   19  vim  /etc/zabbix/zabbix_agent2.conf
   20  systemctl restart zabbix-agent2.service





```

>  
 常规来说，history展示的操作记录，但是没有确切的时间，我们可以在/etc/bashrc文件中加入下列代码展示时间： 


```bash
[root@server ~]# cat /etc/bashrc |tail -5

HISTFILESIZE=2000
HISTSIZE=2000
HISTTIMEFORMAT="%Y%m%d-%H:%M:%S:  "
export HISTTIMEFORMAT

```

>  
 **添加配置后，效果如下所示：** 


```bash
[root@server ~]# history |tail -20
  984  20220418-14:36:44:  sh 1.sh 
  985  20220418-14:37:21:  vim 1.sh 
  986  20220418-14:37:30:  sh 1.sh 
  987  20220418-16:43:01:  vim 1.sh 
  988  20220418-16:43:12:  sh 1.sh 
  989  20220418-16:43:53:  vim 1.sh 
  990  20220418-16:44:03:  sh 1.sh 
  991  20220418-21:46:35:  set +o history;
  992  20220418-21:46:37:  history 
  993  20220418-21:46:50:  history 
  994  20220418-21:47:21:  last
  995  20220418-21:48:48:  lastlog 
  996  20220418-21:49:18:  lastt
  997  20220418-21:49:25:  lastb
  998  20220418-21:50:36:  lastlog 
  999  20220418-21:53:12:  vim .bash_history 
 1000  20220418-21:53:54:  vim /etc/bashrc 
 1001  20220418-21:54:49:  history 
 1002  20220418-21:59:20:  history |tail -20


```

## 2.自定义格式化history

>  
 可以自定义显示的历史数据，需要再/etc/bashrc文件中添加以下脚本即可 使用以下脚本可以格式化history 


```bash
#################### history ##################
USER_IP=`who -u am i 2>/dev/null | awk '{print $NF}' | sed -e 's/[()]//g'`
if [ "$USER_IP" = "" ]
then
USER_IP=`hostname`
fi
export HISTTIMEFORMAT="%F %T $USER_IP `whoami` "
shopt -s histappend
export PROMPT_COMMAND="history -a"
#################  history  ###################

```

>  
 格式化后的状态 


```bash
[root@server ~]# history |head -20
    1  2022-01-27 14:35:25 101.88.40.86 root service status audit
    2  2022-01-27 14:35:41 101.88.40.86 root systemctl status auditd.service 
    3  2022-01-27 14:35:47 101.88.40.86 root systemctl stop  auditd.service 
    4  2022-01-27 14:36:04 101.88.40.86 root service status auditd
    5  2022-01-27 14:36:12 101.88.40.86 root service status audit
    6  2022-01-27 14:36:29 101.88.40.86 root service auditd status
    7  2022-01-27 14:36:34 101.88.40.86 root service auditd stop 
    8  2022-01-27 14:36:37 101.88.40.86 root service auditd status
    9  2022-01-27 14:36:42 101.88.40.86 root ll
   10  2022-01-27 14:36:57 101.88.40.86 root cd /etc/audit/
   11  2022-01-27 14:36:59 101.88.40.86 root ll
   12  2022-01-27 14:37:05 101.88.40.86 root vim audit.rules
   13  2022-01-27 14:37:11 101.88.40.86 root ll
   14  2022-01-27 14:37:20 101.88.40.86 root rm -rf audit.rules
   15  2022-01-27 14:37:21 101.88.40.86 root ll
   16  2022-01-27 14:37:30 101.88.40.86 root cp /root/audit.rules .
   17  2022-01-27 14:37:31 101.88.40.86 root ll
   18  2022-01-27 14:37:36 101.88.40.86 root vim audit.rules
   19  2022-01-27 14:37:49 101.88.40.86 root service auditd start 
   20  2022-01-27 14:37:56 101.88.40.86 root service auditd status

```
# **文章地址： **    https://blog.csdn.net/weixin_55972781/article/details/124260899