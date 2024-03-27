package com.aquarius.wizard.es;

import com.aquarius.wizard.common.utils.JSONUtils;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.fasterxml.jackson.core.type.TypeReference;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndexLifecycleClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indexlifecycle.DeleteAction;
import org.elasticsearch.client.indexlifecycle.GetLifecyclePolicyRequest;
import org.elasticsearch.client.indexlifecycle.GetLifecyclePolicyResponse;
import org.elasticsearch.client.indexlifecycle.LifecycleAction;
import org.elasticsearch.client.indexlifecycle.LifecyclePolicy;
import org.elasticsearch.client.indexlifecycle.LifecyclePolicyMetadata;
import org.elasticsearch.client.indexlifecycle.Phase;
import org.elasticsearch.client.indexlifecycle.PutLifecyclePolicyRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetComposableIndexTemplateRequest;
import org.elasticsearch.client.indices.GetComposableIndexTemplatesResponse;
import org.elasticsearch.client.indices.GetIndexTemplatesRequest;
import org.elasticsearch.client.indices.GetIndexTemplatesResponse;
import org.elasticsearch.client.indices.IndexTemplateMetadata;
import org.elasticsearch.client.indices.PutComposableIndexTemplateRequest;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.ComposableIndexTemplate;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.cluster.metadata.Template;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaoyijie
 * @since 2024/3/27 16:44
 */
public class ESIlmTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String indexAlias = "event_pipeline_realtime";

    String indexTemplateName = "event_template";

    //todo 这个不完整，不建议java api操作，建议rest api
    @Test
    public void testIlm() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);

        ClusterUpdateSettingsRequest clusterUpdateSettingsRequest = new ClusterUpdateSettingsRequest();
        Map<String, Object> persistentSettingMap = new HashMap<>();
        persistentSettingMap.put("indices.lifecycle.poll_interval", "10s");
        clusterUpdateSettingsRequest.persistentSettings(persistentSettingMap);
        ClusterUpdateSettingsResponse clusterUpdateSettingsResponse = esClient.cluster().putSettings(clusterUpdateSettingsRequest, RequestOptions.DEFAULT);
        System.out.println(clusterUpdateSettingsResponse.isAcknowledged());

        String lifecycleName = "nexus_edt_event_data_lifecycle";
        String indexPatternsBase = "event_pipeline_realtime-";
        String indexPatterns = indexPatternsBase + "*";
        String indexInitName = indexPatternsBase + "000001";

        TimeValue timeValue = TimeValue.timeValueMinutes(5);
        //创建ILM策略
        boolean b = createLifecyclePolicy(esClient, lifecycleName, timeValue);
        System.out.println("创建ILM策略是否成功 = " + b);
        //PUT _index_template/<模板名称>
        //{
        //  ...模板定义...
        //}

        //https://stackoverflow.com/questions/67769047/elasticsearch-index-template-not-found
        //我在使用 Elasticsearch 版本 7.10.2 的 Rest High Level Client 时遇到了同样的问题。
        //当我记录请求的 URL 时，我可以看到它PutIndexTemplateRequest使用 API _template，而_index_template不是在稍后通过CreateIndexRequest.
        //解决方案是使用PutComposableIndexTemplateRequest：
        PutComposableIndexTemplateRequest request = new PutComposableIndexTemplateRequest();
        request.name(indexTemplateName);
        Settings settings = Settings.builder()
                .put("number_of_replicas", 1)
                .put("index.lifecycle.name", lifecycleName)
                .put("index.lifecycle.rollover_alias", indexAlias)
                //.loadFromSource(json, XContentType.JSON)
                .build();

        String mappingJson = "{\n" +
                "      \"properties\": {\n" +
                "        \"apexId\": {\n" +
                "          \"type\": \"keyword\",\n" +
                "          \"copy_to\": \"searchContent\"\n" +
                "        },\n" +
                "        \"appId\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"appVersion\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"content\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"index\": false\n" +
                "        },\n" +
                "        \"createTime\": {\n" +
                "          \"type\": \"date\"\n,\"format\":\"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis\"" +
                "        },\n" +
                "        \"error\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"index\": false\n" +
                "        },\n" +
                "        \"eventCode\": {\n" +
                "          \"type\": \"keyword\",\n" +
                "          \"copy_to\": \"searchContent\"\n" +
                "        },\n" +
                "        \"eventName\": {\n" +
                "          \"type\": \"keyword\",\n" +
                "          \"copy_to\": \"searchContent\"\n" +
                "        },\n" +
                "        \"eventType\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"projectId\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"sendType\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"ts\": {\n" +
                "          \"type\": \"long\"\n" +
                "        },\n" +
                "        \"wechatOpenId\": {\n" +
                "          \"type\": \"keyword\",\n" +
                "          \"copy_to\": \"searchContent\"\n" +
                "        },\n" +
                "        \"wechatUnionId\": {\n" +
                "          \"type\": \"keyword\",\n" +
                "          \"copy_to\": \"searchContent\"\n" +
                "        },\n" +
                "        \"searchContent\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        }\n" +
                "      }\n" +
                "    }";
        CompressedXContent mappings = new CompressedXContent(mappingJson);

        Template template = new Template(settings, mappings, null);

        ComposableIndexTemplate composableIndexTemplate =
                new ComposableIndexTemplate(Arrays.asList(indexPatterns),
                        template, null, null, null, null);

        request.indexTemplate(composableIndexTemplate);

        AcknowledgedResponse response = esClient.indices().putIndexTemplate(request, RequestOptions.DEFAULT);
        System.out.println("创建索引模版是否成功 = " + response.isAcknowledged());

        //创建初始索引并使用别名
        CreateIndexResponse indexResponse = esClient.indices().create(
                new CreateIndexRequest(indexInitName).alias(new Alias(indexAlias).writeIndex(true)),
                RequestOptions.DEFAULT);
        System.out.println("创建初始索引是否成功 = " + indexResponse.isAcknowledged());
    }

    public static boolean createLifecyclePolicy(RestHighLevelClient esClient, String lifecycleName, TimeValue timeValue) throws IOException {
        IndexLifecycleClient indexLifecycleClient = esClient.indexLifecycle();
        //PUT /_ilm/policy/${lifecycleName}
        //{
        //  "policy": {
        //    "phases": {
        //      "hot": {
        //        "min_age": "0ms",
        //        "actions": {}
        //      },
        //      "delete": {
        //        "min_age": "7d",
        //        "actions": {
        //          "delete": {}
        //        }
        //      }
        //    }
        //  }
        //}
        Map<String, Phase> phases = new HashMap<>();
        Map<String, LifecycleAction> hotActions = new HashMap<>();

        // 构造 hot 阶段
        Phase hotPhase = new Phase("hot", TimeValue.timeValueMillis(5), hotActions);
        phases.put("hot", hotPhase);
        // 构造 delete 阶段
        DeleteAction deleteAction = new DeleteAction();
        Map<String, LifecycleAction> deleteActions = new HashMap<>();
        deleteActions.put("delete", deleteAction);
        Phase deletePhase = new Phase("delete", timeValue, deleteActions);
        phases.put("delete", deletePhase);
        LifecyclePolicy lifecyclePolicy = new LifecyclePolicy(lifecycleName, phases);
        PutLifecyclePolicyRequest request = new PutLifecyclePolicyRequest(lifecyclePolicy);
        org.elasticsearch.client.core.AcknowledgedResponse acknowledgedResponse = indexLifecycleClient.putLifecyclePolicy(request, RequestOptions.DEFAULT);
        return acknowledgedResponse.isAcknowledged();
    }

    @Test
    public void docInsert() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        String userJson = "{\"content\":\"{}\",\"eventCode\":\"dc_001\",\"createTime\":\"2024-03-25 13:51:11\",\"appId\":\"10010102\",\"eventName\":\"ceshi数据\",\"projectId\":\"100101\",\"ts\":500945850139792}";
        Map<String, Object> map = JSONUtils.parseObject(userJson, new TypeReference<Map<String, Object>>() {
        });
        for (int i = 0; i < 20; i++) {
            System.out.println("i = " + i);
            IndexRequest request = new IndexRequest(indexAlias);
            Date date = new Date();
            map.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            //map.put("createTime", date.getTime());
            map.put("ts", date.getTime());
            String json = JSONUtils.toJsonString(map);
            System.out.println("json = " + json);
            request.source(json, XContentType.JSON);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            DocWriteResponse.Result result = response.getResult();
            //初次新增时为CREATED,再次新增时为UPDATED
            //请注意,这里的更新是覆盖更新,上面没有的key会被删除掉
            System.out.println(result);
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        esClient.close();
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
    public void queryIndexTemplate() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        GetComposableIndexTemplateRequest request = new GetComposableIndexTemplateRequest(indexTemplateName);
        GetComposableIndexTemplatesResponse response = esClient.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
        Map<String, ComposableIndexTemplate> indexTemplates = response.getIndexTemplates();
        for (Map.Entry<String, ComposableIndexTemplate> entry : indexTemplates.entrySet()) {
            String key = entry.getKey();
            System.out.println("key = " + key);
            ComposableIndexTemplate indexTemplate = entry.getValue();
            Map<String, Object> metadata = indexTemplate.metadata();
            System.out.println(metadata);
            List<String> patterns = indexTemplate.indexPatterns();
            System.out.println(patterns);
            Template template = indexTemplate.template();
            Settings settings = template.settings();
            System.out.println(settings);
            CompressedXContent mappings = template.mappings();
            System.out.println(mappings);
        }
    }
}
