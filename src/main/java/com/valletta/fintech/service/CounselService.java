package com.valletta.fintech.service;

import com.valletta.fintech.dto.CounselDto.Request;
import com.valletta.fintech.dto.CounselDto.Response;

public interface CounselService {

    Response create(Request request);
}
