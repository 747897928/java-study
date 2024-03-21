package com.aquarius.wizard.es;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class ESIndexTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    @Test
    public void createIndex() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndicesClient indicesClient = esClient.indices();
        CreateIndexRequest request = new CreateIndexRequest("user");
        CreateIndexResponse response = indicesClient.create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        esClient.close();
    }

    @Test
    public void getIndex() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndicesClient indicesClient = esClient.indices();
        GetIndexRequest indexRequest = new GetIndexRequest("user");
        GetIndexResponse response = indicesClient.get(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getAliases());
        Map<String, MappingMetadata> mappings = response.getMappings();
        for (Map.Entry<String, MappingMetadata> entry : mappings.entrySet()) {
            String key = entry.getKey();
            MappingMetadata value = entry.getValue();
            System.out.println(key);
            System.out.println(value);
        }
        System.out.println(response.getSettings());
        esClient.close();
    }

    @Test
    public void deleteIndex() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndicesClient indicesClient = esClient.indices();
        DeleteIndexRequest indexRequest = new DeleteIndexRequest("user");
        AcknowledgedResponse response = indicesClient.delete(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
        esClient.close();
    }

}
