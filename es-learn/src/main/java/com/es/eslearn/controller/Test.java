package com.es.eslearn.controller;

import com.es.eslearn.util.DateUtil;
import com.es.eslearn.util.ToolsUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.util.Maps;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/es")
public class Test {

    @Resource
    private ElasticsearchSave elasticsearchSave;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/index")
    public Map<String, Object> createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("ppp");
        request.mapping("{\n" +
                "                    \"properties\": {\n" +
                "                      \"title\":{\n" +
                "                        \"type\": \"text\"\n" +
                "                      },\n" +
                "                      \"desc\":{\n" +
                "                        \"type\": \"text\"\n" +
                "                      }\n" +
                "                    }\n" +
                "                  }", XContentType.JSON);
        final CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
        restHighLevelClient.close();
        return Collections.singletonMap("result", response.isAcknowledged());
    }

    @GetMapping("delete")
    public Map<String ,Object> deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("test");
        final AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
        return Collections.singletonMap("res", response.isAcknowledged());
    }

    @GetMapping("/value")
    public String testValue(){
        return "sdf";
    }



    /**
     * 统计，虚指令集的调用总数，调用异常数，调用成功率，业务失败数，业务成功率，平均耗时,调用占比的top 10
     *
     * @param param
     * @return
     */
    public Map<String, Object> queryServExcAnalysisTopTen(Map<String, Object> param) {

        Map<String, Object> respMap = new HashMap<>();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //时间选择，今日、昨日、本周、本月、时间范围
        String beginTime = "";
        String endTime = "";
        String dayType = param.get("dayType").toString();
        if ("0".equals(dayType)) {
            //今日
            Map<String, String> dateMap = DateUtil.getDaysBeginAndEndTime();
            beginTime = dateMap.get("beginTime") + ":00";
            endTime = dateMap.get("endTime") + ":59";
        } else if ("1".equals(dayType)) {
            //昨日
            Map<String, String> dateMap = DateUtil.getYesterdayBeginAndEndTime();
            beginTime = dateMap.get("beginTime") + ":00";
            endTime = dateMap.get("endTime") + ":59";
        } else if ("2".equals(dayType)) {
            //近一周
            Map<String, String> dateMap = DateUtil.getDaySevenRange();
            beginTime = dateMap.get("beginTime") + " 00:00:00";
            endTime = dateMap.get("endTime") + " 23:59:59";
        } else if ("3".equals(dayType)) {
            //近一月
            Map<String, String> dateMap = DateUtil.getDayTRange();
            beginTime = dateMap.get("beginTime") + " 00:00:00";
            endTime = dateMap.get("endTime") + " 23:59:59";
        } else {
            //时间范围
            if (ToolsUtil.isNotNullAndNotEmpty(param.get("startTime")) && (ToolsUtil.isNotNullAndNotEmpty(param.get("endTime")))) {
                beginTime = param.get("startTime").toString();
                endTime = param.get("endTime").toString();
            }
        }
        //构建条件，时间范围
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("invokeTime");
        rangeQuery.from(beginTime);
        rangeQuery.to(endTime);
        boolQueryBuilder.must(rangeQuery);
        //统计类型(调用总数，调用异常数，调用成功率，业务失败数，业务成功率，平均耗时,调用占比)
        List<Map<String, Object>> transferAllList = new ArrayList<>();
        List<Map<String, Object>> transferErrList = new ArrayList<>();
        List<Map<String, Object>> transferSucPerList = new ArrayList<>();
        List<Map<String, Object>> servErrList = new ArrayList<>();
        List<Map<String, Object>> servSuccPerList = new ArrayList<>();
        List<Map<String, Object>> avgExcTimeList = new ArrayList<>();
        List<Map<String, Object>> maxExcTimeList = new ArrayList<>();
        List<Map<String, Object>> minExcTimeList = new ArrayList<>();
        List<Map<String, Object>> transferPerList = new ArrayList<>();
        //聚合查询
        TermsAggregationBuilder terms = AggregationBuilders
                .terms("groupByServName").field("servName" + ".keyword").size(10)
                //平均
                .subAggregation(AggregationBuilders.avg("avgExecTime").field("execTime"))
                //最大
                .subAggregation(AggregationBuilders.max("maxExecTime").field("execTime"))
                //最小
                .subAggregation(AggregationBuilders.min("minExecTime").field("execTime"))
                //子查询,成功失败状态分组
                .subAggregation(AggregationBuilders.terms("subInvokeStatus").field("invokeStatus.keyword").size(10000))
                .subAggregation(AggregationBuilders.terms("subExecStatus").field("execStatus.keyword").size(10000));

        List<AbstractAggregationBuilder> list = new ArrayList<>();
        //聚合总数，计算调用占比
        ValueCountAggregationBuilder valCount = AggregationBuilders.count("recordSum").field("serialId");
        list.add(valCount);
        list.add(terms);
        //执行查询
        Aggregations aggregation = elasticsearchSave.aggSearch(boolQueryBuilder, list, RequestRecord.class);
        Terms groups = aggregation.get("groupByServName");
        //数据处理
        DecimalFormat df = new DecimalFormat("#0.00");
        List<Map<String, Object>> dataList = new ArrayList<>();
        //查询总数
        ValueCount valueCount = aggregation.get("recordSum");
        long recordSum = valueCount.getValue();

        for (Terms.Bucket entry : groups.getBuckets()) {
            Map<String, Object> dataMap = new HashMap();
            dataMap.put("servName", entry.getKey());//虚指令集名称
            dataMap.put("transferCount", entry.getDocCount());//调用总数
            //子分组，先初始化数据
            dataMap.put("transferErrCount", 0);//调用异常数量
            dataMap.put("transferSuccPer", "0.00");//调用成功率
            dataMap.put("servErrorCount", 0);//业务失败数量
            dataMap.put("servSuccPer", "0.00");//业务成功率
            Terms subgroupsI = entry.getAggregations().get("subInvokeStatus");
            Terms subgroupsE = entry.getAggregations().get("subExecStatus");
            ParsedAvg subgroups = entry.getAggregations().get("avgExecTime");
            dataMap.put("avgExecTime", decimalFormat.format(subgroups.getValue()/1000));//平均耗时
            ParsedMax maxSubgroups = entry.getAggregations().get("maxExecTime");
            dataMap.put("maxExecTime", decimalFormat.format(maxSubgroups.getValue()/1000));//最大耗时
            ParsedMin minSubgroups = entry.getAggregations().get("minExecTime");
            dataMap.put("minExecTime", decimalFormat.format(minSubgroups.getValue()/1000));//最小耗时

            for (Terms.Bucket subEntry : subgroupsI.getBuckets()) {
                if ("200".equals(subEntry.getKeyAsString())) {
                    dataMap.put("transferSuccPer", String.valueOf(df.format(Double.valueOf(subEntry.getDocCount()) / entry.getDocCount() * 100)));
                    subEntry.getDocCount();
                } else {
                    dataMap.put("transferErrCount", subEntry.getDocCount());
                }
            }
            for (Terms.Bucket subEntry : subgroupsE.getBuckets()) {
                if ("200".equals(subEntry.getKeyAsString())) {
                    dataMap.put("servSuccPer", String.valueOf(df.format(Double.valueOf(subEntry.getDocCount()) / entry.getDocCount() * 100)));
                    subEntry.getDocCount();
                } else {
                    dataMap.put("servErrorCount", subEntry.getDocCount());
                }
            }
            //调用占比
            String invokePer ="100%";
            if (recordSum != 0) {
                invokePer = decimalFormat.format((entry.getDocCount() / (double) recordSum) * 100);
                dataMap.put("transferPer", invokePer);
            }
            dataList.add(dataMap);
        }

        //结果剔除逻辑执行
        dataList = recordService.resultProcess(dataList,param);

        for (Map<String, Object> dataMap : dataList) {

            Map<String, Object> transferCount = Maps.newHashMap();
            Map<String, Object> transferErrCount = Maps.newHashMap();
            Map<String, Object> transferSuccPer = Maps.newHashMap();
            Map<String, Object> servErrorCount = Maps.newHashMap();
            Map<String, Object> servSuccPer = Maps.newHashMap();
            Map<String, Object> avgExecTime = Maps.newHashMap();
            Map<String, Object> maxExecTime = Maps.newHashMap();
            Map<String, Object> minExecTime = Maps.newHashMap();
            Map<String, Object> transferPer = Maps.newHashMap();

            //调用总数
            transferCount.put("servName", dataMap.get("servName"));
            transferCount.put("value", String.valueOf(dataMap.get("transferCount")));
            transferAllList.add(transferCount);

            //调用异常数
            transferErrCount.put("servName", dataMap.get("servName"));
            transferErrCount.put("value", String.valueOf(dataMap.get("transferErrCount")));
            transferErrList.add(transferErrCount);

            //调用成功率
            transferSuccPer.put("servName", dataMap.get("servName"));
            transferSuccPer.put("value", dataMap.get("transferSuccPer"));
            transferSucPerList.add(transferSuccPer);

            //业务失败数
            servErrorCount.put("servName", dataMap.get("servName"));
            servErrorCount.put("value", String.valueOf(dataMap.get("servErrorCount")));
            servErrList.add(servErrorCount);

            //业务成功率
            servSuccPer.put("servName", dataMap.get("servName"));
            servSuccPer.put("value", dataMap.get("servSuccPer"));
            servSuccPerList.add(servSuccPer);

            //平均耗时
            avgExecTime.put("servName", dataMap.get("servName"));
            avgExecTime.put("value", dataMap.get("avgExecTime"));
            avgExcTimeList.add(avgExecTime);

            //最小耗时
            minExecTime.put("servName", dataMap.get("servName"));
            minExecTime.put("value", dataMap.get("minExecTime"));
            minExcTimeList.add(minExecTime);

            //最大耗时
            maxExecTime.put("servName", dataMap.get("servName"));
            maxExecTime.put("value", dataMap.get("maxExecTime"));
            maxExcTimeList.add(maxExecTime);

            //调用占比
            transferPer.put("servName", dataMap.get("servName"));
            transferPer.put("value", dataMap.get("transferPer"));
            transferPerList.add(transferPer);
        }

        System.out.println(transferAllList);
        respMap.put("transferAllList", sortLongList(transferAllList));
        respMap.put("transferErrList", sortLongList(transferErrList));
        respMap.put("transferSucPerList", sortDoubleList(transferSucPerList));
        respMap.put("servErrList", sortLongList(servErrList));
        respMap.put("servSuccPerList", sortDoubleList(servSuccPerList));
        respMap.put("avgExcTimeList", sortDoubleList(avgExcTimeList));
        respMap.put("maxExcTimeList", sortDoubleList(maxExcTimeList));
        respMap.put("minExcTimeList", sortDoubleList(minExcTimeList));
        respMap.put("transferPerList", sortDoubleList(transferPerList));
        return respMap;
    }

    // 排序公共方法
    public List<Map<String, Object>> sortLongList(List<Map<String, Object>> mapList) {
        List<Map<String, Object>> collect = mapList.stream().sorted((a, b) -> {
            String aValue = (String) a.get("value");
            String bValue = (String) b.get("value");
            if (!StringUtils.isEmpty(aValue) && !StringUtils.isEmpty(bValue)) {
                try {
                    // 默认降序, 升序的话 把 dt1 和 dt2 调换位置
                    return Long.compare(Long.parseLong(bValue), Long.parseLong(aValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            } else {
                return 0;
            }
        }).collect(Collectors.toList());
        return collect;
    }

    public List<Map<String, Object>> sortDoubleList(List<Map<String, Object>> mapList) {
        List<Map<String, Object>> collect = mapList.stream().sorted((a, b) -> {
            String aValue = (String) a.get("value");
            String bValue = (String) b.get("value");
            if (!StringUtils.isEmpty(aValue) && !StringUtils.isEmpty(bValue)) {
                try {
                    // 默认降序, 升序的话 把 dt1 和 dt2 调换位置
                    return Double.compare(Double.parseDouble(bValue), Double.parseDouble(aValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            } else {
                return 0;
            }
        }).collect(Collectors.toList());
        return collect;
    }


    /**
     * 工单过滤结果集二次处理
     * @return
     */
    public List<Map<String, Object>> resultProcess(List<Map<String, Object>> dataList,Map<String, Object> param) {
        log.info("执行结果剔除。更改统计数值！");
        BoolQueryBuilder boolQueryBuilder = getBoolQueryBuilder(param);
        Map<String, Integer> fieldMap = new HashMap<>();
        List<Map<String, Object>> dataListNew = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> data = dataList.get(i);
            String key = "";
            if (data.get("servName")!=null){
                key = CommonConstant.ELIMINATE_KEY_SERV + data.get("servName");
                fieldMap.put(data.get("servName").toString(), i);
            }else {
                key = CommonConstant.ELIMINATE_KEY_NE + data.get("logNeName");
                fieldMap.put(data.get("logNeName").toString(), i);
            }
            Collection<Object> values = cnccCache.hmget(key).values();
            for (Object obj : values) {
                BoolQueryBuilder queryBuilder =  QueryBuilders.boolQuery();
                queryBuilder.must(boolQueryBuilder);
                JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                Integer isSuccess = jsonObject.getInteger("processingType");
                String column = jsonObject.get("columnName").toString();
                String value = jsonObject.get("value").toString();
                Object type = jsonObject.get("type");
                queryBuilder = getBoolQueryBuilder(queryBuilder, column, value, Integer.parseInt(type.toString()));
                data = doQueryByBuilder(queryBuilder,data,isSuccess);
            }
            dataListNew.add(data);
        }
        return dataListNew;
    }

}
