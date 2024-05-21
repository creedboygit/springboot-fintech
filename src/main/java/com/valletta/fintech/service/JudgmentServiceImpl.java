package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.Application;
import com.valletta.fintech.domain.Judgment;
import com.valletta.fintech.dto.ApplicationDto.AcceptTermsRequest;
import com.valletta.fintech.dto.ApplicationDto.GrantAmount;
import com.valletta.fintech.dto.JudgmentDto.Request;
import com.valletta.fintech.dto.JudgmentDto.Response;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.ApplicationRepository;
import com.valletta.fintech.repository.JudgmentRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JudgmentServiceImpl implements JudgmentService {

    private final ApplicationRepository applicationRepository;
    private final JudgmentRepository judgmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {

        Long applicationId = request.getApplicationId();

        // 신청 정보가 있는지 검증
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // request dto -> entity -> save
        Judgment judgment = modelMapper.map(request, Judgment.class);
        Judgment saved = judgmentRepository.save(judgment);

        // save -> response dto
        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long judgmentId) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(judgment, Response.class);
    }

    @Override
    public Response getJudgmentByApplication(Long applicationId) {

        // 신청 정보가 있는지 검증
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Judgment judgment = judgmentRepository.findByApplicationId(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(judgment, Response.class);
    }

    @Override
    public Response update(Long judgmentId, Request request) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        judgment.update(request);
        Judgment saved = judgmentRepository.save(judgment);
        return modelMapper.map(saved, Response.class);
    }

    @Override
    public void delete(Long judgmentId) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        judgment.updateDeleted();
        judgmentRepository.save(judgment);
    }

    @Override
    public GrantAmount grant(Long judgmentId) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        Long applicationId = judgment.getApplicationId();
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        BigDecimal approvalAmount = judgment.getApprovalAmount();

        application.updateApprovalAmount(approvalAmount);

        applicationRepository.save(application);

        return modelMapper.map(application, GrantAmount.class);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
