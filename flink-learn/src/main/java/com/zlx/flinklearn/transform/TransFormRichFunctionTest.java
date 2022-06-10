package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 富函数
 */
@Slf4j
public class TransFormRichFunctionTest {
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

        dataStreamSource.map(new RichMapFunction<Event, Integer>() {
            @Override
            public Integer map(Event event) throws Exception {
                // 返回user的长度
                return event.user.length();
            }

            /**
             * 每一个任务的生命周期函数只调用一次
             * @param parameters
             * @throws Exception
             */
            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                log.info("open生命周期被调用{}号任务启动",getRuntimeContext().getIndexOfThisSubtask());
            }

            @Override
            public void close() throws Exception {
                super.close();
                log.info("close生命周期被调用{}号任务结束",getRuntimeContext().getIndexOfThisSubtask());
            }
        }).setParallelism(2).
                print();

        environment.execute();
    }
}
