package org.example.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.kafka.stream.ExampleStream;

public class StreamServletContextListener implements ServletContextListener {

  private static final Logger log = LogManager.getLogger(StreamServletContextListener.class);

  private ExampleStream stream = new ExampleStream();

  public void contextInitialized(ServletContextEvent contextEvent) {
    log.info("Starting Kafka Stream");
    stream.stream();
  }

}
