package com.valletta.fintech.controller;

import com.valletta.fintech.dto.JudgmentDto.Request;
import com.valletta.fintech.dto.JudgmentDto.Response;
import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.service.JudgmentService;
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
@RequestMapping("/api/v1/judgments")
public class JudgmentController extends AbstractController {

    private final JudgmentService judgmentService;

    @PostMapping
    public ResponseDto<Response> create(@RequestBody Request request) {

        return ok(judgmentService.create(request));
    }

    @GetMapping("/{judgmentId}")
    public ResponseDto<Response> get(@PathVariable("judgmentId") Long judgmentId) {

        return ok(judgmentService.get(judgmentId));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseDto<Response> getJudgmentByApplication(@PathVariable("applicationId") Long applicationId) {

        return ok(judgmentService.getJudgmentByApplication(applicationId));
    }

    @PutMapping("/{judgmentId}")
    public ResponseDto<Response> update(@PathVariable("judgmentId") Long judgmentId, @RequestBody Request request) {

        return ok(judgmentService.update(judgmentId, request));
    }

    @DeleteMapping("/{judgmentId}")
    public ResponseDto<Void> delete(@PathVariable("judgmentId") Long judgmentId) {

        judgmentService.delete(judgmentId);
        return ok();
    }
}
