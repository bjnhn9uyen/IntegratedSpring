package tacos.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tacos.Order;

/*
 * To start sending and receiving messages from a RabbitMQ broker with Spring, you need to add spring-boot-starter-amqp to your build. It will trigger
 * auto-configuration that will create a AMQP connection factory and RabbitTemplate beans (similar to JmsTemplate in the JMS module). But unlike the
 * JmsTemplate methods, which only routed messages to a given queue or topic, RabbitTemplate methods send messages in terms of exchanges and routing
 * keys
 *
 * Whereas JMS messages are addressed with the name of a destination from which the receiver will retrieve them, AMQP messages are addressed with the
 * name of an exchange and a routing key, which are decoupled from the queue that the receiver is listening to. The most important thing to understand
 * is that messages are sent to exchanges with routing keys and they’re consumed from queues. How they get from an exchange to a queue depends on the
 * binding definitions and what best suits your use cases (details in RabbitMQ in Action book)
 */
@Service
public class RabbitOrderMessagingService implements OrderMessagingService {

	private RabbitTemplate rabbit;

	@Autowired
	public RabbitOrderMessagingService(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}

	@Override
	public void sendOrder(Order order) {

		/*
		 * Once you have a MessageConverter in hand, it’s simple work to convert an Order to a Message. You must supply any message properties with a
		 * MessageProperties, but if you don’t need to set any such properties, a default instance of MessageProperties is fine. Then, all that’s left
		 * is to call send(), passing in the exchange and routing key (both of which are optional) along with the message. In this example, you’re
		 * specifying only the routing key—tacocloud.order—along with the message, so the default exchange will be used. The the default exchange name
		 * is an empty String (same as the default routing key). You can override these defaults by setting the spring.rabbitmq.template.exchange and
		 * spring.rabbitmq.template .routing-key properties in the application.yml file
		 */
//		MessageConverter converter = rabbit.getMessageConverter();
//		MessageProperties props = new MessageProperties();
//		props.setHeader("X_ORDER_SOURCE", "WEB");
//		Message message = converter.toMessage(order, props);
//		rabbit.send("tacocloud.order", message);

		/*
		 * It’s even easier to use convertAndSend() to let RabbitTemplate handle all of the conversion work for you. However, you don’t have quick
		 * access to the MessageProperties object. A MessagePostProcessor can help you with that
		 */
		rabbit.convertAndSend("tacocloud.order.queue", order, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				MessageProperties props = message.getMessageProperties();
				props.setHeader("X_ORDER_SOURCE", "WEB");
				return message;
			}
		});
	}

}
