package src.domain.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import src.domain.sample.entity.Sample;


public interface SampleRepository extends JpaRepository<Sample, Long> {
}