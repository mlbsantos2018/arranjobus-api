package com.transporte.domain.model;

import com.transporte.domain.enums.Role;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Usuario {

    private UUID id;
    private String username;
    private String password;
    private Role role;
    private Boolean ativo;
}

