package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.Order;
import tacos.User;

public interface OrderRepository extends CrudRepository<Order, Long> {

	/* Along with adding this method, you’ve also added the necessary findByUser() method. Because it contains the 'findByUser' in it. */
	List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
