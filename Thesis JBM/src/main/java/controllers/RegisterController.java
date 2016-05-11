package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Credentials;
import services.UserService;
import domain.User;
import forms.CustomerForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserService userService;

	// Constructor
	// ---------------------------------------------------------------
	public RegisterController() {
		super();
	}

	// Listing
	// -------------------------------------------------------------------

	// Creation
	// ------------------------------------------------------------------


	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public ModelAndView registerUser() {
		ModelAndView result;

		CustomerForm customerForm = userService.constructNew();

		result = createEditModelAndViewUser(customerForm);

		return result;
	}

	// Edition
	// -------------------------------------------------------------------


	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUser(
			@ModelAttribute("customerForm") @Valid CustomerForm customerForm,
			BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndViewUser(customerForm);
		} else {
			try {
				User user = this.userService
						.reconstruct(customerForm);
				userService.save(user);
				// result = new ModelAndView("redirect:../welcome/index.do");
				result = createIndexModelAndView("welcome/index.do",
						"welcome/index", "welcome.customer.success");
			} catch (Throwable oops) {
				if (oops.getMessage().equals("Passwords are different")) {
					result = createEditModelAndViewUser(customerForm,
							"register.password.error");
				} else if (oops.getMessage().equals("TOS is False")) {
					result = createEditModelAndViewUser(customerForm,
							"register.tos.error");
				} else if (oops instanceof DataIntegrityViolationException) {
					result = createEditModelAndViewUser(customerForm,
							"register.duplicated.username");
				} else {
					result = createEditModelAndViewUser(customerForm,
							"register.commit.error");
				}
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndViewUser(
			CustomerForm customerForm) {
		ModelAndView result;

		result = createEditModelAndViewUser(customerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewUser(
			CustomerForm customerForm, String message) {
		ModelAndView result;

		result = new ModelAndView("register/registerUser");
		result.addObject("customerForm", customerForm);
		result.addObject("message", message);
		return result;
	}

	protected ModelAndView createIndexModelAndView(String requestURI,
			String uri, String successMessage) {
		ModelAndView result;

		result = new ModelAndView(uri);
		result.addObject("requestURI", requestURI);
		result.addObject("successMessage", successMessage);
		result.addObject("credentials", new Credentials());

		return result;
	}
}
