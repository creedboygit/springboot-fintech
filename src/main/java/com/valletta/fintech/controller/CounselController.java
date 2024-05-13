package com.valletta.fintech.controller;

import com.valletta.fintech.dto.CounselDto.Request;
import com.valletta.fintech.dto.CounselDto.Response;
import com.valletta.fintech.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/counsels")
public class CounselController {

    private final CounselService counselService;

    @PostMapping()
    public Response createCounsel(@RequestBody Request request) {
        Response response = counselService.create(request);
//        return response.getCounselId();
        return response;
    }
}
