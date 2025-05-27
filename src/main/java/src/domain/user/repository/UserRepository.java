package src.domain.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import src.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
