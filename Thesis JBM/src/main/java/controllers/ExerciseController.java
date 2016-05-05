package controllers;

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
import domain.Exam;
import domain.Exercise;
import domain.Exercise;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private ExamService examService;

	// Constructor
	// ---------------------------------------------------------------
	public ExerciseController() {
		super();
	}

	// Listing
	// -------------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		String uri = "exercise/list";
		String requestURI = "exercise/list.do";
		Collection<Exercise> exercises = exerciseService.findAll();
		result = createListModelAndView(requestURI, exercises, uri);

		return result;
	}

	@RequestMapping(value = "/listByExam", method = RequestMethod.GET)
	public ModelAndView listbyexam(@RequestParam int examId) {
		ModelAndView result;
		Collection<Exercise> exercises = exerciseService.findByExamId(examId);

		String uri = "exercise/listByExam";
		String requestURI = "exercise/listByExam.do";

		result = createListModelAndView(requestURI, exercises, uri);

		return result;
	}

	// Creation
	// ------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int examId) {

		ModelAndView result;

		Exercise exercise = exerciseService.create();

		result = createCreateModelAndView(exercise);
		return result;
	}

	// Edition
	// -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int exerciseId,
			@RequestParam int examId) {
		ModelAndView result;
		Exercise exercise = exerciseService.findOne(exerciseId);

		result = createEditModelAndView(exercise);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam int examId,
			@Valid Exercise exercise, BindingResult binding,
			RedirectAttributes redirect) {
		ModelAndView result;

		if (binding.hasErrors()) {
			if (exercise.getId() == 0) {
				result = createCreateModelAndView(exercise,
						"exercise.commit.error");
			} else {
				result = createCreateModelAndView(exercise,
						"exercise.commit.error");
			}
		} else {
			try {
				if (exercise.getExams().size() == 0) {
					Exam exam = examService.findOne(examId);
					exercise.getExams().add(exam);
					exam.getExercises().add(exercise);
				}
				exerciseService.save(exercise);
				result = new ModelAndView("redirect:listByExam.do?examId="
						+ examId);
			} catch (Throwable oops) {
				if (exercise.getId() == 0) {
					redirect.addFlashAttribute("successMessage",
							"exercise.deleteSuccess");
					result = createCreateModelAndView(exercise,
							"exercise.commit.error");
				} else {
					result = createCreateModelAndView(exercise,
							"exercise.commit.error");
				}

			}

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam int examId,
			@ModelAttribute Exercise exercise, BindingResult bindingResult,
			RedirectAttributes redirect) {
		ModelAndView result;

		try {
			redirect.addFlashAttribute("successMessage",
					"exercise.deleteSuccess");
			exerciseService.delete(exercise);
			result = new ModelAndView("redirect:listByExam.do?examId=" + examId);
		} catch (Throwable oops) {
			if (oops.getMessage() == "Error") {
				result = createEditModelAndView(exercise, "exercise.error");
			} else {
				result = createEditModelAndView(exercise,
						"exercise.commit.error");
			}
		}
		return result;
	}

	// Other bussiness method

	protected ModelAndView createEditModelAndView(Exercise exercise) {
		assert exercise != null;

		ModelAndView result;

		result = createEditModelAndView(exercise, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(Exercise exercise) {
		assert exercise != null;

		ModelAndView result;

		result = createCreateModelAndView(exercise, null);
		return result;
	}

	// Other bussiness method
	protected ModelAndView createEditModelAndView(Exercise exercise,
			String message) {
		assert exercise != null;
		ModelAndView result;
		result = new ModelAndView("exercise/edit");
		result.addObject("exercise", exercise);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createCreateModelAndView(Exercise exercise,
			String message) {
		assert exercise != null;
		ModelAndView result;
		result = new ModelAndView("exercise/create");
		result.addObject("create", true);
		result.addObject("exercise", exercise);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createListModelAndView(String requestURI,
			Collection<Exercise> exercises, String uri) {
		ModelAndView result;

		result = new ModelAndView(uri);
		result.addObject("exercises", exercises);
		result.addObject("requestURI", requestURI);

		return result;
	}

	protected ModelAndView createXMLModelAndView(String requestURI,
			Exercise exercise, String uri) {
		ModelAndView result;

		result = new ModelAndView(uri);
		result.addObject("exercise", exercise);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
