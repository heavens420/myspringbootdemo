package tk.zlx.kafkaconsumer.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Controller;

@Controller
public class ConsumerController {

    @KafkaListener(topics = {"test"})
    public void receiveMessage(ConsumerRecord<String,String> message, Acknowledgment ack){
        try {
            String value = message.value();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ack.acknowledge();
        }
    }
}
