package src.domain.sample.service;

import src.domain.sample.dto.SampleRequest;
import src.domain.sample.dto.SampleResponse;

public interface SampleService {
	SampleResponse.SampleCreateResponse create(SampleRequest.SampleCreateRequest dto);

	SampleResponse.SampleUpdateResponse update(SampleRequest.SampleUpdateRequest dto);

	SampleResponse.SampleReadListResponse read(Long id);

	SampleResponse.SampleReadResponse readDetail(SampleRequest.SampleReadRequest dto);

	SampleResponse.SampleDeleteResponse delete(SampleRequest.SampleDeleteRequest dto);
}
