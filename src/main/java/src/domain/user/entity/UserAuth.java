package src.domain.user.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.SuperBuilder;
import src.domain.user.constant.UserSocial;
import src.global.common.entity.BaseAuthEntity;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth")
public class UserAuth extends BaseAuthEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "socialType", nullable = false)
	private UserSocial socialType;

	// @Column(name = "provider_id", nullable = false, unique = true)
	// private String providerId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
