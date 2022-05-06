package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.Taco;

public class TacoRepresentationAssembler extends RepresentationModelAssemblerSupport<Taco, TacoRepresentation> {

	/*
	 * TacoRepresentationAssembler has a default constructor that informs the superclass (RepresentationModelAssemblerSupport) that it will be using
	 * DesignTacoController to determine the base path for any URLs in links it creates when creating a TacoRepresentation.
	 */
	public TacoRepresentationAssembler() {
		super(DesignTacoController.class, TacoRepresentation.class);
	}

	/*
	 * Default implementation will assume a no-arg constructor and use reflection but can be overridden to MANUALLY set up the object instance
	 * initially (e.g. to improve performance if this becomes an issue). In this case, TacoRepresentation requires construction with a Taco, so you’re
	 * required to override it.
	 */
	@Override
	protected TacoRepresentation instantiateModel(Taco taco) {
		return new TacoRepresentation(taco);
	}

	/*
	 * Here you’re telling it to create a TacoRepresentation object from a Taco, and to automatically give it a self link with the URL being derived
	 * from the Taco object’s id property. toModel() appears to have a similar purpose to instantiateModel(), but they serve slightly different
	 * purposes. Whereas instantiateModel() is intended to only instantiate a RepresentationModel object, toModel() is intended not only to create the
	 * RepresentationModel object, but also to populate it with links. Under the covers, toModel() will call instantiateModel().
	 */
	@Override
	public TacoRepresentation toModel(Taco taco) {
		return createModelWithId(taco.getId(), taco);
	}

}
