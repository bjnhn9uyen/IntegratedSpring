package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import tacos.Order;

/*
 * Unlike the JMS and RabbitMQ options, however, there isn’t a Spring Boot starter for Kafka. You only need one dependency is 'spring-kafka', its
 * presence will trigger Spring Boot autoconfiguration for Kafka that will arrange for a KafkaTemplate in the Spring application context. All you need
 * to do is inject the KafkaTemplate and go to work sending and receiving messages. KafkaTemplate defaults to work with a Kafka broker on localhost,
 * listening on port 9092 (see application.yml file)
 */
@Service
public class KafkaOrderMessagingService implements OrderMessagingService {

	private KafkaTemplate<String, Order> kafkaTemplate;

	@Autowired
	public KafkaOrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void sendOrder(Order order) {

		/*
		 * There are no convertAndSend() methods. That’s because KafkaTemplate is typed with generics and is able to deal with domain types directly
		 * when sending messages. In a way, all of the send() methods are doing the job of convertAndSend(). For our purposes, we’re going to focus on
		 * sending the message pay-load 'order' to a given topic 'tacocloud.orders.topic'
		 */
//		kafkaTemplate.send("tacocloud.orders.topic", order);

		/*
		 * If you set your default topic to 'tacocloud.orders.topic' by setting the 'spring.kafka.template.default-topic' property (in the
		 * application.yml file), you can simplify the sendOrder() method slightly
		 */
		kafkaTemplate.sendDefault(order);
	}

}
