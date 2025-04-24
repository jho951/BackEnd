package src.domain.sample.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import src.domain.sample.entity.Sample;
import src.domain.sample.dto.SampleRequest;
import src.domain.sample.dto.SampleResponse;
import src.domain.sample.repository.SampleRepository;

import src.global.exception.GlobalException;
import src.global.constant.code.ErrorCode;

@Service
@Slf4j
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {
	private final SampleRepository sampleRepository;

	@Transactional
	@Override
	public SampleResponse.SampleCreateResponse create(SampleRequest.SampleCreateRequest dto) {
		try {
			Sample createSample = sampleRepository.save(dto.toCreateEntity());
			return SampleResponse.SampleCreateResponse.from(createSample);
		} catch (DataIntegrityViolationException e) {
			log.warn("제약 조건 위반: {}", e.getMessage(), e);
			throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		} catch (PersistenceException e) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
		}
	}

	@Transactional
	@Override
	public SampleResponse.SampleUpdateResponse update(SampleRequest.SampleUpdateRequest dto){
		try {
			Sample updateSample = sampleRepository.findSampleByIdAndVersion(dto.getId(),dto.getVersion())
				.orElseThrow(() -> new EntityNotFoundException("Sample not found"));
			sampleRepository.save(dto.toUpdateEntity(updateSample));
			return SampleResponse.SampleUpdateResponse.from(dto.toUpdateEntity(updateSample));
		} catch (DataIntegrityViolationException e) {
			throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		} catch (PersistenceException e) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public SampleResponse.SampleReadListResponse read(SampleRequest.SampleReadRequest dto) {
		Long id = dto.toReadEntity().getId();
		if (id == null) {
			try {
				List<Sample> samples = sampleRepository.findAll();
				return SampleResponse.SampleReadListResponse.from(samples);
			} catch (DataIntegrityViolationException e) {
				throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
			}
		}else {
			try {
				Sample sample = sampleRepository.findById(id)
					.orElseThrow(() -> new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA));
				return SampleResponse.SampleReadListResponse.from(sample);
			}catch (EntityNotFoundException e) {
				throw new GlobalException(ErrorCode.INVALID_TOKEN);
			}
		}
	}

	@Transactional(readOnly = true)
	@Override
	public SampleResponse.SampleReadResponse readDetail(Long id) {
		try {
			Sample readSample = sampleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Sample not found"));
			return SampleResponse.SampleReadResponse.from(readSample);
		}catch (DataIntegrityViolationException e){
			throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		}
	}

	@Transactional
	@Override
	public SampleResponse.SampleDeleteResponse delete(SampleRequest.SampleDeleteRequest dto){
		try{
			Sample deleteSample = sampleRepository.findById(dto.toDeleteEntity().getId())
				.orElseThrow(() -> new EntityNotFoundException("Sample not found"));
			sampleRepository.delete(deleteSample);
			return SampleResponse.SampleDeleteResponse.from(deleteSample);
		}catch (DataIntegrityViolationException e){
			throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		}
	}
}
