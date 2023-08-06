package MyES.doc;

import MyES.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class EsTest_Doc_Insert_Batch {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // // 批量插入数据
        // 准备插入请求1
        IndexRequest indexRequest1 = new IndexRequest("user");
        indexRequest1.id("1005");
        // 准备数据1
        User user1 = new User("潘金莲", 22, "女");
        ObjectMapper mapper1 = new ObjectMapper();
        String user1Json = mapper1.writeValueAsString(user1);
        // 将数据放到请求1中
        indexRequest1.source(user1Json, XContentType.JSON);
        
        // 准备插入请求2
        IndexRequest indexRequest2 = new IndexRequest("user");
        indexRequest2.id("1006");
        // 准备数据2
        User user2 = new User("李瓶儿", 21, "女");
        ObjectMapper mapper2 = new ObjectMapper();
        String user2Json = mapper2.writeValueAsString(user2);
        // 将数据放到请求2中
        indexRequest2.source(user2Json, XContentType.JSON);
        
        // 创建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        // 将单独请求放在批量请求中
        bulkRequest.add(indexRequest1);
        bulkRequest.add(indexRequest2);
        bulkRequest.add(new IndexRequest("user").id("1001").source(XContentType.JSON, "name", "张三", "age", 20, "gender", "男"));
        bulkRequest.add(new IndexRequest("user").id("1002").source(XContentType.JSON, "name", "张四", "age", 22, "gender", "男"));
        bulkRequest.add(new IndexRequest("user").id("1003").source(XContentType.JSON, "name", "张五", "age", 24, "gender", "男"));
        bulkRequest.add(new IndexRequest("user").id("1014").source(XContentType.JSON, "name", "张六", "age", 23, "gender", "男"));
        // 执行发送批量请求
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        
        // 获取批量请求的响应
        System.out.println(bulkResponse.getTook().toString());
        BulkItemResponse[] items = bulkResponse.getItems();
        for (BulkItemResponse item : items) {
            System.out.println(!item.isFailed());
        }
        
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
