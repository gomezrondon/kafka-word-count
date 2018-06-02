package com.gomezrondon.kafka.kafkawordcount;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableBinding(WordCountBinder.class)
public class KafkaWordCountApplication implements ApplicationRunner {

	private Log log = LogFactory.getLog(getClass());
	private final MessageChannel wordCountOut;

	public KafkaWordCountApplication(WordCountBinder binding) {
		this.wordCountOut = binding.wordCountOut();
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaWordCountApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {

		//List<String> typeOperation = Arrays.asList("this is a Despoito"," no etiendo Retiro","claro que si Cancelacion","despacito comop no PagoTDC","solo una palabra renovacion");
		List<String> typeOperation = Arrays.asList("this is a Despoito");
		final int[] count = {0};
		Runnable runnable =()->{

			String rOperation = typeOperation.get(count[0]);
			Message<String> message = MessageBuilder
					.withPayload(rOperation)
					.setHeader(KafkaHeaders.MESSAGE_KEY, rOperation.getBytes())
					.build();
			try {
				this.wordCountOut.send(message);
				log.info(">>> Sent "+ message.getPayload());
			}catch (Exception e){
				log.error(e);
			}

			count[0]++;
			/*
			if(count[0]> (typeOperation.size()-1)){
				count[0]=0; // reset counter
			}
			*/
		};

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
	}

	@Component
	public static class WordCountSink {

		private final Log log = LogFactory.getLog(getClass());

		@StreamListener
		public void process(@Input(WordCountBinder.WORD_COUNT_KTABLE_IN) KTable<String, Long> counts){

			log.info("***********************************************************************");

			counts
					.toStream()
					.foreach((key, value) -> log.info(key + " = "+value ));

		}

	}


}
