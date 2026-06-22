package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.UsuarioCreateRequest;
import com.transporte.application.dto.response.UsuarioResponse;
import com.transporte.application.mapper.UsuarioMapper;
import com.transporte.application.service.UsuarioService;
import com.transporte.domain.model.Usuario;
import com.transporte.domain.enums.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Gerenciamento de Usuários")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Criar um novo usuário")
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioCreateRequest request) {
        var usuario = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.valueOf(request.getRole()))
                .build();
        
        var usuarioCriado = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(usuarioCriado));
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponse>> listar() {
        var usuarios = usuarioService.listar();
        return ResponseEntity.ok(usuarios.stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/{id}/role")
    @Operation(summary = "Alterar role", description = "Alterar a role de um usuário")
    public ResponseEntity<UsuarioResponse> alterarRole(@PathVariable UUID id, @RequestParam String role) {
        var usuarioAtualizado = usuarioService.alterarRole(id, Role.valueOf(role));
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioAtualizado));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Alterar status", description = "Ativar ou desativar um usuário")
    public ResponseEntity<UsuarioResponse> alterarStatus(@PathVariable UUID id, @RequestParam Boolean ativo) {
        var usuarioAtualizado = usuarioService.alterarStatus(id, ativo);
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioAtualizado));
    }
}
