package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

public class TransFormMapTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        environment.setParallelism(1);

        List<Event> list = new ArrayList<>();
        list.add(new Event("nana", "/usr", 10000L));
        list.add(new Event("yingying", "/opt", 20000L));
        list.add(new Event("who", "/root", 30000L));

        // 从集合中读取
        final DataStreamSource<Event> stream = environment.fromCollection(list);

        // 1 使用自定义类的方式处理数据
        final SingleOutputStreamOperator<String> resultStream = stream.map(new TransFormMap());

        // 2 使用匿名内部类的方式处理数据
        final SingleOutputStreamOperator<String> resultStream2 = stream.map(new MapFunction<Event, String>() {
            @Override
            public String map(Event event) {
                return event.user;
            }
        });

        // 3 使用lambda表达式处理数据
        final SingleOutputStreamOperator<String> resultStream3 = stream.map(event -> event.user);

        resultStream.print();
        resultStream2.print();
        resultStream3.print();

        environment.execute();
    }
}
