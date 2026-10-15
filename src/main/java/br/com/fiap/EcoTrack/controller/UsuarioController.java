package br.com.fiap.EcoTrack.controller;

import br.com.fiap.EcoTrack.dto.UsuarioCadastroDTO;
import br.com.fiap.EcoTrack.dto.UsuarioExibicaoDTO;
import br.com.fiap.EcoTrack.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioExibicaoDTO> cadastrar(
            @RequestBody @Valid UsuarioCadastroDTO usuarioDTO) {

        return ResponseEntity.ok(
                usuarioService.cadastrarUsuario(usuarioDTO)
        );
    }
}