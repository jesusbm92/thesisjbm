package controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ExerciseService;
import services.QuestionService;
import domain.Exam;
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
	
	
	
	// Other bussiness method
//		protected ModelAndView createEditModelAndView(Question question,
//				String message) {
//			assert question != null;
//			ModelAndView result;
//			result = new ModelAndView("question/administrator/edit");
//			result.addObject("question", question);
//			result.addObject("message", message);
//			result.addObject("create", false);
//			result.addObject("languages", Language.values());
//			result.addObject("map", map);
//
//			return result;
//		}

//		protected ModelAndView createCreateModelAndView(Question question,
//				String message) {
//			assert question != null;
//			Map<String, Integer> map = muscleService.findAllIdName();
//			ModelAndView result;
//			result = new ModelAndView("question/administrator/create");
//			result.addObject("question", question);
//			result.addObject("map", map);
//			result.addObject("message", message);
//			result.addObject("languages", Language.values());
//			result.addObject("create", true);
//
//			return result;
//		}

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
