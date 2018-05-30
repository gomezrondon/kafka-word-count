package com.gomezrondon.kafka.kafkawordcount;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WordCountBinder {

    String WORD_COUNT_OUT = "wcout";

    @Output(WORD_COUNT_OUT)
    MessageChannel wordCountOut();


}
