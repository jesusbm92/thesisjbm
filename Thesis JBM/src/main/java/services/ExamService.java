package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Exam;
import domain.Exercise;

import repositories.ExamRepository;


@Transactional
@Service
public class ExamService {

	// Managed repository-----------------------

		@Autowired
		private ExamRepository examRepository;
	
		
		
		// Constructors --------------------------
		public ExamService() {
			super();
		}
		
		
		// Simple CRUD methods -----------------
		/**
		 * Constructor por defecto de ExamService
		 * 
		 * @return Exam exam
		 */
		public Exam create() {
			Exam exam = new Exam();

			return exam;
		}

		/**
		 * Devuelve una colección con todos los objetos de tipo Exam
		 * 
		 * @return Collection<Exam> exams
		 */
		public Collection<Exam> findAll() {
			return examRepository.findAll();
		}

		/**
		 * Devuelve una instancia de un objetos de tipo Exam En caso de no
		 * encontrarse, devuelve null
		 * 
		 * @return Exam exam
		 */
		public Exam findOne(int examId) {
			return examRepository.findOne(examId);
		}

		/**
		 * Persiste (guarda o crea) el objeto de tipo Exam en la base de datos a
		 * través del repositorio ExamRepository
		 * 
		 * @return void
		 */
		public void save(Exam exam) {
			// TODO Restricciones de Save
			// Muscle muscle = exam.getMuscle();
			//
			// muscle.getExams().add(exam);
			//
			// muscleService.save(muscle);
			//
			// ExamGroup examGroup = exam.getExamGroup();
			//
			// examGroup.getExams().add(exam);
			//
			// examGroupService.save(examGroup);

			examRepository.save(exam);
		}

		/**
		 * Elimina el objeto de tipo Exam de la base de datos a través del
		 * repositorio ExamRepository
		 * 
		 * @return void
		 */
		public void delete(Exam exam) {
			Assert.notNull(exam);
			// TODO Restricciones de Borrado

			for (Exercise q: exam.getExercises()){
				q.getExams().remove(exam);				
			}
			exam.setExercises(new ArrayList<Exercise>());			
			examRepository.delete(exam);
		}


	
}
