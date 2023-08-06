package MyES.search;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.IOException;

public class EsTest_Search_Hightlight_Query {
    public static void main(String[] args) throws IOException {
        // 创建 ES 客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
        
        // 高亮查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        
        // 设置查询条件的请求体
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询字段
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "张");
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color = 'red'>");
        highlightBuilder.field("name");
        highlightBuilder.postTags("</font>");
        
        searchSourceBuilder.highlighter(highlightBuilder);
        
        searchSourceBuilder.query(termQueryBuilder);
        
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
