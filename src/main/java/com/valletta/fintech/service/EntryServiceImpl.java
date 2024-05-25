package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.Application;
import com.valletta.fintech.domain.Entry;
import com.valletta.fintech.dto.BalanceDto;
import com.valletta.fintech.dto.EntryDto.Request;
import com.valletta.fintech.dto.EntryDto.Response;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.ApplicationRepository;
import com.valletta.fintech.repository.EntryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final BananceService bananceService;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {

        // 계약 체결 여부 검증
        if (!isContractedApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Entry entry = modelMapper.map(request, Entry.class);
        entry.updateApplicationId(applicationId);
        entryRepository.save(entry);

        // 대출 잔고 관리
        bananceService.create(applicationId,
            BalanceDto.Request.builder()
                .entryAmount(entry.getEntryAmount())
                .build());

        return modelMapper.map(entry, Response.class);
    }

    private boolean isContractedApplication(Long applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);

        return application.filter(value -> value.getContractedAt() != null).isPresent();
    }
}
