package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 归约操作
 */
public class TransFormReduceTest {
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

        // 根据用户名统计相同用户名的个数
        final SingleOutputStreamOperator<Tuple2<String, Long>> reduce = dataStreamSource.map(new MapFunction<Event, Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> map(Event event) throws Exception {
                        return Tuple2.of(event.user, 1L);
                    }
                }).keyBy(data -> data.f0)
                .reduce(new ReduceFunction<Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> reduce(Tuple2<String, Long> stringLongTuple2, Tuple2<String, Long> t1) throws Exception {
                        return Tuple2.of(stringLongTuple2.f0, stringLongTuple2.f1 + t1.f1);
                    }
                });

        // 获取用户名最多的一组数据
        // 强行让所有数据归并到一个key 即也会发送到一个slot中
        final SingleOutputStreamOperator<Tuple2<String, Long>> reduce1 = reduce.keyBy(data -> "key")
                .reduce(new ReduceFunction<Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> reduce(Tuple2<String, Long> stringLongTuple2, Tuple2<String, Long> t1) throws Exception {
                        return stringLongTuple2.f1 > t1.f1 ? stringLongTuple2 : t1;
                    }
                });

        reduce.print("reduce--");
        reduce1.print("reduce1--");

        environment.execute();
    }
}
