package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.model.Usuario;
import br.com.fiap.EcoTrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService
        implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        Usuario usuario =
                usuarioRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "Usuário não encontrado!"
                                )
                        );

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getPerfil())
                .build();
    }
}