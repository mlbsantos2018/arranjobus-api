package com.transporte.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {

    private String token;
    private String type;
    private long expiresIn;
}
