package org.example.kafka.consumer;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleConsumerConfig {

	private static final Logger log = LogManager.getLogger(ExampleConsumerConfig.class);

	private final String bootstrapServers;
	private final String topic;
	private final String groupId;
	private final String autoOffsetReset = "earliest";
	private final String enableAutoCommit = "false";
	private final String securityProtocol = "PLAINTEXT";
	private final String serializerClass = "org.apache.kafka.common.serialization.StringSerializer";

	public ExampleConsumerConfig(String bootstrapServers, String topic, String groupId) {
		this.bootstrapServers = bootstrapServers;
		this.topic = topic;
		this.groupId = groupId;
	}

	public static ExampleConsumerConfig fromEnv() {
		String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
		log.info("BOOTSTRAP_SERVERS: {}", bootstrapServers);
		String topic = System.getenv("TOPIC_1");
		log.info("TOPIC_1: {}", topic);
		String groupId = System.getenv("GROUP_ID");
		log.info("GROUP_ID: {}", groupId);

		return new ExampleConsumerConfig(bootstrapServers, topic, groupId);
	}

	public static Properties createProperties(ExampleConsumerConfig config) {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOffsetReset());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getSerializerClass());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getSerializerClass());
		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, config.getSecurityProtocol());

		return props;
	}

	public String getBootstrapServers() {
		return bootstrapServers;
	}

	public String getTopic() {
		return topic;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}

	public String getEnableAutoCommit() {
		return enableAutoCommit;
	}

	public String getSecurityProtocol() {
		return securityProtocol;
	}

	public String getSerializerClass() {
		return serializerClass;
	}

}
