package com.zlx.flinklearn.dataSource;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 自定义数据源
 */
public class DefineAnyDataSource {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 这种形式 并行度只能是 1 提高并行度会报错 若要提高源数据要实现ParallelSourceFunction接口
        environment.setParallelism(1);

        // 设置多并行度报错
//        final DataStreamSource<Event> streamSource = environment.addSource(new RandomSource()).setParallelism(2);
        final DataStreamSource<Event> streamSource = environment.addSource(new RandomSource());

        final DataStreamSource<Event> parallelismSource = environment.addSource(new RandomSourceWithMoreParallelisms()).setParallelism(2);

//        streamSource.print("data--");
        parallelismSource.print("paralle--");

        environment.execute();
    }
}
