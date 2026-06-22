package com.transporte.application.dto.response;

import com.transporte.domain.enums.Role;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioResponse {

    private UUID id;
    private String username;
    private Role role;
    private Boolean ativo;
}
