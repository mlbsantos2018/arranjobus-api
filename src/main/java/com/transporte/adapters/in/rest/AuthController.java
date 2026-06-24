package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.LoginRequest;
import com.transporte.application.dto.response.JwtResponse;
import com.transporte.application.dto.response.UsuarioResponse;
import com.transporte.application.mapper.UsuarioMapper;
import com.transporte.application.service.UsuarioService;
import com.transporte.infrastructure.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Auth", description = "Autenticação e JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autenticar usuário e obter JWT token")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get current user", description = "Obter informações do usuário atual")
    public ResponseEntity<UsuarioResponse> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuario = usuarioService.buscarPorUsername(username);
        return ResponseEntity.ok(usuarioMapper.toResponse(usuario));
    }
}
