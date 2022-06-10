package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/**
 * 简单聚合
 */
public class TransFormAggTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);

        final DataStreamSource<Event> dataStreamSource = environment.fromElements(
                new Event("kak", "/fk", 10000L),
                new Event("sf", "/fkihd", 20000L),
                new Event("sf", "/hfska", 20400L),
                new Event("sf", "/fa", 23000L),
                new Event("sf", "/fasf", 20000L),
                new Event("fjlds", "/oij", 4000L)
        );

        // max 只更新最后一次最新时间 其它重复字段同第一次出现的
        dataStreamSource.keyBy(new KeySelector<Event, String>() {
            @Override
            public String getKey(Event event) throws Exception {
                // 根据user字段进行分组
                return event.user;
            }
        })
                // 取相同分组的最大时间 可传字段名称和索引下标,从0开始
                .max("timestamp")
//                .max(2)
                .print("max--");

        // maxby根据最新时间 取最新时间的整条记录
        dataStreamSource.keyBy(event -> event.user).maxBy("timestamp").print("maxby--");

        environment.execute();
    }
}
