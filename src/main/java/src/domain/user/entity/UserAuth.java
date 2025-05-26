package src.domain.user.entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.SuperBuilder;
import src.global.common.entity.BaseAuditableEntity;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth")
public class UserAuth extends BaseAuditableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "password")
	private String password;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
