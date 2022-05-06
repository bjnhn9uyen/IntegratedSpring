package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Ingredient;

/*
 * similar to TacoRepresentation and TacoRepresentationAssembler
 */
class IngredientRepresentationAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientRepresentation> {

	public IngredientRepresentationAssembler() {
		super(IngredientController.class, IngredientRepresentation.class);
	}

	@Override
	protected IngredientRepresentation instantiateModel(Ingredient ingredient) {
		return new IngredientRepresentation(ingredient);
	}

	@Override
	public IngredientRepresentation toModel(Ingredient ingredient) {
		return createModelWithId(ingredient.getId(), ingredient);
	}

}