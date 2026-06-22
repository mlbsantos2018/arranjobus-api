package com.transporte.infrastructure.security;

import com.transporte.application.service.UsuarioService;
import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioService.buscarPorUsername(username);

            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()))
                    .accountLocked(!usuario.getAtivo())
                    .build();
        } catch (RecursoNaoEncontradoException ex) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username, ex);
        }
    }
}
