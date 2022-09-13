# 关机/重启/注销

| 常用命令          | 作用                     |
| ----------------- | ------------------------ |
| shutdown -h now   | 即刻关机                 |
| shutdown -h 10    | 10分钟后关机             |
| shutdown -h 11:00 | 11：00关机               |
| shutdown -h +10   | 预定时间关机（10分钟后） |
| shutdown -c       | 取消指定时间关机         |
| shutdown -r now   | 重启                     |
| shutdown -r 10    | 10分钟之后重启           |
| shutdown -r 11:00 | 定时重启                 |
| reboot            | 重启                     |
| init 6            | 重启                     |
| init 0            | ⽴刻关机                 |
| telinit 0         | 关机                     |
| poweroff          | ⽴刻关机                 |
| halt              | 关机                     |
| sync              | buff数据同步到磁盘       |
| logout            | 退出登录Shell            |

# 系统信息和性能查看

| 常用命令                           | 作用                               |
| ---------------------------------- | ---------------------------------- |
| uname -a                           | 查看内核/OS/CPU信息                |
| uname -r                           | 查看内核版本                       |
| uname -m                           | 查看处理器架构                     |
| arch                               | 查看处理器架构                     |
| hostname                           | 查看计算机名                       |
| who                                | 显示当前登录系统的⽤户             |
| who am i                           | 显示登录时的⽤户名                 |
| whoami                             | 显示当前⽤户名                     |
| <u>cat /proc/version</u>           | 查看linux版本信息                  |
| <u>cat /proc/cpuinfo</u>           | 查看CPU信息                        |
| <u>cat /proc/interrupts</u>        | 查看中断                           |
| <u>cat /proc/loadavg</u>           | 查看系统负载                       |
| uptime                             | 查看系统运⾏时间、⽤户数、负载     |
| env                                | 查看系统的环境变量                 |
| l<u>susb -tv</u>                   | 查看系统USB设备信息                |
| <u>lspci -tv</u>                   | 查看系统PCI设备信息                |
| <u>lsmod</u>                       | 查看已加载的系统模块               |
| <u>grep MemTotal /proc/meminfo</u> | 查看内存总量                       |
| <u>grep MemFree /proc/meminfo</u>  | 查看空闲内存量                     |
| <u>free -m</u>                     | 查看内存⽤量和交换区⽤量           |
| date                               | 显示系统⽇期时间                   |
| cal 2021                           | 显示2021⽇历表                     |
| top                                | 动态显示cpu/内存/进程等情况        |
| <u>vmstat 1 20</u>                 | 每1秒采⼀次系统状态，采20次        |
| iostat                             | 查看io读写/cpu使⽤情况             |
| <u>sar -u 1 10</u>                 | 查询cpu使⽤情况（1秒⼀次，共10次） |
| <u>sar -d 1 10</u>                 | 查询磁盘性能                       |

# 磁盘和分区

| 常用命令                            | 作用                           |
| ----------------------------------- | ------------------------------ |
| <u>fdisk -l</u>                     | 查看所有磁盘分区               |
| <u>swapon -s</u>                    | 查看所有交换分区               |
| df -h                               | 查看磁盘使⽤情况及挂载点       |
| df -lh                              | 同上                           |
| du -sh /dir                         | 查看指定某个⽬录的⼤⼩         |
| du -sk * \| sort -rn                | 从⾼到低依次显示⽂件和⽬录⼤⼩ |
| mount /dev/hda2 /mnt/hda2           | 挂载hda2盘                     |
| mount -t ntfs /dev/sdc1 /mnt/usbhd1 | 指定⽂件系统类型挂载（如ntfs） |
| mount -o loop xxx.iso /mnt/cdrom    | 挂 载 iso ⽂ 件                |
| umount -v /dev/sda1                 | 通过设备名卸载                 |
| umount -v /mnt/mymnt                | 通过挂载点卸载                 |
| fuser -km /mnt/hda1                 | 强制卸载(慎⽤)                 |

# ⽤户和⽤户组

| 常用命令                                           | 作用                                           |
| -------------------------------------------------- | ---------------------------------------------- |
| useradd 用户名                                     | 创建⽤户                                       |
| userdel -r 用户名                                  | 删除⽤户                                       |
| usermod -g group_name user_name                    | 修改⽤户的组                                   |
| usermod -aG group_name user_name                   | 将⽤户添加到组                                 |
| usermod -s /bin/ksh -d /home/codepig –g dev 用户名 | 修改⽤户codesheep的登录Shell、主⽬录以及⽤户组 |
| groups test                                        | 查看test⽤户所在的组                           |
| groupadd group_name                                | 创建⽤户组                                     |
| groupdel group_name                                | 删除⽤户组                                     |
| groupmod -n new_name old_name                      | 重命名⽤户组                                   |
| su - user_name                                     | 登录后切换用户                                 |
| passwd                                             | 修改⼝令                                       |
| passwd 用户名                                      | 修改某⽤户的⼝令                               |
| w                                                  | 查看活动⽤户                                   |
| id 用户名                                          | 查看指定⽤户codesheep信息                      |
| last                                               | 查看⽤户登录⽇志                               |
| <u>crontab -l</u>                                  | 查看当前⽤户的计划任务                         |
| cut -d: -f1 /etc/passwd                            | 查看系统所有⽤户                               |
| cut -d: -f1 /etc/group                             | 查看系统所有组                                 |

# ⽹络和进程管理

| 常用命令                                                     | 作用                                 |
| ------------------------------------------------------------ | ------------------------------------ |
| <u>ifconfig</u>                                              | 查看⽹络接⼝属性                     |
| <u>ifconfig eth0</u>                                         | 查看某⽹卡的配置                     |
| <u>route -n</u>                                              | 查看路由表                           |
| ==netstat -lntp==                                            | 查看所有监听端⼝                     |
| ==netstat -antp==                                            | 查看已经建⽴的TCP连接                |
| ==netstat -an\|grep 8101==                                   | 搜索8101端口的连接                   |
| netstat -lutp                                                | 查看TCP/UDP的状态信息                |
| ifup eth0                                                    | 启⽤eth0⽹络设备                     |
| ifdown eth0                                                  | 禁⽤eth0⽹络设备                     |
| iptables -L                                                  | 查看iptables规则                     |
| ifconfig eth0 192.168.1.1 netmask 255.255.255.0              | 配置ip地址                           |
| dhclient eth0                                                | 以dhcp模式启⽤eth0                   |
| route add -net 0/0 gw Gateway_IP                             | 配置默认⽹关                         |
| route add -net 192.168.0.0 netmask 255.255.0.0 gw 192.168.1.1 | 配置静态路由到达⽹络'192.168.0.0/16' |
| route del 0/0 gw Gateway_IP                                  | 删除静态路由                         |
| hostname                                                     | 查看主机名                           |
| host [www.baidu.com](http://www.baidu.com)                   | 解析主机名                           |
| nslookup [www.baidu.com](http://www.baidu.com)               | 查询DNS记录，查看域名解析是否正常    |
| ==ps -ef==                                                   | ==查看所有进程==                     |
| ==ps -ef \| grep 进程名==                                    | ==过滤出你需要的进程==               |
| kill -s name                                                 | kill指定名称的进程                   |
| kill -s pid                                                  | kill指定pid的进程                    |
| top                                                          | 实时显示进程状态                     |
| vmstat 1 20                                                  | 每1秒采⼀次系统状态，采20次          |
| iostat                                                       | iostat                               |
| sar -u 1 10                                                  | 查询cpu使⽤情况（1秒⼀次，共10次）   |
| sar -d 1 10                                                  | 查询磁盘性能                         |

# 常见系统服务命令

| 常用命令                   | 作用         |
| -------------------------- | ------------ |
| chkconfig --list           | 列出系统服务 |
| service <服务名> status    | 查看某个服务 |
| service <服务名> start     | 启动某个服务 |
| service <服务名> stop      | 终⽌某个服务 |
| service <服务名> restart   | 重启某个服务 |
| systemctl status <服务名>  | 查看某个服务 |
| systemctl start <服务名>   | 启动某个服务 |
| systemctl stop <服务名>    | 终⽌某个服务 |
| systemctl restart <服务名> | 重启某个服务 |
| systemctl enable <服务名>  | 关闭⾃启动   |
| systemctl disable <服务名> | 关闭⾃启动   |

# ⽂件和⽬录操作

| 常用命令                 | 作用                                                         |
| ------------------------ | ------------------------------------------------------------ |
| cd <⽬录名>              | 进⼊某个⽬录                                                 |
| cd ..                    | 回上级⽬录                                                   |
| cd ../..                 | 回上两级⽬录                                                 |
| cd                       | 进个⼈主⽬录                                                 |
| cd -                     | 回上⼀步所在⽬录                                             |
| pwd                      | 显示当前路径                                                 |
| ls                       | 查看⽂件⽬录列表                                             |
| ls -F                    | 查看⽬录中内容（显示是⽂件还是⽬录）                         |
| ls -l                    | 查看⽂件和⽬录的详情列表                                     |
| ls -a                    | 查看隐藏⽂件                                                 |
| ls -lh                   | 查看⽂件和⽬录的详情列表（增强⽂件⼤⼩易读性）               |
| ls -lSr                  | 查看⽂件和⽬录列表（以⽂件⼤⼩升序查看）                     |
| tree                     | 查看⽂件和⽬录的树形结构                                     |
| mkdir <⽬录名>           | 创建⽬录                                                     |
| mkdir dir1 dir2          | 同时创建两个⽬录                                             |
| mkdir -p /tmp/dir1/dir2  | 创建⽬录树                                                   |
| rm -f file1              | 删除'file1'⽂件                                              |
| rmdir dir1               | 删除'dir1'⽬录                                               |
| rm -rf dir1              | 删除'dir1'⽬录和其内容                                       |
| rm -rf dir1 dir2         | 同时删除两个⽬录及其内容                                     |
| mv old_dir new_dir       | 重命名/移动⽬录                                              |
| cp file1 file2           | 复制⽂件                                                     |
| cp dir/* .               | 复制某⽬录下的所有⽂件⾄当前⽬录                             |
| cp -a dir1 dir2          | 复制⽬录                                                     |
| cp -a /tmp/dir1 .        | 复制⼀个⽬录⾄当前⽬录                                       |
| ln -s file1 link1        | 创建指向⽂件/⽬录的软链接                                    |
| ln file1 lnk1            | 创建指向⽂件/⽬录的物理链接                                  |
| find / -name file1       | 从根⽬录开始搜索⽂件/⽬录                                    |
| find / -user user1       | 搜索⽤户user1的⽂件/⽬录                                     |
| find /dir -name *.bin    | 在⽬录/dir中搜带有.bin后缀的⽂件                             |
| locate <关键词>          | 快速定位⽂件                                                 |
| locate *.mp4             | 寻找.mp4结尾的⽂件                                           |
| whereis <关键词>         | 显示某⼆进制⽂件/可执⾏⽂件的路径                            |
| which <关键词>           | 查找系统⽬录下某的⼆进制⽂件                                 |
| chmod ugo+rwx dir1       | 设置⽬录所有者(u)、群组(g)及其他⼈(o)的读（r）写(w)执⾏(x)权限 |
| chmod go-rwx dir1        | 移除群组(g)与其他⼈(o)对⽬录的读写执⾏权限                   |
| chown user1 file1        | 改变⽂件的所有者属性                                         |
| chown -R user1 dir1      | 改变⽬录的所有者属性                                         |
| chgrp group1 file1       | 改变⽂件群组                                                 |
| chown user1:group1 file1 | 改变⽂件的所有⼈和群组                                       |

# ⽂件查看和处理

| 常用命令                      | 作用                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| vi file1                      | 查看file1  输入/查找内容 往下查找，直接输入查找内容会往上查找，：set hls 打开高亮 ：set nohls 关闭高亮 |
| cat file1                     | 查看⽂件内容                                                 |
| cat -n file1                  | 查看内容并标示⾏数                                           |
| tac file1                     | 从最后⼀⾏开始反看⽂件内容                                   |
| more file1                    | more file1  命令类似 cat ，不过会以一页一页的形式显示，更方便使用者逐页阅读，而最基本的指令就是按空白键（space）就往下一页显示，按 b 键就会往回（back）一页显示，而且还有搜寻字串的功能（与 vi 相似），使用中的说明文件，请按 h 。 |
| less file1                    | 类似more命令，但允许反向操作                                 |
| head -2 file1                 | 查看⽂件前两⾏                                               |
| tail -2 file1                 | 查看⽂件后两⾏                                               |
| tail -f /log/msg              | 实时查看添加到⽂件中的内容                                   |
| grep codesheep hello.txt      | 在⽂件hello.txt中查找关键词codesheep                         |
| grep ^sheep hello.txt         | 在⽂件hello.txt中查找以sheep开头的内容                       |
| grep [0-9] hello.txt          | 选择hello.txt⽂件中所有包含数字的⾏                          |
| sed 's/s1/s2/g' hello.txt     | 将hello.txt⽂件中的s1替换成s2                                |
| sed '/^$/d' hello.txt         | 从hello.txt⽂件中删除所有空⽩⾏                              |
| sed '/ *#/d; /^$/d' hello.txt | 从hello.txt⽂件中删除所有注释和空⽩⾏                        |
| sed -e '1d' hello.txt         | 从⽂件hello.txt 中排除第⼀⾏                                 |
| sed -n '/s1/p' hello.txt      | 查看只包含关键词"s1"的⾏                                     |
| sed -e 's/ *$//' hello.txt    | 删除每⼀⾏最后的空⽩字符                                     |
| sed -e 's/s1//g' hello.txt    | 从⽂档中只删除词汇s1并保留剩余全部                           |
| sed -n '1,5p;5q' hello.txt    | 查看从第⼀⾏到第5⾏内容                                      |
| sed -n '5p;5q' hello.txt      | 查看第5⾏                                                    |
| paste file1 file2             | 合并两个⽂件或两栏的内容                                     |
| paste -d '+' file1 file2      | 合并两个⽂件或两栏的内容，中间⽤"+"区分                      |
| sort file1                    | 排序file1内容                                                |
| sort file1 file2              | 排序两个⽂件的内容                                           |
| comm -1 file1 file2           | ⽐较两个⽂件的内容(去除'file1'所含内容)                      |
| comm -2 file1 file2           | ⽐较两个⽂件的内容(去除'file2'所含内容                       |
| comm -3 file1 file2           | ⽐较两个⽂件的内容(去除两⽂件共有部分)                       |

## Linux中根据文件大小排序(document used 简称du)

sort
-n 依照数值的大小排序。
-r 以相反的顺序来排序。

du
–max-depth=<目录层数> 超过指定层数的目录后，予以忽略。
-h或–human-readable 会显示以K，M，G为单位，提高信息的可读性。
-m或–megabytes 以1MB为单位。
-s或–summarize 仅显示总计。
–exclude=<目录或文件> 略过指定的目录或文件。

**du -h** 

**du -hm**

**du -hm    * | sort -nr**

**du -hm    * | sort -nr    | head -10**

[weblogic@test logs]$ du -h * | sort -nr | head -8
1166    test2020-08-18-14:01:41.out
1132    test2020-09-21-23:05:35.out
1028    test2020-08-04-11:06:05.out
609     test2020-10-30-15:26:59.out
490     test2020-08-28-16:32:38.out
361     test2020-01-04-10:05:36.out
182     test2020-04-08-09:23:32.out
171     test2020-11-17-15:45:21.out

# 打包和解压

| 常用命令                          | 作用                      |
| --------------------------------- | ------------------------- |
| zip xxx.zip file1                 | 压缩file1⾄zip包          |
| zip -r xxx.zip file1 file2        | 将多个⽂件+⽬录压成zip包  |
| unzip xxx.zip                     | 解压zip包                 |
| tar -cvf xxx.tar file1            | 将file1创建⾮压缩tar包    |
| tar -cvf xxx.tar file1 file2 dir1 | 将多个⽂件+⽬录打tar包    |
| tar -tf xxx.tar                   | 查看tar包的内容           |
| tar -xvf xxx.tar                  | 解压tar包                 |
| tar -xvf xxx.tar -C /dir          | 将tar包解压⾄指定⽬录/dir |
| tar -cvfj xxx.tar.bz2 dir         | 创建bz2压缩包             |
| tar -jxvf xxx.tar.bz2             | 解压bz2压缩包             |
| tar -cvfz xxx.tar.gz dir          | 创建gzip压缩包            |
| tar -zxvf xxx.tar.gz              | 解压gzip压缩包            |
| bunzip2 xxx.bz2                   | 解压bz2压缩包             |
| bzip2 filename                    | 压缩⽂件                  |
| gunzip xxx.gz                     | 解压gzip压缩包            |
| gzip filename                     | 压缩⽂件                  |
| gzip -9 filename                  | 最⼤程度压缩              |

# RPM包管理命令

| 常用命令                  | 作用                          |
| ------------------------- | ----------------------------- |
| rpm -qa                   | 查看已安装的rpm包             |
| rpm -q pkg_name           | 查询某个rpm包                 |
| rpm -q --whatprovides xxx | 显示xxx功能是由哪个包提供的   |
| rpm -q --whatrequires xxx | 显示xxx功能被哪个程序包依赖的 |
| rpm -q --changelog xxx    | 显示xxx包的更改记录           |
| rpm -qi pkg_name          | 查看⼀个包的详细信息          |
| rpm -qd pkg_name          | 查询⼀个包所提供的⽂档        |
| rpm -qc pkg_name          | 查看已安装rpm包提供的配置⽂件 |
| rpm -ql pkg_name          | 查看⼀个包安装了哪些⽂件      |
| rpm -qf filename          | 查看某个⽂件属于哪个包        |
| rpm -qR pkg_name          | 查询包的依赖关系              |
| rpm -ivh xxx.rpm          | 安装rpm包                     |
| rpm -ivh --test xxx.rpm   | 测试安装rpm包                 |
| rpm -ivh --nodeps xxx.rpm | 安装rpm包时忽略依赖关系       |
| rpm -e xxx                | 卸载程序包                    |
| rpm -Fvh pkg_name         | 升级确定已安装的rpm包         |
| rpm -Uvh pkg_name         | 升级rpm包(若未安装则会安装)   |
| rpm -V pkg_name           | RPM包详细信息校验             |

# YUM包管理命令

| 常用命令                            | 作用                 |
| ----------------------------------- | -------------------- |
| yum repolist enabled                | 显示可⽤的源仓库     |
| yum search pkg_name                 | 搜索软件包           |
| yum install pkg_name                | 下载并安装软件包     |
| yum install --downloadonly pkg_name | 只 下 载 不 安 装    |
| yum list                            | 显示所有程序包       |
| yum list installed                  | 查看当前系统已安装包 |
| yum list updates                    | 查看可以更新的包列表 |
| yum check-update                    | 查看可升级的软件包   |
| yum update                          | 更新所有软件包       |
| yum update pkg_name                 | 升级指定软件包       |
| yum deplist pkg_name                | 列出软件包依赖关系   |
| yum remove pkg_name                 | 删除软件包           |
| yum clean all                       | 清除缓存             |
| yum clean packages                  | 清除缓存的软件包     |
| yum clean headers                   | 清除缓存的header     |

# DPKG包管理命令

| 常用命令             | 作用                  |
| -------------------- | --------------------- |
| dpkg -c xxx.deb      | 列出deb包的内容       |
| dpkg -i xxx.deb      | 安装/更新deb包        |
| dpkg -r pkg_name     | 移除deb包             |
| dpkg -P pkg_name     | 移除deb包(不保留配置) |
| dpkg -l              | 查看系统中已安装deb包 |
| dpkg -l pkg_name     | 显示包的⼤致信息      |
| dpkg -L pkg_name     | 查看deb包安装的⽂件   |
| dpkg -s pkg_name     | 查看包的详细信息      |
| dpkg –unpack xxx.deb | 解开deb包的内容       |

# APT软件⼯具

| 常用命令                  | 作用                   |
| ------------------------- | ---------------------- |
| apt-cache search pkg_name | 搜索程序包             |
| apt-cache show pkg_name   | 获取包的概览信息       |
| apt-get install pkg_name  | 安装/升级软件包        |
| apt-get purge pkg_name    | 卸载软件（包括配置）   |
| apt-get remove pkg_name   | 卸载软件（不包括配置） |
| apt-get update            | 更新包索引信息         |
| apt-get upgrade           | 更新已安装软件包       |
| apt-get clean             | 清理缓存               |

