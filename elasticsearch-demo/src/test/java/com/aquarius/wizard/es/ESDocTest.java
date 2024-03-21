package com.aquarius.wizard.es;

import com.aquarius.wizard.common.utils.JSONUtils;
import com.aquarius.wizard.entity.User;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class ESDocTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String index = "user";

    @Test
    public void docInsert() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndexRequest request = new IndexRequest();
        String id = "1001";
        request.index(index).id(id);

        User user = new User();
        user.setName("张三");
        user.setSex("男");
        user.setAge(18);

        String userJson = JSONUtils.toJsonString(user);
        request.source(userJson, XContentType.JSON);
        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        //初次新增时为CREATED,再次新增时为UPDATED
        //请注意,这里的更新是覆盖更新,上面没有的key会被删除掉
        System.out.println(result);
        esClient.close();
    }

    @Test
    public void docUpdate() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        UpdateRequest request = new UpdateRequest();

        String id = "1001";
        request.index(index).id(id);

        //需要偶数个数的参数,前一个奇数是key,后一个偶数是value，如果出现参数个数是奇数,则会报下列的错误
        //java.lang.IllegalArgumentException: The number of object passed must be even but was [3]
        //request.doc(XContentType.JSON, "sex", "女", "language");

        //这里更新的时候,之前新增是没有language这个key的,更新的时候如果有则更新,如果没有则新增
        request.doc(XContentType.JSON, "sex", "女", "language", "Java");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
        esClient.close();
    }

    @Test
    public void docUpdate2() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        UpdateRequest request = new UpdateRequest();

        String id = "1006";
        request.index(index).id(id);
        User user = new User();
        user.setName("张三");
        user.setSex("男");
        user.setAge(18);

        Map<String, Object> map = JSONUtils.beanToMap(user);

        map.put("game", "虚幻引擎");
        map.remove("name");
        //{"age":18,"sex":"男","game":"虚幻引擎"}
        System.out.println(JSONUtils.toJsonString(map));
        //从测试结果看出,name被删除了，但是es里有name这个key,所以这个不是覆盖更新
        //es中的结果
        //{
        //    "_source": {
        //        "name": "张三",
        //        "age": 18,
        //        "sex": "男",
        //        "game": "虚幻引擎"
        //    }
        //}
        request.doc(map, XContentType.JSON);

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);
        esClient.close();
    }

    @Test
    public void docGet() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        GetRequest request = new GetRequest();
        String id = "1001";
        request.index(index).id(id);

        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        esClient.close();
    }

    @Test
    public void docDelete() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        DeleteRequest request = new DeleteRequest();
        String id = "1001";
        request.index(index).id(id);

        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
        esClient.close();
    }

    @Test
    public void docBatchInsert() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index(index).id("1001").source(XContentType.JSON, "name", "lili", "sex", "女", "language", "Java"));

        User user = new User();
        user.setName("张三");
        user.setSex("男");
        user.setAge(18);

        String json = JSONUtils.toJsonString(user);
        bulkRequest.add(new IndexRequest().index(index).id("1002").source(json, XContentType.JSON));

        Map<String, Object> map = JSONUtils.beanToMap(user);
        map.put("game", "虚幻引擎");
        map.remove("name");
        bulkRequest.add(new IndexRequest().index(index).id("1003").source(map));

        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        BulkItemResponse[] responseItems = response.getItems();
        for (BulkItemResponse responseItem : responseItems) {
            System.out.println(responseItem.isFailed());
        }
        esClient.close();
    }

    @Test
    public void docBatchDelete() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new DeleteRequest().index(index).id("1001"));
        bulkRequest.add(new DeleteRequest().index(index).id("1002"));
        bulkRequest.add(new DeleteRequest().index(index).id("1003"));

        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.getTook());
        BulkItemResponse[] responseItems = response.getItems();
        for (BulkItemResponse responseItem : responseItems) {
            System.out.println(responseItem.isFailed());
        }
        esClient.close();
    }
}
