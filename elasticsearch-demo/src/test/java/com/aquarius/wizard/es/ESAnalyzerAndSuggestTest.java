package com.aquarius.wizard.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author zhaoyijie
 * @since 2024/4/25 10:26
 */
public class ESAnalyzerAndSuggestTest {

    String hostname = "127.0.0.1";
    int port = 9200;
    String username = "elastic";
    String password = "elastic";

    String index = "hotel";

    @Test
    public void analyzerAndSuggestTest1() throws IOException {
        //elasticsearch中分词器(analyzer)的组成包含三部分
        //character filters:在tokenizer之前对文本进行处理。例如删除字符、替换字符
        //tokenizer:将文本按照一定的规则切割成词条(term)。例如keyword，就是不分词;还有ik_smart
        //tokenizer filter:将tokenizer输出的词条做进一步处理。例如大小写转换、同义词处理、拼音处理等

        //拼音分词器适合在创建倒排索引的时候使用，但不能在搜索中文的时候使用
        //应该是用户输入中文用中文去搜，用户输入拼音才用拼音去搜，不然输入中文会搜到同音字

        //PUT /hotel
        //{
        //  "settings": {
        //    "analysis": {
        //      "analyzer": {
        //        "text_anlyzer": {
        //          "tokenizer": "ik_max_word",
        //          "filter": "py"
        //        },
        //        "completion_analyzer": {
        //          "tokenizer": "keyword",
        //          "filter": "py"
        //        }
        //      },
        //      "filter": {
        //        "py": {
        //          "type": "pinyin",
        //          "keep_full_pinyin": false,
        //          "keep_joined_full_pinyin": true,
        //          "keep_original": true,
        //          "limit_first_letter_length": 16,
        //          "remove_duplicated_term": true,
        //          "none_chinese_pinyin_tokenize": false
        //        }
        //      }
        //    }
        //  },
        //  "mappings": {
        //    "properties": {
        //      "id":{
        //        "type": "keyword"
        //      },
        //      "name":{
        //        "type": "text",
        //        "analyzer": "text_anlyzer",
        //        "search_analyzer": "ik_smart",
        //        "copy_to": "all"
        //      },
        //      "address":{
        //        "type": "keyword",
        //        "index": false
        //      },
        //      "price":{
        //        "type": "integer"
        //      },
        //      "score":{
        //        "type": "integer"
        //      },
        //      "brand":{
        //        "type": "keyword",
        //        "copy_to": "all"
        //      },
        //      "city":{
        //        "type": "keyword"
        //      },
        //      "starName":{
        //        "type": "keyword"
        //      },
        //      "business":{
        //        "type": "keyword",
        //        "copy_to": "all"
        //      },
        //      "location":{
        //        "type": "geo_point"
        //      },
        //      "pic":{
        //        "type": "keyword",
        //        "index": false
        //      },
        //      "all":{
        //        "type": "text",
        //        "analyzer": "text_anlyzer",
        //        "search_analyzer": "ik_smart"
        //      },
        //      "suggestion":{
        //          "type": "completion",
        //          "analyzer": "completion_analyzer"
        //      }
        //    }
        //  }
        //}

        //elasticsearch提供CompletionSuggester查询来实现自动补全功能。这个查询会匹配以用户输入内容开头的词条并返回。
        // 为了提高补全查询的效率，对于文档中字段的类型有一些约束:
        // 参与补全查询的字段必须是completion类型，
        //

        //GET /hotel/_search
        //{
        //    "suggest": {
        //        "mySuggestion": {
        //            "text": "h",//关键字
        //            "completion": {
        //                "field": "suggestion",//补全查询的字段
        //                "skip_duplicates": true,//跳过重复的
        //                "size": 10//获取前10条结果
        //            }
        //        }
        //    }
        //}
        RestHighLevelClient esClient = ElasticsearchUtils.getEsClient(hostname, port, username, password);
        SearchRequest request = new SearchRequest();
        request.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        String suggestionName = "mySuggestion";
        suggestBuilder.addSuggestion(suggestionName,
                SuggestBuilders.completionSuggestion("suggestion")
                        .skipDuplicates(true).size(10).prefix("h")
        );
        builder.suggest(suggestBuilder);
        request.source(builder);
        System.out.println(request.source());
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();
        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggestion = suggest.getSuggestion(suggestionName);
        for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> options : suggestion) {
            for (Suggest.Suggestion.Entry.Option option : options.getOptions()) {
                Text text = option.getText();
                System.out.println(text);
            }
        }
        esClient.close();
    }

}
