package com.valletta.fintech.service;

import com.valletta.fintech.dto.FileDto;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void save(MultipartFile file) throws IOException;

    Resource load(String fileName) throws MalformedURLException;

//    Stream<Path> loadAll() throws IOException;

    List<FileDto> loadAll() throws IOException;
}
