package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	
}
