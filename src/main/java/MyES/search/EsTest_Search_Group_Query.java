package MyES.search;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class EsTest_Search_Group_Query {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 分组查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        
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
