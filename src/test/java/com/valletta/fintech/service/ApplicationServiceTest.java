package com.valletta.fintech.service;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

import com.valletta.fintech.domain.Application;
import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;
import com.valletta.fintech.repository.ApplicationRepository;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
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

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

        Response actual = applicationService.create(request);

        Assertions.assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        Assertions.assertThat(actual.getName()).isSameAs(entity.getName());
    }
}