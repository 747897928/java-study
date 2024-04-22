package com.aquarius.wizard.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

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
        //TF-IDF（Term Frequency-Inverse Document Frequency，词频-逆向文件频率）是一种用于信息检索和文本挖掘的加权技术，
        // 主要用于评估词项（字词）在一个文档或语料库中的重要程度。TF-IDF的核心思想是，如果一个词在一个文档中出现的频率高（TF值大），
        // 同时在其他文档中出现的频率低（IDF值大），则这个词具有较好的类别区分能力，适合用于分类。
        //
        //TF-IDF的计算公式为：TF-IDF(t,d)=TF(t,d)×IDF(t)。其中，TF（Term Frequency）表示词项在特定文档中出现的频率，这个数值通常会被归一化，
        // 以防止偏向长文件。IDF（Inverse Document Frequency）表示词项在语料库中的逆向文件频率，如果一个词项在语料库中出现的文档数量少，
        // 那么它的IDF值就大。
        //GET /hotel/_search
        //{
        //    "query": {
        //        "function_score": {
        //            "query": {
        //                "match": {"all": "外滩"}    //原始查询条件，搜索文档并根据相关性打分(query score)
        //            },
        //            "functions": [
        //                {
        //                    "filter": {"term": {"id": 1}},  //过滤条件，符合条件的文档才会被重新算分
        //                    "weight": 10 //算分函数，算分函数的结果称为function score，将来会与query score运算，得到新算分，
        //                    //常见的算分函数有:weight:给一个常量值，作为函数结果(function score)
        //                    field_value_factor:用文档中的某个字段值作为函数结果
        //                    random_score:随机生成一个值，作为函数结果
        //                    script_score:自定义计算公式，公式结果作为函数结果
        //                }
        //            ],
        //            "boost_mode": "multiply"
        //        }
        //    }
        //}

        //{"query":{"function_score":{"query":{"match":{"all":"外滩"}},"functions":[{"filter":{"term":{"id":1}},"weight":10}],"boost_mode":"multiply"}}}

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder queryScoreBuilder = QueryBuilders.matchQuery("all", "外滩");
        QueryBuilder filter = QueryBuilders.termQuery("id", 1);
        ScoreFunctionBuilder<WeightBuilder> scoreFunctionBuilder = new WeightBuilder();
        scoreFunctionBuilder.setWeight(10);
        FunctionScoreQueryBuilder.FilterFunctionBuilder filterFunctionBuilder = new FunctionScoreQueryBuilder.FilterFunctionBuilder(filter, scoreFunctionBuilder);
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{filterFunctionBuilder};
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryScoreBuilder, filterFunctionBuilders);
        functionScoreQueryBuilder.boostMode(CombineFunction.MULTIPLY);
        builder.query(functionScoreQueryBuilder);
        request.source(builder);
        System.out.println(request.source().toString());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits hits = response.getHits();
        //总条数
        System.out.println(hits.getTotalHits());
        //查询的时间
        System.out.println(response.getTook());
        float maxScore = hits.getMaxScore();
        System.out.println("maxScore = " + maxScore);
        for (SearchHit hit : hits) {
            float score = hit.getScore();
            System.out.println("score = " + score);
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }

    @Test
    public void functionSourceTest2() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        //案例：给“如家”这个品牌的酒店排名靠前一些
        //把这个问题翻译下，function score需要的三要素
        //1.哪些文档需要算分加权。品牌为如家的酒店
        //2.算分函数是什么？weight就可以
        //3.加权模式是什么？就和
        //GET /hotel/_search
        //{
        //    "query": {
        //        "function_score": {
        //            "query": {
        //                "match": {"all": "外滩"}
        //            },
        //            "functions": [ //算分函数
        //                {
        //                    "filter": { //满足的条件，平拍必须是如家
        //                        "term": {
        //                            "brand": {"value": "如家"}
        //                        }
        //                    },
        //                    "weight": 2 //算分权重为2
        //                }
        //            ],
        //            "boost_mode": "sum"
        //        }
        //    }
        //}

        //{"query":{"function_score":{"query":{"match":{"all":"外滩"}},"functions":[{"filter":{"term":{"brand":{"value":"如家"}}},"weight":2}],"boost_mode":"sum"}}}

        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MatchQueryBuilder queryScoreBuilder = QueryBuilders.matchQuery("all", "外滩");
        //过滤条件
        QueryBuilder filter = QueryBuilders.termQuery("brand", "如家");

        //ScoreFunctionBuilder<WeightBuilder> scoreFunctionBuilder = new WeightBuilder();
        //scoreFunctionBuilder.setWeight(2);
        //FunctionScoreQueryBuilder.FilterFunctionBuilder filterFunctionBuilder = new FunctionScoreQueryBuilder.FilterFunctionBuilder(filter, scoreFunctionBuilder);

        //算分函数
        WeightBuilder weightBuilder = ScoreFunctionBuilders.weightFactorFunction(2);
        FunctionScoreQueryBuilder.FilterFunctionBuilder filterFunctionBuilder = new FunctionScoreQueryBuilder.FilterFunctionBuilder(filter, weightBuilder);

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{filterFunctionBuilder};
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryScoreBuilder, filterFunctionBuilders);
        functionScoreQueryBuilder.boostMode(CombineFunction.SUM);
        builder.query(functionScoreQueryBuilder);
        request.source(builder);
        System.out.println(request.source().toString());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits hits = response.getHits();
        //总条数
        System.out.println(hits.getTotalHits());
        //查询的时间
        System.out.println(response.getTook());
        float maxScore = hits.getMaxScore();
        System.out.println("maxScore = " + maxScore);
        for (SearchHit hit : hits) {
            float score = hit.getScore();
            System.out.println("score = " + score);
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }

    @Test
    public void highlightTest() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //高亮查询，默认情况下，ES搜索字段必须与高亮字段一致
        builder.query(QueryBuilders.matchQuery("all", "如家"));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        String highlightFieldName = "name";
        highlightBuilder.field(highlightFieldName);
        //设置为true将导致仅在查询与该字段匹配时突出显示该字段。默认值为false，表示无论查询是否与之匹配，术语都会在所有请求字段上突出显示。
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<em>");
        highlightBuilder.postTags("</em>");
        builder.highlighter(highlightBuilder);
        request.source(builder);
        System.out.println(request.source());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        //总条数
        System.out.println(hits.getTotalHits());
        //查询的时间
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get(highlightFieldName);
            Text[] fragments = highlightField.getFragments();
            for (Text fragment : fragments) {
                System.out.println(fragment.string());
            }
        }
        esClient.close();
    }


    @Test
    public void geoSortTest() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        //{
        //    "query": {
        //        "match_all": {}
        //    },
        //    "sort": [
        //        {
        //            "price": {
        //                "order": "asc"
        //            }
        //        },
        //        {
        //            "_geo_distance": {
        //                "location": [
        //                    {
        //                        "lat": 31.21,
        //                        "lon": 121.5
        //                    }
        //                ],
        //                "unit": "km",
        //                "order": "asc"
        //            }
        //        }
        //    ]
        //}
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        //距离排序与普通字段排序有所差异
        //价格排序
        builder.sort("price", SortOrder.ASC);
        //距离排序
        builder.sort(SortBuilders.geoDistanceSort("location", new GeoPoint(31.21, 121.5))
                .order(SortOrder.ASC)
                .unit(DistanceUnit.KILOMETERS)
        );
        request.source(builder);
        System.out.println(request.source());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //System.out.println(response);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
            Object[] sortValues = hit.getSortValues();
            if (sortValues != null) {
                for (int i = 0; i < sortValues.length; i++) {
                    Object sortValue = sortValues[i];
                    System.out.println("i = " + i + ", sortValue = " + sortValue);
                }
            }
            System.out.println("****************************************************");
        }
        esClient.close();
    }

}
