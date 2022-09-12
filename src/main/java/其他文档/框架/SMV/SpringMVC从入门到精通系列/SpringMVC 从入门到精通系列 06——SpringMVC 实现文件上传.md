# SpringMVC 从入门到精通系列 06——SpringMVC 实现文件上传
 # 文章目录

---

SpringMVC框架提供了MultipartFile对象，该对象表示上传的文件，要求变量名称必须和表单file标签的name属性名称相同。具体步骤如下：

**1、导入依赖：**

```xml
<!--文件上传组件-->
<dependency>
  <groupId>commons-fileupload</groupId>
  <artifactId>commons-fileupload</artifactId>
  <version>1.3.1</version>
</dependency>
<dependency>
  <groupId>commons-io</groupId>
  <artifactId>commons-io</artifactId>
  <version>2.4</version>
</dependency>

```
**2、配置文件解析器**

```
<!--配置文件解析器-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver" >
    <property name="maxUploadSize" value="10485760"></property><!--配置最大上传10M-->
</bean>
```

**3、编写文件上传的**JSP页面

<form action="${pageContext.request.contextPath}/user/fileUpload" method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="upload" /><br/>
    <input type="submit" value="上传" />
</form>
**4、编写文件上传的**Controller控制器

```java
@RequestMapping(path = "/fileUpload")
public String fileUpload(HttpServletRequest request, MultipartFile upload) throws Exception {
	System.out.println("文件上传");
	// 使用fileupload组件完成文件上传
	// 上传的位置
	String path = request.getSession().getServletContext().getRealPath("/uploads/");
	// 判断，该路径是否存在
	File file = new File(path);
	if(!file.exists()){
	    // 创建该文件夹
	    file.mkdirs();
	}
	
	// 说明上传文件项
	// 获取上传文件的名称
	String filename = upload.getOriginalFilename();
	// 把文件的名称设置唯一值，uuid
	String uuid = UUID.randomUUID().toString().replace("-", "");
	filename = uuid+"_"+filename;
	// 完成文件上传
	upload.transferTo(new File(path,filename));
	
	return "success";
}

```

**5、测试方法**
 <img src="https://img-blog.csdnimg.cn/20210603151935240.png#pic_left" alt="在这里插入图片描述" width="420"/>

 <img src="https://img-blog.csdnimg.cn/20210603151952719.png#pic_left" alt="在这里插入图片描述" width="300"/>

 <img src="https://img-blog.csdnimg.cn/2021060315200726.png#pic_left" alt="在这里插入图片描述" width="400"/>

 # **文章地址： **     https://blog.csdn.net/weixin_43819566/article/details/117523456