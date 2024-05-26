package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.Application;
import com.valletta.fintech.domain.Entry;
import com.valletta.fintech.dto.BalanceDto;
import com.valletta.fintech.dto.BalanceDto.UpdateRequest;
import com.valletta.fintech.dto.EntryDto.Request;
import com.valletta.fintech.dto.EntryDto.Response;
import com.valletta.fintech.dto.EntryDto.UpdateResponse;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.ApplicationRepository;
import com.valletta.fintech.repository.BalanceRepository;
import com.valletta.fintech.repository.EntryRepository;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceService bananceService;
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

    @Override
    public Response get(Long applicationId) {

        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);

        if (entry.isPresent()) {
            return modelMapper.map(entry, Response.class);
        } else {
            return null;
        }
    }

    @Override
    public UpdateResponse update(Long entryId, Request request) {

        // entry
        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        BigDecimal beforeEntryAmount = entry.getEntryAmount();
        entry.updateEntryAmount(request.getEntryAmount());

        entryRepository.save(entry);

        // before -> after
        Long applicationId = entry.getApplicationId();
        BigDecimal afterEntryAmount = request.getEntryAmount();

        // balance update
        bananceService.update(applicationId, UpdateRequest.builder()
            .applicationId(applicationId)
            .beforeEntryAmount(beforeEntryAmount)
            .afterEntryAmount(afterEntryAmount)
            .build());

        return UpdateResponse.builder()
            .entryId(entryId)
            .applicationId(applicationId)
            .beforeEntryAmount(beforeEntryAmount)
            .afterEntryAmount(afterEntryAmount)
            .build();
    }

    private boolean isContractedApplication(Long applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);

        return application.filter(value -> value.getContractedAt() != null).isPresent();
    }
}
