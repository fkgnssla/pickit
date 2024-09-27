package com.ssafy.pickit.global.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${spring.rabbitmq.host}")
	private String rabbitmqHost;

	@Value("${spring.rabbitmq.port}")
	private int rabbitmqPort;

	@Value("${spring.rabbitmq.username}")
	private String rabbitmqUsername;

	@Value("${spring.rabbitmq.password}")
	private String rabbitmqPassword;

	@Value("${rabbitmq.queue.name}")
	private String queueName;

	// 메시지를 받는 우체국처럼 어디로 보낼지 결정하는 역할
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;

	// 메시지가 어디로 가야 할지를 알려주는 주소(RabbitMQ내에 여러 큐가 존재할 수 있기에 필요하다.)
	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	@Bean
	public Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}

	// 어떤 큐에 어떤 키가 라우팅 될지 설정한다.
	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setHost(rabbitmqHost);
		cachingConnectionFactory.setPort(rabbitmqPort);
		cachingConnectionFactory.setUsername(rabbitmqUsername);
		cachingConnectionFactory.setPassword(rabbitmqPassword);
		return cachingConnectionFactory;
	}
}
