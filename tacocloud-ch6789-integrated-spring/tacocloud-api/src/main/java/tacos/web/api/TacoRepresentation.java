package tacos.web.api;

import java.util.Date;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import tacos.Taco;

/**
 * You need to add links to the Taco model contained within the list. This class converts Taco objects to a new Taco representation model. The
 * TacoRepresentation object will look a lot like a Taco, but it will also be able to carry links. They both have name, createdAt, and ingredients
 * properties. But TacoRepresentation extends RepresentationModel to inherit a list of Link object and methods to manage the list of links.
 * TacoRepresentation doesn’t include the id property from Taco. The RepresentationModel’s self link will serve as the identifier for the
 * representation model from the perspective of an API client.
 * 
 * The result of response sent to clients will be in JSON format. If you were to re-factor the name of the TacoRepresentation class to something else,
 * the field name in the resulting JSON would change to match it. This would likely break any clients coded to count on that name. The @Relation
 * annotation can help break the coupling between the JSON field name and the resource type class names as defined in Java. By annotating TacoResource
 * with @Relation, you can specify how Spring HATEOAS should name the field in the resulting JSON.
 */
@Relation(value = "taco", collectionRelation = "tacos")
public class TacoRepresentation extends RepresentationModel<TacoRepresentation> {

	private static final IngredientRepresentationAssembler ingredientAssembler = new IngredientRepresentationAssembler();

	@Getter
	private final String name;

	@Getter
	private final Date createdAt;

	/* TacoRepresentation carries IngredientRepresentation objects instead of Ingredient objects */
	@Getter
	private final CollectionModel<IngredientRepresentation> ingredients;

	/* TacoRepresentation has a single constructor that accepts a Taco and copies the pertinent properties from the Taco to its own properties */
	public TacoRepresentation(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
	}
}

/*
 * In case you are using HATEOAS v1.0 and above (Spring boot >= 2.2.0), do note that the class names have changed. Notably the below classes have been
 * renamed:
 * 
 * ResourceSupport changed to RepresentationModel
 * 
 * Resource changed to EntityModel
 * 
 * Resources changed to CollectionModel
 * 
 * ResourceAssemblerSupport changed to RepresentationModelAssemblerSupport
 * 
 * ResourceProcessor changed to RepresentationModelProcessor
 * 
 * PagedResources changed to PagedModel
 */