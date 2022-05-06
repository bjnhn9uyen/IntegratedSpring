package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
	/*
	 * In the JDBC versions of the repositories, you explicitly declared the methods you wanted the repository to provide. But with Spring Data, you
	 * can extend the CrudRepository interface instead. Notice that it’s parameterized, with the first parameter being the entity type the repository
	 * is to persist, and the second parameter being the type of the entity ID property. For IngredientRepository, the parameters should be Ingredient
	 * and String.
	 * 
	 * There’s no need to write an implementation! When the application starts, Spring Data JPA automatically generates an implementation on the fly.
	 * Just inject them into the controllers like you did for the JDBC-based implementations, and you’re done. The methods provided by CrudRepository
	 * are great for general-purpose persistence of entities.
	 * 
	 * You can similarly define the TacoRepository and the OrderRepository like this.
	 */
}
