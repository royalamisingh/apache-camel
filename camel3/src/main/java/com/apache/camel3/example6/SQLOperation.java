package com.apache.camel3.example6;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SQLOperation {
	public static void main(String[] args) throws Exception {

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/camel_tutorial");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		SimpleRegistry registry = new SimpleRegistry();
		registry.bind("myDataSource", MysqlDataSource.class, dataSource);
		@SuppressWarnings("resource")
		CamelContext context = new DefaultCamelContext(registry);
		
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start")
				.to("jdbc:myDataSource")
				.bean(new ResultHandler(),"printResult");
			}
		});

		context.start();
		
		ProducerTemplate producer = context.createProducerTemplate();
		producer.sendBody("direct:start", "Select * from customer");
		
	}
}
