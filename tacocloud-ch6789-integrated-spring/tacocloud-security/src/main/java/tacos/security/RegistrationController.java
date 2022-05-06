package tacos.security;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.data.UserRepository;

/**
 * Like any typical Spring MVC controller, RegistrationController is annotated with @Controller to designate it as a controller and to mark it for
 * component scanning. It’s also annotated with @RequestMapping such that it will handle requests whose path is /register.
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

	/*
	 * inject UserRepository and PasswordEncoder for the processRegistration() method below. This is PasswordEncoder bean you declared before in
	 * SecurityConfig class
	 */
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;

	public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@ModelAttribute(name = "reg") // see '<form method="POST" th:object="${reg}" th:action="@{/register}" id="registerForm">' in registration.html
	public RegistrationFormat registrationModel() {
		return new RegistrationFormat();
	}

	@GetMapping
	public String registerForm() {
		return "registration";
	}

	@PostMapping
	public String processRegistration(@Valid @ModelAttribute("reg") RegistrationFormat regModel, Errors errors) {

		if (errors.hasErrors()) {
			return "registration";
		}

		userRepo.save(regModel.toUser(passwordEncoder));
		return "redirect:/login";
	}

}
