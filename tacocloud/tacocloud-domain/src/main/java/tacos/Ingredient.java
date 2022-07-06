package tacos;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {
	/**
	 * In order to declare this as a JPA entity, Ingredient must be annotated with @Entity. And its id property must be annotated with @Id to
	 * designate it as the property that will uniquely identify the entity in the database.
	 *
	 * The @NoArgsConstructor annotation at the class level. JPA requires that entities have a no arguments (fields/properties) constructor, so
	 * Lombok’s @NoArgsConstructor does that for you. You don’t want to be able to use it, though, so you make it private by setting the access
	 * attribute to AccessLevel.PRIVATE. And because there are final properties that must be set, you also set the force attribute to true, which
	 * results in the Lombok-generated constructor setting them to null.
	 *
	 * You also add a @RequiredArgsConstructor. The @Data implicitly adds a required arguments constructor, but when a @NoArgsConstructor is used,
	 * that constructor gets removed. An explicit @RequiredArgsConstructor ensures that you’ll still have a required arguments constructor in addition
	 * to the private no-arguments constructor.
	 */

	@Id
	private final String id;

	private final String name;

	private final Type type;

	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

}
