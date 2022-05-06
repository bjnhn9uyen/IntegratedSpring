package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.User;
import tacos.data.UserRepository;

/**
 * UserRepositoryUserDetailsService is annotated with @Service. This is another one of Spring’s stereotype annotations that flag it for inclusion in
 * Spring’s component scanning, so there’s no need to explicitly declare this class as a bean. Spring will automatically discover it and instantiate
 * it as a bean. This service will be @Autowired into SecurityConfig for user authentication.
 * 
 * In the SecurityConfig class, you simply make a call to the userDetailsService() method, passing in the UserDetailsService instance that has
 * been @Autowired into SecurityConfig (in the implementation of configure(AuthenticationManagerBuilder) method). Because UserDetailsService is an
 * interface, it needs to be implemented. When you pass UserDetailsService instance into the userDetailsService() method in the SecurityConfig class,
 * you actually pass this implementation.
 */
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

	/* inject an instance of UserRepository for the loadUserByUsername() method to call findByUsername() on the UserRepository to look up a User. */
	private UserRepository userRepo;

	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}

}
