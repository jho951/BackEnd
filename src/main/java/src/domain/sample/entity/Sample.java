package src.domain.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import lombok.experimental.SuperBuilder;
import src.global.common.entity.BaseEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sample")
public class Sample extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}
