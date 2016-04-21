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

import services.ExamService;
import services.ExerciseService;
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
	public ModelAndView edit(@RequestParam int examId) {
		ModelAndView result;
		Collection<Exercise> exercises = exerciseService.findByExamId(examId);

		String uri = "exercise/listByExam";
		String requestURI = "exercise/listByExam.do";
		
		result = createListModelAndView(requestURI, exercises, uri);

		return result;
	}
	
	// Other bussiness method
//		protected ModelAndView createEditModelAndView(Exercise exercise,
//				String message) {
//			assert exercise != null;
//			ModelAndView result;
//			result = new ModelAndView("exercise/administrator/edit");
//			result.addObject("exercise", exercise);
//			result.addObject("message", message);
//			result.addObject("create", false);
//			result.addObject("languages", Language.values());
//			result.addObject("map", map);
//
//			return result;
//		}

//		protected ModelAndView createCreateModelAndView(Exercise exercise,
//				String message) {
//			assert exercise != null;
//			Map<String, Integer> map = muscleService.findAllIdName();
//			ModelAndView result;
//			result = new ModelAndView("exercise/administrator/create");
//			result.addObject("exercise", exercise);
//			result.addObject("map", map);
//			result.addObject("message", message);
//			result.addObject("languages", Language.values());
//			result.addObject("create", true);
//
//			return result;
//		}

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
