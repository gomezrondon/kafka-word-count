package com.gomezrondon.kafka.kafkawordcount;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WordCountBinder {

    String WORD_COUNT_OUT = "wcout";
    String WORD_COUNT_IN = "wcin";

    @Output(WORD_COUNT_OUT)
    MessageChannel wordCountOut();

    @Input(WORD_COUNT_IN)
    KStream<?, ?> wordCountIn();

}
