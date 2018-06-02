package com.gomezrondon.kafka.kafkawordcount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    private Log log = LogFactory.getLog(getClass());

    @StreamListener
    public void process(@Input(WordCountBinder.WORD_COUNT_IN) KStream<String, String> event){

        event.foreach((s, value) -> log.info("++++++ "+value));

      //  return null;

    }

}
