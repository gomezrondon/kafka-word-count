package com.gomezrondon.kafka.kafkawordcount;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class WordConsumer {
    private Log log = LogFactory.getLog(getClass());

    @KafkaListener(topics = "word-count-out")
    public void consumer(Message<String> message){

        log.info("Consumer: "+message.getPayload());
    }
}
