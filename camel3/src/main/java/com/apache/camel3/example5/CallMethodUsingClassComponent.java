package com.apache.camel3.example5;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

public class CallMethodUsingClassComponent {


	public static void main(String[] args) throws Exception {

		MyService myService = new MyService();
		SimpleRegistry registry = new SimpleRegistry();
		registry.bind("myService", MyService.class, myService);
		@SuppressWarnings("resource")
		CamelContext context = new DefaultCamelContext(registry);
		
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				//from("direct:start").to("class:com.apache.camel3.example5.MyService?method=doSomething");
				from("direct:start").to("bean:myService?method=doSomething");
			}
		});

		context.start();
		
		ProducerTemplate producer = context.createProducerTemplate();
		producer.sendBody("direct:start", "Hello How Are You !");
	}


}
