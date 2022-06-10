package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.FilterFunction;

public class TransFormFilter implements FilterFunction<Event> {
    @Override
    public boolean filter(Event event) throws Exception {
        return event.user.equals("nana");
    }
}
