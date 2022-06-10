package com.zlx.flinklearn.start;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        // 1 创建环境
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 1.5 从参数中提取主机和端口号  参数要在启动的时候给定或者在idea Configurations里面配置 --host=127.0.0.1 --port=7777
//        ParameterTool tool = ParameterTool.fromArgs(args);
//        final String host = tool.get("host");
//        final int port = tool.getInt("port");

        // 2 读取文件
//        final DataStreamSource<String> streamSource = environment.readTextFile("C:\\workspace\\java\\myproject\\myspringbootdemo\\flink-learn\\input/words.txt");
        final DataStreamSource<String> streamSource = environment.socketTextStream("127.0.0.1", 7777);
//        final DataStreamSource<String> streamSource = environment.socketTextStream(host, port);

        // 3 将每行转换为二元组类型
        final SingleOutputStreamOperator<Tuple2<String, Long>> operator = streamSource.flatMap((String line, Collector<Tuple2<String, Long>> output) -> {
            final String[] words = line.split(" ");
            for (String word : words) {
                output.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        // 4 分组
//        final KeyedStream<Tuple2<String, Long>, Tuple> tuple2TupleKeyedStream = operator.keyBy(0);
        final KeyedStream<Tuple2<String, Long>, String> tuple2StringKeyedStream = operator.keyBy(data -> data.f0);

        // 5 聚合
        final SingleOutputStreamOperator<Tuple2<String, Long>> sum = tuple2StringKeyedStream.sum(1);

        // 6 输出结果
        sum.print();

        // 7 启动执行 一直
        environment.execute();
    }
}
