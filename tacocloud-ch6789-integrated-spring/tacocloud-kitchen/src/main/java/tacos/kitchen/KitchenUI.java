package tacos.kitchen;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;

@Component
@Slf4j
public class KitchenUI {

	public void displayOrder(Order order) {
		// To display it in some sort of UI.
		log.info("RECEIVED ORDER:  " + order);
	}

}
