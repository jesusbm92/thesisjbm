package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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

import services.QuestionService;
import services.AnswerService;
import services.UserService;
import domain.Exam;
import domain.Answer;
import domain.Answer;
import domain.Exercise;
import domain.Metadata;
import domain.Question;
import domain.Statistic;


@Controller
@RequestMapping("/answer")
public class AnswerController {

	
	// Services ----------------------------------------------------------------

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;

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
		Question q = questionService.findOne(questionId);
		
		result.addObject("exam", q);
		
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
	
	
	// Creation
		// ------------------------------------------------------------------
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int questionId) {

			ModelAndView result;

			Answer answer = answerService.create();

			result = createCreateModelAndView(answer);
			return result;
		}

		// Edition
		// -------------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int answerId,
				@RequestParam int questionId) {
			ModelAndView result;
			Answer answer = answerService.findOne(answerId);

			result = createEditModelAndView(answer);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@RequestParam int questionId,
				@Valid Answer answer, BindingResult binding,
				RedirectAttributes redirect) {
			ModelAndView result;

			if (binding.hasErrors() && answer.getQuestion()!=null) {
				if (answer.getId() == 0 ) {
					result = createCreateModelAndView(answer,
							"answer.commit.error");
				} else {
					result = createEditModelAndView(answer,
							"answer.commit.error");
				}
			} else {
				try {
					if (answer.getQuestion()==null) {
						Question question = questionService.findOne(questionId);
						answer.setQuestion(question);
					}
					answerService.save(answer);
					result = new ModelAndView(
							"redirect:listByQuestion.do?questionId=" + questionId);
				} catch (Throwable oops) {
					if (answer.getId() == 0) {
						redirect.addFlashAttribute("successMessage",
								"answer.deleteSuccess");
						result = createCreateModelAndView(answer,
								"answer.commit.error");
					} else {
						result = createCreateModelAndView(answer,
								"answer.commit.error");
					}

				}

			}

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@RequestParam int questionId,
				@ModelAttribute Answer answer, BindingResult bindingResult,
				RedirectAttributes redirect) {
			ModelAndView result;

			try {
				redirect.addFlashAttribute("successMessage",
						"answer.deleteSuccess");
				answerService.delete(answer);
				result = new ModelAndView("redirect:listByQuestion.do?questionId="
						+ questionId);
			} catch (Throwable oops) {
				if (oops.getMessage() == "Error") {
					result = createEditModelAndView(answer, "answer.error");
				} else {
					result = createEditModelAndView(answer,
							"answer.commit.error");
				}
			}
			return result;
		}

		// Other bussiness method

		protected ModelAndView createEditModelAndView(Answer answer) {
			assert answer != null;

			ModelAndView result;

			result = createEditModelAndView(answer, null);

			return result;
		}

		protected ModelAndView createCreateModelAndView(Answer answer) {
			assert answer != null;

			ModelAndView result;

			result = createCreateModelAndView(answer, null);
			return result;
		}

		// Other bussiness method
		protected ModelAndView createEditModelAndView(Answer answer,
				String message) {
			assert answer != null;
			ModelAndView result;
			result = new ModelAndView("answer/edit");
			result.addObject("answer", answer);
			result.addObject("message", message);

			return result;
		}

		protected ModelAndView createCreateModelAndView(Answer answer,
				String message) {
			assert answer != null;
			ModelAndView result;
			result = new ModelAndView("answer/create");
			result.addObject("create", true);
			result.addObject("answer", answer);
			result.addObject("message", message);

			return result;
		
		}


		protected ModelAndView createListModelAndView(String requestURI,
				Collection<Answer> answers, String uri) {
			ModelAndView result;

			result = new ModelAndView(uri);
			result.addObject("answers", answers);
			result.addObject("requestURI", requestURI);
			if (userService.IAmAUser()) {
				result.addObject("currentUser", userService.findByPrincipal());
			}

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
