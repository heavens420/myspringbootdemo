package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

public class TransFormFlatMapTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);

        List<Event> list = new ArrayList<>();
        list.add(new Event("nana", "/usr", 10000L));
        list.add(new Event("yingying", "/opt", 20000L));
        list.add(new Event("who", "/root", 30000L));

        // 从集合中读取
        final DataStreamSource<Event> stream = environment.fromCollection(list);

        final SingleOutputStreamOperator<String> result = stream.flatMap(new TransFormFlatMap());

        final SingleOutputStreamOperator<String> result2 = stream.flatMap(new FlatMapFunction<Event, String>() {
            @Override
            public void flatMap(Event event, Collector<String> collector) throws Exception {
                collector.collect(event.user);
                collector.collect(event.url);
                collector.collect(event.timestamp.toString());
            }
        });

        final SingleOutputStreamOperator<String> result3 = stream.flatMap((Event event, Collector<String> collector) -> {
            collector.collect(event.user);
            collector.collect(event.url);
            collector.collect(event.timestamp.toString());
        })
                // 泛型擦除后的定义泛型 两种方式都行
//                .returns(Types.STRING)
                .returns(new TypeHint<String>() {});


        result.print();
        result2.print();
        result3.print();

        environment.execute();
    }
}
