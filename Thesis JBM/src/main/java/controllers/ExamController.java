package controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ExamService;
import domain.Exam;
import domain.Exercise;


@Controller
@RequestMapping("/exam")
public class ExamController {

	
	// Services ----------------------------------------------------------------

	@Autowired
	private ExamService examService;


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
	
	// Other bussiness method
//		protected ModelAndView createEditModelAndView(Exam exam,
//				String message) {
//			assert exam != null;
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

//		protected ModelAndView createCreateModelAndView(Exam exam,
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
				Collection<Exam> exams, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("exams", exams);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
	
}
