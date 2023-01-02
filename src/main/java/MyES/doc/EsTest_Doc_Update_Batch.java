package MyES.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EsTest_Doc_Update_Batch {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        UpdateRequest updateRequest1 = new UpdateRequest("user","user", "1005");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "金莲");
        updateRequest1.doc(map1);
        
        UpdateRequest updateRequest2 = new UpdateRequest("user","user", "1006");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "瓶儿");
        updateRequest2.doc(map2);
        
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(updateRequest1);
        bulkRequest.add(updateRequest2);
        
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        BulkItemResponse[] items = bulkResponse.getItems();
        for (BulkItemResponse item : items) {
            System.out.println("是否修改成功：" + !item.isFailed());
        }
        
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
