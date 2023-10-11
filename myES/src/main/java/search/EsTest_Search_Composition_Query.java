package search;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class EsTest_Search_Composition_Query {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 1. 组合查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        
        // 设置查询条件的请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 组合条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        // boolQueryBuilder.must(QueryBuilders.matchQuery("age", 21));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("gender", "男"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 23));
        
        // 范围查询
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").gte(20));
        
        searchSourceBuilder.query(boolQueryBuilder);
        
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
