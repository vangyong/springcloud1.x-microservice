package cn.segema.cloud.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {

	@Bean
	public org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory() {
		com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();

		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		connectionFactory.setUsername("springcloud");
		connectionFactory.setPassword("springcloud");

		connectionFactory.setAutomaticRecoveryEnabled(true);
		connectionFactory.setNetworkRecoveryInterval(10000);

		Map<String, Object> connectionFactoryPropertiesMap = new HashMap();
		connectionFactoryPropertiesMap.put("principal", "RobertoHuang");
		connectionFactoryPropertiesMap.put("description", "RGP订单系统V2.0");
		connectionFactoryPropertiesMap.put("emailAddress", "RobertoHuang@foxmail.com");
		connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
		return cachingConnectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(
			org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}
}
