package org.example.kafka.stream;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ExampleStream {

  private static final Logger log = LogManager.getLogger(ExampleStream.class);

  private ExampleStreamConfig config;
  private KafkaStreams streams;
  private StreamsBuilder builder;
  private Properties props;

  public ExampleStream() {
    config = ExampleStreamConfig.fromEnv();
    props = ExampleStreamConfig.createProperties(config);
    builder = new StreamsBuilder();
    streams = new KafkaStreams(builder.build(), props);
  }

  @SuppressWarnings("unchecked")
  public void stream() {
      Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServers());
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> textLines = builder.stream("my-topic-1");
      KTable<String, Long> wordCounts = textLines.flatMapValues(new ValueMapper<String, Iterable<String>>() {
              @Override
              public Iterable<String> apply(String textLine) {
                  return Arrays.asList(textLine.toLowerCase().split("\\W+"));
              }
          }).groupBy(new KeyValueMapper<String, String, String>() {
              @Override
              public String apply(String key, String word) {
                  return word;
              }
          }).count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));

      wordCounts.toStream().to("my-topic-2", Produced.with(Serdes.String(), Serdes.Long()));

      KafkaStreams streams = new KafkaStreams(builder.build(), props);
      
      log.info("Starting stream: " + streams.toString());
      streams.start();
  }

}
