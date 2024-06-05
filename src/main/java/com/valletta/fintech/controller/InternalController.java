package com.valletta.fintech.controller;

import com.valletta.fintech.dto.BalanceDto.UpdateRequest;
import com.valletta.fintech.dto.EntryDto.Request;
import com.valletta.fintech.dto.EntryDto.Response;
import com.valletta.fintech.dto.EntryDto.UpdateResponse;
import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.service.EntryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/internal/applications")
public class InternalController extends AbstractController {

    private final EntryService entryService;

//    private final RepaymentService repaymentService;

    @PostMapping("/{applicationId}/entries")
    public ResponseDto<Response> create(@PathVariable("applicationId") Long applicationId, @RequestBody Request request) {

        return ok(entryService.create(applicationId, request));
    }

    @GetMapping("/{applicationId}/entries")
    public ResponseDto<Response> get(@PathVariable("applicationId") Long applicationId) {

        return ok(entryService.get(applicationId));
    }

    @PutMapping("/entries/{entryId}")
    public ResponseDto<UpdateResponse> update(@PathVariable("entryId") Long entryId, @RequestBody Request request) {

        return ok(entryService.update(entryId, request));
    }

    @PostMapping("{applicationId}/repayments")
    public ResponseDTO<RepaymentDTO.Response> create(@PathVariable Long applicationId, @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.create(applicationId, request));
    }

    @GetMapping("{applicationId}/repayments")
    public ResponseDTO<List<ListResponse>> getPayments(@PathVariable Long applicationId) {
        return ok(repaymentService.get(applicationId));
    }

    @PutMapping("/repayments/{repaymentId}")
    public ResponseDTO<RepaymentDTO.UpdateResponse> update(@PathVariable Long repaymentId,
                                                           @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.update(repaymentId, request));
    }
}
