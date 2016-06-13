package controllers;

import java.text.DecimalFormat;
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
import domain.Answer;
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
		for (Exam exam : exams) {
			Integer nquestion = 0;
			Double difficulty = 0.0;
			for (Exercise e : exam.getExercises()) {
				for (Question q : e.getQuestions()) {
					if (q.getStatistic().getPercentage() > -1.0) {
						nquestion++;
						difficulty = difficulty
								+ Double.valueOf(q.getDifficulty());
					}
				}
			}
			difficulty = difficulty / nquestion;
			DecimalFormat df2 = new DecimalFormat(".##");
			exam.setDifficulty(df2.format(difficulty));
			examService.save(exam);
		}
		result = createListModelAndView(requestURI, exams, uri);

		return result;
	}

	@RequestMapping(value = "/xml", method = RequestMethod.GET)
	public ModelAndView xml(@RequestParam int examId) {
		ModelAndView result;
		Exam exam = examService.findOne(examId);

		String uri = "exam/xml";
		String requestURI = "exam/xml.do";

		String xmlToExam = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<p:examen barajaEjercicios=\"false\" barajaPreguntas=\"false\" barajaRespuestas=\"true\" numEnunciados=\"9\" ordenEjercicios=\"10,3,5,ej1,2,4,6,7,8,9\" tipoNumeracionEjercicios=\"ordinal_literal\" tipoNumeracionPreguntas=\"decimal\" tipoNumeracionRespuestas=\"caracteres_minusculas\" xmlns:p=\"barajador_examen.modelo.jaxb.modeloexamen\" xmlns:xsi=\"ht//www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"barajador_examen.modelo.jaxb.modeloexamen modeloExamen.xsd \">\n <cabecera>\n <infoUniversidad>\n <nombre>Universidad Politécnica de Madrid</nombre>\n <siglas>UPM</siglas>\n </infoUniversidad>\n <infoFacultad>\n <nombre>Escuela Técnica Superior de Ingenieros Informáticos</nombre>\n <siglas>ETSIINF</siglas>\n </infoFacultad>\n <infoDepartamento>\n <nombre>Departamento de Lenguajes y Sistemas Informáticos e Ingeniería del Software</nombre>\n <siglas>DLSIIS</siglas>\n </infoDepartamento>\n <infoAsignatura>\n <nombre>Programación II</nombre>\n <tipo>Troncal</tipo>\n </infoAsignatura>\n <tipoExamen>Itinerario Flexible</tipoExamen>\n <fechaExamen>\n <fechaSeparadores tipo=\"separadores\">\n"
				+ exam.getDate()
				+ "</fechaSeparadores>\n </fechaExamen>\n <tituloExamen>\n <nombreUniversidad>false</nombreUniversidad>\n <nombreFacultad>false</nombreFacultad>\n <nombreDepartamento>true</nombreDepartamento>\n <siglasUniversidad>true</siglasUniversidad>\n <siglasFacultad>true</siglasFacultad>\n <siglasDepartamento>false</siglasDepartamento>\n </tituloExamen>\n <subtituloExamen>\n <nombreAsignatura>true</nombreAsignatura>\n <siglasAsignatura>false</siglasAsignatura>\n <tipoAsignatura>false</tipoAsignatura>\n<tipoExamen>true</tipoExamen>\n <fechaExamen>true</fechaExamen>\n </subtituloExamen>\n <textoCaratulaExamen>\n <prefacioDelExamen>\n <elemento nombreElemento=\"Realización\">\n<![CDATA[El test se realizará en la hoja de respuesta. Es \textbf{importante} que no olvidéis rellenar vuestros datos personales y el código clave de vuestro enunciado. Se pueden utilizar hojas aparte en sucio. ]]>\n </elemento>\n <elemento nombreElemento=\"Duración\"> </elemento>\n <elemento nombreElemento=\"Puntuación\"> </elemento>\n <elemento nombreElemento=\"Calificaciones\"> </elemento>\n <elemento nombreElemento=\"Revisión\"> </elemento>\n </prefacioDelExamen>\n </textoCaratulaExamen>\n </cabecera> \n<ejercicios>\n";

		for (Exercise exercise : exam.getExercises()) {
			xmlToExam = xmlToExam.concat("<ejercicio etiqueta=\""
					+ exercise.getName() + "\"> <enunciado>\n<![CDATA["
					+ exercise.getText() + "]]></enunciado>\n <preguntas>\n");

			for (Question question : exercise.getQuestions()) {

				Boolean unique = false;
				Integer i = 0;
				for (Answer a : question.getAnswers()) {
					if (a.getIsCorrect()) {
						i++;
					}
					if (i > 1) {
						unique = true;
					}
				}
				String textUniqueMultiple = "";
				if (unique) {
					textUniqueMultiple = "Multiple";
				} else {
					textUniqueMultiple = "Unica";
				}

				String xmlToQuestion = " <pregunta peso=\""
						+ question.getWeight() + "\" codigo=\""
						+ question.getName() + "\">\n <texto>"
						+ question.getText() + "\n</texto>\n<respuestas"
						+ textUniqueMultiple
						+ " numColumnas=\"1\" numSoluciones=\""
						+ textUniqueMultiple.toLowerCase()
						+ "\" pesoRespCorrecta=\"" + question.getWeight()
						+ "\" pesoRespIncorrecta=\"" + question.getWeightfail()
						+ "\">\n";

				for (Answer a : question.getAnswers()) {
					xmlToQuestion = xmlToQuestion
							.concat("<respuesta correcta=\"" + a.getIsCorrect()
									+ "\">\n<texto>" + a.getText()
									+ "</texto>\n</respuesta>\n");
					a.setQuestion(question);
				}
				// Concat XML endTags
				xmlToQuestion = xmlToQuestion.concat("</respuestas"
						+ textUniqueMultiple + ">\n</pregunta>\n");

				question.setXml(xmlToQuestion);

				xmlToExam = xmlToExam.concat(xmlToQuestion);
			}

			xmlToExam = xmlToExam
					.concat("</preguntas>\n<metadatos></metadatos>\n</ejercicio>\n");

		}

		exam.setXml(xmlToExam.concat(" </ejercicios>\n</p:examen>"));
		examService.save(exam);

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
