package MyES.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsTest_Doc_Delete {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 删除请求
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("user");
        deleteRequest.id("1001");
        
        // 发送删除请求 获取响应
        DeleteResponse deleteResponse = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
        
        System.out.println(deleteResponse.getResult().getLowercase());
        
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
