package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Exam;
import domain.Metadata;
import domain.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Integer> {

	
}
