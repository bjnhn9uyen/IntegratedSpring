package tacos.kitchen.messaging.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.kitchen.KitchenUI;

/*
 * KafkaTemplate differs from JmsTemplate and RabbitTemplate in that it doesn’t offer any methods for receiving messages. That means the only way to
 * consume messages from a Kafka topic using Spring is to write a message listener
 */
//@Profile("kafka-listener")
@Component
@Slf4j
public class OrderListener {

	private KitchenUI ui;

	@Autowired
	public OrderListener(KitchenUI ui) {
		this.ui = ui;
	}

	@KafkaListener(topics = "tacocloud.orders.topic")
	public void handle(Order order, ConsumerRecord<String, Order> record) {
		log.info("Received from partition {} with timestamp {}", record.partition(), record.timestamp());

		ui.displayOrder(order);
	}

	/*
	 * Alternate implementation: you could ask for a Message instead of a ConsumerRecord and achieve the same thing
	 */
//	@KafkaListener(topics = "tacocloud.orders.topic")
//	public void handle(Order order, Message<Order> message) {
//		MessageHeaders headers = message.getHeaders();
//		log.info("Received from partition {} with timestamp {}", headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
//				headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
//		ui.displayOrder(order);
//	}

	/*
	 * The message pay-load is also available via ConsumerRecord .value() or Message.getPayload(). This means that you could ask for the Order through
	 * those objects instead of asking for it directly as a parameter to handle().
	 */

}
