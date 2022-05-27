package tacos.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.Taco;
import tacos.data.TacoRepository;

/**
 * The REST end-point provided by Spring Data REST for replacing the DesignTacoController doesn’t have recentTacos() method. So we need to create this
 * RecentTacosController and only need the recentTacos() method in this REST controller.
 *
 * RecentTacosController is annotated with @RepositoryRestController to adopt Spring Data REST’s base path for its request mappings.
 *
 * Although @RepositoryRestController is named similarly to @RestController, it doesn’t carry the same semantics as @RestController. Specifically, it
 * doesn’t ensure that values returned from handler methods are automatically written to the body of the response. Therefore, you need to either
 * annotate the method with @ResponseBody or return a ResponseEntity that wraps the response data. Here you chose to return a ResponseEntity.
 */
@RepositoryRestController
public class RecentTacosController {

	private TacoRepository tacoRepo;

	@Autowired
	public RecentTacosController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}

	/**
	 * Even though @GetMapping is mapped to the path /tacos/recent, the @RepositoryRestController annotation at the class level will ensure that it
	 * will be prefixed with Spring Data REST’s base path. As you’ve configured it (in application.yml), the recentTacos() method will handle GET
	 * requests for /api/tacos/recent.
	 */
	@GetMapping(path = "/tacos/recent", produces = "application/hal+json")
	public ResponseEntity<CollectionModel<TacoRepresentation>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		List<Taco> tacos = tacoRepo.findAll(page).getContent();

		CollectionModel<TacoRepresentation> recentsRepresentation = new TacoRepresentationAssembler().toCollectionModel(tacos);

		recentsRepresentation.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecentTacosController.class).recentTacos()).withRel("recents"));
		return new ResponseEntity<>(recentsRepresentation, HttpStatus.OK);
	}

}
