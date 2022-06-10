package com.zlx.flinklearn.dataSink;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataOutput2File {
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(2);

        List<Event> list = new ArrayList<>();
        list.add(new Event("nana", "/usr", 10000L));
        list.add(new Event("yingying", "/opt", 20000L));
        list.add(new Event("who", "/root", 30000L));
        list.add(new Event("o", "/rotert", 30030L));
        list.add(new Event("fafo", "/rofot", 30020L));
        list.add(new Event("gero", "/rogfsdt", 30010L));

        // 从集合中读取
        final DataStreamSource<Event> stream = environment.fromCollection(list);

        final StreamingFileSink<String> fileSink = StreamingFileSink.<String>forRowFormat(new Path("C:\\workspace\\java\\myproject\\myspringbootdemo\\flink-learn/output"),
                        new SimpleStringEncoder<>("UTF-8"))
                .withRollingPolicy(DefaultRollingPolicy.builder()
                        .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
                        .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
                        .withMaxPartSize(1024 * 1024 * 1024)
                        .build())
                .build();

        stream.map(Event::toString)
                .addSink(fileSink).setParallelism(4);

        environment.execute();
    }
}
