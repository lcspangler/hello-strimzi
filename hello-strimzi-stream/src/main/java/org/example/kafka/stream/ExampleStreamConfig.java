package org.example.kafka.stream;

import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleStreamConfig {

  private static final Logger log = LogManager.getLogger(ExampleStreamConfig.class);

  private final String bootstrapServers;
  private final String inboundTopic;
  private final String outboundTopic;
  private final String autoOffsetReset;
  private final String securityProtocol;
  private final String serde;
  private final String groupId;
  private final String deserializerClass;
  private final String serializerClass;
  private final String acks;

  public ExampleStreamConfig(String bootstrapServers, String inboundTopic, String outboundTopic,
      String autoOffsetReset, String securityProtocol, String serde, String groupId, String deserializerClass, String serializerClass, String acks) {
    this.bootstrapServers = bootstrapServers;
    this.inboundTopic = inboundTopic;
    this.outboundTopic = outboundTopic;
    this.autoOffsetReset = autoOffsetReset;
    this.securityProtocol = securityProtocol;
    this.serde = serde;
    this.groupId = groupId;
	this.deserializerClass = deserializerClass;
	this.serializerClass = serializerClass;
	this.acks = acks;
  }

  public static ExampleStreamConfig fromEnv() {
    String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
    log.info("BOOTSTRAP_SERVERS: {}", bootstrapServers);

    String inboundTopic = System.getenv("INBOUND_TOPIC");
    log.info("INBOUND_TOPIC: {}", inboundTopic);

    String outboundTopic = System.getenv("OUTBOUND_TOPIC");
    log.info("OUTBOUND_TOPIC: {}", outboundTopic);

    String autoOffsetReset = System.getenv("AUTO_OFFSET_RESET");
    log.info("AUTO_OFFSET_RESET: {}", autoOffsetReset);

    String securityProtocol = System.getenv("SECURITY_PROTOCOL");
    log.info("SECURITY_PROTOCOL: {}", securityProtocol);

    String serde = System.getenv("SERDE");
    log.info("SERDE: {}", serde);
    
    String groupId= System.getenv("GROUP_ID");
    log.info("GROUP_ID: {}", groupId);

	String deserializerClass = System.getenv("DESERIALIZER_CLASS");
	log.info("DESERIALIZER_CLASS: {}", deserializerClass);
	
	String serializerClass = System.getenv("SERIALIZER_CLASS");
	log.info("SERIALIZER_CLASS: {}", serializerClass);
	
	String acks = System.getenv("ACKS");
	log.info("ACKS: {}", acks);

    
    return new ExampleStreamConfig(bootstrapServers, null, null, autoOffsetReset, securityProtocol,
        serde, groupId, deserializerClass, serializerClass, acks);
  }

  public static Properties createProperties(ExampleStreamConfig config) {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-group");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
    props.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, config.getSecurityProtocol());
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
	props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
	props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOffsetReset());
	props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getDeserializerClass());
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getDeserializerClass());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getAutoOffsetReset());
    
	props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
	props.put(ProducerConfig.ACKS_CONFIG, config.getAcks());
	props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, config.getSerializerClass());
	props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, config.getSerializerClass());
    
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, config.getSecurityProtocol());

    return props;
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public String getInboundTopic() {
    return inboundTopic;
  }

  public String getOutboundTopic() {
    return outboundTopic;
  }

  public String getAutoOffsetReset() {
    return autoOffsetReset;
  }

  public String getSecurityProtocol() {
    return securityProtocol;
  }

  public String getSerde() {
    return serde;
  }

  public String getGroupId() {
	return groupId;
  }

  public String getDeserializerClass() {
	return deserializerClass;
  }
  
  public String getSerializerClass() {
	return serializerClass;
  }

  public String getAcks() {
	    return acks;
  }

  
}
