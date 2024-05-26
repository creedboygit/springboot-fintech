package com.valletta.fintech.service;

import com.valletta.fintech.dto.EntryDto.Request;
import com.valletta.fintech.dto.EntryDto.Response;

public interface EntryService {

    Response create(Long applicationId, Request request);

    Response get(Long applicationId);
}
