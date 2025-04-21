package src.domain.sample.controller;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import src.domain.sample.dto.SampleRequest;
import src.global.constant.code.SuccessCode;
import src.domain.sample.dto.SampleResponse;
import src.domain.sample.service.SampleService;
import src.global.common.dto.BaseResponseWrapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/v1/sample")
@RequiredArgsConstructor
public class SampleController {
    private final SampleService sampleService;

    @PostMapping("/create")
    @Operation(summary = "sample create api", description = "sample entity를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 생성하였습니다.")
    public BaseResponseWrapper<SampleResponse.SampleCreateResponse> create(@RequestBody @Valid SampleRequest.SampleCreateRequest dto) {
        SampleResponse.SampleCreateResponse response = sampleService.create(dto);
        return BaseResponseWrapper.ok(SuccessCode.SUCCESS, response);
    }

    @PutMapping("/update")
    @Operation(summary = "sample update api", description = "sample entity를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 수정하였습니다.")
    public BaseResponseWrapper<SampleResponse.SampleUpdateResponse> update(@RequestBody SampleRequest.SampleUpdateRequest dto) {
        SampleResponse.SampleUpdateResponse response = sampleService.update(dto);
        return BaseResponseWrapper.ok(SuccessCode.SUCCESS,response);
    }

    @GetMapping("/read")
    @Operation(summary = "sample read api", description = "sample entity를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 가져왔습니다.")
    public BaseResponseWrapper<SampleResponse.SampleReadListResponse> read(
        @RequestParam(required = false) Long id) {
        SampleResponse.SampleReadListResponse response = sampleService.read(id);
        return BaseResponseWrapper.ok(SuccessCode.SUCCESS, response);
    }

    @GetMapping("/read/detail/{id}")
    @Operation(summary = "sample read by id api", description = "해당 id의 sample entity를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 가져왔습니다.")
    public BaseResponseWrapper<SampleResponse.SampleReadResponse> readDetail(SampleRequest.SampleReadRequest dto) {
        SampleResponse.SampleReadResponse response = sampleService.readDetail(dto);
        return BaseResponseWrapper.ok(SuccessCode.SUCCESS, response);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "sample delete api", description = "sample entity를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "성공적으로 삭제했습니다.")
    public BaseResponseWrapper<SampleResponse.SampleDeleteResponse> delete(@RequestBody SampleRequest.SampleDeleteRequest dto) {
        SampleResponse.SampleDeleteResponse response = sampleService.delete(dto);
        return BaseResponseWrapper.ok(SuccessCode.SUCCESS,response);
    }
}
