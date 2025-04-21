package src.domain.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import src.domain.sample.entity.Sample;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long> {

	@Query("SELECT s FROM Sample s WHERE s.id = :id AND s.version = :version")
	Optional<Sample> findSampleByIdAndVersion(@Param("id") Long id, @Param("version") Long version);
}