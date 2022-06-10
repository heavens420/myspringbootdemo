package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class TransFormFlatMap implements FlatMapFunction<Event,String> {
    @Override
    public void flatMap(Event event, Collector<String> collector) {
        collector.collect(event.user);
        collector.collect(event.url);
        collector.collect(event.timestamp.toString());
    }
}
