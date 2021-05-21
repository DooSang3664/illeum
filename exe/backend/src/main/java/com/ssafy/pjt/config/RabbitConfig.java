package com.ssafy.pjt.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

	@Autowired
	ConnectionFactory connectionFactory;
	
	@Value("amq.direct")
	String exchange;
	
	@Value("queue-name")
	String queueName;
	
	@Value("routing-key")
	String routingkey;
	
	@Value("topic-name")
	String topicName;
	
	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}
	
	@Bean
	TopicExchange topic() {
		return new TopicExchange(topicName);
	}
}