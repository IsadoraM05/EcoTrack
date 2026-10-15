package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.dto.UsuarioCadastroDTO;
import br.com.fiap.EcoTrack.dto.UsuarioExibicaoDTO;
import br.com.fiap.EcoTrack.model.Usuario;
import br.com.fiap.EcoTrack.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioExibicaoDTO cadastrarUsuario(
            UsuarioCadastroDTO usuarioDTO) {

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setSenha(
                passwordEncoder.encode(usuarioDTO.senha())
        );

        Usuario usuarioSalvo =
                usuarioRepository.save(usuario);

        return new UsuarioExibicaoDTO(usuarioSalvo);
    }
}