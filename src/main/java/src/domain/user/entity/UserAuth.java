package src.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import src.domain.user.constant.UserSocial;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth")
public class UserAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "socialType", nullable = false)
	private UserSocial socialType;

	@Column(name = "provider_id", nullable = false, unique = true)
	private String providerId;

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private User user;
}
