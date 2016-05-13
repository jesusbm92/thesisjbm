package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ExamService;
import services.ExerciseService;
import services.UserService;
import utilities.FileToObjectsConverter;
import domain.Exam;
import domain.Exercise;
import domain.Exam;
import domain.Exam;
import domain.Question;
import domain.User;

@Controller
@RequestMapping("/exam")
public class ExamController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ExamService examService;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private UserService userService;

	// Constructor
	// ---------------------------------------------------------------
	public ExamController() {
		super();
	}

	// Listing
	// -------------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		String uri = "exam/list";
		String requestURI = "exam/list.do";
		Collection<Exam> exams = examService.findAll();
		result = createListModelAndView(requestURI, exams, uri);

		return result;
	}

	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public ModelAndView xml(@RequestParam int examId) {
		ModelAndView result;
		Exam exam = examService.findOne(examId);

		String uri = "exam/xml";
		String requestURI = "exam/xml.do";

		result = createXMLModelAndView(requestURI, exam, uri);

		return result;
	}

	// Creation
	// ------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		Exam exam = examService.create();

		result = createCreateModelAndView(exam);
		return result;
	}

	// Edition
	// -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int examId) {
		ModelAndView result;
		Exam exam = examService.findOne(examId);

		result = createEditModelAndView(exam);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Exam exam, BindingResult binding,
			RedirectAttributes redirect) {
		ModelAndView result;

		if (binding.hasErrors() && exam.getOwners() != null) {
			if (exam.getId() == 0) {
				result = createCreateModelAndView(exam, "exam.commit.error");
			} else {
				result = createCreateModelAndView(exam, "exam.commit.error");
			}
		} else {
			try {
				exam.setDifficulty("?");
				exam.setDate(new Date());
				exam.setXml("");
				if (userService.IAmAUser()) {
					if (exam.getOwners() == null) {
						exam.setOwners(new ArrayList<User>());
						exam.getOwners().add(userService.findByPrincipal());
					} else {
						exam.getOwners().add(userService.findByPrincipal());
					}
				}
				examService.save(exam);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				if (exam.getId() == 0) {
					redirect.addFlashAttribute("successMessage",
							"exam.deleteSuccess");
					result = createCreateModelAndView(exam, "exam.commit.error");
				} else {
					result = createCreateModelAndView(exam, "exam.commit.error");
				}

			}

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute Exam exam,
			BindingResult bindingResult, RedirectAttributes redirect) {
		ModelAndView result;

		try {
			redirect.addFlashAttribute("successMessage", "exam.deleteSuccess");
			examService.delete(exam);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			if (oops.getMessage() == "Error") {
				result = createEditModelAndView(exam, "exam.error");
			} else {
				result = createEditModelAndView(exam, "exam.commit.error");
			}
		}
		return result;
	}

	// Other bussiness method

	protected ModelAndView createEditModelAndView(Exam exam) {
		assert exam != null;

		ModelAndView result;

		result = createEditModelAndView(exam, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(Exam exam) {
		assert exam != null;

		ModelAndView result;

		result = createCreateModelAndView(exam, null);
		return result;
	}

	protected ModelAndView createCreateWithDataModelAndView(Exam exam) {
		assert exam != null;

		ModelAndView result;

		result = createCreateModelAndView(exam, null);
		return result;
	}

	// Other bussiness method
	protected ModelAndView createEditModelAndView(Exam exam, String message) {
		assert exam != null;
		Collection<Exercise> exercises = exerciseService.findAll();
		Collection<User> users = userService.findAll();
		if (userService.IAmAUser()) {
			users.remove(userService.findByPrincipal());
		}
		ModelAndView result;
		result = new ModelAndView("exam/edit");
		result.addObject("allexercises", exercises);
		result.addObject("exam", exam);
		result.addObject("users", users);
		result.addObject("message", message);
		if (userService.IAmAUser()) {
			result.addObject("currentUser", userService.findByPrincipal());
		}
		return result;
	}

	protected ModelAndView createCreateModelAndView(Exam exam, String message) {
		assert exam != null;
		Collection<Exercise> exercises = exerciseService.findAll();
		Collection<User> users = userService.findAll();
		if (userService.IAmAUser()) {
			users.remove(userService.findByPrincipal());
		}
		ModelAndView result;
		result = new ModelAndView("exam/create");
		result.addObject("allexercises", exercises);
		result.addObject("create", true);
		result.addObject("exam", exam);
		result.addObject("users", users);
		result.addObject("message", message);
		if (userService.IAmAUser()) {
			result.addObject("currentUser", userService.findByPrincipal());
		}

		return result;
	}

	protected ModelAndView createListModelAndView(String requestURI,
			Collection<Exam> exams, String uri) {
		ModelAndView result;

		result = new ModelAndView(uri);
		result.addObject("exams", exams);
		result.addObject("requestURI", requestURI);
		if (userService.IAmAUser()) {
			User currentUser = userService.findByPrincipal();
			result.addObject("currentUser", currentUser);
		}

		return result;
	}

	protected ModelAndView createXMLModelAndView(String requestURI, Exam exam,
			String uri) {
		ModelAndView result;

		result = new ModelAndView(uri);
		result.addObject("exam", exam);
		result.addObject("requestURI", requestURI);
		if (userService.IAmAUser()) {
			result.addObject("currentUser", userService.findByPrincipal());
		}
		return result;
	}

}
