package com.valletta.fintech.service;

import com.valletta.fintech.dto.BalanceDto.Request;
import com.valletta.fintech.dto.BalanceDto.Response;
import com.valletta.fintech.dto.BalanceDto.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, Request request);

    Response update(Long applicationId, UpdateRequest request);
}
