package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Usuario;
import com.transporte.domain.enums.Role;
import com.transporte.domain.ports.out.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criar(Usuario usuario) {
        usuario.setId(UUID.randomUUID());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setAtivo(true);
        return usuarioRepository.criar(usuario);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario", id.toString()));
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.buscarPorUsername(username)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario", username));
    }

    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    public Usuario alterarRole(UUID id, Role novoRole) {
        Usuario usuario = buscarPorId(id);
        usuario.setRole(novoRole);
        return usuarioRepository.atualizar(usuario);
    }

    public Usuario alterarStatus(UUID id, Boolean ativo) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(ativo);
        return usuarioRepository.atualizar(usuario);
    }
}
