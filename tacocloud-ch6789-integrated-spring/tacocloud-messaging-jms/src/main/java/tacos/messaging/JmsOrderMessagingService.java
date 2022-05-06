package tacos.messaging;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.Order;

/*
 * You’re going to use asynchronous messaging to send orders from the Taco Cloud web-site to a separate application in the Taco Cloud kitchens where
 * the tacos will be prepared. There's three options that Spring offers for asynchronous messaging: the Java Message Service (JMS), RabbitMQ and
 * Advanced Message Queueing Protocol (AMQP), and Apache Kafka. This is example of JMS (needs spring-boot-starter-artemis dependency)
 */
@Service
public class JmsOrderMessagingService implements OrderMessagingService {

	/*
	 * Spring supports JMS through a template-based abstraction known as JmsTemplate. Without JmsTemplate, you’d need to write code to create a
	 * connection and session with the message broker (in server side), and more code to deal with any exceptions that might be thrown in the course
	 * of sending a message.
	 */
	private JmsTemplate jms;

	@Autowired
	public JmsOrderMessagingService(JmsTemplate jms) {
		this.jms = jms;
	}

	/*
	 * The sendOrder() method calls jms.send(). But notice that the call to jms.send() doesn’t specify a destination (as a parameter). In order for
	 * this to work, you must also specify a default destination name with the spring.jms.template.default-destination property in the application.yml
	 * file
	 */
	@Override
	public void sendOrder(Order order) {

		/*
		 * passing in an anonymous inner-class implementation of MessageCreator. This implementation overrides createMessage() to create a new object
		 * message from the given Order object
		 */
//		jms.send(new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return session.createObjectMessage(order);
//			}
//		});

		/* Recognizing that MessageCreator is a functional interface, you can tidy up the sendOrder() method a bit with a lambda */
//		jms.send(session -> session.createObjectMessage(order));

		/* One way of doing that without specifying a default destination name is by passing the name as the first parameter to send() */
//		jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));

		/*
		 * Wouldn’t it be simpler if you only needed to specify the object that’s to be sent (and optionally the destination) without providing a
		 * MessageCreator? That describes succinctly how convertAndSend() works. You pass the object that’s to be sent directly to convertAndSend(),
		 * and the object will be converted into a Message before being sent.
		 */
//		jms.convertAndSend("tacocloud.order.queue", order);

		/*
		 * We need a way to communicate the source of an order to the kitchens at the restaurants. This will enable the kitchen staff to employ a
		 * different process for store orders than for web orders. We do it by adding a new source property to the Order object to carry this
		 * information, populating it with WEB for orders placed online or with STORE for orders placed in the stores. But that would require a change
		 * to both the website’s Order class and the kitchen application’s Order class. An easier solution would be to add a custom header to the
		 * message to carry the order’s source. By using a MessagePostProcessor as the final parameter to convertAndSend() to add the X_ORDER_SOURCE
		 * header before the message is sent
		 */
//		jms.convertAndSend("tacocloud.order.queue", order, new MessagePostProcessor() {
//			@Override
//			public Message postProcessMessage(Message message) throws JMSException {
//				message.setStringProperty("X_ORDER_SOURCE", "WEB");
//				return message;
//			}
//		});

		/*
		 * MessagePostProcessor is a functional interface. This means that you can simplify it a bit by replacing the anonymous inner class with a
		 * lambda
		 */
//		jms.convertAndSend("tacocloud.order.queue", order, message -> {
//			message.setStringProperty("X_ORDER_SOURCE", "WEB");
//			return message;
//		});

		/*
		 * In this example, although you only need this particular MessagePostProcessor for this one call to convertAndSend(), but in a real-world
		 * project, you may find yourself using the same MessagePostProcessor for several different calls to convertAndSend(). A method reference
		 * (addOrderSource) is a better choice than a lambda, avoiding unnecessary code duplication
		 */
		jms.convertAndSend("tacocloud.order.queue", order, this::addOrderSource);
	}

	private Message addOrderSource(Message message) throws JMSException {
		message.setStringProperty("X_ORDER_SOURCE", "WEB");
		return message;
	}

}