package doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class EsTest_Doc_Update {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // // 修改文档
        // 添加修改请求
        UpdateRequest updateRequest = new UpdateRequest();
        // // 全量更新 —— 准备修改数据
        // User user = new User("张三", 22, "男");
        // ObjectMapper objectMapper = new ObjectMapper();
        // String userJson = objectMapper.writeValueAsString(user);
        // // 全量更新 —— 设置修改数据
        // updateRequest.index("user").id("1001").doc(userJson, XContentType.JSON);
        
        // // 局部更新1 —— 准备修改数据
        // Map<String, Object> map = new HashMap<>();
        // map.put("age", 30);
        // updateRequest.index("user").id("1001").doc(map, XContentType.JSON);
        
        // 局部更新2 —— 准备修改数据
        updateRequest.index("user").id("1001").doc(XContentType.JSON, "gender", "女");
        
        // 发送修改请求
        UpdateResponse updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);
        
        // 获取响应数据
        System.out.println("响应结果：" + updateResponse.getResult().getLowercase());
        
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
