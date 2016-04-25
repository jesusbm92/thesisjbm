package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Question;

import repositories.QuestionRepository;

@Transactional
@Service
public class QuestionService {

	// Managed repository-----------------------

	@Autowired
	private QuestionRepository questionRepository;

	// Constructors --------------------------
	public QuestionService() {
		super();
	}

	// Simple CRUD methods -----------------
	/**
	 * Constructor por defecto de QuestionService
	 * 
	 * @return Question question
	 */
	public Question create() {
		Question question = new Question();

		return question;
	}

	/**
	 * Devuelve una colección con todos los objetos de tipo Question
	 * 
	 * @return Collection<Question> questions
	 */
	public Collection<Question> findAll() {
		return questionRepository.findAll();
	}

	/**
	 * Devuelve una instancia de un objetos de tipo Question En caso de no
	 * encontrarse, devuelve null
	 * 
	 * @return Question question
	 */
	public Question findOne(int questionId) {
		return questionRepository.findOne(questionId);
	}

	/**
	 * Persiste (guarda o crea) el objeto de tipo Question en la base de datos a
	 * través del repositorio QuestionRepository
	 * 
	 * @return void
	 */
	public void save(Question question) {
		// TODO Restricciones de Save
		// Muscle muscle = question.getMuscle();
		//
		// muscle.getQuestions().add(question);
		//
		// muscleService.save(muscle);
		//
		// QuestionGroup questionGroup = question.getQuestionGroup();
		//
		// questionGroup.getQuestions().add(question);
		//
		// questionGroupService.save(questionGroup);

		questionRepository.save(question);
	}

	/**
	 * Elimina el objeto de tipo Question de la base de datos a través del
	 * repositorio QuestionRepository
	 * 
	 * @return void
	 */
	public void delete(Question question) {
		Assert.notNull(question);
		// TODO Restricciones de Borrado

		questionRepository.delete(question);
	}

	public Collection<Question> findByExerciseId(int exerciseId) {
		return questionRepository.findByExerciseId(exerciseId);
	}

}
