package com.valletta.fintech.service;

import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;

public interface ApplicationService {

    Response create(Request request);

    Response get(Long applicationId);
}
