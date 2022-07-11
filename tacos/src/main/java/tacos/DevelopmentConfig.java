package tacos;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

/**
 * It’s possible to use @Profile on an entire @Configuration annotated class. The @Profile("!prod") annotation below declares that CommandLineRunner
 * bean (or any bean in this class) were ALWAYS created for development purpose UNLESS the 'prod' profile is active. Another way, you can also create
 * another profile for development purpose and name it 'dev'. The @Profile("dev") declares that CommandLineRunner bean were ONLY created IF the 'dev'
 * profile is active.
 */
//@Profile("dev")
//@Profile("!prod")
//@Configuration
public class DevelopmentConfig {

//	@Bean
//	public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo, PasswordEncoder encoder, TacoRepository tacoRepo) {
//		return new CommandLineRunner() {
//			@Override
//			public void run(String... args) throws Exception {
//				Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
//				Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
//				Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
//				Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
//				Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
//				Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
//				Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
//				Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
//				Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
//				Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
//				repo.save(cornTortilla);
//				repo.save(flourTortilla);
//				repo.save(carnitas);
//		        repo.save(groundBeef);
//		        repo.save(lettuce);
//		        repo.save(tomatoes);
//		        repo.save(cheddar);
//		        repo.save(jack);
//		        repo.save(salsa);
//		        repo.save(sourCream);
//
//				/* userRepo for ease of testing with a built-in user */
//				userRepo.save(new User("habuma00", encoder.encode("aA@00000"), "Craig Walls", "123 North Street", "Cross Roads", "TX", "76227",
//						"1231231234"));
//
//				Taco taco1 = new Taco();
//				taco1.setName("Carnivore");
//				taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
//				tacoRepo.save(taco1);
//
//				Taco taco2 = new Taco();
//				taco2.setName("Bovine Bounty");
//				taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
//				tacoRepo.save(taco2);
//
//				Taco taco3 = new Taco();
//				taco3.setName("Veg-Out");
//				taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
//				tacoRepo.save(taco3);
//			}
//		};
//	}

}
