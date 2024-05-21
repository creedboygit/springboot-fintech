package com.valletta.fintech.service;

import com.valletta.fintech.dto.ApplicationDto.GrantAmount;
import com.valletta.fintech.dto.JudgmentDto.Request;
import com.valletta.fintech.dto.JudgmentDto.Response;

public interface JudgmentService {

    Response create(Request request);

    Response get(Long judgmentID);

    Response getJudgmentByApplication(Long applicationId);

    Response update(Long judgmentId, Request request);

    void delete(Long judgmentId);

    GrantAmount grant(Long judgmentId);
}
