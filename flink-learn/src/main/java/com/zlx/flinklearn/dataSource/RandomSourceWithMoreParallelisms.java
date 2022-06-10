package com.zlx.flinklearn.dataSource;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 实现ParallelSourceFunction 接口可设置多并行度
 */
public class RandomSourceWithMoreParallelisms implements ParallelSourceFunction<Event> {

    private boolean running = true;

    String[] names = {"aa","dd","ss","ff","rr"};
    String[] urls = {"/qwe", "/sdf", "/gfd", "/wrw", "/vz", "/wety"};
    Random random = new Random();

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        while (running) {
            String name = names[random.nextInt(names.length)];
            String url = urls[random.nextInt(urls.length)];

            sourceContext.collect(new Event(name,url, Instant.now().toEpochMilli()));

            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }
}
