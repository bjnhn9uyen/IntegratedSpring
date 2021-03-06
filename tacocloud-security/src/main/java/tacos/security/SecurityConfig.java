package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** @Configuration annotation designates this class as a configuration class. */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * Customizing user authentication
	 */

	/* inject userDetailsService for the implementation of configure(AuthenticationManagerBuilder) method below */
	@Autowired
	private UserDetailsService userDetailsService;

	/*
	 * As with JDBC-based authentication, you can (and should) also configure a password encoder so that the password can be encoded in the database.
	 * You’ll do this by first declaring a bean of type BCryptPasswordEncoder and then injecting it into your user details service configuration by
	 * calling passwordEncoder().
	 */
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
		/**
		 * because the encoder() method is annotated with @Bean, it will be used to declare a PasswordEncoder bean in the Spring application context.
		 * Any calls to encoder() will then be intercepted to return the bean instance from the application context.
		 *
		 * StandardPasswordEncoder is deprecated. Because it uses digest based password encoding and that is not considered secure. If you are
		 * developing a new system, BCryptPasswordEncoder is a better choice both in terms of security and interoperability with other languages.
		 */
	}

	/* This method will be invoked every time you login. */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)

				.passwordEncoder(encoder());
	}

	/* Intercepting requests to ensure that the user has proper authority is one of the most common things you’ll configure HttpSecurity to do. */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * You need to ensure that requests for /design and /orders are only available to authenticated users; all other requests should be permitted
		 * for all users. The order of these rules is important. Security rules declared first take precedence over those declared lower down. If you
		 * were to swap the order of those two security rules, all requests would have permitAll() applied to them; the rule for /design and /orders
		 * requests would have no effect.
		 */
		http.authorizeRequests()

//				.antMatchers("/design", "/orders", "/orders/current").access("hasRole('ROLE_USER')")

				.antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS

				.antMatchers("/design", "/orders/**").permitAll()

				.antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()

				.antMatchers("/", "/**").access("permitAll")

				/*
				 * Expressions can be much more flexible, suppose that you only wanted to allow users with ROLE_USER authority to create new tacos on
				 * Tuesdays
				 */
				// .access("hasRole('ROLE_USER') && T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK) ==
				// T(java.util.Calendar).TUESDAY")

				/*
				 * To replace the built-in login page, you first need to tell Spring Security what path your custom login page will be at. Then you
				 * need to provide a controller that handles requests at this path. Because your login page will be fairly simple (nothing but a view)
				 * it’s easy enough to declare it as a view controller in WebConfig. (see WebConfig)
				 */
				.and().formLogin().loginPage("/login")

				/*
				 * By default, Spring Security listens for login requests at /login and expects that the username and password fields be named
				 * 'username' and 'password'. This is configurable, you can customize the path and field names. ('authenticate', 'user', 'pwd')
				 */
				// .loginProcessingUrl("/authenticate")
				// .usernameParameter("user")
				// .passwordParameter("pwd")

				/*
				 * If the user click on 'Design a taco' in the home page prior to logging in, a successful login would take them to the design page.
				 * But you can change that by specifying a default success page. Optionally, you can force the user to the specific page after login,
				 * even if they were navigating elsewhere prior to logging in, by passing true as a second parameter to defaultSuccessUrl. In this
				 * case, defaultSuccessUrl will force the user to the home page after a successful login even if they clicked on 'Design a taco' prior
				 * to logging in. Without defaultSuccessUrl, after a successful login, the user will go directly to the design page if they clicked on
				 * 'Design a taco' prior to logging in.
				 */
				// .defaultSuccessUrl("/", true)// If you enable this line, the test will fail because it expects the URL prior to logging in

				.and().logout().logoutSuccessUrl("/")

				/* Make H2-Console non-secured; for debug purposes */
				.and().csrf().ignoringAntMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**")

				/* Allow pages to be loaded in frames from the same origin; needed for H2-Console */
				.and().headers().frameOptions().sameOrigin();
	}
}
