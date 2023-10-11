package doc;

import MyES.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

public class EsTest_Doc_Insert {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // // 添加文档
        // 添加索引请求
        IndexRequest indexRequest = new IndexRequest("user","user");
        indexRequest.id("1002");
        
        User user = new User("李四", 22, "男");
        
        // 向 ES 插入数据，必须将对象转成 JSON 格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        
        // 将数据转成 JSON 字符串后，放在请求体中。
        indexRequest.source(userJson, XContentType.JSON);
        
        // 发送请求。获取响应
        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
        
        // 从响应中获取数据
        RestStatus status = indexResponse.status();
        System.out.println("响应状态：" + status.getStatus());
        DocWriteResponse.Result indexResponseResult = indexResponse.getResult();
        String lowercase = indexResponseResult.getLowercase();
        System.out.println("响应结果：" + lowercase);
        System.out.println("索引ID：" + indexResponse.getId());
        System.out.println("序列号：" + indexResponse.getSeqNo());
        System.out.println("版本号：" + indexResponse.getVersion());
        System.out.println("分片ID：" + indexResponse.getShardId().toString());
        System.out.println("分片信息：" + indexResponse.getShardInfo().toString());
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
