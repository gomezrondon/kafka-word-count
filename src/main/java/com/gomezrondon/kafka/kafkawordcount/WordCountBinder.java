package com.gomezrondon.kafka.kafkawordcount;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface WordCountBinder {

    String WORD_COUNT_OUT = "wcout";
    String WORD_COUNT_IN = "wcin";
    String WORD_COUNTER = "wcounter";
    String WORD_COUNT_KTABLE_OUT = "wckout";
    String WORD_COUNT_KTABLE_IN = "wckin";

    @Output(WORD_COUNT_OUT)
    MessageChannel wordCountOut();

    @Input(WORD_COUNT_IN)
    KStream<?, ?> wordCountIn();

    // ---------- KTable output

    @Output(WORD_COUNT_KTABLE_OUT)
    KStream<String, Long> wordCountKtableOut();

    @Input(WORD_COUNT_KTABLE_IN)
    KTable<String, Long> wordCountKtableIn();

}
