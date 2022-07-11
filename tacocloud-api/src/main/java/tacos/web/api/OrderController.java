package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Order;
import tacos.data.OrderRepository;

@RestController
@RequestMapping(path = "/orders", produces = "application/json")
@CrossOrigin(origins = "*")// for Angular app
public class OrderController {

	private OrderRepository repo;

	public OrderController(OrderRepository repo) {
		this.repo = repo;
	}

	@GetMapping(produces = "application/json")
	public Iterable<Order> allOrders() {
		return repo.findAll();
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Order postOrder(@RequestBody Order order) {
		return repo.save(order);
	}

	/*
	 * PUT is the semantic opposite of GET. Whereas GET requests are for transferring data from the server to the client, PUT requests are for sending
	 * data from the client to the server.
	 */
	@PutMapping(path = "/{orderId}", consumes = "application/json")
	public Order putOrder(@RequestBody Order order) {
		return repo.save(order);
	}

	/*
	 * Whereas PUT is really intended to perform a 'wholesale replacement' operation rather than an 'update' operation. In contrast, the purpose of
	 * HTTP PATCH is to perform a patch or partial update of resource data. Instead of completely replacing the Order with the new data sent in, it
	 * inspects each field of the incoming Order object and applies any non-null values to the existing Order.
	 */
	@PatchMapping(path = "/{orderId}", consumes = "application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {

		Order order = repo.findById(orderId).get();
		if (patch.getDeliveryName() != null) {
			order.setDeliveryName(patch.getDeliveryName());
		}
		if (patch.getDeliveryStreet() != null) {
			order.setDeliveryStreet(patch.getDeliveryStreet());
		}
		if (patch.getDeliveryCity() != null) {
			order.setDeliveryCity(patch.getDeliveryCity());
		}
		if (patch.getDeliveryState() != null) {
			order.setDeliveryState(patch.getDeliveryState());
		}
		if (patch.getDeliveryZip() != null) {
			order.setDeliveryZip(patch.getDeliveryState());
		}
		if (patch.getCcNumber() != null) {
			order.setCcNumber(patch.getCcNumber());
		}
		if (patch.getCcExpiration() != null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
		if (patch.getCcCVV() != null) {
			order.setCcCVV(patch.getCcCVV());
		}
		return repo.save(order);
	}

	/**
	 * The @ResponseStatus annotation is to ensure that the response’s HTTP status is 204 (NO CONTENT). There’s no need to communicate any resource
	 * data back to the client for a resource that no longer exists, so responses to DELETE requests typically have no body and therefore should
	 * communicate an HTTP status code to let the client know not to expect any content
	 */
	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
		try {
			repo.deleteById(orderId);
		} catch (EmptyResultDataAccessException e) {
		}
	}

}
