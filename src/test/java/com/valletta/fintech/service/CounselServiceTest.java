package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.domain.Counsel;
import com.valletta.fintech.dto.CounselDto.Request;
import com.valletta.fintech.dto.CounselDto.Response;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.repository.CounselRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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

    @Test
    void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {

        Long findId = 1L;

        Counsel entity = Counsel.builder()
            .counselId(1L)
            .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);

        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @Test
    void Should_ThrowException_When_RequestNotExistCounselId() {

        Long findId = 2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {

        Long findId = 1L;

        Counsel entity = Counsel.builder()
            .counselId(1L)
            .name("Member Ju")
            .build();

        Request request = Request.builder()
            .name("Member Park")
            .build();

        when(counselRepository.save(any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.update(findId, request);

        assertThat(actual.getCounselId()).isSameAs(findId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }
}