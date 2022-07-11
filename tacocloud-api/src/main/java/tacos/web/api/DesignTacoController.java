package tacos.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

/**
 * The @RestController annotation is a stereotype annotation like @Controller and @Servicetells. It tells Spring that all handler methods in the
 * controller should have their return value written directly to the body of the response, rather than being carried in the model to a view for
 * rendering. Alternatively, you could have annotated DesignTacoController with @Controller, just like with any Spring MVC controller. But then you’d
 * need to also annotate all of the handler methods with @ResponseBody to achieve the same result.
 *
 * The produces attribute in the @RequestMapping annotation, specifies that any of the handler methods in DesignTacoController will only handle
 * requests if the request’s Accept header includes “application/json”
 */
@RestController
@RequestMapping(path = "/design", produces = "application/json")
public class DesignTacoController {

	private TacoRepository tacoRepo;

	@Autowired
	public DesignTacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	@GetMapping("/recent")
	public Iterable<Taco> recentTacos() {
//	public CollectionModel<EntityModel<Taco>> recentTacos() {
//	public CollectionModel<TacoRepresentation> recentTacos() {
		/*
		 * constructs a PageRequest object that specifies that you only want the first (0th) page of 12 results, sorted in descending order by the
		 * taco’s creation date. The content of this page of results is returned to the client, this is what your Angular code needs
		 * (recents.component.ts in Angular project)
		 */
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page).getContent();
//		List<Taco> tacos = tacoRepo.findAll(page).getContent();

		/*
		 * To add hyper-links to the list of recently created tacos, you no longer return the list of tacos directly. Instead, you use
		 * CollectionModel.wrap() to wrap the list of tacos as an instance of CollectionModel<EntityModel<Taco>>. But before returning the
		 * CollectionModel object, you add a link whose relationship name is 'recents' and whose URL is http://localhost:8080/design/recent
		 */
//		CollectionModel<EntityModel<Taco>> collectionModel = CollectionModel.wrap(tacos);
//		collectionModel.add(Link.of("http://localhost:8080/design/recent", "recents"));
//		return collectionModel;

		/*
		 * Hard-coding a URL like this is a really bad idea. You need a way to not hard-code a URL with localhost:8080 in it. WebMvcLinkBuilder of the
		 * Spring HATEOAS link builders is smart enough to know what the host name is without you having to hard-code it. The slash() method appends a
		 * slash (/) and the given value to the URL. Finally, you specify a relation name for the Link. In this example, the relation is named
		 * 'recents'.
		 */
//		CollectionModel<EntityModel<Taco>> collectionModel = CollectionModel.wrap(tacos);
//		collectionModel.add(WebMvcLinkBuilder.linkTo(DesignTacoController.class).slash("recent").withRel("recents"));
//		return collectionModel;

		/*
		 * WebMvcLinkBuilder has another method that can help eliminate any hard-coding associated with link URLs. Instead of calling slash(), you can
		 * call linkTo() by giving it a method on the controller (recentTacos() method) to have WebMvcLinkBuilder derive the base URL from both the
		 * controller’s base path and the method’s mapped path.
		 */
//		CollectionModel<EntityModel<Taco>> collectionModel = CollectionModel.wrap(tacos);
//		collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos()).withRel("recents"));
//		return collectionModel;

		/*
		 * Rather than return a CollectionModel<EntityModel<Taco>>, recentTacos() now returns a TacoRepresentationAssembler<TacoRepresentation> to
		 * take advantage of your new TacoRepresentation type. The toCollectionModel() method cycles through all of the Taco objects, calling the
		 * toModel() method that you overrode in TacoRepresentationAssembler to create a list of TacoRepresentation objects.
		 */
//		CollectionModel<TacoRepresentation> recentCollectionModels = new TacoRepresentationAssembler().toCollectionModel(tacos);
//		recentCollectionModels.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DesignTacoController.class).recentTacos()).withRel("recents"));
//		return recentCollectionModels;

	}

	/**
	 * This is an end-point that fetches a single Taco by its ID. By using a placeholder variable '{id}' in the handler method’s path and accepting a
	 * path variable using @PathVariable annotation, you can capture the ID and use it to look up the Taco object through the repository
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
//		return null;
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		/*
		 * By returning null, the client receives a response with an empty body and an HTTP status code of 200 (OK). The client is handed a response
		 * it can’t use, but the status code indicates everything is fine. A better approach would be to return a response with an HTTP 404 (NOT
		 * FOUND) status
		 */
	}

	/**
	 * You’re not specifying a path attribute here, so the postTaco() method will handle requests for /design as specified in the
	 * class-level @RequestMapping on DesignTacoController. Here you use 'consumes' attribute to say that the method will only handle requests whose
	 * Content-type matches application/json
	 *
	 * an HTTP status of 201 (CREATED) is more descriptive than 200 (OK). It tells the client that not only was the request successful, but a resource
	 * was created as a result.
	 *
	 * The @RequestBody annotation indicates that the body of the request should be converted to a Taco object and bound to the parameter. This
	 * annotation is important, without it, Spring MVC would assume that you want request parameters (either query parameters or form parameters) to
	 * be bound to the Taco object
	 */
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
}
