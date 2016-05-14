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

import services.AnswerService;
import services.ExerciseService;
import services.MetadataService;
import services.QuestionService;
import services.UserService;
import domain.Answer;
import domain.Exam;
import domain.Exercise;
import domain.Metadata;
import domain.Question;
import domain.Question;
import domain.Statistic;

@Controller
@RequestMapping("/question")
public class QuestionController {

	// Services ----------------------------------------------------------------

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private MetadataService metadataService;
	
	@Autowired
	private UserService userService;

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
		Collection<Question> questions = questionService
				.findByExerciseId(exerciseId);

		String uri = "question/listByExercise";
		String requestURI = "question/listByExercise.do";

		result = createListModelAndView(requestURI, questions, uri);

		result.addObject("exam", exerciseService.findOne(exerciseId).getExam());
		
		return result;
	}

	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public ModelAndView xml(@RequestParam int questionId) {
		ModelAndView result;
		Question question = questionService.findOne(questionId);
		Boolean unique = false;
		Integer i=0;
		for(Answer a: question.getAnswers()){
			if(a.getIsCorrect()){
				i++;
			}
			if(i>1){
				unique=true;
			}
		}
		String textUniqueMultiple = "";
		if(unique){
			textUniqueMultiple="Multiple";
		}
		else{
			textUniqueMultiple="Unica";
		}
		
		String xmlToQuestion = " <pregunta peso=\""
				+ question.getWeight()
				+ "\" codigo=\""
				+ question.getName()
				+ "\">\n <texto>"
				+ question.getText()
				+ "\n</texto>\n<respuestas"+textUniqueMultiple+" numColumnas=\"1\" numSoluciones=\""+textUniqueMultiple.toLowerCase()+"\" pesoRespCorrecta=\""+question.getWeight()+"\" pesoRespIncorrecta=\""
				+ question.getWeightfail() + "\">\n";
		
		for (Answer a : question.getAnswers()) {
			xmlToQuestion = xmlToQuestion
					.concat("<respuesta correcta=\""
							+ a.getIsCorrect() + "\">\n<texto>"
							+ a.getText() + "</texto>\n</respuesta>\n");
			a.setQuestion(question);
		}
		// Concat XML endTags
		xmlToQuestion = xmlToQuestion
				.concat("</respuestas"+textUniqueMultiple+">\n</pregunta>\n");
		
		question.setXml(xmlToQuestion);
		question = questionService.save(question);
		
		String uri = "question/xml";
		String requestURI = "question/xml.do";

		result = createXMLModelAndView(requestURI, question, uri);

		return result;
	}

	@RequestMapping(value = "/allToPick", method = RequestMethod.GET)
	public ModelAndView alltopick(@RequestParam int exerciseId) {
		ModelAndView result;
		Collection<Question> questions = questionService.findAll();

		String uri = "question/allToPick";
		String requestURI = "question/allToPick.do";

		result = createListModelAndView(requestURI, questions, uri);
		result.addObject("pickToCopy", true);
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

	@RequestMapping(value = "/createFromParent", method = RequestMethod.GET)
	public ModelAndView createFromParent(@RequestParam int exerciseId,
			@RequestParam int questionId, RedirectAttributes redirect) {
		ModelAndView result;
		Question question = new Question();

		try {

			Question exParent = questionService.findOne(questionId);
			Exercise exercise = exerciseService.findOne(exerciseId);
			BeanUtils.copyProperties(question, exParent);
			question.setId(0);
			Collection<Metadata> metadatas = new ArrayList<Metadata>();
			question.setAnswers(new ArrayList<Answer>());
			metadatas.addAll(exParent.getMetadata());
			question.setMetadata(metadatas);
			question.setExercise(exercise);
			if (userService.IAmAUser()) {
				question.setOwner(userService.findByPrincipal());
			}
			Statistic statistic = new Statistic();
			statistic.setQuestion(exParent);
			statistic.setPercentage(exParent.getStatistic().getPercentage());
			question.setStatistic(statistic);
			question = questionService.save(question);
			for (Answer a : exParent.getAnswers()) {
				Answer ans = new Answer();
				BeanUtils.copyProperties(ans, a);
				ans.setQuestion(question);
				ans.setId(0);
				question.getAnswers().add(answerService.save(ans));
			}
			
			question = questionService.save(question);
			
			// questions.addAll(exParent.getQuestions());
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
						"question.commit.error");
			}

		}

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

		if (binding.hasErrors() && question.getExercise()!=null && question.getStatistic() != null && question.getMetadata()!=null) {
			if (question.getId() == 0) {
				result = createCreateModelAndView(question,
						"question.commit.error");
			} else {
				result = createCreateModelAndView(question,
						"question.commit.error");
			}
		} else {
			try {
				if (question.getExercise() == null) {
					Exercise exercise = exerciseService.findOne(exerciseId);
					question.setExercise(exercise);
					Statistic s = new Statistic();
					s.setQuestion(question);
					question.setStatistic(s);
					s.setPercentage(-1.0);
				}
				
				if(question.getMetadata()==null){
					question.setMetadata(new ArrayList<Metadata>());
				}
				if (userService.IAmAUser()) {
					question.setOwner(userService.findByPrincipal());
				}
				question.setDifficulty(question.getStatistic().getPercentage().toString());
				questionService.save(question);
				result = new ModelAndView(
						"redirect:listByExercise.do?exerciseId=" + exerciseId);
			} catch (Throwable oops) {
				if (question.getId() == 0) {
					redirect.addFlashAttribute("successMessage",
							"question.deleteSuccess");
					result = createCreateModelAndView(question,
							"question.commit.error");
				} else {
					result = createCreateModelAndView(question,
							"question.commit.error");
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
			result = new ModelAndView("redirect:listByExercise.do?exerciseId="
					+ exerciseId);
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
		Collection<Metadata> metadatas = metadataService.findAll();
		ModelAndView result;
		result = new ModelAndView("question/edit");
		result.addObject("question", question);
		result.addObject("metadatas", metadatas);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createCreateModelAndView(Question question,
			String message) {
		assert question != null;
		Collection<Metadata> metadatas = metadataService.findAll();
		ModelAndView result;
		result = new ModelAndView("question/create");
		result.addObject("create", true);
		result.addObject("question", question);
		result.addObject("metadatas", metadatas);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createListModelAndView(String requestURI,
			Collection<Question> questions, String uri) {
		ModelAndView result;

		result = new ModelAndView(uri);
		result.addObject("questions", questions);
		result.addObject("requestURI", requestURI);
		if (userService.IAmAUser()) {
			result.addObject("currentUser", userService.findByPrincipal());
		}

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
