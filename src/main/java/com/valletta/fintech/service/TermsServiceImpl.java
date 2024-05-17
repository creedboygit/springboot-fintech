package com.valletta.fintech.service;

import com.valletta.fintech.domain.Terms;
import com.valletta.fintech.dto.TermsDto.Request;
import com.valletta.fintech.dto.TermsDto.Response;
import com.valletta.fintech.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {

        Terms terms = modelMapper.map(request, Terms.class);

        Terms created = termsRepository.save(terms);

        return modelMapper.map(created, Response.class);
    }
}
