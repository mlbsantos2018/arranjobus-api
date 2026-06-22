package com.transporte.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioCreateRequest {

    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Password é obrigatório")
    private String password;

    @NotNull(message = "Role é obrigatório")
    private String role;
}
