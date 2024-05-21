package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.domain.Application;
import com.valletta.fintech.domain.Judgment;
import com.valletta.fintech.dto.ApplicationDto.GrantAmount;
import com.valletta.fintech.dto.JudgmentDto.Request;
import com.valletta.fintech.dto.JudgmentDto.Response;
import com.valletta.fintech.repository.ApplicationRepository;
import com.valletta.fintech.repository.JudgmentRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class JudgmentServiceTest {

    @InjectMocks
    private JudgmentServiceImpl judgmentService;

    @Mock
    private JudgmentRepository judgmentRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewJudgmentEntity_When_RequestNewJudgment() {

        Request request = Request.builder()
            .applicationId(1L)
            .name("Member Kim")
            .approvalAmount(BigDecimal.valueOf(3000000))
            .build();

        Judgment entity = Judgment.builder()
            .judgmentId(1L)
            .name("심사명")
            .approvalAmount(BigDecimal.valueOf(30000000))
            .build();

//        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().applicationId(1L).build()));
        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));
        when(judgmentRepository.save(any(Judgment.class))).thenReturn(entity);

        Response actual = judgmentService.create(request);

        assertThat(actual.getJudgmentId()).isSameAs(entity.getJudgmentId());
        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(entity.getApprovalAmount());
    }

    @Test
    void Should_ReturnResponseOfExistJudgmentEntity_When_RequestExistJudgmentId() {

        Judgment judgment = Judgment.builder()
            .judgmentId(1L)
            .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));

        Response actual = judgmentService.get(1L);

        assertThat(actual.getJudgmentId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnResponseOfExistJudgmentEntity_When_RequestExistApplicationId() {

        Application application = Application.builder()
            .applicationId(1L)
            .build();

        Judgment judgment = Judgment.builder()
            .judgmentId(1L)
            .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(application));
        when(judgmentRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(judgment));

        Response actual = judgmentService.getJudgmentByApplication(1L);

        assertThat(actual.getJudgmentId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistJudgmentEntity_When_RequestUpdateExistJudgmentInfo() {

        Request request = Request.builder()
            .name("대출심사명1")
            .approvalAmount(BigDecimal.valueOf(4000000))
            .build();

        Judgment judgment = Judgment.builder()
            .judgmentId(1L)
            .name("멤버리")
            .approvalInterestRate(BigDecimal.valueOf(20000000))
            .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));
        when(judgmentRepository.save(any(Judgment.class))).thenReturn(judgment);

        Response actual = judgmentService.update(1L, request);

        assertThat(actual.getJudgmentId()).isSameAs(1L);
        assertThat(actual.getName()).isSameAs(request.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
    }

    @Test
    void Should_DeletedJudgmentEntity_When_RequestdeleteExistJudgmentInfo() {

        Judgment judgment = Judgment.builder()
            .judgmentId(1L)
            .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));
        when(judgmentRepository.save(any(Judgment.class))).thenReturn(judgment);

        judgmentService.delete(1L);

        assertThat(judgment).isNotNull();
        assertThat(judgment.getIsDeleted()).isTrue();
    }

    @Test
    void Should_ReturnUpdateResponseOfExistApplicationEntity_When_RequestGrantApplovalAmountOfJudgmentInfo() {

        Long findId = 1L;

        Judgment judgment = Judgment.builder()
//            .applicationId(1L)
            .name("멤버")
            .applicationId(findId)
            .approvalAmount(BigDecimal.valueOf(20000000))
            .build();

        Application application = Application.builder()
            .applicationId(findId)
            .approvalAmount(BigDecimal.valueOf(20000000))
            .build();

        when(judgmentRepository.findById(findId)).thenReturn(Optional.ofNullable(judgment));
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        GrantAmount actual = judgmentService.grant(findId);

        assertThat(judgment).isNotNull();
        assertThat(actual.getApplicationId()).isSameAs(findId);
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());
    }
}