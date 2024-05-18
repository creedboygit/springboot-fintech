package com.valletta.fintech.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void save(MultipartFile file) throws IOException;
}
