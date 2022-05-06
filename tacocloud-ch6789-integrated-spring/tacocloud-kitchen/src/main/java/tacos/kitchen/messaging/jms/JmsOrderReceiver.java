package tacos.kitchen.messaging.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.OrderReceiver;

/*
 * When it comes to consuming messages, you have the choice of a pull model, where your code requests a message and waits until one arrives (you
 * request a message, and the thread blocks until a message is available), or a push model, in which messages are handed to your code as they become
 * available (you define a message listener that’s invoked any time a message is available). It’s generally accepted that the push model is the best
 * choice, as it doesn’t block a thread. But in some use cases, a listener could be over-burdened if messages arrive too quickly. The pull model
 * enables a consumer to declare that they’re ready to process a new message
 */
//@Profile("jms-template")
//@Component("templateOrderReceiver")
@Component
public class JmsOrderReceiver implements OrderReceiver {

	/* JmsTemplate offers several methods for receiving messages, but all of them use a pull model for requesting a message. */
	private JmsTemplate jms;
//	private MessageConverter converter;

//	@Autowired
//	public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
//		this.jms = jms;
//		this.converter = converter;
//	}

	public JmsOrderReceiver(JmsTemplate jms) {
		this.jms = jms;
	}

	@Override
	public Order receiveOrder() {
		/*
		 * Here you’ve used a String to specify the destination to pull an order from. The receive() method returns an unconverted Message. But what
		 * you really need is the Order that’s inside of the Message, so the very next thing that happens is that you use an injected message
		 * converter to convert the message. The type ID property in the message will guide the converter in converting it to an Order, but it’s
		 * returned as an Object that requires casting before you can return it.
		 */
//		Message message = jms.receive("tacocloud.order.queue");
//		try {
//			return (Order) converter.fromMessage(message);
//		} catch (MessageConversionException | JMSException e) {
//			e.printStackTrace();
//			return null;
//		}

		/*
		 * The receive() methods receive a raw Message, whereas the receiveAndConvert() methods use a configured message converter to convert messages
		 * into domain types. Receiving a raw Message object might be useful in some cases where you need to inspect the message’s properties and
		 * headers. But often you only need the pay-load. Converting that pay-load to a domain type is a two-step process and requires that the
		 * message converter be injected into the component. When you only care about the message’s pay-load, receiveAndConvert() is a lot simpler.
		 * And you no longer need to inject a MessageConverter, because all of the message conversion will be done behind the scenes in
		 * receiveAndConvert()
		 */
		return (Order) jms.receiveAndConvert("tacocloud.order.queue");
	}

}

/*
 * Let’s consider how receiveOrder() might be used in the Taco Cloud kitchen application. A food preparer at one of Taco Cloud’s kitchens might push a
 * button or take some action to indicate that they’re ready to start building tacos. At that point, receiveOrder() would be invoked and the call to
 * receive() or receiveAndConvert() would block. Nothing else would happen until an order message is ready. Once an order arrives, it will be returned
 * from receiveOrder() and its information used to display the details of the order for the food preparer to get to work. This seems like a natural
 * choice for a pull model. (see how push model works at tacos.kitchen.messaging.jms.listener.OrderListener)
 */
