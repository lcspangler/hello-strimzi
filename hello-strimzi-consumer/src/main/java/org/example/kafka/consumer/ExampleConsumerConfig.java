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
	private final String autoOffsetReset;
	private final String enableAutoCommit;
	private final String securityProtocol;
	private final String serializerClass;

	public ExampleConsumerConfig(String bootstrapServers, String topic, String groupId, String autoOffsetReset, String enableAutoCommit, String securityProtocol, String serializerClass) {
		this.bootstrapServers = bootstrapServers;
		this.topic = topic;
		this.groupId = groupId;
		this.autoOffsetReset = autoOffsetReset;
		this.enableAutoCommit = enableAutoCommit;
		this.securityProtocol = securityProtocol;
		this.serializerClass = serializerClass;
	}

	public static ExampleConsumerConfig fromEnv() {
		String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
		log.info("BOOTSTRAP_SERVERS: {}", bootstrapServers);
		
		String topic = System.getenv("CONSUMER_TOPIC");
		log.info("CONSUMER_TOPIC: {}", topic);
		
		String groupId = System.getenv("GROUP_ID");
		log.info("GROUP_ID: {}", groupId);
		
		String autoOffsetReset = System.getenv("AUTO_OFFSET_RESET");
		log.info("AUTO_OFFSET_RESET: {}", autoOffsetReset);
		
		String enableAutoCommit = System.getenv("ENABLE_AUTO_COMMIT");
		log.info("ENABLE_AUTO_COMMIT: {}", enableAutoCommit);
		
		String securityProtocol = System.getenv("SECURITY_PROTOCOL");
		log.info("SECURITY_PROTOCOL: {}", securityProtocol);
		
		String serializerClass = System.getenv("SERIALIZER_CLASS");
		log.info("SERIALIZER_CLASS: {}", serializerClass);

		return new ExampleConsumerConfig(bootstrapServers, topic, groupId, autoOffsetReset, enableAutoCommit, securityProtocol, serializerClass);
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
