package com.zlx.flinklearn.transform;

import com.zlx.flinklearn.pojo.Event;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * 转换规则
 *       传入 Event对象 输出String类型
 */
public class TransFormMap implements MapFunction<Event,String> {
    @Override
    public String map(Event event) throws Exception {
        // 输出user的值
        return event.user;
    }
}
