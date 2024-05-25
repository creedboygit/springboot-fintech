package com.valletta.fintech.service;

import com.valletta.fintech.dto.BalanceDto.Request;
import com.valletta.fintech.dto.BalanceDto.Response;

public interface BananceService {

    Response create(Long applicationId, Request request);
}
