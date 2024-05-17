package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.domain.Terms;
import com.valletta.fintech.dto.TermsDto.Request;
import com.valletta.fintech.dto.TermsDto.Response;
import com.valletta.fintech.repository.TermsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Test
    void Should_ReturnAllResponseOfExistTermsEntities_When_RequestTermsList() {

        Terms entityA = Terms.builder()
            .name("대출 이용 약관 1")
            .termsDetailUrl("https://naver.com/aaa")
            .build();

        Terms entityB = Terms.builder()
            .name("대출 이용 약관 2")
            .termsDetailUrl("https://naver.com/bbb")
            .build();

        List<Terms> list = new ArrayList<>(List.of(entityA, entityB));

        when(termsRepository.findAll()).thenReturn(list);
//        when(termsRepository.findAll()).thenReturn(Arrays.asList(entityA, entityB));

        List<Response> actual = termsService.getAll();

        assertThat(actual.size()).isSameAs(list.size());
    }
}