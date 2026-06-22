package com.transporte.application.mapper;

import com.transporte.application.dto.response.UsuarioResponse;
import com.transporte.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .role(usuario.getRole())
                .ativo(usuario.getAtivo())
                .build();
    }
}
