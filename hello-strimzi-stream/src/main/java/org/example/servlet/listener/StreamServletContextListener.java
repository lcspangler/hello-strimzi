package org.example.servlet.listener;

import javax.servlet.ServletContextEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.kafka.stream.ExampleStream;

public class StreamServletContextListener {

  private static final Logger log = LogManager.getLogger(StreamServletContextListener.class);

  private ExampleStream stream = new ExampleStream();

  public void contextInitialized(ServletContextEvent contextEvent) {
    log.info("Starting streamer");
    stream.stream();
  }

}
