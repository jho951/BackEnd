package src.domain.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import src.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	@Query("SELECT s FROM User s WHERE s.name = :name")
	Optional<User> findUserByName(@Param("name") String name);
}
