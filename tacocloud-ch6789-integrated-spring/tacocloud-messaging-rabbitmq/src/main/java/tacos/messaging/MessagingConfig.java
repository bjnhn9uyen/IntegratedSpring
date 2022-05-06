package tacos.messaging;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * By default, message conversion is performed with SimpleMessageConverter, which is able to convert simple types (like String) and Serializable
 * objects to Message objects. But Spring offers several message converters for RabbitTemplate: Jackson2JsonMessageConverter,
 * MarshallingMessageConverter, SerializerMessageConverter, SimpleMessageConverter, ContentTypeDelegatingMessageConverter, MessagingMessageConverter.
 * If you need to change the message converter, all you need to do is configure a bean of type MessageConverter. Spring Boot auto-configuration will
 * discover this bean and inject it into RabbitTemplate in place of the default message converter
 */
@Configuration
public class MessagingConfig {

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
