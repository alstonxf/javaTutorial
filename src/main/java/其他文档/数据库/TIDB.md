# TIDB - TIDB用户角色权限管理

## 一、查看TIDB 中的所有用户

我们都知道TIDB是兼容MySql的，对于TiDB 的权限管理和MySql中的权限管理也是兼容的，所以说我们完全可以将MySQL中的那一套拿来到TIDB中，是完全适用的。

另外TiDB 将用户账户及角色存储在 `mysql.user`
 系统表里面。每个账户由用户名和 host 作为标识。每个账户可以设置一个密码。

select user,host,authentication_string from mysql.user;

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_fec7e210-ebd5-11ec-8d2e-fa163eb4f6be.png)

下面我们针对TIDB数据库来演示下用户角色权限的配置。注意以下所有操作均在TIDB数据库中进行。

## 二、用户管理

### **1. 创建新用户，并指定用户的登录密码**

```
create user 'bxc' identified by 'bxc123';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_fedac0ce-ebd5-11ec-8d2e-fa163eb4f6be.png)

这种方式创建的用户不限制主机，默认为 ‘bxc’@‘%‘ 所有的主机都可以使用此账户进行连接，如果要限制主机可以在用户名后@跟主机ip即可：

```
create user 'bxc'@'172.160.158.3' identified by 'bxc123';
```

### **2. 查看 mysql.user 表，查看我们创建的用户**

```
select user, host, authentication_string from mysql.user;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_fee704b0-ebd5-11ec-8d2e-fa163eb4f6be.png)

可见已经创建成功了。

### **3. 修改用户密码**

```
alter user 'bxc' identified by 'tidb123';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff0139c0-ebd5-11ec-8d2e-fa163eb4f6be.png)

### **4. 删除用户**

```
drop user 'bxc';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff1773de-ebd5-11ec-8d2e-fa163eb4f6be.png)如果是指定了主机的用户，删除时也要携带主机ip，如：

```
drop user 'bxc'@'172.160.158.3';
```

从上面对用户的操作已经可以感觉出来，就是和我们在MYSQL中的操作相同，下面继续来感受下权限角色的设置。

## 三、用户权限管理

### **1. 赋予用户权限，比如赋予testdb库下所有表的读权限赋予bxc用户**

```
grant select on testdb.* to 'bxc';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff3118b6-ebd5-11ec-8d2e-fa163eb4f6be.png)下面我们用bxc用户，进行查询和写入测试：![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff3cc864-ebd5-11ec-8d2e-fa163eb4f6be.png)可以看到只有读的权限，没有写的权限，下面将写的权限赋予bxc用户：

```
grant insert on testdb.* to 'bxc';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff4c905a-ebd5-11ec-8d2e-fa163eb4f6be.png)

下面再次进行读和写操作就已经有权限了：

### ![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff5809a8-ebd5-11ec-8d2e-fa163eb4f6be.png)**2. 查看用户下的权限**

```
show grants  for 'bxc';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff7543f6-ebd5-11ec-8d2e-fa163eb4f6be.png)

### **3. 收回用户的权限**

```
 revoke insert on testdb.* from bxc;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff88cb7e-ebd5-11ec-8d2e-fa163eb4f6be.png)使用bxc再次进行写操作：![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ff94242e-ebd5-11ec-8d2e-fa163eb4f6be.png)![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ffa2d046-ebd5-11ec-8d2e-fa163eb4f6be.png)

## 四、角色权限管理(不常用)

### **1. 创建角色**

```
create role manager, common;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ffb2b0d8-ebd5-11ec-8d2e-fa163eb4f6be.png)角色信息也是存储在了mysql.user表中了，我们可以查询看下

```
select user, host, authentication_string,Account_locked from mysql.user;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ffc77df6-ebd5-11ec-8d2e-fa163eb4f6be.png)从上面就可以看出角色和用户是用区别的，角色的 `Account_locked`
是处于锁定状态，这也可以理解，毕竟角色我们是用来管理权限的不是用来登录的，同样角色也没有密码。

### **2. 赋予角色权限，比如赋予common角色testdb库下所有表的读权限**

```
grant select on testdb.* to common;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_ffe0a20e-ebd5-11ec-8d2e-fa163eb4f6be.png)

### **3. 赋予manager角色testdb库下所有表的写权限**

```
grant insert, update, delete on testdb.* to manager;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_000c1a9c-ebd6-11ec-8d2e-fa163eb4f6be.png)

### **4. 将角色赋予用户**

```
grant 'common' to 'bxc';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_00195b30-ebd6-11ec-8d2e-fa163eb4f6be.png)![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_002eb8b8-ebd6-11ec-8d2e-fa163eb4f6be.png)使用bxc 去查询发现还是查询不了，此时角色是空的，还需要进行角色的生效：![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_004b02d4-ebd6-11ec-8d2e-fa163eb4f6be.png)

```
set role all;
```

然后查看当前的角色：

```
select current_role();
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_0056d3de-ebd6-11ec-8d2e-fa163eb4f6be.png)并且也有了查询的权限。

### **5. 解除角色与用户的关系**

```
revoke 'common' from 'bxc';
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_00654d06-ebd6-11ec-8d2e-fa163eb4f6be.png)![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_007d5de2-ebd6-11ec-8d2e-fa163eb4f6be.png)

**6. 赋予manager角色全部的权限**

```
grant all on testdb.* to manager;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_0089a98a-ebd6-11ec-8d2e-fa163eb4f6be.png)

**7. 删除角色**

```
 drop role common;
```

![img](https://oss-emcsprod-public.modb.pro/wechatSpider/modb_20220614_009dbd4e-ebd6-11ec-8d2e-fa163eb4f6be.png)