package controllers;

import java.util.Collection;
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

import services.ExerciseService;
import services.QuestionService;
import domain.Exam;
import domain.Exercise;
import domain.Question;
import domain.Question;


@Controller
@RequestMapping("/question")
public class QuestionController {

	
	// Services ----------------------------------------------------------------

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ExerciseService exerciseService;

	// Constructor
	// ---------------------------------------------------------------
	public QuestionController() {
		super();
	}

	// Listing
	// -------------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		String uri = "question/list";
		String requestURI = "question/list.do";
		Collection<Question> questions = questionService.findAll();
		result = createListModelAndView(requestURI, questions, uri);

		return result;
	}
	
	@RequestMapping(value = "/listByExercise", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int exerciseId) {
		ModelAndView result;
		Collection<Question> questions = questionService.findByExerciseId(exerciseId);

		String uri = "question/listByExercise";
		String requestURI = "question/listByExercise.do";
		
		result = createListModelAndView(requestURI, questions, uri);

		return result;
	}
	
	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public ModelAndView xml(@RequestParam int questionId) {
		ModelAndView result;
		Question question = questionService.findOne(questionId);

		String uri = "question/xml";
		String requestURI = "question/xml.do";
		
		result = createXMLModelAndView(requestURI, question, uri);

		return result;
	}
	
	// Creation
		// ------------------------------------------------------------------
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int exerciseId) {

			ModelAndView result;

			Question question = questionService.create();

			result = createCreateModelAndView(question);
			return result;
		}

		// Edition
		// -------------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int questionId,
				@RequestParam int exerciseId) {
			ModelAndView result;
			Question question = questionService.findOne(questionId);

			result = createEditModelAndView(question);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@RequestParam int exerciseId,
				@Valid Question question, BindingResult binding,
				RedirectAttributes redirect) {
			ModelAndView result;

			if (binding.hasErrors()) {
				if (question.getId() == 0) {
					result = createCreateModelAndView(question,
							"question.commit.error");
				} else {
					result = createCreateModelAndView(question,
							"question.commit.error");
				}
			} else {
				try {
					if (question.getExercises().size() == 0) {
						Exercise exercise = exerciseService.findOne(exerciseId);
						question.getExercises().add(exercise);
						exercise.getQuestions().add(question);
					}
					questionService.save(question);
					result = new ModelAndView("redirect:listByExercise.do?exerciseId="
							+ exerciseId);
				} catch (Throwable oops) {
					if (question.getId() == 0) {
						redirect.addFlashAttribute("successMessage",
								"question.deleteSuccess");
						result = createCreateModelAndView(question,
								"question.commit.error");
					} else {
						result = createCreateModelAndView(question,
								"exercise.commit.error");
					}

				}

			}

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@RequestParam int exerciseId,
				@ModelAttribute Question question, BindingResult bindingResult,
				RedirectAttributes redirect) {
			ModelAndView result;

			try {
				redirect.addFlashAttribute("successMessage",
						"question.deleteSuccess");
				questionService.delete(question);
				result = new ModelAndView("redirect:listByExam.do?examId=" + exerciseId);
			} catch (Throwable oops) {
				if (oops.getMessage() == "Error") {
					result = createEditModelAndView(question, "question.error");
				} else {
					result = createEditModelAndView(question,
							"question.commit.error");
				}
			}
			return result;
		}

		// Other bussiness method

		protected ModelAndView createEditModelAndView(Question question) {
			assert question != null;

			ModelAndView result;

			result = createEditModelAndView(question, null);

			return result;
		}

		protected ModelAndView createCreateModelAndView(Question question) {
			assert question != null;

			ModelAndView result;

			result = createCreateModelAndView(question, null);
			return result;
		}

		// Other bussiness method
		protected ModelAndView createEditModelAndView(Question question,
				String message) {
			assert question != null;
			ModelAndView result;
			result = new ModelAndView("question/edit");
			result.addObject("question", question);
			result.addObject("message", message);

			return result;
		}

		protected ModelAndView createCreateModelAndView(Question question,
				String message) {
			assert question != null;
			ModelAndView result;
			result = new ModelAndView("question/create");
			result.addObject("create", true);
			result.addObject("question", question);
			result.addObject("message", message);

			return result;
		}

		protected ModelAndView createListModelAndView(String requestURI,
				Collection<Question> questions, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("questions", questions);
			result.addObject("requestURI", requestURI);

			return result;
		}
		
		protected ModelAndView createXMLModelAndView(String requestURI,
				Question question, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("question", question);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
	
}
