# shiro的具体认证流程
<img src="https://img-blog.csdnimg.cn/9094b04b349f4e5db013d6f0691d1458.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_15,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

```
以后一般继承 AuthorizingRealm（授权）即可；其继承了 AuthenticatingRealm（即身份验证），而且也间接继承了 CachingRealm（带有缓存实现）

```

```
package com.baizhi.springboot_shiro.shiro.realms;

import com.baizhi.springboot_shiro.entity.Perms;
import com.baizhi.springboot_shiro.entity.Role;
import com.baizhi.springboot_shiro.entity.User;
import com.baizhi.springboot_shiro.service.UserService;
import com.baizhi.springboot_shiro.shiro.salt.MyByteSource;
import com.baizhi.springboot_shiro.utils.ApplicationContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {<!-- -->
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {<!-- -->
        System.out.println("调用授权验证");
        //获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        //根据主审分信息获取角色和权限
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        User user = userService.findRolesByUserName(primaryPrincipal);
        if (!CollectionUtils.isEmpty(user.getRoles())){<!-- -->
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {<!-- -->
             simpleAuthorizationInfo.addRole(role.getName());

                //权限信息
                List<Perms> perms = userService.findPermsByRoleId(role.getId());
                if (!CollectionUtils.isEmpty(perms)){<!-- -->
                    perms.forEach(perm -> {<!-- -->
                        simpleAuthorizationInfo.addStringPermission(perm.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
//        if ("xiaochen".equals(primaryPrincipal)){<!-- -->
//            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//            simpleAuthorizationInfo.addRole("admin");
//            return simpleAuthorizationInfo;
//        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {<!-- -->
        String principal = (String) token.getPrincipal();
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        User user = userService.findByUsername(principal);
        if (!ObjectUtils.isEmpty(user)) {<!-- -->
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), new MyByteSource(user.getSalt()), this.getName());
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
        }

        return null;
    }
}


```
1. 自定义Realm继承**AuthorizingRealm**1. 使用doGetAuthenticationInfo方法经行认证1. 通过传入的token获取principal用户名1. 使用principal查找数据库获取user对象1. 调用SimpleAuthenticationInfo方法构造SimpleAuthenticationInfo对象并且返回1. 最后自定义的Realm的父类**AuthenticatingRealm**执行getAuthenticationInfo方法中的assertCredentialsMatch进行密码验证
```
    public final AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {<!-- -->

        AuthenticationInfo info = getCachedAuthenticationInfo(token);
        if (info == null) {<!-- -->
            //otherwise not cached, perform the lookup:
            info = doGetAuthenticationInfo(token);
            log.debug("Looked up AuthenticationInfo [{}] from doGetAuthenticationInfo", info);
            if (token != null &amp;&amp; info != null) {<!-- -->
                cacheAuthenticationInfoIfPossible(token, info);
            }
        } else {<!-- -->
            log.debug("Using cached authentication info [{}] to perform credentials matching.", info);
        }

        if (info != null) {<!-- -->
            assertCredentialsMatch(token, info);
        } else {<!-- -->
            log.debug("No AuthenticationInfo found for submitted AuthenticationToken [{}].  Returning null.", token);
        }

        return info;
    }


```

```

    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {<!-- -->
        CredentialsMatcher cm = getCredentialsMatcher();
        if (cm != null) {<!-- -->
            if (!cm.doCredentialsMatch(token, info)) {<!-- -->
                //not successful - throw an exception to indicate this:
                String msg = "Submitted credentials for token [" + token + "] did not match the expected credentials.";
                throw new IncorrectCredentialsException(msg);
            }
        } else {<!-- -->
            throw new AuthenticationException("A CredentialsMatcher must be configured in order to verify " +
                    "credentials during authentication.  If you do not wish for credentials to be examined, you " +
                    "can configure an " + AllowAllCredentialsMatcher.class.getName() + " instance.");
        }
    }

```

<img src="https://img-blog.csdnimg.cn/b189ad76a8aa45db9c5e70407a48827a.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/> **身份认证流程** <img src="https://img-blog.csdnimg.cn/c4d676451fbf4c2494727a921b7d2f0f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_14,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

```
流程如下：

首先调用 Subject.login(token) 进行登录，其会自动委托给 Security Manager，调用之前必须通过 SecurityUtils.setSecurityManager() 设置；
SecurityManager 负责真正的身份验证逻辑；它会委托给 Authenticator 进行身份验证；
Authenticator 才是真正的身份验证者，Shiro API 中核心的身份认证入口点，此处可以自定义插入自己的实现；
Authenticator 可能会委托给相应的 AuthenticationStrategy 进行多 Realm 身份验证，默认 ModularRealmAuthenticator 会调用 AuthenticationStrategy 进行多 Realm 身份验证；
Authenticator 会把相应的 token 传入 Realm，从 Realm 获取身份验证信息，如果没有返回 / 抛出异常表示身份验证成功了。此处可以配置多个 Realm，将按照相应的顺序及策略进行访问。

```

**参考他人的方法** https://blog.csdn.net/a739260008/article/details/108457418 参考他人的csdn添加链接描述</a>
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/120414539