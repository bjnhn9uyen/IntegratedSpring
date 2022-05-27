package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

/*
 * Receiving messages from a RabbitMQ queue isn’t very different than from JMS, you have two choices: pulling messages from a queue with
 * RabbitTemplate, and having messages pushed to a @RabbitListener-annotated method. This example is the pull-based RabbitTemplate.receive() method
 */
@Profile("rabbitmq-template")
//@Component("templateOrderReceiver")
@Component
public class RabbitOrderReceiver implements OrderReceiver {

	private RabbitTemplate rabbit;
//	private MessageConverter converter;

	public RabbitOrderReceiver(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}

	@Override
	public Order receiveOrder() {

		/*
		 * makes a call to the receive() method on the injected RabbitTemplate to pull an order from the tacocloud.orders queue. This method accepts a
		 * long parameter to indicate a timeout for receiving the messages. By default, the receive timeout is 0 milliseconds. That is, a call to
		 * receive() will return immediately, potentially with a null value if no messages are available. By passing in a timeout value, you can have
		 * the receive() method block until a message arrives or until the timeout expires. Let’s say you decide to wait up to 30 seconds before
		 * giving up. If a Message is returned, you use the MessageConverter from the RabbitTemplate to convert the Message to an Order. On the other
		 * hand, if receive() returns null, you’ll return a null.
		 */
//		Message message = rabbit.receive("tacocloud.orders", 30000);
//		return message != null ? (Order) converter.fromMessage(message) : null;

		/*
		 * You might be thinking that it’d be a good idea to create an @ConfigurationProperties annotated class so you could configure that timeout
		 * with a Spring Boot configuration. You can simply remove the timeout value in the call to receive() and set it in your configuration with
		 * the spring.rabbitmq.template.receive-timeout property. And instead of using receive() and the message converter, you can use
		 * receiveAndConvert() and let it do the conversion for you.
		 */
		return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");

		/*
		 * Instead of casting from Object to Order, you can pass a ParameterizedTypeReference to receiveAndConvert() to receive an Order object
		 * directly. It is a more type-safe approach than casting. The only requirement to using a ParameterizedTypeReference with receiveAndConvert()
		 * is that the message converter must be an implementation of SmartMessageConverter; Jackson2JsonMessageConverter is the only out-of-the-box
		 * implementation to choose from
		 */
//		return rabbit.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<Order>() {
//		});
	}

}
