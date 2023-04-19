package com.rabbit.assignment3.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

	final String exchangeName = "exchange";
	final String routingKey = "edustat";

	final String queueName1 = "topic1";
	final String queueName2 = "topic2";
	final String queueName3 = "topic3";
	final String queueName4 = "topic4";
	final String queueName5 = "topic5";

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}

	@Bean
	Queue topic1() {
		return new Queue(queueName1, false);
	}

	@Bean
	Binding binding1(Queue topic1, TopicExchange exchange) {
		return BindingBuilder.bind(topic1).to(exchange).with(routingKey);
	}

	@Bean
	Queue topic2() {
		return new Queue(queueName2, false);
	}

	@Bean
	Binding binding2(Queue topic2, TopicExchange exchange) {
		return BindingBuilder.bind(topic2).to(exchange).with(routingKey);
	}

	@Bean
	Queue topic3() {
		return new Queue(queueName3, false);
	}

	@Bean
	Binding binding3(Queue topic3, TopicExchange exchange) {
		return BindingBuilder.bind(topic3).to(exchange).with(routingKey);
	}

	@Bean
	Queue topic4() {
		return new Queue(queueName4, false);
	}

	@Bean
	Binding binding4(Queue topic4, TopicExchange exchange) {
		return BindingBuilder.bind(topic4).to(exchange).with(routingKey);
	}

	@Bean
	Queue topic5() {
		return new Queue(queueName5, false);
	}

	@Bean
	Binding binding5(Queue topic5, TopicExchange exchange) {
		return BindingBuilder.bind(topic5).to(exchange).with(routingKey);
	}

}
