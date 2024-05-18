package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.AcceptTerms;
import com.valletta.fintech.domain.Application;
import com.valletta.fintech.domain.Terms;
import com.valletta.fintech.dto.ApplicationDto.AcceptTermsRequest;
import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.AcceptTermsRepository;
import com.valletta.fintech.repository.ApplicationRepository;
import com.valletta.fintech.repository.TermsRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final TermsRepository termsRepository;

    private final AcceptTermsRepository acceptTermsRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(application, Response.class);
    }

    @Override
    public Response update(Long applicationId, Request request) {
        Application entity = applicationRepository.findById(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));
        entity.updateAll(request);

        applicationRepository.save(entity);

        return modelMapper.map(entity, Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application entity = applicationRepository.findById(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        entity.updateDeleted();

        applicationRepository.save(entity);
//        applicationRepository.delete(entity);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, AcceptTermsRequest request) {

        // DB에서 신청서가 존재하는지 확인
        applicationRepository.findById(applicationId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        // DB에서 모든 약관을 ID 오름차순으로 정렬하여 조회
        List<Terms> termsList = termsRepository.findAll(Sort.by(Direction.ASC, "termsId"));
        if (termsList.isEmpty()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // 요청에서 받아온 약관 동의 ID 리스트를 조회
        List<Long> acceptTermsIds = request.getAcceptTermsIds();
        if (acceptTermsIds.size() != termsList.size()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // DB에서 조회한 약관 ID f리스트를 추출하고 오름차순 정렬
        List<Long> termsIds = termsList.stream().map(Terms::getTermsId).toList();
        Collections.sort(acceptTermsIds);

        // 요청에서 받아온 약관 ID들이 DB의 약관 ID들과 일치하는지 확인
        if (!new HashSet<>(termsIds).containsAll(acceptTermsIds)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // 각 약관 동의 ID를 AcceptTerms 엔티티로 변환하여 데이터베이스에 저장
        for (Long acceptTermsId : acceptTermsIds) {
            AcceptTerms accepted = AcceptTerms.builder()
                .termsId(acceptTermsId)
                .applicationId(applicationId)
                .build();

            acceptTermsRepository.save(accepted);
        }

        return true;
    }
}
