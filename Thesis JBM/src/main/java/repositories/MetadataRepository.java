package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Exam;
import domain.Metadata;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {

	
}
