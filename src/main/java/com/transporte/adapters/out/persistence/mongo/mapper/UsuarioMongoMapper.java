package com.transporte.adapters.out.persistence.mongo.mapper;

import com.transporte.adapters.out.persistence.mongo.document.UsuarioDocument;
import com.transporte.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioMongoMapper {

    public UsuarioDocument toUsuarioDocument(Usuario usuario) {
        return UsuarioDocument.builder()
                .id(usuario.getId().toString())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .role(usuario.getRole())
                .ativo(usuario.getAtivo())
                .build();
    }

    public Usuario toUsuario(UsuarioDocument document) {
        return Usuario.builder()
                .id(UUID.fromString(document.getId()))
                .username(document.getUsername())
                .password(document.getPassword())
                .role(document.getRole())
                .ativo(document.getAtivo())
                .build();
    }
}
