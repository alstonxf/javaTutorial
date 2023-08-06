package MyES.search;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

public class EsTest_Search_Page_Query {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 1. 条件查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        
        // 设置查询条件的请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 匹配单个属性查询 termQuery()
        searchSourceBuilder.query(QueryBuilders.termQuery("gender", "男"));
        // 2. 分页查询 第1起 from 哪条数据 = （当前页码-1）* 每页显示数据条数
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        
        // 3. 排序
        searchSourceBuilder.sort("age", SortOrder.ASC);
        
        // 4. 过滤数据
        // 包含
        String[] includes = {"name", "age"};
        // 排除
        String[] excludes = {"gender"};
        searchSourceBuilder.fetchSource(includes, excludes);
        
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
