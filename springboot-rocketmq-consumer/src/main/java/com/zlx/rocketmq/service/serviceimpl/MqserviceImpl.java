package com.zlx.rocketmq.service.serviceimpl;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "topic-springboot",consumerGroup = "${rocketmq.consumer.group}",selectorExpression = "*")
public class MqserviceImpl implements RocketMQListener<String> , RocketMQLocalTransactionListener {
    @Override
    public void onMessage(String o) {
        System.out.println("message:"+o);
    }

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}
