package com.valletta.fintech.service;

import com.valletta.fintech.constant.ResultType;
import com.valletta.fintech.controller.ApplicationController;
import com.valletta.fintech.dto.FileDto;
import com.valletta.fintech.exception.BaseException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

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

    @Override
    public Resource load(String fileName) throws MalformedURLException {

        try {
            Path file = Paths.get(uploadPath).resolve(fileName);

            UrlResource resource = new UrlResource(file.toUri());

            if (resource.isReadable() || resource.exists()) {
                return resource;
            } else {
                throw new BaseException(ResultType.NOT_EXIST);
            }
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    public List<FileDto> loadAll() throws IOException {

        List<FileDto> fileInfos = loadPaths().map(path -> {
            String fileName = path.getFileName().toString();

            // 파일 이름 유효성 검증
            if (fileName.contains("..")) {
                throw new BaseException(ResultType.INVALID_REQUEST, "invalid file path");
            }

            // 파일 경로 유효성 검증
            Path rootPath = Paths.get(uploadPath).normalize();
            Path resolvedPath = rootPath.resolve(fileName).normalize();
            if (!resolvedPath.startsWith(rootPath)) {
                throw new BaseException(ResultType.INVALID_REQUEST, "invalid file path");
            }

            return FileDto.builder()
                .name(fileName)
                .url(MvcUriComponentsBuilder.fromMethodName(ApplicationController.class, "download", fileName).build().toString())
                .build();
        }).toList();

        return fileInfos;
    }

    private Stream<Path> loadPaths() throws IOException {

        try (Stream<Path> paths = Files.walk(Paths.get(uploadPath), 1)) {
            return paths.filter(path -> !path.equals(Paths.get(uploadPath)))
                .toList()
                .stream();
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }
}
