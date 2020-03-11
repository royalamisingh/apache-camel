package com.apache.camel3.example4;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMQConsumer {

	public static void main(String[] args) throws Exception {

		@SuppressWarnings("resource")
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		context.addComponent("jms",
		JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("activemq:queue:my_queue").to("seda:end");
			}
		});

		context.start();
		
		ConsumerTemplate consumer = context.createConsumerTemplate();
		String val = consumer.receiveBody("seda:end", String.class);
		System.out.println(val);
	}

}
