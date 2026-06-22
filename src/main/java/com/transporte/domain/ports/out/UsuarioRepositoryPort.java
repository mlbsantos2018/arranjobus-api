package com.transporte.domain.ports.out;

import com.transporte.domain.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {
    Usuario criar(Usuario usuario);

    Usuario atualizar(Usuario usuario);

    void excluir(UUID id);

    Optional<Usuario> buscarPorId(UUID id);

    Optional<Usuario> buscarPorUsername(String username);

    List<Usuario> listar();

    boolean existePorUsername(String username);
}
