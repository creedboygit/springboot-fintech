package com.valletta.fintech.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController {

//    private final EntryService entryService;

//    private final RepaymentService repaymentService;

//    @PostMapping("{applicationId}/entries")
//    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
//        return ok(entryService.create(applicationId, request));
//    }
}
