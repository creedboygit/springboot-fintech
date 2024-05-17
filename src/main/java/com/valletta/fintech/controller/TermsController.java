package com.valletta.fintech.controller;

import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.dto.TermsDto.Request;
import com.valletta.fintech.dto.TermsDto.Response;
import com.valletta.fintech.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/terms")
public class TermsController extends AbstractController {

    private final TermsService termsService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseDto<Response> create(@RequestBody Request request) {

        return ok(termsService.create(request));
    }
}
