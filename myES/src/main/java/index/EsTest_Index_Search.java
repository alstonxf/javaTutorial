package index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//public class EsTest_Index_Search {
//    public static void main(String[] args) throws IOException {
//        // 创建 ES 客户端
//        RestHighLevelClient esClient = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("localhost", 9200, "http")));
//
//        // 查询索引
//        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
//        GetIndexResponse getIndexResponse = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
//
//        Map<String, List<AliasMetadata>> aliases = getIndexResponse.getAliases();
//        System.out.println("查询索引别名：" + aliases);
//        Map<String, String> dataStreams = getIndexResponse.getDataStreams();
//        System.out.println("数据流：" + dataStreams);
//        Map<String, Settings> defaultSettings = getIndexResponse.getDefaultSettings();
//        System.out.println("默认设置：" + defaultSettings);
//        String[] indices = getIndexResponse.getIndices();
//        System.out.println("索引：" + Arrays.toString(indices));
//        Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();
//        System.out.println("映射：" + mappings);
//        ImmutableOpenMap<String, Settings> settings = getIndexResponse.getSettings();
//        System.out.println("设置：" + settings);
//
//        // 关闭 ES 客户端连接
//        esClient.close();
//    }
//}

public class EsTest_Index_Search {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest();
            GetIndexResponse getIndexResponse = restHighLevelClient.indices().
                    get(getIndexRequest, RequestOptions.DEFAULT);
            System.out.println(getIndexResponse.getAliases());
            System.out.println(getIndexResponse.getMappings());
            System.out.println(getIndexResponse.getSettings());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            restHighLevelClient.close();
        }
    }
}