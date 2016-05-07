package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Answer;

import repositories.AnswerRepository;

@Transactional
@Service
public class AnswerService {

	// Managed repository-----------------------

	@Autowired
	private AnswerRepository answerRepository;

	// Constructors --------------------------
	public AnswerService() {
		super();
	}

	// Simple CRUD methods -----------------
	/**
	 * Constructor por defecto de AnswerService
	 * 
	 * @return Answer answer
	 */
	public Answer create() {
		Answer answer = new Answer();

		return answer;
	}

	/**
	 * Devuelve una colección con todos los objetos de tipo Answer
	 * 
	 * @return Collection<Answer> answers
	 */
	public Collection<Answer> findAll() {
		return answerRepository.findAll();
	}

	/**
	 * Devuelve una instancia de un objetos de tipo Answer En caso de no
	 * encontrarse, devuelve null
	 * 
	 * @return Answer answer
	 */
	public Answer findOne(int answerId) {
		return answerRepository.findOne(answerId);
	}

	/**
	 * Persiste (guarda o crea) el objeto de tipo Answer en la base de datos a
	 * través del repositorio AnswerRepository
	 * 
	 * @return void
	 */
	public Answer save(Answer answer) {

		return answerRepository.save(answer);
	}

	/**
	 * Elimina el objeto de tipo Answer de la base de datos a través del
	 * repositorio AnswerRepository
	 * 
	 * @return void
	 */
	public void delete(Answer answer) {
		Assert.notNull(answer);
		// TODO Restricciones de Borrado

		answerRepository.delete(answer);
	}

	public Collection<Answer> findByQuestionId(int questionId) {
		return answerRepository.findByQuestionId(questionId);
	}

}
