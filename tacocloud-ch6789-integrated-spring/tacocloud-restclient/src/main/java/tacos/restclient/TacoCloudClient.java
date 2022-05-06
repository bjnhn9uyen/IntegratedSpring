package tacos.restclient;

import java.util.Collection;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tacos.Ingredient;
import tacos.Taco;

@Service
//@Slf4j
public class TacoCloudClient {

	private RestTemplate rest;
	private Traverson traverson;

	public TacoCloudClient(RestTemplate rest, Traverson traverson) {
		this.rest = rest;
		this.traverson = traverson;
	}

	/*
	 * GET examples : Suppose that you want to fetch an ingredient from the Taco Cloud API. You can use getForObject() to fetch the ingredient. These
	 * example below uses RestTemplate to fetch an Ingredient object by its ID.
	 */

	/*
	 * Specify parameter as variables argument.
	 * 
	 * The ingredientId parameter passed into getForObject() is used to fill in the {id} placeholder in the given URL. The variable parameters are
	 * assigned to the place holders in the order that they’re given
	 */
	public Ingredient getIngredientById(String ingredientId) {
		return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
	}

	/*
	 * Alternate implementations... The next three methods are alternative implementations of getIngredientById() as shown in chapter 6. If you'd like
	 * to try any of them out, comment out the previous method and uncomment the variant you want to use.
	 */

	/*
	 * Specify parameters with a map.
	 *
	 * The value of ingredientId is mapped to a key of id. The {id} placeholder is replaced by the map entry whose key is id
	 */
//	public Ingredient getIngredientById(String ingredientId) {
//		Map<String, String> urlVariables = new HashMap<>();
//		urlVariables.put("id", ingredientId);
//		return rest.getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, urlVariables);
//	}

	/*
	 * Request with URI instead of String.
	 * 
	 * Using a URI parameter requiring that you construct a URI object before calling getForObject(). Otherwise, it’s similar to both of the previous.
	 * The URI object is defined from a String specification, and its place holders filled in from entries in a Map
	 */
//	public Ingredient getIngredientById(String ingredientId) {
//		Map<String, String> urlVariables = new HashMap<>();
//		urlVariables.put("id", ingredientId);
//		URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}").build(urlVariables);
//		return rest.getForObject(url, Ingredient.class);
//	}

	/*
	 * Use getForEntity() instead of getForObject().
	 * 
	 * If the client needs more than the pay-load body, you may want to consider using getForEntity(). The getForEntity() method works in much the
	 * same way as getForObject(), but instead of returning a domain object that represents the response’s pay-load, it returns a ResponseEntity
	 * object that wraps that domain object. The ResponseEntity gives access to additional response details, such as the response headers. The
	 * getForEntity() method is overloaded with the same parameters as getForObject(), so you can provide the URL variables as a variable list
	 * parameter or call getForEntity() with a URI object
	 */
//	public Ingredient getIngredientById(String ingredientId) {
//		ResponseEntity<Ingredient> responseEntity = rest.getForEntity("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
//		log.info("Fetched time: " + responseEntity.getHeaders().getDate());
//		return responseEntity.getBody();
//	}

	public List<Ingredient> getAllIngredients() {
		return rest.exchange("http://localhost:8080/ingredients", HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {
		}).getBody();
	}

	/*
	 * PUT examples : Suppose that you want to replace an ingredient resource with the data from a new Ingredient object
	 */

	/*
	 * the URL is given as a String and has a placeholder that’s substituted by the given Ingredient object’s id property. The data to be sent is the
	 * Ingredient object itself. And like getForObject() and getForEntity(), the URL variables can be provided as either a variable argument list or
	 * as a Map.
	 */
	public void updateIngredient(Ingredient ingredient) {
		rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
	}

	/*
	 * POST examples : Now let’s say that you add a new ingredient to the Taco Cloud menu. An HTTP POST request to the …/ingredients end-point with
	 * ingredient data in the request body will make that happen
	 */

	/* returns the newly created Ingredient resource after the POST request */
	public Ingredient createIngredient(Ingredient ingredient) {
		return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
	}

	/*
	 * Alternate implementations... The next two methods are alternative implementations of createIngredient() as shown in chapter 6 (in
	 * tacos.web.api.IngredientController). If you'd like to try any of them out, comment out the previous method and uncomment the variant you want
	 * to use.
	 */

	/*
	 * If your client has more need for the location of the newly created resource, then you can call postForLocation() instead. The postForLocation()
	 * works much like postForObject() but it returns a URI of the newly created resource instead of the resource object itself. The URI returned is
	 * derived from the response’s Location header.
	 */
//	public URI createIngredient(Ingredient ingredient) {
//		return rest.postForLocation("http://localhost:8080/ingredients", ingredient, Ingredient.class);
//	}

	/* In the off chance that you need both the location and response pay-load, you can call postForEntity() */
//	public Ingredient createIngredient(Ingredient ingredient) {
//		ResponseEntity<Ingredient> responseEntity = rest.postForEntity("http://localhost:8080/ingredients", ingredient, Ingredient.class);
//		log.info("New resource created at " + responseEntity.getHeaders().getLocation());
//		return responseEntity.getBody();
//	}

	/*
	 * DELETE examples : Suppose that Taco Cloud no longer offers an ingredient and wants it completely removed as an option
	 */

	/*
	 * Only the URL (specified as a String) and a URL variable value '{id}' are given to delete(). But as with the other RestTemplate methods, the URL
	 * could be specified as a URI object or the URL parameters given as a Map
	 */
	public void deleteIngredient(Ingredient ingredient) {
		rest.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
	}

	/*
	 * Traverson (Traverse on) : consumes an API by traversing the API on relation names, and enables clients to navigate an API using hyper-links
	 * embedded in the responses. But one thing it doesn’t do is offer any methods for writing to or deleting from those APIs. In contrast,
	 * RestTemplate can write and delete resources, but doesn’t make it easy to navigate an API. When you need to both navigate an API and update or
	 * delete resources, you’ll need to use RestTemplate and Traverson together. Traverson can still be used to navigate to the link where a new
	 * resource will be created. Then RestTemplate can be given that link to do a POST, PUT, DELETE, or any other HTTP request you need.
	 */

	/*
	 * suppose that you’re interested in retrieving a list of all ingredients.
	 * 
	 * By calling the follow() method on the Traverson object, you can navigate to the resource whose link’s relation name is "ingredients". Now that
	 * the client has navigated to ingredients, you need to obtain the contents of that resource by calling toObject(). Considering that you need to
	 * read it in as a Resources<Ingredient> object, Java type erasure makes it difficult to provide type information for a generic type. But creating
	 * a ParameterizedTypeReference helps with that
	 * 
	 */
	public Iterable<Ingredient> getAllIngredientsWithTraverson() {
		ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType = new ParameterizedTypeReference<CollectionModel<Ingredient>>() {
		};

		CollectionModel<Ingredient> ingredientRes = traverson.follow("ingredients").toObject(ingredientType);

		Collection<Ingredient> ingredients = ingredientRes.getContent();

		return ingredients;
	}

	/*
	 * Let’s say that you want to fetch the most recently created tacos. Starting at the home resource, you can navigate to the recent tacos resource.
	 * Here you follow the Tacos link and then, from there, follow the Recents link. That brings you to the resource you’re interested in, so a call
	 * to toObject() with an appropriate ParameterizedTypeReference gets you what you want.
	 * 
	 * not working yet - try so hard
	 * 
	 */
	public Iterable<Taco> getRecentTacosWithTraverson() {
		ParameterizedTypeReference<CollectionModel<Taco>> tacoType = new ParameterizedTypeReference<CollectionModel<Taco>>() {
		};

		CollectionModel<Taco> tacoRes = traverson.follow("tacos").follow("recents").toObject(tacoType);

		/* Alternatively, the .follow() method can be simplified by listing a trail of relation names */
//		CollectionModel<Taco> tacoRes = traverson.follow("tacos", "recents").toObject(tacoType);

		return tacoRes.getContent();
	}

	/*
	 * Suppose that you want to add a new Ingredient to the Taco Cloud menu. The following addIngredient() method teams up Traverson and RestTemplate
	 * to post a new Ingredient to the API.
	 * 
	 * not working yet - try so hard
	 */
	public Ingredient addIngredient(Ingredient ingredient) {
		/*
		 * After following the Ingredients link, you ask for the link itself by calling asLink(). From that link, you ask for the link’s URL by
		 * calling getHref(). With a URL in hand, you have everything you need to call postForObject() on the RestTemplate instance and save the new
		 * ingredient.
		 */
		String ingredientsUrl = traverson.follow("ingredients").asLink().getHref();
		return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
	}

}
