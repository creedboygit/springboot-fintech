package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.Balance;
import com.valletta.fintech.dto.BalanceDto.Request;
import com.valletta.fintech.dto.BalanceDto.Response;
import com.valletta.fintech.dto.BalanceDto.UpdateRequest;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.BalanceRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {

//        balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        Balance balance = modelMapper.map(request, Balance.class);

        // 첫 생성은 entry amount를 balance로
        BigDecimal entryAmount = request.getEntryAmount();
        balance.updateBalance(applicationId, entryAmount);

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response update(Long applicationId, UpdateRequest request) {

        // balance 찾기
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
        BigDecimal afterEntryAmount = request.getAfterEntryAmount();
        BigDecimal updatedBalance = balance.getBalance();

        // as-is -> to-be
        // balance + -
        updatedBalance = updatedBalance.subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.updateBalance(updatedBalance);
        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(balance, Response.class);
    }
}
