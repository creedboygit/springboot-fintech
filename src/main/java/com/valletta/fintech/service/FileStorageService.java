package com.valletta.fintech.service;

import com.valletta.fintech.dto.FileDto;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void save(Long applicationId, MultipartFile file) throws IOException;

    Resource load(Long applicationId, String filename) throws MalformedURLException;

//    Stream<Path> loadAll() throws IOException;

    List<FileDto> loadAll(Long applicationId) throws IOException;

    void deleteAll(Long applicationId);
}
