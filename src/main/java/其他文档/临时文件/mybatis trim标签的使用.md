# mybatis trim标签的使用
|
|
|mybatis的trim标签一般用于去除sql语句中多余的and关键字，逗号，或者给sql语句前拼接 “where“、“set“以及“values(“ 等前缀，或者添加“)“等后缀，可用于选择性插入、更新、删除或者条件查询等操作。|


---


|属性|描述|
| :-----| :-----|
|prefix|给sql语句拼接的前缀|
|suffix|给sql语句拼接的后缀|
|prefixOverrides|去除sql语句前面的关键字或者字符，该关键字或者字符由prefixOverrides属性指定，假设该属性指定为"AND"，当sql语句的开头为"AND"，trim标签将会去除该"AND"|
|suffixOverrides|去除sql语句后面的关键字或者字符，该关键字或者字符由suffixOverrides属性指定|


<font size="4">下面使用几个例子来说明trim标签的使用。</font>

---


## 1、使用trim标签去除多余的and关键字

<font size="4">有这样的一个例子：</font>

```<code>&lt;select id="findActiveBlogLike"
     resultType="Blog"&gt;
  SELECT * FROM BLOG 
  WHERE 
  &lt;if test="state != null"&gt;
    state = #{state}
  &lt;/if&gt; 
  &lt;if test="title != null"&gt;
    AND title like #{title}
  &lt;/if&gt;
  &lt;if test="author != null and author.name != null"&gt;
    AND author_name like #{author.name}
  &lt;/if&gt;
&lt;/select&gt;
</code>
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  WHERE 
  <if test="state != null">
    state = #{state}
  </if> 
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>

```

<font size="4">如果这些条件没有一个能匹配上会发生什么？最终这条 SQL 会变成这样：</font>

```<code>SELECT * FROM BLOG
WHERE
</code>
SELECT * FROM BLOG
WHERE

```

<font size="4">这会导致查询失败。如果仅仅第二个条件匹配又会怎样？这条 SQL 最终会是这样:</font>

```<code>SELECT * FROM BLOG
WHERE 
AND title like ‘someTitle’
</code>
SELECT * FROM BLOG
WHERE 
AND title like ‘someTitle’

```

<font size="4">你可以使用where标签来解决这个问题，where 元素只会在至少有一个子元素的条件返回 SQL 子句的情况下才去插入“WHERE”子句。而且，若语句的开头为“AND”或“OR”，where 元素也会将它们去除。</font>

```<code>&lt;select id="findActiveBlogLike"
     resultType="Blog"&gt;
  SELECT * FROM BLOG 
  &lt;where&gt; 
    &lt;if test="state != null"&gt;
         state = #{state}
    &lt;/if&gt; 
    &lt;if test="title != null"&gt;
        AND title like #{title}
    &lt;/if&gt;
    &lt;if test="author != null and author.name != null"&gt;
        AND author_name like #{author.name}
    &lt;/if&gt;
  &lt;/where&gt;
&lt;/select&gt;
</code>
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG 
  <where> 
    <if test="state != null">
         state = #{state}
    </if> 
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>

```

<font size="4">trim标签也可以完成相同的功能，写法如下：</font>

```<code>&lt;trim prefix="WHERE" prefixOverrides="AND"&gt;
	&lt;if test="state != null"&gt;
	  state = #{state}
	&lt;/if&gt; 
	&lt;if test="title != null"&gt;
	  AND title like #{title}
	&lt;/if&gt;
	&lt;if test="author != null and author.name != null"&gt;
	  AND author_name like #{author.name}
	&lt;/if&gt;
&lt;/trim&gt;
</code>
<trim prefix="WHERE" prefixOverrides="AND">
	<if test="state != null">
	  state = #{state}
	</if> 
	<if test="title != null">
	  AND title like #{title}
	</if>
	<if test="author != null and author.name != null">
	  AND author_name like #{author.name}
	</if>
</trim>

```

---


## 2、使用trim标签去除多余的逗号

<font size="4">有如下的例子： <img src="https://img-blog.csdn.net/20180711153736702?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3d0X2JldHRlcg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" alt="这里写图片描述"/></font>

<font size="4">如果红框里面的条件没有匹配上，sql语句会变成如下：</font>

```<code>INSERT INTO role(role_name,) VALUES(roleName,)
</code>
INSERT INTO role(role_name,) VALUES(roleName,)

```

<font size="4">插入将会失败。 使用trim标签可以解决此问题，只需做少量的修改，如下所示： <img src="https://img-blog.csdn.net/20180711154105151?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3d0X2JldHRlcg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70" alt="这里写图片描述"/></font>

<font size="4">其中最重要的属性是</font>

```<code>suffixOverrides=","
</code>
suffixOverrides=","

```

<font size="4">表示去除sql语句结尾多余的逗号.</font>

---


---


<font size="4">如果想要了解更多关余trim标签的内容，请移步《trim标签源码解析》。本文参考 Mybatis官方文档</a></font>
# **文章地址： **    https://blog.csdn.net/wt_better/article/details/80992014