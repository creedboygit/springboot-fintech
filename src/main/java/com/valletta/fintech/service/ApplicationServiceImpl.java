package com.valletta.fintech.service;

import com.valletta.fintech.domain.Application;
import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;
import com.valletta.fintech.repository.ApplicationRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, Response.class);
    }
}
