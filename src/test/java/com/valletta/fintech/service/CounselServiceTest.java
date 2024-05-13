package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.domain.Counsel;
import com.valletta.fintech.dto.CounselDto.Request;
import com.valletta.fintech.dto.CounselDto.Response;
import com.valletta.fintech.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {

        Counsel entity = Counsel.builder()
            .name("Member kim")
            .cellPhone("010-3020-3020")
            .email("email@email.com")
            .memo("상담하고싶어요")
            .zipCode("12345")
            .address("서울특별시 강남구 청담동")
            .addressDetail("129-30")
            .build();

        Request request = Request.builder()
            .name("Member kim")
            .cellPhone("010-3020-3020")
            .email("email@email.com")
            .memo("상담하고싶어요")
            .zipCode("12345")
            .address("서울특별시 강남구 청담동")
            .addressDetail("129-30")
            .build();

        when(counselRepository.save(any(Counsel.class))).thenReturn(entity);
        System.out.println("entity = " + entity);
        System.out.println("request = " + request);

        Response actual = counselService.create(request);
        System.out.println("actual = " + actual);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }
}