package com.aquarius.wizard.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

public class ESScoreTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String index = "hotel";

    @Test
    public void functionSourceTest() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        //TF(词条频率) = 词条出现的次数/文档中词条的总数
        //TF这种如果每个文档都有A词条，那么对A词条累加这种算分就没啥意义了
        //TF-IDF算法
        esClient.close();
    }
}
