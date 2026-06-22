package com.transporte.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {

    private int status;
    private String message;
    private String error;
    private LocalDateTime timestamp;
}
