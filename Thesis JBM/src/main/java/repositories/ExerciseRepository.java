package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

	@Query("select e from Exercise e JOIN e.exams ex where ex.id=?1")
	Collection<Exercise> findByExamId(int examId);

	
}
