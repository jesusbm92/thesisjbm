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

import services.QuestionService;
import services.AnswerService;
import domain.Exam;
import domain.Answer;
import domain.Answer;


@Controller
@RequestMapping("/answer")
public class AnswerController {

	
	// Services ----------------------------------------------------------------

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionService questionService;

	// Constructor
	// ---------------------------------------------------------------
	public AnswerController() {
		super();
	}

	// Listing
	// -------------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		String uri = "answer/list";
		String requestURI = "answer/list.do";
		Collection<Answer> answers = answerService.findAll();
		result = createListModelAndView(requestURI, answers, uri);

		return result;
	}
	
	@RequestMapping(value = "/listByQuestion", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int questionId) {
		ModelAndView result;
		Collection<Answer> answers = answerService.findByQuestionId(questionId);

		String uri = "answer/listByQuestion";
		String requestURI = "answer/listByQuestion.do";
		
		result = createListModelAndView(requestURI, answers, uri);

		return result;
	}
	
	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public ModelAndView xml(@RequestParam int answerId) {
		ModelAndView result;
		Answer answer = answerService.findOne(answerId);

		String uri = "answer/xml";
		String requestURI = "answer/xml.do";
		
		result = createXMLModelAndView(requestURI, answer, uri);

		return result;
	}
	
	
	
	// Other bussiness method
//		protected ModelAndView createEditModelAndView(Answer answer,
//				String message) {
//			assert answer != null;
//			ModelAndView result;
//			result = new ModelAndView("answer/administrator/edit");
//			result.addObject("answer", answer);
//			result.addObject("message", message);
//			result.addObject("create", false);
//			result.addObject("languages", Language.values());
//			result.addObject("map", map);
//
//			return result;
//		}

//		protected ModelAndView createCreateModelAndView(Answer answer,
//				String message) {
//			assert answer != null;
//			Map<String, Integer> map = muscleService.findAllIdName();
//			ModelAndView result;
//			result = new ModelAndView("answer/administrator/create");
//			result.addObject("answer", answer);
//			result.addObject("map", map);
//			result.addObject("message", message);
//			result.addObject("languages", Language.values());
//			result.addObject("create", true);
//
//			return result;
//		}

		protected ModelAndView createListModelAndView(String requestURI,
				Collection<Answer> answers, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("answers", answers);
			result.addObject("requestURI", requestURI);

			return result;
		}
		
		protected ModelAndView createXMLModelAndView(String requestURI,
				Answer answer, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("answer", answer);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
	
}
