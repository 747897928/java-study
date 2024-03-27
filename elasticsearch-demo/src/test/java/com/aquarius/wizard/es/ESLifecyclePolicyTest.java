package com.aquarius.wizard.es;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndexLifecycleClient;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indexlifecycle.DeleteAction;
import org.elasticsearch.client.indexlifecycle.DeleteLifecyclePolicyRequest;
import org.elasticsearch.client.indexlifecycle.GetLifecyclePolicyRequest;
import org.elasticsearch.client.indexlifecycle.GetLifecyclePolicyResponse;
import org.elasticsearch.client.indexlifecycle.LifecycleAction;
import org.elasticsearch.client.indexlifecycle.LifecyclePolicy;
import org.elasticsearch.client.indexlifecycle.LifecyclePolicyMetadata;
import org.elasticsearch.client.indexlifecycle.Phase;
import org.elasticsearch.client.indexlifecycle.PutLifecyclePolicyRequest;
import org.elasticsearch.client.indexlifecycle.RolloverAction;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.DeleteComposableIndexTemplateRequest;
import org.elasticsearch.client.indices.GetComposableIndexTemplateRequest;
import org.elasticsearch.client.indices.GetComposableIndexTemplatesResponse;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.cluster.metadata.ComposableIndexTemplate;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2024/3/25 16:53
 */
public class ESLifecyclePolicyTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String index = "event_pipeline_realtime";

    String lifecycleName = "event_data_lifecycle";

    String indexTemplateName = "event_template";

    @Test
    public void lifecyclePolicyCreate() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndexLifecycleClient indexLifecycleClient = esClient.indexLifecycle();
        //PUT _ilm/policy/event_data_lifecycle   #创建 event_data_lifecycle 的索引生命周期管理策略
        //{
        //  "policy": {
        //    "phases": {                     # 阶段 hot warm cold delete
        //      "hot": {
        //        "actions": {                # 达到该阶段时，将会执行的操作
        //          "rollover": {             #  Rollover创建新索引 满足任意条件 条件1：索引大小达到50GB  条件2：索引自从创建起过了30天
        //            "max_size": "50GB",
        //            "max_age": "30d"
        //          }
        //        }
        //      },
        //      "delete": {
        //        "min_age": "7d",           # 进入delete阶段最少需要7天
        //        "actions": {                # 达到delete阶段时，执行索引删除操作
        //          "delete": {}
        //        }
        //      }
        //    }
        //  }
        //}
        Map<String, Phase> phases = new HashMap<>();
        // 构造 hot 阶段,构造一个滚动操作对象
        // 限制索引大小为 50GB
        ByteSizeValue maxSize = new ByteSizeValue(50, ByteSizeUnit.GB);
        // 限制索引最大年龄为 30 天
        TimeValue maxAge = TimeValue.timeValueDays(30);
        // 限制索引文档数量为 10000
        Long maxDocs = 9000000000000000000L;
        RolloverAction rolloverAction = new RolloverAction(maxSize, maxAge, maxDocs);
        Map<String, LifecycleAction> hotActions = new HashMap<>();
        hotActions.put("rollover", rolloverAction);
        Phase hotPhase = new Phase("hot", null, hotActions);
        phases.put("hot", hotPhase);

        // 构造 delete 阶段
        DeleteAction deleteAction = new DeleteAction();
        Map<String, LifecycleAction> deleteActions = new HashMap<>();
        deleteActions.put("delete", deleteAction);
        Phase deletePhase = new Phase("delete", TimeValue.timeValueDays(7), deleteActions);

        phases.put("delete", deletePhase);

        LifecyclePolicy lifecyclePolicy = new LifecyclePolicy(lifecycleName, phases);
        PutLifecyclePolicyRequest request = new PutLifecyclePolicyRequest(lifecyclePolicy);
        org.elasticsearch.client.core.AcknowledgedResponse acknowledgedResponse = indexLifecycleClient.putLifecyclePolicy(request, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());

    }


    @Test
    public void lifecyclePolicyDelete() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndexLifecycleClient indexLifecycleClient = esClient.indexLifecycle();
        DeleteLifecyclePolicyRequest request = new DeleteLifecyclePolicyRequest(lifecycleName);
        org.elasticsearch.client.core.AcknowledgedResponse acknowledgedResponse = indexLifecycleClient.deleteLifecyclePolicy(request, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());

    }

    @Test
    public void lifecyclePolicySearch() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndexLifecycleClient indexLifecycleClient = esClient.indexLifecycle();
        GetLifecyclePolicyRequest request = new GetLifecyclePolicyRequest();
        GetLifecyclePolicyResponse policyResponse = indexLifecycleClient.getLifecyclePolicy(request, RequestOptions.DEFAULT);
        ImmutableOpenMap<String, LifecyclePolicyMetadata> policies = policyResponse.getPolicies();
        for (ObjectObjectCursor<String, LifecyclePolicyMetadata> policy : policies) {
            System.out.println("policy.index = " + policy.index);
            System.out.println("policy.key = " + policy.key);
            LifecyclePolicyMetadata policyMetadata = policy.value;
            LifecyclePolicy lifecyclePolicy = policyMetadata.getPolicy();
            System.out.println("lifecyclePolicy.getName() = " + lifecyclePolicy.getName());
            Map<String, Phase> phases = lifecyclePolicy.getPhases();
            //System.out.println("lifecyclePolicy.getPhases() = " + phases);
            //这里Phase转json会序列化失败，所以得拆开来调用它的toString方法
            for (Map.Entry<String, Phase> phaseEntry : phases.entrySet()) {
                String key = phaseEntry.getKey();
                System.out.println("phaseEntry.getKey() = " + key);
                Phase phase = phaseEntry.getValue();
                String phaseString = phase.toString();
                System.out.println("phaseString = " + phaseString);

            }
        }
    }

    @Test
    public void createIndexTemplate() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        //PUT _index_template/<模板名称>
        //{
        //  ...模板定义...
        //}
        PutIndexTemplateRequest request = new PutIndexTemplateRequest(indexTemplateName);
        //这里的模式支持*这种匹配方式，但是我们不需要这种匹配方式，精确匹配就行
        List<String> indexPatterns = new ArrayList<>();
        indexPatterns.add(index);
        request.patterns(indexPatterns);

        Settings.Builder settingsBuilder = Settings.builder()
                .put("number_of_shards", 1)
                .put("index.lifecycle.name", lifecycleName)
                .put("index.lifecycle.rollover_alias", index);
        request.settings(settingsBuilder);

        AcknowledgedResponse response = esClient.indices().putTemplate(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged()) {
            System.out.println("Index template created successfully.");
        } else {
            System.out.println("Failed to create index template.");
        }
    }


    @Test
    public void queryIndexTemplate() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        GetComposableIndexTemplateRequest request = new GetComposableIndexTemplateRequest(indexTemplateName);
        GetComposableIndexTemplatesResponse response = esClient.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
        Map<String, ComposableIndexTemplate> indexTemplates = response.getIndexTemplates();
        System.out.println(indexTemplates);
    }

    @Test
    public void deleteIndexTemplate() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        DeleteComposableIndexTemplateRequest request = new DeleteComposableIndexTemplateRequest(indexTemplateName);
        AcknowledgedResponse acknowledgedResponse = esClient.indices().deleteIndexTemplate(request, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());
    }

    @Test
    public void createIndex() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        IndicesClient indicesClient = esClient.indices();
        CreateIndexRequest request = new CreateIndexRequest(index);
        String json = "{\"mappings\":{\"properties\":{\"apexId\":{\"type\":\"keyword\",\"copy_to\":\"searchContent\"},\"appId\":{\"type\":\"keyword\"},\"appVersion\":{\"type\":\"keyword\"},\"content\":{\"type\":\"text\",\"index\":false},\"createTime\":{\"type\":\"date\"},\"error\":{\"type\":\"text\",\"index\":false},\"eventCode\":{\"type\":\"keyword\",\"copy_to\":\"searchContent\"},\"eventName\":{\"type\":\"keyword\",\"copy_to\":\"searchContent\"},\"eventType\":{\"type\":\"keyword\"},\"projectId\":{\"type\":\"keyword\"},\"sendType\":{\"type\":\"keyword\"},\"ts\":{\"type\":\"long\"},\"wechatOpenId\":{\"type\":\"keyword\",\"copy_to\":\"searchContent\"},\"wechatUnionId\":{\"type\":\"keyword\",\"copy_to\":\"searchContent\"},\"searchContent\":{\"type\":\"keyword\"}}}}";
        request.source(json, XContentType.JSON);
        CreateIndexResponse response = indicesClient.create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        esClient.close();
    }

    @Test
    public void analyze() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer("ik_max_word", "张三", "李四");
        try {
            AnalyzeResponse response = esClient.indices().analyze(request, RequestOptions.DEFAULT);
            List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
            System.out.println(tokens);
        } catch (ElasticsearchStatusException e) {
            System.err.println(e.getMessage());
        }
    }

}
