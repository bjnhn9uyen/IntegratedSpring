package tacos.kitchen.messaging.jms.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import tacos.Order;
import tacos.kitchen.KitchenUI;

//@Profile("jms-listener")
@Component
public class OrderListener {

	private KitchenUI ui;

	@Autowired
	public OrderListener(KitchenUI ui) {
		this.ui = ui;
	}

	/**
	 * The receiveOrder() method is annotated with @JmsListener to “listen” for messages on the tacocloud.order.queue destination. It doesn’t deal
	 * with JmsTemplate, nor is it explicitly invoked by your application code. Instead, framework code within Spring waits for messages to arrive on
	 * the specified destination, and when they arrive, the receiveOrder() method is invoked automatically with the message’s Order pay-load as a
	 * parameter. In many ways, the @JmsListener annotation is like one of Spring MVC’s request mapping annotations, such as @GetMapping
	 * or @PostMapping. In Spring MVC, methods annotated with one of the request mapping methods react to requests to a specified path. Similarly,
	 * methods that are annotated with @JmsListener react to messages that arrive in a destination
	 */
	@JmsListener(destination = "tacocloud.order.queue")
	public void receiveOrder(Order order) {
		ui.displayOrder(order);
	}

}

/*
 * The food preparers may not be able to prepare tacos as quickly as orders come in because they may have half-fulfilled an order when a new order is
 * displayed on the screen. The kitchen user interface would need to buffer the orders as they arrive to avoid over-burdening the kitchen staff.
 * That's why using push model isn’t the best choice. On the contrary, push model a perfect fit when messages can be handled quickly
 */
