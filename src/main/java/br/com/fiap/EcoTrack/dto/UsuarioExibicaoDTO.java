package br.com.fiap.EcoTrack.dto;

import br.com.fiap.EcoTrack.model.Usuario;

public record UsuarioExibicaoDTO(

        Long usuarioId,
        String nome,
        String email,
        String perfil
) {

    public UsuarioExibicaoDTO(Usuario usuario) {

        this(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );
    }
}