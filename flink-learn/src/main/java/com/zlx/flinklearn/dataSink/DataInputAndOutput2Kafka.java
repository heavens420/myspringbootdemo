package com.zlx.flinklearn.dataSink;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

public class DataInputAndOutput2Kafka {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);
        Properties properties = new Properties();

        // 将数据写入到kafka
        final DataStreamSource<String> flink_in = environment.addSource(new FlinkKafkaConsumer<String>("flink_in",
                new SimpleStringSchema(),
                properties));

        final SingleOutputStreamOperator<String> result = flink_in.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                final String[] event = s.split(",");
                return new Event(event[0], event[1], Long.parseLong(event[2])).toString();
            }
        });

        // 将数据发送到kafka
        result.addSink(new FlinkKafkaProducer<String>("jing.tk:50017", "flink_out", new SimpleStringSchema()));


        environment.execute();
    }
}
