package com.transporte.adapters.out.persistence.mongo.adapter;

import com.transporte.adapters.out.persistence.mongo.mapper.UsuarioMongoMapper;
import com.transporte.adapters.out.persistence.mongo.repository.UsuarioMongoRepository;
import com.transporte.domain.model.Usuario;
import com.transporte.domain.ports.out.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UsuarioMongoAdapter implements UsuarioRepositoryPort {

    private final UsuarioMongoRepository repository;
    private final UsuarioMongoMapper mapper;

    @Override
    public Usuario criar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(UUID.randomUUID());
        }
        var document = mapper.toUsuarioDocument(usuario);
        var saved = repository.save(document);
        return mapper.toUsuario(saved);
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        var document = mapper.toUsuarioDocument(usuario);
        var updated = repository.save(document);
        return mapper.toUsuario(updated);
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id.toString()).map(mapper::toUsuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username).map(mapper::toUsuario);
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll().stream()
                .map(mapper::toUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorUsername(String username) {
        return repository.existsByUsername(username);
    }
}
