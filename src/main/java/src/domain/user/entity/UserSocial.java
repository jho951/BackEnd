package src.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private UserSocialType socialType;

	@Column(name = "provider_id", nullable = false, unique = true)
	private String providerId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
