package com.aquarius.wizard.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
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

    String index = "hotel";

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
    public void docMultiQuery() throws IOException {
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //建议使用copy to
        builder.query(QueryBuilders.multiMatchQuery("java", "name", "age", "sex"));
        //(当前页码-1)*每页显示数据条数
        int currentPage = 2;
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
    public void docGeoBoundingBoxQuery() throws IOException {
        //根据经纬度查询，官方文档。例如:
        //geo_bounding_box:查询geo_point值落在某个矩形范围的所有文档，比较适合查询一定范围内的所有信息
        // geo_bounding_box查询GET /indexName/_search
        //GET /hotel/_search
        //{
        //    "query": {
        //        "geo_bounding_box": {
        //            "FIELD": {
        //                "top_left": {
        //                    "lat": 31.1,
        //                    "lon": 121.5
        //                },
        //                "bottom_right": {
        //                    "lat": 30.9,
        //                    "lon": 121.7
        //                }
        //            }
        //        }
        //    }
        //}
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        GeoBoundingBoxQueryBuilder location = QueryBuilders.geoBoundingBoxQuery("location");
        GeoPoint topLeft = new GeoPoint(31.1, 121.5);
        GeoPoint bottomRight = new GeoPoint(30.9, 121.7);
        location.setCorners(topLeft, bottomRight);
        builder.query(location);
        request.source(builder);
        System.out.println(request.source().toString());
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
    public void docGeoDistanceQuery() throws IOException {
        //根据经纬度查询，官方文档。例如:
        //geo_distance:查询到指定中心点小于某个距离值的所有文档，中心点，长度distance画圆，在园内的文档
        // geo_distance查询GET /indexName/_search
        //GET /hotel/_search
        //{
        //    "query": {
        //        "geo_distance": {
        //            "location": "31.21,121.5",
        //            "distance": "15km"
        //        }
        //    }
        //}

        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        GeoDistanceQueryBuilder location = QueryBuilders.geoDistanceQuery("location");
        GeoPoint center = new GeoPoint(31.21, 121.5);
        location.distance("15km").point(center);
        builder.query(location);
        request.source(builder);
        System.out.println(request.source().toString());
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
        //term查询
        // GET /indexName/_search
        //{
        //    "query": {
        //        "term": {
        //            "FIELD": {
        //                "value": "VALUE"
        //            }
        //        }
        //    }
        //}
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

        //range查询:
        // range查询
        // GET /indexName/_search
        //{
        //    "query": {
        //        "range": {
        //            "FIELD": {
        //                "gte": 10,
        //                "lte": 20
        //            }
        //        }
        //    }
        //}

        //GET /hotel/_search
        //{
        //  "query": {
        //    "range": {
        //      "price": {
        //        "gte": 249,
        //        "lte": 336
        //      }
        //    }
        //  }
        //}
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
        System.out.println(request.source());
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

    @Test
    public void boolQueryTest() throws IOException {
        //复合查询布尔查询
        //复合查询 Boolean Query
        //布尔查询是一个或多个查询子句的组合。子查询的组合方式有:
        // must:必须匹配每个子查询，类似"与"
        // should:选择性匹配子查询，类似"或"
        // must_not:必须不匹配，不参与算分，类似"非"
        //filter:必须匹配，不参与算分
        //{
        //    "query": {
        //        "bool": {
        //            "must": [
        //                {
        //                    "term": {"city": "上海"}
        //                }
        //            ],
        //            "filter": [
        //                {
        //                    "range": {"score": {"gte": 45}}
        //                }
        //            ],
        //            "must_not": [
        //                {
        //                    "range": {"price": {"lte": 500}}
        //                }
        //            ],
        //            "should": [
        //                {
        //                    "term": {"brand": "皇冠假日"}
        //                },
        //                {
        //                    "term": {"brand": "华美达"}
        //                }
        //            ]
        //        }
        //    }
        //}
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("city","上海"));
        boolQueryBuilder.should(QueryBuilders.termQuery("brand","皇冠假日"));
        boolQueryBuilder.should(QueryBuilders.termQuery("brand","华美达"));
        boolQueryBuilder.mustNot(QueryBuilders.rangeQuery("price").lte(500));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("score").gte(45));
        builder.query(boolQueryBuilder);
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
        }
        esClient.close();
    }


}

