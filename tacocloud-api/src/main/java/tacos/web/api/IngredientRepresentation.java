package tacos.web.api;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import tacos.Ingredient;
import tacos.Ingredient.Type;

/*
 * similar to TacoRepresentation and TacoRepresentationAssembler
 */
public class IngredientRepresentation extends RepresentationModel<IngredientRepresentation> {

	@Getter
	private String name;

	@Getter
	private Type type;

	public IngredientRepresentation(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}

}
