package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.Balance;
import com.valletta.fintech.dto.BalanceDto.Request;
import com.valletta.fintech.dto.BalanceDto.Response;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.BalanceRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalanceServiceImpl implements BananceService {

    private final BalanceRepository balanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {

        balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        Balance balance = modelMapper.map(request, Balance.class);

        BigDecimal entryAmount = request.getEntryAmount();
        balance.updateBalance(applicationId, entryAmount);

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, Response.class);
    }
}
