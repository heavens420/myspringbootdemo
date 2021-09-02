package com.zlx.rocketmq.service.impl;

import com.zlx.rocketmq.service.MqService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MqserviceImpl implements MqService {

    @Resource
    private RocketMQTemplate template;

    @Override
    public void sendStringMessage(String topic,String body) {
        template.convertAndSend(topic,body);
    }

    @Override
    public void SendOneWayMessage(String topic, String body) {
        template.sendOneWay(topic,body);
    }

    @Override
    public void SendSyncMessage(String topic, String body) {
        template.syncSend(topic, body);
    }

    @Override
    public void SendAsyncMessage(String topic, String body) {
        template.asyncSend(topic,body, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("send success");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("send failed");
            }
        });
    }
}
