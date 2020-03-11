package com.apache.camel3.example3;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProdAndConsumerExample {
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start").process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						System.out.println("I am the processor, I am converting the message to upper case!");
                        String message = exchange.getIn().getBody(String.class);
                        exchange.getMessage().setBody(message.toUpperCase(), String.class);
					}
				}).to("seda:end");
			}
		});

		context.start();

		ProducerTemplate producer = context.createProducerTemplate();
		producer.sendBody("direct:start", "Hello How Are You !");

		ConsumerTemplate consumer = context.createConsumerTemplate();
		String val = consumer.receiveBody("seda:end", String.class);
		System.out.println(val);
	}
}
