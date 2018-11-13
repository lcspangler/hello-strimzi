package org.example.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.kafka.consumer.ExampleConsumer;

public class ConsumerServletContextListener implements ServletContextListener {

	private static final Logger log = LogManager.getLogger(ConsumerServletContextListener.class);
	private ExampleConsumer consumer = new ExampleConsumer();

	public void contextInitialized(ServletContextEvent contextEvent) {
		log.info("Starting consumer");
		consumer.consume();
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		log.info("Stopping consumer");
		consumer.closeConsumer();
	}

}
