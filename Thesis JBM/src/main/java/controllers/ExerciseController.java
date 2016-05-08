package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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

import com.mchange.v2.beans.BeansUtils;

import services.AnswerService;
import services.ExamService;
import services.ExerciseService;
import services.QuestionService;
import domain.Answer;
import domain.Exam;
import domain.Exercise;
import domain.Exercise;
import domain.Metadata;
import domain.Question;
import domain.Statistic;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private ExamService examService;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
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

	@RequestMapping(value = "/allToPick", method = RequestMethod.GET)
	public ModelAndView alltopick(@RequestParam int examId) {
		ModelAndView result;
		Collection<Exercise> exercises = exerciseService.findAll();

		String uri = "exercise/allToPick";
		String requestURI = "exercise/allToPick.do";

		result = createListModelAndView(requestURI, exercises, uri);
		result.addObject("pickToCopy", true);
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

	@RequestMapping(value = "/createFromParent", method = RequestMethod.GET)
	public ModelAndView createFromParent(@RequestParam int examId,
			@RequestParam int exerciseId, RedirectAttributes redirect) {
		ModelAndView result;
		Exercise exercise = new Exercise();

		try {

			Exercise exParent = exerciseService.findOne(exerciseId);
			Exam exam = examService.findOne(examId);
			BeanUtils.copyProperties(exercise, exParent);
			exercise.setId(0);
			Collection<Exam> exams = new ArrayList<Exam>();
			Collection<Question> questions = new ArrayList<Question>();
			for (Question q: exParent.getQuestions()){
				Question qst = new Question();
				qst.setDifficulty(q.getDifficulty());
				Statistic s = new Statistic();
				s.setQuestion(qst);
				s.setPercentage(q.getStatistic().getPercentage());
				qst.setStatistic(s);
				qst.setName(q.getName());
				qst.setText(q.getText());
				qst.setWeight(q.getWeight());
				qst.setWeightfail(q.getWeightfail());
				qst.setXml(q.getXml());
				Collection<Metadata> metadatas = new ArrayList<Metadata>();
				metadatas.addAll(q.getMetadata());
				qst.getExercises().add(exercise);
				qst = questionService.save(qst);
				questions.add(qst);
				for(Answer a : q.getAnswers()){
					Answer answer = new Answer();
					BeanUtils.copyProperties(answer, a);
					answer.setQuestion(qst);
					answer.setId(0);
					qst.getAnswers().add(answerService.save(answer));
				}
			}
//			questions.addAll(exParent.getQuestions());
			exams.addAll(exParent.getExams());
			exercise.setExams(exams);
			exercise.setQuestions(questions);
			exercise.getExams().add(exam);
			exam.getExercises().add(exercise);
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
