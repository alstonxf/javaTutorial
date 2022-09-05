# springMVC基础整理
## springMVC

SpringMVC的概述

```
1.三层架构
	c/s架构 b/s架构
	表现层:web,获取到前端的请求数据,然后将处理后的结果响应给用户.
	业务层:service
	持久层:dao
2.MVC的设计模型  
	MVC:模型视图控制器.
        model 模型JavaBean
        view  视图:jsp html
        controller 控制器 servlet 接受请求处理请求
3.SpringMVC
	概念:基于java实现的MVC设计模式请求驱动类型的轻量级web框架.属于SpringFramework的后续产品
	让java类成为处理请求的控制器,而无需实现任何接口,同时他还支持restful编程风格的请求.
4.优势:
	1.清晰的角色划分
		每个什么器都实现不同的功能.逻辑清晰
	2.分工明确
	3.由于命令对象就是一个POJO
	4.和Spring其他框架无缝衔接
5.和struts优劣分析:
	共同点:
		他们都是表现层框架,都是基于MVC编写的
		他们的底层都离不开原始的servletApi
		他们处理请求的机制是一个核心控制器.servlet和filter
	区别:
		Spring MVC的入口是servlet 而struts是filter过滤器
		SpringMVC是基于方法设计的,strur是基于类设计的,因此SpringMvc是单例的而strur是多例的.所以MVC户比Struts稍微快一些.省去了创建对象的时间
		SpringMVC使用更加简洁处理ajax请求更加的方便
		Struts的OGNL表达式使得页面的开发效率比SpringMVC效率更高,但执行效率没有jstl提升,尤其是表单标签远没有HTML效率高
		
		


```

## SpringMVC的入门程序

<img src="https://img-blog.csdnimg.cn/20200508154500142.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

```
入门程序的需求:
	index.jsp 写一个超链接的标签,后台接受到请求.编写方法,接收到请求方法执行,转发到jsp的成功的jsp页面上面.
	导入好jar包的坐标
	创建好xml文件
	web.xml文件里面配置servlet
1.搭建开发的环境	
	maven,页面等
2.编写入门的程序
	 1.启动服务器,加载一些配置文件
	 	dispatchServlet对象的创建
	 	Springconfig.xml被加载了,里面有IOC的扫描配置,配置了之后的话我们可以将一些类配置到Spring容器里面去.
	 2.发送请求,后台处理请求
	 	点击超链接之后,先将请求传到了servlet里面,起到了控制的作用,相当于指挥中心,请求过来通过注解@RequestMapping来找到相对应名字的方法.方法返回的是一个字符串,然后控制器找到视图解析器	InternalResourceViewResolver,用前缀加上return的字符串加上后缀就可以找到需要转发的页面,然后将其转发到新的页面上面去.就是success.jsp
	 
<bean id="internalResourceViewResolver"      class="org.springframework.web.servlet.view.InternalResourceViewResolver">
                <property name="prefix" value="/WEB-INF/pages/"></property>
                <property name="suffix" value=".jsp"></property>
</bean>
	 	



```

```
	 <!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
<!--      初始化就让他加载配置文件臊面全局-->
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springMvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>//配置了这个标签之后,我们只要已启动服务器的话这servlet就会被创建了
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
<!--    /表示发任何的请求都会经过这个servlet-->
  </servlet-mapping>
</web-app>


```

```
package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器的类
 */
@Controller//这个应该是IOC的内容.
public class HelloControl {<!-- -->
    @RequestMapping(path="/hello")//请求映射
    public String sayHello(){<!-- -->
        System.out.println("hello StringMvc");
        return "success";
    }
}


```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd">

<!--    开启注解扫描-->
    <context:component-scan base-package="com.demo"></context:component-scan>

<!--    配置视图解析器-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>


<!--    开启注解支持Mvc-->
    <mvc:annotation-driven></mvc:annotation-driven>
</beans>

```

<img src="https://img-blog.csdnimg.cn/20200508162523644.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## 入门中案例中组件介绍

<img src="https://img-blog.csdnimg.cn/20200427221035106.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

```
SpringMvc框架基于组件的方式来实现执行流程
    前端控制器:由他调用其他的组件,他是整个控制中心
    处理器映射器:找到HelloControl中的sayHello方法将其返回
    处理适配器:接受到前端控制返回的任何类的任何方法
    
    <mvc:annotation-driven->说明
    在Spring各个组件中,处理映射器,处理适配器,视图解析器成为Springmvc的三大组件.使用<mvc:an...自动加载RequestMappingHandlerAdapter(处理适配器),可用在Springmvc.xml配置文件中使用<mvc:annotation..替代注解处理器和适配器的配置



```

## @RequestMapping

```
1.作用:
	用于建立请求url和处理请求方法之间的对应关系.相当于建立了一个有标记的桥梁,指定好两端中间确定了唯一的路过来执行方法
2.可以作用在方法和类上面
	这样子可以相当于设置了好几级目录,通过多级目录来访问.访问的时候路劲要写类上面的注解里面的值然后加上方法上面的注解的名.注解放在类上面可以表示某一个特殊的类.
    @Target({<!-- -->ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Mapping
3.属性:
	@AliasFor:这个注解成对出现,互为别名
	1.value:如果只有一个属性value那么这个value可以省略不写
	2.path:接受请求的url,和value互为别名
	3.name:
	4.method:他表明可以接受的请求方式,里面的值是一个枚举类型,可以取得值是HTTP的其中请求方式.RequestMethod.POST.超链接是get的请求方式.
	5.Params:用于指定限制请求参数的条件他支持简单的表达式,要求请求参数的key和value必须和配置的一模一样
	如果这里面写的username那么超链接里面的/hello?username=hehe.否则访问不到这个方法如果是username=hehehe
	那么传的参数的值也必须是一样的.
	6.headers:用于指定限制请求消息头的条件
		发送的请求中必须包含我所限制的请求头否则请求不到该方法.
	



```

```
public @interface RequestMapping {<!-- -->

	/**
	 * Assign a name to this mapping.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used on both levels, a combined name is derived by concatenation
	 * with "#" as separator.
	 * @see org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
	 * @see org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy
	 */
	String name() default "";

	/**
	 * The primary mapping expressed by this annotation.
	 * <p>This is an alias for {@link #path}. For example
	 * {@code @RequestMapping("/foo")} is equivalent to
	 * {@code @RequestMapping(path="/foo")}.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this primary mapping, narrowing it for a specific handler method.
	 */
	@AliasFor("path")
	String[] value() default {<!-- -->};

	/**
	 * In a Servlet environment only: the path mapping URIs (e.g. "/myPath.do").
	 * Ant-style path patterns are also supported (e.g. "/myPath/*.do").
	 * At the method level, relative paths (e.g. "edit.do") are supported within
	 * the primary mapping expressed at the type level. Path mapping URIs may
	 * contain placeholders (e.g. "/${connect}")
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this primary mapping, narrowing it for a specific handler method.
	 * @see org.springframework.web.bind.annotation.ValueConstants#DEFAULT_NONE
	 * @since 4.2
	 */
	@AliasFor("value")
	String[] path() default {<!-- -->};

	/**
	 * The HTTP request methods to map to, narrowing the primary mapping:
	 * GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this HTTP method restriction (i.e. the type-level restriction
	 * gets checked before the handler method is even resolved).
	 */
	RequestMethod[] method() default {<!-- -->};

	/**
	 * The parameters of the mapped request, narrowing the primary mapping.
	 * <p>Same format for any environment: a sequence of "myParam=myValue" style
	 * expressions, with a request only mapped if each such parameter is found
	 * to have the given value. Expressions can be negated by using the "!=" operator,
	 * as in "myParam!=myValue". "myParam" style expressions are also supported,
	 * with such parameters having to be present in the request (allowed to have
	 * any value). Finally, "!myParam" style expressions indicate that the
	 * specified parameter is *not* supposed to be present in the request.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this parameter restriction (i.e. the type-level restriction
	 * gets checked before the handler method is even resolved).
	 * <p>Parameter mappings are considered as restrictions that are enforced at
	 * the type level. The primary path mapping (i.e. the specified URI value)
	 * still has to uniquely identify the target handler, with parameter mappings
	 * simply expressing preconditions for invoking the handler.
	 */
	String[] params() default {<!-- -->};

	/**
	 * The headers of the mapped request, narrowing the primary mapping.
	 * <p>Same format for any environment: a sequence of "My-Header=myValue" style
	 * expressions, with a request only mapped if each such header is found
	 * to have the given value. Expressions can be negated by using the "!=" operator,
	 * as in "My-Header!=myValue". "My-Header" style expressions are also supported,
	 * with such headers having to be present in the request (allowed to have
	 * any value). Finally, "!My-Header" style expressions indicate that the
	 * specified header is *not* supposed to be present in the request.
	 * <p>Also supports media type wildcards (*), for headers such as Accept
	 * and Content-Type. For instance,
	 * <pre class="code">
	 * &amp;#064;RequestMapping(value = "/something", headers = "content-type=text/*")
	 * </pre>
	 * will match requests with a Content-Type of "text/html", "text/plain", etc.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this header restriction (i.e. the type-level restriction
	 * gets checked before the handler method is even resolved).
	 * @see org.springframework.http.MediaType
	 */
	String[] headers() default {<!-- -->};

	/**
	 * The consumable media types of the mapped request, narrowing the primary mapping.
	 * <p>The format is a single media type or a sequence of media types,
	 * with a request only mapped if the {@code Content-Type} matches one of these media types.
	 * Examples:
	 * <pre class="code">
	 * consumes = "text/plain"
	 * consumes = {"text/plain", "application/*"}
	 * </pre>
	 * Expressions can be negated by using the "!" operator, as in "!text/plain", which matches
	 * all requests with a {@code Content-Type} other than "text/plain".
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings override
	 * this consumes restriction.
	 * @see org.springframework.http.MediaType
	 * @see javax.servlet.http.HttpServletRequest#getContentType()
	 */
	String[] consumes() default {<!-- -->};

	/**
	 * The producible media types of the mapped request, narrowing the primary mapping.
	 * <p>The format is a single media type or a sequence of media types,
	 * with a request only mapped if the {@code Accept} matches one of these media types.
	 * Examples:
	 * <pre class="code">
	 * produces = "text/plain"
	 * produces = {"text/plain", "application/*"}
	 * produces = "application/json; charset=UTF-8"
	 * </pre>
	 * <p>It affects the actual content type written, for example to produce a JSON response
	 * with UTF-8 encoding, {@code "application/json; charset=UTF-8"} should be used.
	 * <p>Expressions can be negated by using the "!" operator, as in "!text/plain", which matches
	 * all requests with a {@code Accept} other than "text/plain".
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings override
	 * this produces restriction.
	 * @see org.springframework.http.MediaType
	 */
	String[] produces() default {<!-- -->};

}


```

**Supported at the type level as well as at the method level!**
	 * When used on both levels, a combined name is derived by concatenation
	 * with "#" as separator.
	 * @see org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
	 * @see org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy
	 */
	String name() default "";

	/**
	 * The primary mapping expressed by this annotation.
	 * <p>This is an alias for {@link #path}. For example
	 * {@code @RequestMapping("/foo")} is equivalent to
	 * {@code @RequestMapping(path="/foo")}.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this primary mapping, narrowing it for a specific handler method.
	 */
	@AliasFor("path")
	String[] value() default {<!-- -->};
	
	/**
	 * In a Servlet environment only: the path mapping URIs (e.g. "/myPath.do").
	 * Ant-style path patterns are also supported (e.g. "/myPath/*.do").
	 * At the method level, relative paths (e.g. "edit.do") are supported within
	 * the primary mapping expressed at the type level. Path mapping URIs may
	 * contain placeholders (e.g. "/${connect}")
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this primary mapping, narrowing it for a specific handler method.
	 * @see org.springframework.web.bind.annotation.ValueConstants#DEFAULT_NONE
	 * @since 4.2
	 */
	@AliasFor("value")
	String[] path() default {<!-- -->};
	
	/**
	 * The HTTP request methods to map to, narrowing the primary mapping:
	 * GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this HTTP method restriction (i.e. the type-level restriction
	 * gets checked before the handler method is even resolved).
	 */
	RequestMethod[] method() default {<!-- -->};
	
	/**
	 * The parameters of the mapped request, narrowing the primary mapping.
	 * <p>Same format for any environment: a sequence of "myParam=myValue" style
	 * expressions, with a request only mapped if each such parameter is found
	 * to have the given value. Expressions can be negated by using the "!=" operator,
	 * as in "myParam!=myValue". "myParam" style expressions are also supported,
	 * with such parameters having to be present in the request (allowed to have
	 * any value). Finally, "!myParam" style expressions indicate that the
	 * specified parameter is *not* supposed to be present in the request.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this parameter restriction (i.e. the type-level restriction
	 * gets checked before the handler method is even resolved).
	 * <p>Parameter mappings are considered as restrictions that are enforced at
	 * the type level. The primary path mapping (i.e. the specified URI value)
	 * still has to uniquely identify the target handler, with parameter mappings
	 * simply expressing preconditions for invoking the handler.
	 */
	String[] params() default {<!-- -->};
	
	/**
	 * The headers of the mapped request, narrowing the primary mapping.
	 * <p>Same format for any environment: a sequence of "My-Header=myValue" style
	 * expressions, with a request only mapped if each such header is found
	 * to have the given value. Expressions can be negated by using the "!=" operator,
	 * as in "My-Header!=myValue". "My-Header" style expressions are also supported,
	 * with such headers having to be present in the request (allowed to have
	 * any value). Finally, "!My-Header" style expressions indicate that the
	 * specified header is *not* supposed to be present in the request.
	 * <p>Also supports media type wildcards (*), for headers such as Accept
	 * and Content-Type. For instance,
	 * <pre class="code">
	 * &amp;#064;RequestMapping(value = "/something", headers = "content-type=text/*")
	 * </pre>
	 * will match requests with a Content-Type of "text/html", "text/plain", etc.
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings inherit
	 * this header restriction (i.e. the type-level restriction
	 * gets checked before the handler method is even resolved).
	 * @see org.springframework.http.MediaType
	 */
	String[] headers() default {<!-- -->};
	
	/**
	 * The consumable media types of the mapped request, narrowing the primary mapping.
	 * <p>The format is a single media type or a sequence of media types,
	 * with a request only mapped if the {@code Content-Type} matches one of these media types.
	 * Examples:
	 * <pre class="code">
	 * consumes = "text/plain"
	 * consumes = {"text/plain", "application/*"}
	 * </pre>
	 * Expressions can be negated by using the "!" operator, as in "!text/plain", which matches
	 * all requests with a {@code Content-Type} other than "text/plain".
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings override
	 * this consumes restriction.
	 * @see org.springframework.http.MediaType
	 * @see javax.servlet.http.HttpServletRequest#getContentType()
	 */
	String[] consumes() default {<!-- -->};
	
	/**
	 * The producible media types of the mapped request, narrowing the primary mapping.
	 * <p>The format is a single media type or a sequence of media types,
	 * with a request only mapped if the {@code Accept} matches one of these media types.
	 * Examples:
	 * <pre class="code">
	 * produces = "text/plain"
	 * produces = {"text/plain", "application/*"}
	 * produces = "application/json; charset=UTF-8"
	 * </pre>
	 * <p>It affects the actual content type written, for example to produce a JSON response
	 * with UTF-8 encoding, {@code "application/json; charset=UTF-8"} should be used.
	 * <p>Expressions can be negated by using the "!" operator, as in "!text/plain", which matches
	 * all requests with a {@code Accept} other than "text/plain".
	 * <p>**Supported at the type level as well as at the method level!**
	 * When used at the type level, all method-level mappings override
	 * this produces restriction.
	 * @see org.springframework.http.MediaType
	 */
	String[] produces() default {<!-- -->};

}

</code></pre> 
<h2>请求参数的绑定</h2> 
<pre><code class="prism language-java">将参数封装为JavaBean对象然后进行传输.如果传输的JavaBean对象中有引用对象,那么在前端传递数据的时候name应该要写user.name..usename.money 加上引用对象的名字来封装到JavaBean里面

</code></pre> 
<h2>请求参数绑定集合参数</h2> 
<pre><code class="prism language-java">list map集合的绑定.
前端绑定表单的时候应该要写name=list[0].name....
对于map集合name=map['key']
这样子封装会简单很多


</code></pre> 
<h2>配置过滤器解决中文乱码问题</h2> 
<pre><code class="prism language-java"><!--    配置解决中文乱码的过滤器
post请求会存在中文乱码的问题-->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/</url-pattern>
    </filter-mapping>


第二种方法:
	post传来的信息是 iso 编码因此
 String finalusrename = new String(userName.getBytes("iso-8859-1"),"utf-8");
然后finalUsername就可以正常的操作了.
</code></pre> 
<h2>自定义类型转换</h2> 
<pre><code class="prism language-java">前端提交的任何数据都是字符串的类型.Springmvc在封装前端的数据到后端的JavaBen里面的时候自动进行了类型转换
但是有的格式不支持转换就需要自己定义类型转换器.
1.自定类型转换器
	需要注册一个类型转换器给到前端控制器,然后需要转换的时候

</code></pre> 
<p>1.编写代码

## 请求参数绑定集合参数

```
list map集合的绑定.
前端绑定表单的时候应该要写name=list[0].name....
对于map集合name=map['key']
这样子封装会简单很多



```

## 自定义类型转换

```
前端提交的任何数据都是字符串的类型.Springmvc在封装前端的数据到后端的JavaBen里面的时候自动进行了类型转换
但是有的格式不支持转换就需要自己定义类型转换器.
1.自定类型转换器
	需要注册一个类型转换器给到前端控制器,然后需要转换的时候


```

```
package com.demo.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转日期
 */
public class StringToConverter implements Converter<String, Date> {<!-- -->
    @Override
    public Date convert(String source) {<!-- -->
        //判断是否为空
        if(source==null){<!-- -->
            throw  new RuntimeException("请输入数据");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {<!-- -->
            Date date = simpleDateFormat.parse(source);
            return date;
        } catch (ParseException e) {<!-- -->
            throw  new RuntimeException("转换失败");
        }

    }
}


```

2.配置注册组件

```
<!--配置自定义的类型转换器-->
    <bean id="conversionServiceFactoryBean" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.demo.utils.StringToConverter"></bean>
<!--                注册新的组件,增加了新的转换功能,原来的转化功能并没有覆盖掉-->
            </set>
        </property>
    </bean>
<!-- 配置使其生效-->
<mvc:annotation-driven conversion-service="conversionServiceFactoryBean"></mvc:annotation-driven>

```

## 获取Servlet的原生API

```
   //参数列表里面写我们需要的request对象

@RequestMapping("/get")
    public String testGet(HttpServletRequest request, HttpServletResponse response){<!-- -->
        String username = request.getParameter("username");
        System.out.println(username);
        return "success";
    }

```

## @RequestParam

```
1.作用:把请求中指定的参数给控制器中的形式参数赋值.
        @Controller
        @RequestMapping(path="/anno")
        public class AnnoController {<!-- -->
            @RequestMapping(path="/testAnno")
            public String testAnno(@RequestParam(name="name") String username){<!-- -->//指定了前端传的参数的名字
                System.out.println("执行了");
                return "success";
            }
        }

```

## @RequestBody

```
作用:用于获取请求体的内容,直接使用得到的是key=value&amp;key=value...结构的数据
	get方式不适用
属性:required 是否必须含有请求体,默认值为true,当取值是true是必须要使用post,使用get方式会报错,如果使用的是false那么get我们得到的是null
后面封装json数据的话我们就会使用到这个注解.
@RequestBody这个注解加到参数列表的前面表示得到的是整个请求体的内容,用字符串来接受


```

## @PathVariable

```
作用:作用于绑定url中的占位符,例如,请求url中的/delete/{<!-- -->id} 这个{<!-- -->id}就是url的占位符,url支持占位符是Spring3.0之后加入的,是Springmvc支持rest风格url的重要标志
属性:
	value:用于指定url占位符的名称
	required:是否必须提供占位符
	
restful标称风格就是每个请求的地址都是相同的,但是每个方法的请求方式是不一样的,因此我们可以根据这个来定位需要执行的方法.如果有好几个使用 同样的请求方式呢.查询一般发送的都是请求方法.请求路径上面可以写.这个注解可以提取到占位符所提供的数据.

		@RequestMapping(path="/anno")
public class AnnoController {<!-- -->
    @RequestMapping(path="/testAnno/{sid}")
    public String testAnno(@RequestVarivble(name="sid") String id){<!-- -->
        System.out.println("执行了");
        return "success";
    }
}
	


```

## HiddenHttpMethodFilter过滤器

```
webClient 调用静态方法可以使用各种方式发送请求


```

## @RequestHeader

```
作用:用于获取消息的请求头
属性:
	value:提供消息头的名称
	required:是否必须有此消息头
注:
	在实际开发中不怎么用


```

## @CookieValue

```
获取cookie的值:



```

## @ ModelAttribute

```
作用:
	他使用修饰方法和参数
	出现在方法上面,表示当前方法会在控制器的方法执行之前执行 ,他可以修饰没返回值的方法,也可以修饰有具体返回值的方法.
	出现再参数上面上,获取指定的数据给参数赋值.
	属性:
	如果是有返回值的方法的话,将这个注解架在上面然后返回值就可以直接作为参数,如果不是的话需要加上注解
	
	@RequestMapping(value="/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("abc") User user){
        System.out.println("testModelAttribute执行了...");
        System.out.println(user);
        return "success";
    }

    @ModelAttribute
    public void showUser(String uname, Map<String,User> map){
        System.out.println("showUser执行了...");
        // 通过用户查询数据库（模拟）
        User user = new User();
        user.setUname(uname);
        user.setAge(20);
        user.setDate(new Date());
        map.put("abc",user);
    }

    /**
     * 该方法会先执行

    @ModelAttribute
    public User showUser(String uname){
        System.out.println("showUser执行了...");
        // 通过用户查询数据库（模拟）
        User user = new User();
        user.setUname(uname);
        user.setAge(20);
        user.setDate(new Date());
        return user;
    }
     */
		


```

## @SessionAttributes

```
作用:用于多次执行控制器放方法之间的参数共享
只可以作用在类上面

package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(path="/anno")
@SessionAttributes(value = {"username"})//表明将request域中的值添加到session域中
public class AnnoController {
    @RequestMapping(path="/testAnno")
    public String testAnno(@RequestParam(name="name") String username){
        System.out.println("执行了");
        return "success";
    }
    @RequestMapping("/testSessionAttribute")
    public String testSessionAttribute(Model model){
        model.addAttribute("username","zhangsan");//帮忙存到request域中
        return "success";
//其中model对象是用来将数据传到request域中的,然后ModelMap 对象中提供了get方法可以从域中取出存入的数据,因此可以实现各个域中的数据共享.
        删除域对象.可以使用SessionStatus对象,然后调用这个对象的.setComplete()方法可以清楚域中的对象.
    }
}




```

# part2

## 环境的搭建

## 响应请求和结果视图

## 返回字符串

```
就是上面的SessionAttribute的例子.一样在success里面展示存在request里面的值.


```

## 返回值为void类型.

```
没写返回值,默认的请求路径是一级目录下面的二级目录的页面,如果不存在就会报错.

@RequestMapping("/testVoid")
    public  void testVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("执行了");
/*        request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);
        return;//写这个让后面的代码不在执行.*/
  /*      System.out.println(request.getContextPath());
        String name = request.getContextPath();//打印的是虚拟目录
        System.out.println("path is :" + name);
        response.sendRedirect(request.getContextPath()+"/index.jsp");*/
                response.setContentType("text/html;charset=UTF-8");//解决中文乱码的问题.
                response.getWriter().write("你好");

    }


```

## 返回值是ModelAndView对象

```
ModelAndView是Springmvc为我们提供的一个对象,该对象也可以用做控制器方法的返回值
该对象有两个方法

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        //创建MV对象
        ModelAndView mv = new ModelAndView();
        //创建user对象
        User user = new User();
        mv.addObject("user",user);
//跳转到哪个页面
        mv.setViewName("success");
        return mv;
        }


```

## requestBody响应json数据

```
 /**
     * 模拟ajax
    之前需要读取到string类型的数据转换为json使用jakson
    现在加上两个注解,不管是读取还是写回都是直接可以自动的封装大大的简化了开发的效率
     */
    @RequestMapping("/testAjax")
    //下面的这两个注解可以直接将字符串峰装维json数据,不管是读取到的还是输入的
        public @ResponseBody User testAjax(@RequestBody User user){
        System.out.println(user);
        user.setUsername("caixiaocai");
        return user;


        }

```

## 静态资源拦截

```
<!--前端控制器哪些资源不拦截-->
    <mvc:resources mapping="/js/**" location="/js/"/>
    <!-- 设置静态资源不过滤 -->
    <mvc:resources location="/css/" mapping="/css/**"/>
    <!-- 样式 -->
    <mvc:resources location="/images/" mapping="/images/**"/>


```

## springmvc实现文件上传

```
默认的表单提交的请求体的格式是键值对的关系.
1.form表单的enctype
    multipart/form-data:不对字符编码。在使用包含文件上传控件的表单时，必须使用该值。
    默认值是:application/x-www-form-urlencoded
    默认地，表单数据会编码为 "application/x-www-form-urlencoded"。就是说，在发送到服务器之前，所有字符都会进行编码（空格转换为 "+" 加号，特殊符号转换为 ASCII HEX 值）。
2.method方法必须改为post方法,因为get方法参数的大小是有限制的,大文件的传输必须要使用post格式来进行传输
3.必须提供一个文件的选择区域
	<input type="file"/> 
	
使用Commons-fileupload组件实现文件上传,需要导入该组件相应的支撑,common-io是Commons-fileupload的支持jar包.


```

```
package com.demo.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 文件上传
     * @return
     */
    @RequestMapping("/fileUpload1")
    public String fileUpload(HttpServletRequest request) throws Exception {
        System.out.println("传统方式文件上传...");
        //文件上传的位置
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        String path = request.getRealPath("/uploads");//跟上面返回的一样.都是部署到tomCat里面的绝对路径
        String contextPath = request.getContextPath();
        request.getContextPath();
        System.out.println(contextPath);

        System.out.println(path);
        File file = new File(realPath);
        System.out.println(realPath);
        if( ! file.exists()){
            file.mkdirs();
        }
        //解析request的对象,获取文件上传项.
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> fileItems = upload.parseRequest(request);
        for (FileItem fileItem : fileItems) {
            if(fileItem.isFormField()){
                //是整形行的表单项
            }else{
                //获取到上传文件的文件名
                String fileName = fileItem.getName();
                //完成文件的上传
                //使得文件名唯一
                String uuid = UUID.randomUUID().toString().replace("-","");
                fileItem.write(new File(realPath,uuid+fileName));
                //删除临时文件
                fileItem.delete();
            }

        }
        return "success";
    }


}


```

## Springmvc上传文件的原理

```
前端的上传文件的请求,发送request请求到前端控制器,我们需要配置一个文件解析器.文件解析器解析完request后将upload请求再次发回到前端控制器.前段控制器将解析后的参数传到文件上传的方法里面.进行下载,Springmvc就是将解析功能帮忙给做了.前端文件李彪的属性名必须是和方法参数的参数名是一致的.


```

1.配置解析器对象

```
    <!-- 配置文件解析器对象，要求id名称必须是multipartResolver -->
    <bean id="multipartResolver"
          class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="10485760"/>
    </bean>

```

```
  @RequestMapping("/fileUpload2")
    public String fileUpload1(HttpServletRequest request, MultipartFile upload) throws IOException {
        System.out.println("Springmvc上传方式");
        String path = request.getRealPath("uploads");
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = upload.getName();//获取文件名
       String uuid = UUID.randomUUID().toString().replace('-',' ');
        upload.transferTo(new File(path,uuid+filename+"newWay"));

        return "success";
    }

```

## Spring跨服务器方式的文件传输

```
分服务器的目的:
	1.应用服务器;负责部署项目应用
	2.数据库服务器:运行我们的数据库
	3.缓存和消息服务器:负责处理大并发的缓存和消息
	4.文件服务器:负责存储用户上传文件的服务器
	
用户发送请求到应用服务器上传了一张图片,应用服务器将图片存到对应的图片服务器.这个就是跨服务器的文件上传.



 @RequestMapping("/fileUpload3")
    public String fileUpload3( MultipartFile upload) throws IOException {
        System.out.println("跨服务器上传方式");
    //定义上传服务器路劲
        String path = "http://localhost:9090/uploads/";
        String filename = upload.getName();//获取文件名
        String uuid = UUID.randomUUID().toString().replace('-',' ');
        //创建客户端连接
        Client client = Client.create();

        //和图片服务器进行连接
        WebResource resource = client.resource(path+filename);

        //上传文件
        resource.put(upload.getBytes());

        return "success";
    }

}

```

<img src="https://img-blog.csdnimg.cn/20200514214441604.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## request.getContextPath and request.getRealPath(’/’)

```
前者获取到相对路劲,也就是虚拟路径,
后者获取到的是绝对路径


```

## Springmvc异常处理

```
1.异常处理的思路
	1.Controller调用service,service调用dao,异常都是向上抛出的,最终DispatchServlet找到异常处理器进行异常的处理.需要给页面很好的提示信息
	需要配置一个异常处理器,前端控制器将错误给到异常出机器然后经过处理之后将友好的错误提示页面提交到页面展示给用户.不配组件的话前端会将错误直接抛给页面
	
	
2.解决办法:
	1.编写自定义的类
	2.编写异常处理器
	3.配置异常处理器


```

1.自定义一个错误类

```
package com.demo.exception;

public class SysException extends Exception {
    //存储提示信息
    private String message;


    public SysException(String message) {
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


```

2.在代码错误的地方捕获并且抛出

```
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/testException")
    public String testException() throws Exception{
        System.out.println("testException执行了");
        try {
            int a =1/0;
        } catch (Exception e) {
            //控制台打印
            e.printStackTrace();
            throw new SysException("查询所有的用户出现错误");
        }
        return "success";
    }
}


```

3.编写异常处理器

```
package com.demo.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysExceptionHandler implements HandlerExceptionResolver {
    /**
     * 异常处理器
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //准备获取到异常对象
        System.out.println("异常处理器执行了");
        SysException exception =null;
        if(e instanceof SysException){
            exception=(SysException)e;
        }else {
            exception = new SysException("系统正在维护");

        }
        //创建modelAndView
        ModelAndView mv = new ModelAndView();
        System.out.println(exception.getMessage());
        mv.addObject("msg",exception.getMessage());
        //往哪里跳转
        mv.setViewName("error");
        return mv;
    }
}


```

## xml文件中配置异常处理器

```
<!--    配置异常处理器-->
    <bean id="exceptionHandler" class="com.demo.exception.SysExceptionHandler"></bean>
配置的是自定义的异常处理器的.自己写的类    

```

## Springmvc的拦截器 HandlerIntercepator

```
相当于servlet的过滤器
1.作用
	拦截器会在请求进入controller前先进入到拦截器,然后拦截器执行一些操作之后将请求传到controller,然后controller再一次将请求发送到烂机器里面,拦截器执行完一些操作后在次放行.可以实现一些特定的功能.可以写多个拦截器,形成拦截器链.
		过滤器是servlet规范中的一部分,任何的web功能都可以使用
		拦截器:Spring框架自己的,只有使用了Springmvc框架的工程才可以使用.
		过滤器:如果赔了"/*",可以对所有的资源进行拦截.
		拦截器他只会拦截访问控制器的方法,如果访问的是jsp,html,css,image或者js是不会进行拦截的.
		他也是aop思想的提现.我们要实现自定义拦截器必须要实现:HandlerIntercepator


```

```
实现方法:
	1.编写拦截器
	2.配置拦截器


```

## 1.自定义拦截器

必须要实现HandlerInterceprot接口,里面有三个方法

```
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 重写预处理方法
     * return true直接放行,执行下一个拦截器,没有的话直接执行controller中的方法
     * return false 不放行,可以使用直接跳转
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器执行了");
        return true;
    }
}


```

## 2.配置拦截器Springmvc.xml

```
<!--    配置拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/user/*"/>
            <bean id="myInterceptor" class="com.demo.intercepor.MyInterceptor"></bean>
<!--&amp;lt;!&amp;ndash;            <mvc:exclude-mapping path=""/>&amp;ndash;&amp;gt;表示不拦截的路劲-->
        </mvc:interceptor>
    </mvc:interceptors>


```

## 拦截器HandlerInerceptor方法

```
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;

public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}


//跳转页面只能跳转一次,只执行第一次跳转.
第一个先执行的方法可以用来执行判断是否登陆.最后的方法可以用来实现资源额释放.

```

# part3

## ssm框架的整合

```
1.三层架构的
	表现层:Springmvc
	业务层:Spring ioc aop
	持久层:mybatis
2.用Spring框架去整合其他的两个框架
	用什么方式来配置呢.
	选择配置文件加上注解的文件来进行配置,因为这样子比较简单.先保证每个框架都可以实现功能然后在实现框架的整合.



```

```
1.搭建Spring的环境
	1.创建好applicationContext.xml文件.里面配置好注解的扫描,注意只有service和dao将Controller排除掉
	2.在service类上面加上注解

```

## applicationContext.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--开启注解的扫描，希望处理service和dao，controller不需要Spring框架去处理-->
    <context:component-scan base-package="cn.itcast" >
        <!--配置哪些注解不扫描-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

  

</beans>

```

```

2.搭建Springmvc环境
	1.在webappp下面的xml文件下面配上前端的控制器DispatchServlet,并且让其加载Springmvc.xml文件还需要配置过滤器解决中文乱码问题.指定编码的格式.
	2.编写Springmvc.xml
		配置注解开启,只扫描Controller
		配置视图解析器



```

```
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>



  <!--配置前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--加载springmvc.xml配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--启动服务器，创建该servlet-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

  <!--解决中文乱码的过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  
</web-app>


```

## Springmvc.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--开启注解扫描，只扫描Controller注解-->
    <context:component-scan base-package="cn.itcast">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

    <!--配置的视图解析器对象-->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--过滤静态资源-->
    <mvc:resources location="/css/" mapping="/css/**" />
    <mvc:resources location="/images/" mapping="/images/**" />
    <mvc:resources location="/js/" mapping="/js/**" />

    <!--开启SpringMVC注解的支持-->
    <mvc:annotation-driven/>

</beans>

```

## Spring整合springmvc框架

<img src="https://img-blog.csdnimg.cn/20200515142451130.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

```
Spring整合springmvc框架,启动tomCat服务器的时候,需要加载Spring的配置文件,完成依赖注入.
servletContext:最大的域对象,只会被创建一次,服务器一启动就会创建这个对象,服务器关闭时才销毁,和服务器的生命周期一样.监听器可以监听ServletContext对象的创建和销毁,在监听到ServletContext创建时就进行加载Spring配置文件创建web版工厂,存储到servletContext域中

Spring-web jar包中提供了监听器的类
	<!--  配置监听器 默认只可以加载WEB-IFO下面的applicationContext.xml文件-->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
<!--  提供参数来解决读取不到配置文件的问题-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>


```

```
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <!--配置Spring的监听器，默认只加载WEB-INF目录下的applicationContext.xml配置文件-->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!--设置配置文件的路径-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <context-param>
    <param-name/>
    <param-value/>
  </context-param>

  <!--配置前端控制器-->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--加载springmvc.xml配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--启动服务器，创建该servlet-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

  <!--解决中文乱码的过滤器-->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  
</web-app>


```

## Mybatis环境的搭建

```
我们可以使用注解的方式来配置mybatis
1.在dao的接口方法上面写注解的查询插入等语句
2.编写核心配置文件sqlMapConfig.xml



```

2.sqlMapConfig.xml文件

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!---->
<configuration>
<!--    配置环境-->
    <environments default="mysql">
        <environment id="mysql">
<!--            事务的类型-->
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///ssm"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
<!--    配置映射文件-->
    <mappers>
<!--        <mapper class="com.demo.dao.AccountDao"></mapper>  这个表明这一个类可以被扫描到-->
<!--        整个包可以被扫描到-->
        <package name="com.demo.dao"/>
    </mappers>
    
</configuration>

```

## 2.mybatis dao接口

```
package com.demo.dao;

import com.demo.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 账户接口
 */

public interface AccountDao {
    /**
     * 查找所有的
     * @return
     */
    @Select("select * from account")
    public List<Account> findAll();

    /**
     * 保存账户信息
     */
    @Insert("insert into account (name,money) values(#{name},#{money})")
    public void saveAccount( Account account);

}


```

## 3.mybatis测试类

```
package com.demo.test;

import com.demo.dao.AccountDao;
import com.demo.domain.Account;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class TestMybatis {


    @Test
    public void testMybatis() throws IOException {
        //加载配置文件
        InputStream in = TestMybatis.class.getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
        //创建代理对象
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);

        Account account = new Account();
        account.setName("hehe");
        account.setMoney(12.33d);
        accountDao.saveAccount(account);
        sqlSession.commit();//需要提交事务


        List<Account> all = accountDao.findAll();
        for (Account accounts : all) {
            System.out.println(accounts);
        }
        sqlSession.close();
        in.close();
    }
}


```

## Spring框架整合mybatis

```
想法是把dao的代理对象存到容器里面去然后service就可以调用容器中的对象来进行操作
1.编写applicationContext配置文件
	在里面整合mybatis的框架,这里就是将mybatis配置文件的呢内容转到spring文件里面去.


```

applicationContext配置

```
<!--Spring整合mybatis框架-->
<!--    配置连接池-->
    <bean class="com.mchange.v2.c3p0.ComboPooledDataSource" id="dataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
        <property name="jdbcUrl" value="jdbc:mysql:///ssm"></property>
        <property name="user" value="root"></property>
        <property name="password" value="root"></property>
    </bean>
    <!--配置sqlSessionFacroy-->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
<!--    配置AccountDao接口所在的包-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer" id="mapperScannerConfigurer">
        <property name="basePackage" value="com.demo.dao"></property>
    </bean>

```

## 配置事务管理器

```
<!--    配置Spring框架声明式事务管理-->
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="dataSourceTransactionManage">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    
<!--    配置事务管理器-->
    <tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManage">

<!--    配置事务通知-->
        <tx:attributes>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" read-only="false" isolation="DEFAULT"></tx:method>
        </tx:attributes>
    </tx:advice>


    <!--    配置AOP增强-->
    <aop:config>
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.demo.service.impl.*ServiceImpl.*(..))"></aop:advisor>
    </aop:config>

```

=“root”>

```
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer" id="mapperScannerConfigurer">
    <property name="basePackage" value="com.demo.dao"></property>
</bean>


```

```

### 配置事务管理器

```xml
<!--    配置Spring框架声明式事务管理-->
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="dataSourceTransactionManage">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    
<!--    配置事务管理器-->
    <tx:advice id="txAdvice" transaction-manager="dataSourceTransactionManage">

<!--    配置事务通知-->
        <tx:attributes>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" read-only="false" isolation="DEFAULT"></tx:method>
        </tx:attributes>
    </tx:advice>


    <!--    配置AOP增强-->
    <aop:config>
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.demo.service.impl.*ServiceImpl.*(..))"></aop:advisor>
    </aop:config>


```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--开启注解的扫描，希望处理service和dao，controller不需要Spring框架去处理-->
    <context:component-scan base-package="cn.itcast" >
        <!--配置哪些注解不扫描-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>

    <!--Spring整合MyBatis框架-->
    <!--配置连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql:///ssm"/>
        <property name="user" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <!--配置SqlSessionFactory工厂-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!--配置AccountDao接口所在包-->
    <bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.itcast.dao"/>
    </bean>

    <!--配置Spring框架声明式事务管理-->
    <!--配置事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>

    <!--配置AOP增强-->
    <aop:config>
        <aop:advisor advice-ref="txAdvice" pointcut="execution(* cn.itcast.service.impl.*ServiceImpl.*(..))"/>
    </aop:config>

</beans>

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/105800984