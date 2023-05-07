package myHTML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class readHTML {
    public static void main(String[] args) throws IOException {

//        要获取 HTML 网页中用户输入的数据，您需要使用 Java 中的一些网络编程类和方法。以下是一些基本的步骤：
//        使用 Java 中的 URL 类创建一个 URL 对象，该对象表示要访问的 HTML 页面的地址。
        URL url = new URL("file:///Users/lixiaofeng/Downloads/TEST5.HTML");
//        打开 URL 对象的连接，并获取输入流以读取 HTML 页面的内容。
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        读取 HTML 页面的内容，并解析其中的表单数据。您可以使用 Java 中的一些 HTML 解析器（如 Jsoup）或正则表达式来解析 HTML 页面。
        String line = "";
        while ((line = reader.readLine()) != null) {
            // 解析表单数据
            System.out.println("获取到输入内容："+line);
        }
//        获取表单数据后，您可以将其存储到数据库或使用其他方式进行处理。例如，如果表单包含用户名和密码字段，您可以将其存储到数据库中以进行身份验证。

// 处理表单数据
//        需要注意的是，如果您想从 HTML 表单中获取用户输入的数据，最好在 HTML 表单中使用 POST 方法提交数据。这样可以保护用户输入的隐私，避免敏感信息在 URL 参数中传递。
    }
}
