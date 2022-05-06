package tacos.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import tacos.Order;

/*
 * Whichever form of convertAndSend() you choose (in the JmsOrderMessagingService), the Order passed into convertAndSend() is converted into a Message
 * before it’s sent. Under the covers, this is achieved with an implementation of MessageConverter that does the dirty work of converting objects to
 * Messages. MessageConverter is a Spring-defined interface, and Spring already offers a handful of implementations
 * 
 * There's four Spring message converters: SimpleMessageConverter, MappingJackson2MessageConverter, MarshallingMessageConverter, and
 * MessagingMessageConverter. SimpleMessageConverter is the default, but it requires that the object being sent implement Serializable. You may prefer
 * to use one of the other message converters, such as MappingJackson2MessageConverter, to avoid that restriction. To apply a different message
 * converter, all you must do is declare an instance of the chosen converter as a bean. Spring Boot auto-configuration will discover this bean and
 * inject it into JmsTemplate in place of the default message converter
 */
@Configuration
public class MessagingConfig {

	// This bean declaration will enable MappingJackson2MessageConverter to be used instead of the default (SimpleMessageConverter)
	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();

		// setTypeIdPropertyName() method enables the receiver to know what type to convert an incoming message to.
		messageConverter.setTypeIdPropertyName("_typeId");

		// Instead of the fully qualified class-name being sent in the message’s _typeId property, the value order will be sent
		Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
		typeIdMappings.put("order", Order.class);
		messageConverter.setTypeIdMappings(typeIdMappings);

		return messageConverter;
	}

}