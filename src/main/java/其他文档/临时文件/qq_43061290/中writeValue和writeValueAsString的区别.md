# 中writeValue和writeValueAsString的区别
格式对比： writeValue（参数，obj）：直接将传入的对象序列化为json，并且返回给客户端 writeValueAsString（obj）：将传入的对象序列化为json，返回给调用者 共性： 将将对象转为json字符串 不同： writeValue（参数，obj）： 参数有四种重载形式 第一种：file 将转换后的json字符串保存到指定的file文件中 第二种：writer 将转换后的json字符串保存到字符输出流中 第二种：outputStream将转换后的json字符串保存到字节输出流中 第四种：jsonGenerator类（不常用，不作介绍

除此之外可以将代码封装

```
    /**
     * 直接将传入的对象序列化为json，并且写回客户端
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {<!-- -->
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将传入的对象序列化为json，返回
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {<!-- -->
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/104577391