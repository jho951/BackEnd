package src.domain.user.entity;

import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.security.crypto.password.PasswordEncoder;

import src.global.common.entity.BaseAuditableEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_auth")
public class UserAuth extends BaseAuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(name = "password")
	private String password;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public void encodePassword(PasswordEncoder encoder) {
		this.password = encoder.encode(this.password);
	}
}
