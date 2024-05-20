package com.valletta.fintech.service;

import static org.junit.jupiter.api.Assertions.*;

import com.valletta.fintech.dto.JudgmentDto.Request;
import com.valletta.fintech.dto.JudgmentDto.Response;
import com.valletta.fintech.repository.ApplicationRepository;
import com.valletta.fintech.repository.JudgmentRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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



        Response actual = judgmentService.create(request);
    }

}