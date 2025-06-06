package src.domain.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import src.domain.user.entity.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, UUID> {
}
