package com.valletta.fintech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.domain.Application;
import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;
import com.valletta.fintech.repository.ApplicationRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    ApplicationRepository applicationRepository;

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
}