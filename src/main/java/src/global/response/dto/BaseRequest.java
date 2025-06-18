package src.global.response.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Version;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseRequest {

	@Version
	private Long version;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}
