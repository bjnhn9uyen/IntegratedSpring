package tacos.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import tacos.User;

@Data
public class RegistrationFormat {

	@Pattern(regexp = "^"

			+ "(?=.{8,20}$)"// at least 8-20 characters long

			+ "(?![_.])" // no _ or . at the beginning

			+ "(?!.*[_.]{2})" // no __ or _. or ._ or .. inside

			+ "[a-zA-Z0-9._]+" // allowed characters, underscores, and dots

			+ "(?<![_.])" // no _ or . at the end

			+ "$",

			message = "Username must be 8 to 20 chars long; alphanumeric chars, underscore and dot are allowed; "
					+ "underscore and dot can not be at the end or start of a username or next to each other, or used multiple times in a row")
	private String username;
	/*
	 * Avoid writing apostrophes ' in error message, because, for example, "can't" will be lost the apostrophe ' when it passed to Thymeleaf for
	 * rendering error message, so the test will fail.
	 */

	@Pattern(regexp = "^"

			+ "(?=.*[0-9])" // a digit must occur at least once

			+ "(?=.*[a-z])" // a lower case letter must occur at least once

			+ "(?=.*[A-Z])" // an upper case letter must occur at least once

			+ "(?=.*[@#$%^&+=])" // a special character must occur at least once

			+ "(?=\\S+$)" // no whitespace allowed in the entire string

			+ ".{8,}" // at least 8 chars long

			+ "$",

			message = "Password must be at least 8 chars long, contains at least one digit, one lower alpha char, one upper alpha char, "
					+ "one special char, and not contains whitespace")
	private String password;

	@NotBlank(message = "The password confirmation does not match")
	private String pwConfirm;

	private String fullname;
	private String street;
	private String city;
	private String state;
	private String zip;

	@Pattern(regexp = "^[1-9][0-9]{9,14}$", message = "Phone number must be 10 to 15 digits and not start with 0")
	private String phone;
	/* [1-9] : start number != 0; [0-9]{9,14} : the 9->14 next numbers can be in [0-9] */

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phone);
		/*
		 * toUser() method uses those properties to create a new User object, which is what processRegistration() will save (in
		 * RegistrationController), using the injected UserRepository. This uses PasswordEncoder bean (in SecurityConfig class) to encode the password
		 * before saving it to the database.
		 */
	}

	/*
	 * override Lombok setter method to compare 'password' and 'pwConfirm' received from the registration form. Lombok uses name of the fields to
	 * generate their getter/setter methods in alphabet order. Therefore, if you name the password confirmation is 'confirm', Lombok priority sets
	 * value for 'confirm', and then 'password'. And if you try to compare value of 'confirm' and 'password' as below, the value of 'password' is null
	 * in this moment, it hasn't been set yet. If you change 'confirm' to 'pwConfirm', the value of 'password' is already set now for comparison,
	 * because 'pw' in 'pwConfirm' is after of 'pa' in 'password' in alphabet order
	 */
	public void setPwConfirm(String pwConfirm) {
		if (!pwConfirm.equals(password)) {
			this.pwConfirm = ""; // set to null String for @NotBlank error message
		} else {
			this.pwConfirm = pwConfirm;
		}
	}
}
