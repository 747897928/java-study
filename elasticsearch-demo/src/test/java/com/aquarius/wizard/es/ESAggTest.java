package com.aquarius.wizard.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2024/4/22 11:37
 */
public class ESAggTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String index = "hotel";

    @Test
    public void aggTest() throws IOException {
        //聚合(aggregations)可以实现对文档数据的统计、分析、运算。聚合常见的有三类
        //桶(Bucket)聚合:用来对文档做分组
        //聚合的这个字段一定是不分词的，你可以是keyword,日期，数值，布尔，但是不能是text类型
        //TermAggregation:按照文档字段值分组
        //Date Histogram:按照日期阶梯分组，例如一周为一组，或者一月为一组
        //
        // 度量(Metric)聚合:用以计算一些值，比如:最大值、最小值、平均值等。主要针对数值类型的聚合，不能根据一个字符串去聚合
        // 管道(pipeline)聚合:其它聚合的结果为基础做聚合


        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        //{
        //    "size": 0,//设置size为0，结果中不包含稳定，只包括聚合效果
        //    "aggregations": { //定义聚合
        //        "brandAgg": {//给聚合起个名字
        //            "terms": {//聚合类型，按品牌值聚合,所以选择term
        //                "field": "brand",//参与聚合的字段
        //                "size": 20,//希望获取的聚合结果数量
        //                "order": [
        //                    {
        //                        "_count": "asc"
        //                    },
        //                    {
        //                        "_key": "asc"
        //                    }
        //                ]
        //            }
        //        }
        //    }
        //}
        builder.size(0);
        String aggName = "brandAgg";
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName)
                .field("brand").size(20).order(BucketOrder.count(true));

        builder.aggregation(aggregationBuilder);

        request.source(builder);
        System.out.println(request.source());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //System.out.println(response);
        Aggregations aggregations = response.getAggregations();
        //根据名称获取聚合结果
        Terms brandTerms = aggregations.get(aggName);
        //获取桶
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            //获取key,也是品牌信息
            String brandName = bucket.getKeyAsString();
            System.out.println("brandName = " + brandName);
            long docCount = bucket.getDocCount();
            System.out.println("docCount = " + docCount);

        }
        esClient.close();

    }


    @Test
    public void aggMetricsTest() throws IOException {

        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        //{
        //    "size": 0,
        //    "aggregations": {
        //        "brandAgg": {
        //            "terms": {
        //                "field": "brand",
        //                "size": 20,
        //                "order": [
        //                    {
        //                        "scoreAgg.avg": "desc"
        //                    }
        //                ]
        //            },
        //            "aggregations": {
        //                "scoreAgg": {
        //                    "stats": {
        //                        "field": "score"
        //                    }
        //                }
        //            }
        //        }
        //    }
        //}

        builder.size(0);
        String aggName = "brandAgg";
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName)
                .field("brand").size(20).order(BucketOrder.aggregation("scoreAgg.avg", false));
        //是brand聚合的子聚合，也就是分组后对每组分到的计算
        //state可以计算min，max，avg等
        TermsAggregationBuilder termsAggregationBuilder = aggregationBuilder.subAggregation(
                AggregationBuilders.stats("scoreAgg").field("score")
        );
        builder.aggregation(aggregationBuilder);

        request.source(builder);
        System.out.println(request.source());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //System.out.println(response);
        Aggregations aggregations = response.getAggregations();
        //根据名称获取聚合结果
        Terms brandTerms = aggregations.get(aggName);
        //获取桶
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            //获取key,也是品牌信息
            String brandName = bucket.getKeyAsString();
            System.out.println("brandName = " + brandName);
            long docCount = bucket.getDocCount();
            System.out.println("docCount = " + docCount);
        }
        //{
        //  "took" : 63,
        //  "timed_out" : false,
        //  "_shards" : {
        //    "total" : 1,
        //    "successful" : 1,
        //    "skipped" : 0,
        //    "failed" : 0
        //  },
        //  "hits" : {
        //    "total" : {
        //      "value" : 201,
        //      "relation" : "eq"
        //    },
        //    "max_score" : null,
        //    "hits" : [ ]
        //  },
        //  "aggregations" : {
        //    "brandAgg" : {
        //      "doc_count_error_upper_bound" : 0,
        //      "sum_other_doc_count" : 0,
        //      "buckets" : [
        //        {
        //          "key" : "万丽",
        //          "doc_count" : 2,
        //          "scoreAgg" : {
        //            "count" : 2,
        //            "min" : 46.0,
        //            "max" : 47.0,
        //            "avg" : 46.5,
        //            "sum" : 93.0
        //          }
        //        },
        //        {
        //          "key" : "凯悦",
        //          "doc_count" : 8,
        //          "scoreAgg" : {
        //            "count" : 8,
        //            "min" : 45.0,
        //            "max" : 47.0,
        //            "avg" : 46.25,
        //            "sum" : 370.0
        //          }
        //        },
        //        {
        //          "key" : "和颐",
        //          "doc_count" : 12,
        //          "scoreAgg" : {
        //            "count" : 12,
        //            "min" : 44.0,
        //            "max" : 47.0,
        //            "avg" : 46.083333333333336,
        //            "sum" : 553.0
        //          }
        //        },
        //        {
        //          "key" : "丽笙",
        //          "doc_count" : 2,
        //          "scoreAgg" : {
        //            "count" : 2,
        //            "min" : 46.0,
        //            "max" : 46.0,
        //            "avg" : 46.0,
        //            "sum" : 92.0
        //          }
        //        }
        //      ]
        //    }
        //  }
        //}
        esClient.close();

    }
}
