package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.domain.Terms;
import com.valletta.fintech.dto.TermsDto.Request;
import com.valletta.fintech.dto.TermsDto.Response;
import com.valletta.fintech.repository.TermsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class TermsServiceImplTest {

    @InjectMocks
    private TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewTermsEntity_When_RequestTerms() {

        Terms entity = Terms.builder()
            .name("대출 이용 약관")
            .termsDetailUrl("https://aaa.com")
            .build();

        Request request = Request.builder()
            .name("대출 이용 약관")
            .termsDetailUrl("https://aaa.com")
            .build();

        when(termsRepository.save(any(Terms.class))).thenReturn(entity);

        Response actual = termsService.create(request);

        assertThat(actual.getTermsId()).isSameAs(entity.getTermsId());
        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());
    }
}