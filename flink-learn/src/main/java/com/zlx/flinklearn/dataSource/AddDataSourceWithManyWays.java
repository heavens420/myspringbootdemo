package com.zlx.flinklearn.dataSource;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 从集合读取数据
 */
public class AddDataSourceWithManyWays {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        environment.setParallelism(1);

        List<Event> list = new ArrayList<>();
        list.add(new Event("nana", "/usr", 10000L));
        list.add(new Event("yingying", "/opt", 20000L));
        list.add(new Event("who", "/root", 30000L));

        // 1 从集合中读取
        final DataStreamSource<Event> stream = environment.fromCollection(list);

        // 2 从集合中读取的另一种方式
        final DataStreamSource<Event> dataStreamSource = environment.fromElements(
                new Event("kak", "/fk", 10000L),
                new Event("sf", "/fkihd", 20000L),
                new Event("fjlds", "/oij", 4000L)
        );

        // 3 从文件中读取
        final DataStreamSource<String> textFile = environment.readTextFile("C:\\workspace\\java\\myproject\\myspringbootdemo\\flink-learn\\src\\main\\java\\com\\zlx\\flinklearn\\pojo\\Event.java");

        // 4 从socket文本流中读取
        final DataStreamSource<String> socketTextStream = environment.socketTextStream("localhost", 7777);

        // 5 从kafka中读取数据
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "jing.tk:50017");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        final DataStreamSource<String> kafkaStream = environment.addSource(new FlinkKafkaConsumer<String>("flink", new SimpleStringSchema(), properties));

        stream.print("list-->");
        dataStreamSource.print("list2-->");
        textFile.print("text-->");
        socketTextStream.print("socket-->");
        kafkaStream.print("kafka-->");

        environment.execute();


    }
}
