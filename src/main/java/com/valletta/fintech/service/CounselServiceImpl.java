package com.valletta.fintech.service;

import com.valletta.fintech.domain.Counsel;
import com.valletta.fintech.dto.CounselDto.Request;
import com.valletta.fintech.dto.CounselDto.Response;
import com.valletta.fintech.repository.CounselRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService {

    private final ModelMapper modelMapper;

    private final CounselRepository counselRepository;

    @Override
    public Response create(Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.updateAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);

        return modelMapper.map(created, Response.class);
    }
}

