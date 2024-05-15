package com.valletta.fintech.service;

import com.valletta.fintech.dto.CounselDto.Request;
import com.valletta.fintech.dto.CounselDto.Response;
import com.valletta.fintech.dto.ResponseDto;

public interface CounselService {

    Response create(Request request);

    Response get(Long counselId);

    Response update (Long counselId, Request request);

    void delete(Long counselId);
}
