package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	/*
	 * you’ll use findByUsername() method in the user details service to look up a User by their username.
	 *
	 * When generating the repository implementation, Spring Data examines any methods in the repository interface, parses the method name, and
	 * attempts to understand the method’s purpose in the context of the persisted object.
	 *
	 * Repository methods are composed of a verb, an optional subject, the word 'By', and a predicate. In the case of findByUsername(), the verb is
	 * 'find' and the predicate is 'Username'; the subject isn’t specified and is implied to be an 'User'.
	 *
	 * Spring Data knows that this method is intended to find 'User', because you’ve parameterized CrudRepository with 'User'. This method should find
	 * all 'User' entities by matching their 'Username' property with the value passed in as a parameter to the method.
	 *
	 * read more in 3.2.4 Customizing JPA repositories.
	 *
	 * Because your User class implements UserDetails, and because UserRepository provides a findByUsername() method, they’re perfectly suitable for
	 * use in a custom UserDetailsService implementation. (see UserRepositoryUserDetailsService)
	 */
}
