package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.exception.BaseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void save(MultipartFile file) {

        try {
            // 파일 이름 검증
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename(), "File name must not be null");

            // 업로드 경로 설정
            Path targetLocation = Paths.get(uploadPath).resolve(originalFilename);

            // 파일 저장
            file.transferTo(targetLocation.toFile());

//            Files.copy(file.getInputStream(), Paths.get(uploadPath).resolve(
//                Objects.requireNonNull(file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }
}
