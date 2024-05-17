package com.valletta.fintech.service;

import com.valletta.fintech.dto.TermsDto.Request;
import com.valletta.fintech.dto.TermsDto.Response;
import java.util.List;

public interface TermsService {

    Response create(Request request);

    List<Response> getAll();
}
