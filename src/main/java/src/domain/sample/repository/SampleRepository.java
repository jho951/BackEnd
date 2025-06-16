package src.domain.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import src.domain.sample.entity.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long> {

	// @Query("SELECT s FROM Sample s WHERE s.id = :id")
	// Optional<Sample> findSampleByIdAndVersion(@Param("id") Long id);
}