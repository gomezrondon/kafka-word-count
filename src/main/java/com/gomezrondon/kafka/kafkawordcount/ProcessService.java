package com.gomezrondon.kafka.kafkawordcount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ProcessService {

    private Log log = LogFactory.getLog(getClass());

    @StreamListener
    @SendTo(WordCountBinder.WORD_COUNT_KTABLE_OUT)
    public KStream<String, Long> process(@Input(WordCountBinder.WORD_COUNT_IN) KStream<String, String> event){
      //  just for test
      //  event.foreach((s, value) -> log.info("++++++ "+value));

        KStream<String, Long> kStream = event.mapValues(textLine -> textLine.toLowerCase())
                .flatMapValues(textLine -> Arrays.asList(textLine.split("\\W+")))
                .selectKey((key, word) -> word)
                .groupByKey()
                .count(Materialized.as(WordCountBinder.WORD_COUNTER))
                .toStream();

      //  System.out.println("< Word Count > "+kStream.groupByKey()+" < /Word Count > ");

        return kStream;

    }

}
