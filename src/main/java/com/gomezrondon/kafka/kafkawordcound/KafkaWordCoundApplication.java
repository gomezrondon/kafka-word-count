package com.gomezrondon.kafka.kafkawordcound;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableBinding(WordCountBinder.class)
public class KafkaWordCoundApplication implements ApplicationRunner {

	private Log log = LogFactory.getLog(getClass());
	private final MessageChannel wordCountOut;

	public KafkaWordCoundApplication(WordCountBinder binding) {
		this.wordCountOut = binding.wordCountOut();
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaWordCoundApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {

		List<String> typeOperation = Arrays.asList("this is a Despoito"," no etiendo Retiro","claro que si Cancelacion","despacito comop no PagoTDC","solo una palabra renovacion");

		Runnable runnable =()->{
			String rOperation = typeOperation.get(new Random().nextInt(typeOperation.size()));
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


		};

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
	}
}
