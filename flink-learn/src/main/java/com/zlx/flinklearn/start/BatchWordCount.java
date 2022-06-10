package com.zlx.flinklearn.start;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 经典 DataSet API处理
 */
public class BatchWordCount {
    public static void main(String[] args) throws Exception {

        // 1 创建环境
        final ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

        // 2 从文件读数据
        final DataSource<String> dataSource = environment.readTextFile("C:\\workspace\\java\\myproject\\myspringbootdemo\\flink-learn\\input/words.txt");

        // 3 将每行进行分词 转化为一个二元组类型
        final FlatMapOperator<String, Tuple2<String, Long>> operator = dataSource.flatMap((String line, Collector<Tuple2<String, Long>> output) -> {
            // 分词
            final String[] words = line.split(" ");
            for (String word : words) {
                // 统计
                output.collect(Tuple2.of(word, 1L));
            }
        })  // returns的意义是防止泛型擦除
                .returns(Types.TUPLE(Types.STRING, Types.LONG));

        // 4 按照word分组  0代表索引即根据word进行分组
        final UnsortedGrouping<Tuple2<String, Long>> tuple2UnsortedGrouping = operator.groupBy(0);

        // 5 分组内聚合统计 1 代表索引 即 1L 对次数进行统计
        final AggregateOperator<Tuple2<String, Long>> sum = tuple2UnsortedGrouping.sum(1);

        // 6 输出统计结果
        sum.print();
    }
}
