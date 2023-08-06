package MyES.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsTest_Doc_Delete_Batch {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 准备删除的请求
        DeleteRequest deleteRequest1 = new DeleteRequest("user");
        deleteRequest1.id("1002");
        DeleteRequest deleteRequest2 = new DeleteRequest("user");
        deleteRequest2.id("1003");
        DeleteRequest deleteRequest3 = new DeleteRequest("user");
        deleteRequest3.id("1004");
        
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(deleteRequest1);
        bulkRequest.add(deleteRequest2);
        bulkRequest.add(deleteRequest3);
    
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    
        BulkItemResponse[] items = bulkResponse.getItems();
        for (BulkItemResponse item : items) {
            System.out.println(item.isFailed());
        }
    
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
