package com.zlx.rocketmq.service;

public interface MqService {
    void sendStringMessage(String topic,String body);

    void SendOneWayMessage(String topic, String body);

    void SendSyncMessage(String topic, String body);

    void SendAsyncMessage(String topic, String body);
}
