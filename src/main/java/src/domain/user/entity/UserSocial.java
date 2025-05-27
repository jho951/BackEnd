package src.domain.user.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import src.domain.user.constant.UserSocialType;
import src.global.common.entity.BaseAuditableEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_social")
public class UserSocial extends BaseAuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private UserSocialType socialType;

	@Column(name = "provider_id", nullable = false, unique = true)
	private String providerId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
