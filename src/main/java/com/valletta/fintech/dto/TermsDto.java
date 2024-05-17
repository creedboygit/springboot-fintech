package com.valletta.fintech.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TermsDto implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Request {

        private String name;
        private String termsDetailUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private Long termsId;
        private String name;
        private String termsDetailUrl;
        private String createdAt;
        private String deletedAt;
    }
}
