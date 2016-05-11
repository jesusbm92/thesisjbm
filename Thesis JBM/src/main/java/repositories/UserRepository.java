package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select a from User a where a.userAccount.username = '?1'")
	User findActorByUsername(String username);

	@Query("select a from User a where a.userAccount.id= ?1")
	User findByUserAccountId(int id);
}
