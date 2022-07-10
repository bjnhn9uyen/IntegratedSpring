package tacos;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Data;

/**
 * Because you use Lombok to automatically generate accessor methods at runtime, there’s no need to do anything more than declare properties. They’ll
 * have appropriate getter and setter methods as needed at runtime.
 *
 * The @Data annotation declares that this class is a model. @Data implies the generation of a required arguments constructor, you can have any
 * constructor with specific arguments (fields/properties) as your requirement. In fact, the @Data annotation is provided by Lombok and tells Lombok
 * to generate all of getter and setter methods as well as a constructor that accepts all final properties as arguments. By using Lombok, you can keep
 * the code for Ingredient slim and trim.
 *
 * The @RestResource annotation lets you give the entity any relation name and the URL path you want (in the json result)
 */
@Data
@Entity
@RestResource(rel = "tacos", path = "tacos")
public class Taco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date createdAt;

	@NotNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;

	/**
	 * To declare the relationship between a Taco and its associated Ingredient list, you annotate ingredients with @ManyToMany. A Taco can have many
	 * Ingredient objects, and an Ingredient can be a part of many Tacos.
	 */
	@ManyToMany(targetEntity = Ingredient.class)
	@Size(min = 1, message = "You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;

	/** createdAt() is annotated with @PrePersist to set the createdAt property to the current date and time before Taco is persisted. */
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

}
