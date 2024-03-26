package com.aquarius.wizard.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhaoyijie
 * @since 2024/3/21 11:47
 */
public class ESQueryTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String index = "user";

    @Test
    public void docQuery() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        //(当前页码-1)*每页显示数据条数
        int currentPage = 2;//第一页
        int pageSize = 2;
        builder.from((currentPage - 1) * pageSize);
        builder.size(pageSize);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        //总条数
        System.out.println(hits.getTotalHits());
        //查询的时间
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }

    @Test
    public void docTermQuery() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("name", "张"));
        //(当前页码-1)*每页显示数据条数
        int currentPage = 1;//第一页
        int pageSize = 2;
        builder.from((currentPage - 1) * pageSize);
        builder.size(pageSize);
        String[] includes = {"name"};
        String[] excludes = {"age"};
        builder.fetchSource(includes, excludes);
        builder.sort("age", SortOrder.DESC);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        //总条数
        System.out.println(hits.getTotalHits());
        //查询的时间
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }


    @Test
    public void docGroupQuery() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //bool查询是Elasticsearch中的布尔查询，它允许组合多个查询条件以过滤和排序文档。bool查询有四个主要子句：
        //
        //must：所有的查询子句都必须匹配，类似于"AND"操作。
        //should：至少一个查询子句必须匹配，类似于"OR"操作。
        //must_not：查询子句不应该匹配，类似于"NOT"操作。
        //filter：与must相似，但不会影响查询的相关性得分。
        //bool查询具有以下特点
        //
        //内部可以包含多个全文检索和精确查询语法
        //子查询可以任意顺序出现
        //可以嵌套多个查询，包括bool查询
        //如果bool查询中没有must条件，should中必须至少满足一条才会返回结果。
        boolQueryBuilder.must(QueryBuilders.rangeQuery("age").gt(16).lte(20));
        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男"));
        boolQueryBuilder.mustNot(QueryBuilders.rangeQuery("age").gte(19).lt(20));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 22));

        //表示偏差两个字符都是允许的
        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name", "wangwu").fuzziness(Fuzziness.TWO);


        builder.query(boolQueryBuilder);
        //(当前页码-1)*每页显示数据条数
        //int currentPage = 1;//第一页
        //int pageSize = 2;
        //builder.from((currentPage - 1) * pageSize);
        //builder.size(pageSize);
        //String[] includes = {"name"};
        //String[] excludes = {"age"};
        //builder.fetchSource(includes, excludes);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("age");
        builder.highlighter(highlightBuilder);

        builder.sort("age", SortOrder.DESC);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits hits = response.getHits();
        //总条数
        System.out.println(hits.getTotalHits());
        //查询的时间
        System.out.println(response.getTook());

        for (SearchHit hit : hits) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
            System.out.println(hit.getSourceAsString());
        }
        esClient.close();
    }


    @Test
    public void docAggQuery() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder.aggregation(aggregationBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        //{
        //    "aggregations": {
        //        "max#maxAge": {
        //            "value": 22
        //        }
        //    }
        //}
        esClient.close();
    }

    @Test
    public void docGroupAggQuery() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("aggGroup").field("age");
        builder.aggregation(aggregationBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        //"{
        //    "aggregations": {
        //        "lterms#aggGroup": {
        //            "doc_count_error_upper_bound": 0,
        //            "sum_other_doc_count": 0,
        //            "buckets": [
        //                {
        //                    "key": 18,
        //                    "doc_count": 4
        //                },
        //                {
        //                    "key": 20,
        //                    "doc_count": 1
        //                },
        //                {
        //                    "key": 22,
        //                    "doc_count": 1
        //                }
        //            ]
        //        }
        //    }
        //}
        esClient.close();
    }
}

