package org.example.kafka.stream;

import java.util.Properties;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
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

    // streams.start();


    // KStream<String, String> inputStream = builder.stream("my-topic-1");

    // KStream<String, String> outputStream = inputStream.

    KStream<String, String> messages = builder.stream("my-topic-1");

    messages.to("my-topic-2");

    log.info("Starting stream");
    streams.start();

  }

}
