package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    TermsRepository termsRepository;

    @Mock
    AcceptTermsRepository acceptTermsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() {

        Application entity = Application.builder()
            .name("김네임")
            .cellPhone("010-2030-2030")
            .email("email@email.com")
            .hopeAmount(BigDecimal.valueOf(50000000))
            .build();

        Request request = Request.builder()
            .name("김네임")
            .cellPhone("010-2030-2030")
            .email("email@email.com")
            .hopeAmount(BigDecimal.valueOf(50000000))
            .build();

        when(applicationRepository.save(any(Application.class))).thenReturn(entity);

        Response actual = applicationService.create(request);

        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @Test
    void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() {

        Long findId = 1L;

        Application entity = Application.builder()
            .applicationId(1L)
            .build();

        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = applicationService.get(findId);

        assertThat(actual.getApplicationId()).isSameAs(findId);
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestUpdateExistApplicationInfo() {

        Long findId = 1L;

        Application entity = Application.builder()
            .applicationId(1L)
            .hopeAmount(BigDecimal.valueOf(50000000))
            .build();

        Request request = Request.builder()
            .hopeAmount(BigDecimal.valueOf(5000000))
            .build();

        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));
        when(applicationRepository.save(any(Application.class))).thenReturn(entity);

        Response actual = applicationService.update(findId, request);

        assertThat(actual.getApplicationId()).isSameAs(findId);
        assertThat(actual.getHopeAmount()).isSameAs(request.getHopeAmount());
    }

    @Test
    void Should_DeleteApplicationEntity_When_RequestDeleteExistApplicationInfo() {

        Long targetId = 1L;

        Application entity = Application.builder()
            .applicationId(1L)
            .build();

        when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));
        when(applicationRepository.save(any(Application.class))).thenReturn(entity);

        applicationService.delete(targetId);

        assert entity != null;
        assertThat(entity.getIsDeleted()).isSameAs(true);
    }

    @Test
    void Should_AddAcceptTerms_When_RequestAcceptTermsOfApplication() {

        Terms entityA = Terms.builder()
            .termsId(1L)
            .name("약관 1")
            .termsDetailUrl("https://naver.com/agg1")
            .build();

        Terms entityB = Terms.builder()
            .termsId(2L)
            .name("약관 2")
            .termsDetailUrl("https://naver.com/agg2")
            .build();

//        List<Long> acceptTermsIds = List.of(1L, 2L);
        List<Long> acceptTermsIds = Arrays.asList(1L, 2L);

        AcceptTermsRequest acceptTermsRequest = AcceptTermsRequest.builder()
            .acceptTermsIds(acceptTermsIds)
            .build();

        Long findApplicationId = 1L;

        when(applicationRepository.findById(findApplicationId)).thenReturn(Optional.ofNullable(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Direction.ASC, "termsId"))).thenReturn(List.of(entityA, entityB));
        when(acceptTermsRepository.save(any(AcceptTerms.class))).thenReturn(AcceptTerms.builder().build());

        Boolean actual = applicationService.acceptTerms(findApplicationId, acceptTermsRequest);

//        Assertions.assertThat(actual).isSameAs(true);
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void Should_ThrowException_When_RequestNotAllAcceptTermsOfApplication() {

        Terms entityA = Terms.builder()
            .termsId(1L)
            .name("약관 1")
            .termsDetailUrl("https://naver.com/agg1")
            .build();

        Terms entityB = Terms.builder()
            .termsId(2L)
            .name("약관 2")
            .termsDetailUrl("https://naver.com/agg2")
            .build();

        List<Long> acceptTermsIds = Arrays.asList(1L);

        AcceptTermsRequest acceptTermsRequest = AcceptTermsRequest.builder()
            .acceptTermsIds(acceptTermsIds)
            .build();

        Long findApplicationId = 1L;

        when(applicationRepository.findById(findApplicationId)).thenReturn(Optional.ofNullable(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Direction.ASC, "termsId"))).thenReturn(List.of(entityA, entityB));

        assertThrows(BaseException.class, () -> applicationService.acceptTerms(findApplicationId, acceptTermsRequest));
    }

    @Test
    void Should_ThrowException_When_RequestNotExistAcceptTermsOfApplication() {

        Terms entityA = Terms.builder()
            .termsId(1L)
            .name("약관 1")
            .termsDetailUrl("https://naver.com/agg1")
            .build();

        Terms entityB = Terms.builder()
            .termsId(2L)
            .name("약관 2")
            .termsDetailUrl("https://naver.com/agg2")
            .build();

        List<Long> acceptTermsIds = Arrays.asList(1L, 3L);

        AcceptTermsRequest acceptTermsRequest = AcceptTermsRequest.builder()
            .acceptTermsIds(acceptTermsIds)
            .build();

        Long findApplicationId = 1L;

        when(applicationRepository.findById(findApplicationId)).thenReturn(Optional.ofNullable(Application.builder().build()));
        when(termsRepository.findAll(Sort.by(Direction.ASC, "termsId"))).thenReturn(List.of(entityA, entityB));

        assertThrows(BaseException.class, () -> applicationService.acceptTerms(findApplicationId, acceptTermsRequest));
    }
}