package org.example.kafka.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleConsumer {
	private static final Logger log = LogManager.getLogger(ExampleConsumer.class);
	
	private static final String DEMO_TIMEOUT = "2000";
	
	private ExampleConsumerConfig config;
	private KafkaConsumer consumer;
	private Properties props;
	private boolean commit;

	public ExampleConsumer() {
		config = ExampleConsumerConfig.fromEnv();
		props = ExampleConsumerConfig.createProperties(config);
		consumer = new KafkaConsumer(props);
		commit = !Boolean.parseBoolean(config.getEnableAutoCommit());
	}

	@SuppressWarnings("unchecked")
	public String consume() {
		consumer.subscribe(Collections.singletonList(config.getTopic()));
		log.info("Subscribed to topics: {}", consumer.listTopics());
		
		while (true) {
			log.info("Retrieving records");
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(Integer.parseInt(DEMO_TIMEOUT)));

			if (records.isEmpty()) {
				log.info("Found no records");
				continue;
			}

			log.info("Total No. of records received : {}", records.count());
			for (ConsumerRecord<String, String> record : records) {
				log.info("Record received partition : {}, key : {}, value : {}, offset : {}", record.partition(),
						record.key(), record.value(), record.offset());
			}

			List<PartitionInfo> partitions = consumer.partitionsFor("my-topic");
			for (PartitionInfo partition : partitions) {
				log.info("TOPIC: {}", partition.topic());
				log.info("PARTITION: {}", partition.partition());
			}
		}
	}

	public void closeConsumer() {
		consumer.close();
	}

}
