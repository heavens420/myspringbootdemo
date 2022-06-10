package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

public class TransFormFilterTest {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);

        List<Event> list = new ArrayList<>();
        list.add(new Event("nana", "/usr", 10000L));
        list.add(new Event("yingying", "/opt", 20000L));
        list.add(new Event("who", "/root", 30000L));

        // 从集合中读取
        final DataStreamSource<Event> stream = environment.fromCollection(list);

        // 1 自定义类过滤器
        final SingleOutputStreamOperator<Event> restult = stream.filter(new TransFormFilter());

        // 2 匿名内部类过滤器
        final SingleOutputStreamOperator<Event> result2 = stream.filter(new FilterFunction<Event>() {
            @Override
            public boolean filter(Event event) throws Exception {
                return event.user.equals("nana");
            }
        });

        // 3 lambda表达式过滤器
        final SingleOutputStreamOperator<Event> result3 = stream.filter(event -> event.user.equals("nana"));

        restult.print();
        result2.print();
        result3.print();

        environment.execute();
    }
}
