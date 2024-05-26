package com.valletta.fintech.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EntryDto implements Serializable {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private BigDecimal entryAmount;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long entryId;
        private Long applicationId;
        private BigDecimal entryAmount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateResponse {

        private Long entryId;
        private Long applicationId;

        @JsonFormat(shape = Shape.STRING, pattern = "0")
        private BigDecimal beforeEntryAmount;

        @JsonFormat(shape = Shape.STRING, pattern = "0")
        private BigDecimal afterEntryAmount;
    }
}
