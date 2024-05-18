package com.valletta.fintech.controller;

import com.valletta.fintech.dto.ApplicationDto.AcceptTermsRequest;
import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;
import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDto<Response> create(@RequestBody Request request) {

        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDto<Response> get(@PathVariable("applicationId") Long applicationId) {

        return ok(applicationService.get(applicationId));
    }

    @PutMapping("{applicationId}")
    public ResponseDto<Response> update(@PathVariable("applicationId") Long applicationId, @RequestBody Request request) {

        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("{applicationId}")
    public ResponseDto<Void> delete(@PathVariable("applicationId") Long applicationId) {

        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("{applicationId}/terms")
    public ResponseDto<Boolean> acceptTerms(@PathVariable("applicationId") Long applicationId, @RequestBody AcceptTermsRequest request) {

        return ok(applicationService.acceptTerms(applicationId, request));
    }
}
