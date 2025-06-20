package src.domain.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import src.global.security.auth.entity.Auth;

public interface UserAuthRepository extends JpaRepository<Auth, UUID> {
}
