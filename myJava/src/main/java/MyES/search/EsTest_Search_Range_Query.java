package MyES.search;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class EsTest_Search_Range_Query {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 范围查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        
        // 设置查询条件的请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        
        rangeQuery.gte("22");
        rangeQuery.lte("23");
        
        searchSourceBuilder.query(rangeQuery);
        
        searchRequest.source(searchSourceBuilder);
        
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] searchHit = searchHits.getHits();
        for (SearchHit hit : searchHit) {
            System.out.println(hit.getSourceAsString());
        }
        
        // 关闭 ES 客户端连接
        esClient.close();
    }
}
