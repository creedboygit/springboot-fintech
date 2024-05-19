package com.valletta.fintech.controller;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.dto.ApplicationDto.AcceptTermsRequest;
import com.valletta.fintech.dto.ApplicationDto.Request;
import com.valletta.fintech.dto.ApplicationDto.Response;
import com.valletta.fintech.dto.FileDto;
import com.valletta.fintech.dto.ResponseDto;
import com.valletta.fintech.exception.BaseException;
import com.valletta.fintech.service.ApplicationService;
import com.valletta.fintech.service.FileStorageService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;

    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseDto<Response> create(@RequestBody Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/{applicationId}")
    public ResponseDto<Response> get(@PathVariable("applicationId") Long applicationId) {
        return ok(applicationService.get(applicationId));
    }

    @PutMapping("{applicationId}")
    public ResponseDto<Response> update(@PathVariable("applicationId") Long applicationId, @RequestBody Request request) {
        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("{applicationId}")
    public ResponseDto<Void> delete(@PathVariable("applicationId") Long applicationId) {
        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("{applicationId}/terms")
    public ResponseDto<Boolean> acceptTerms(@PathVariable("applicationId") Long applicationId, @RequestBody AcceptTermsRequest request) {
        return ok(applicationService.acceptTerms(applicationId, request));
    }

    @PostMapping("/files")
//    public ResponseDto<Void> upload(@RequestParam("file") MultipartFile file) throws IOException {
    public ResponseDto<Void> upload(MultipartFile file) throws IOException {
        fileStorageService.save(file);
        return ok();
    }

    @GetMapping("/files")
    public ResponseEntity<Resource> download(@RequestParam(value = "fileName") String fileName) throws MalformedURLException {
        Resource file = fileStorageService.load(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/infos")
    public ResponseDto<List<FileDto>> getFileInfos() throws IOException {
        List<FileDto> fileInfos = fileStorageService.loadAll();
        return ok(fileInfos);
    }

    @DeleteMapping("/files")
    public ResponseDto<Void> deleteAll() {
        fileStorageService.deleteAll();
        return ok();
    }
}
