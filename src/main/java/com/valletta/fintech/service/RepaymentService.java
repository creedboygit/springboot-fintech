package com.valletta.fintech.service;

import com.valletta.fintech.dto.EntryDto.UpdateResponse;
import java.util.List;

public interface RepaymentService {

    Response create(Long applicationId, Request request);

    List<ListResponse> get(Long applicationId);

    UpdateResponse update(Long repaymentId, Request request);

    void delete(Long repaymentId);
}
