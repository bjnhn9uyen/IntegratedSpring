package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

/**
 * Because you use Lombok to automatically generate accessor methods at runtime, there’s no need to do anything more than declare properties. They’ll
 * have appropriate getter and setter methods as needed at runtime.
 *
 * The @Data annotation declares that this class is a model. @Data implies the generation of a required arguments constructor, you can have any
 * constructor with specific arguments (fields/properties) as your requirement. In fact, the @Data annotation is provided by Lombok and tells Lombok
 * to generate all of getter and setter methods as well as a constructor that accepts all final properties as arguments. By using Lombok, you can keep
 * the code for Ingredient slim and trim.
 */
@Data
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * The class level: @Table specifies that Order entities should be persisted to a table named Taco_Order in the database. Although you could have
	 * used this annotation on any of the entities, it’s necessary with Order. Without it, JPA would default to persisting the entities to a table
	 * named Order, but order is a reserved word in SQL and would cause problems.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date placedAt;

	@ManyToOne
	private User user;
	/**
	 * When you initially create the Order object that’s bound to the order form, it’d be nice if you could prepopulate the Order with the user’s name
	 * and address, so they don’t have to reenter it for each order. Perhaps even more important, when you save their order, you should associate the
	 * Order entity with the User that created the order. The @ManyToOne annotation on this property indicates that an order belongs to a single user,
	 * and, conversely, that a user may have many orders.
	 */

	@NotBlank(message = "Delivery name is required")
	private String deliveryName;

	@NotBlank(message = "Street is required")
	private String deliveryStreet;

	@NotBlank(message = "City is required")
	private String deliveryCity;

	@NotBlank(message = "State is required")
	private String deliveryState;

	@NotBlank(message = "ZIP code is required")
	private String deliveryZip;

	/*
	 * You need to not only ensure that the ccNumber property isn’t empty, but that it contains a value that could be a valid credit card number. This
	 * annotation uses the Luhn algorithm check. But doesn’t guarantee that the credit card number is actually assigned to an account or that the
	 * account can be used for charging.
	 */
	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;

	/** The ccCVV property is annotated with @Digits to ensure that the value contains exactly three numeric digits. */
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;

	/** The @ManyToMany annotation declares that an Order can have many Taco objects, and a Taco can be a part of many Orders */
	@ManyToMany(targetEntity = Taco.class)
	private List<Taco> tacos = new ArrayList<>();

	public void addDesign(Taco design) {
		this.tacos.add(design);
	}

	/** placedAt() is annotated with @PrePersist to set the placedAt property to the current date and time before Order is persisted. */
	@PrePersist
	void placedAt() {
		this.placedAt = new Date();
	}
}
