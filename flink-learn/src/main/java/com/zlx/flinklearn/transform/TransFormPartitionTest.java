package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * 分区
 */
@Slf4j
public class TransFormPartitionTest {
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

        // 1 随机分区
//        dataStreamSource.shuffle().print().setParallelism(4);

        // 2 轮询分区
//        dataStreamSource.rebalance().print().setParallelism(4);

        // 3 缩放分区
        environment.addSource(new RichParallelSourceFunction<Integer>() {
                    @Override
                    public void run(SourceContext<Integer> sourceContext) {
                        for (int i = 0; i < 8; i++) {
                            if (i % 2 == getRuntimeContext().getIndexOfThisSubtask()) {
                                sourceContext.collect(i);
                            }
                        }
                    }

                    @Override
                    public void cancel() {
                        log.info("cancel task");
                    }
                })
                .setParallelism(2)
                .rescale();
//                .print()
//                .setParallelism(4);

        // 4 广播分区  每一个分区都发送一份完整的数据
//        dataStreamSource.broadcast().print().setParallelism(3);

        // 5 全局分区  将所有数据仅发送到某一个分区
//        dataStreamSource.global().print().setParallelism(3);

        // 自定义分区
        environment.fromElements(1, 2, 4, 4, 6, 6, 7, 8, 9, 10)
                .partitionCustom(new Partitioner<Integer>() {
                    // 选择分区
                    @Override
                    public int partition(Integer integer, int i) {
                        return integer % 2;
                    }
                }, new KeySelector<Integer, Integer>() {
                    // 根据当前数据值进行分组
                    @Override
                    public Integer getKey(Integer integer) throws Exception {
                        return integer;
                    }
                })
                .print()
                .setParallelism(5);

        environment.execute();
    }
}
