package com.valletta.fintech.controller;

import com.valletta.fintech.dto.EntryDto.Request;
import com.valletta.fintech.dto.EntryDto.Response;
import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/internal/applications")
public class InternalController extends AbstractController {

    private final EntryService entryService;

//    private final RepaymentService repaymentService;

    @PostMapping("{applicationId}/entries")
    public ResponseDto<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {

        return ok(entryService.create(applicationId, request));
    }
}
