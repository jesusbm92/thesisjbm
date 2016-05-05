package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Exam;
import domain.Exercise;
import domain.Question;

import repositories.ExerciseRepository;

@Transactional
@Service
public class ExerciseService {

	// Managed repository-----------------------

	@Autowired
	private ExerciseRepository exerciseRepository;

	// Constructors --------------------------
	public ExerciseService() {
		super();
	}

	// Simple CRUD methods -----------------
	/**
	 * Constructor por defecto de ExerciseService
	 * 
	 * @return Exercise exercise
	 */
	public Exercise create() {
		Exercise exercise = new Exercise();

		return exercise;
	}

	/**
	 * Devuelve una colección con todos los objetos de tipo Exercise
	 * 
	 * @return Collection<Exercise> exercises
	 */
	public Collection<Exercise> findAll() {
		return exerciseRepository.findAll();
	}

	/**
	 * Devuelve una instancia de un objetos de tipo Exercise En caso de no
	 * encontrarse, devuelve null
	 * 
	 * @return Exercise exercise
	 */
	public Exercise findOne(int exerciseId) {
		return exerciseRepository.findOne(exerciseId);
	}

	/**
	 * Persiste (guarda o crea) el objeto de tipo Exercise en la base de datos a
	 * través del repositorio ExerciseRepository
	 * 
	 * @return void
	 */
	public void save(Exercise exercise) {

		exerciseRepository.save(exercise);
	}

	/**
	 * Elimina el objeto de tipo Exercise de la base de datos a través del
	 * repositorio ExerciseRepository
	 * 
	 * @return void
	 */
	public void delete(Exercise exercise) {
		Assert.notNull(exercise);
		// TODO Restricciones de Borrado

		for (Question q: exercise.getQuestions()){
			q.getExercises().remove(exercise);				
		}
		
		for (Exam e: exercise.getExams()){
			e.getExercises().remove(exercise);				
		}
		
		exercise.setQuestions(new ArrayList<Question>());
		exercise.setExams(new ArrayList<Exam>());
		exerciseRepository.delete(exercise);
	}

	public Collection<Exercise> findByExamId(int examId) {
		return exerciseRepository.findByExamId(examId);
	}

}
