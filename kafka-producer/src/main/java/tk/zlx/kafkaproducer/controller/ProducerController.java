package tk.zlx.kafkaproducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class ProducerController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 单向消息 只管发 不管发送成功与否 接收成功与否
     * @param topic
     * @param message
     */
    @GetMapping("/oneway")
    public void sendOneWayMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    /**
     * 发送同步消息
     * @param topic
     * @param message
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/syncmessage")
    public void SendSyncMessage(String topic, String message) throws ExecutionException, InterruptedException {
        kafkaTemplate.send(topic,message).get();
    }

    /**
     * 发送异步消息
     * @param topic
     * @param message
     */
    @GetMapping("/asyncmessage")
    public void sendAsyncMessage(String topic, String message) {
        kafkaTemplate.send(topic,message).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("发送失败");
            }

            @Override
            public void onSuccess(Object o) {
                System.out.println("发送成功");
            }
        });
    }

    /**
     * 发送默认消息
     * @param topic
     * @param message
     */
    @GetMapping("/defaultmessage")
    public void sendDefaultMessage(String topic, String message) {
        kafkaTemplate.sendDefault(topic, message);
    }


}
