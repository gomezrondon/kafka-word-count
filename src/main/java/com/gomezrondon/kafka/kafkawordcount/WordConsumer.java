package com.gomezrondon.kafka.kafkawordcount;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class WordConsumer {

    @KafkaListener(topics = "word-count-out")
    public void consumer(Message<String> message){

        System.out.println("Consumer: "+message.getPayload());
    }
}
