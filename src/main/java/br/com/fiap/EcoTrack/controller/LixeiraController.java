package br.com.fiap.EcoTrack.controller;

import br.com.fiap.EcoTrack.dto.LixeiraCadastroDTO;
import br.com.fiap.EcoTrack.dto.LixeiraExibicaoDTO;
import br.com.fiap.EcoTrack.service.LixeiraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lixeiras")
public class LixeiraController {

    @Autowired
    private LixeiraService lixeiraService;

    @PostMapping
    public ResponseEntity<LixeiraExibicaoDTO> salvar(
            @RequestBody @Valid LixeiraCadastroDTO lixeiraDTO) {
        return ResponseEntity.ok(
                lixeiraService.salvarLixeira(lixeiraDTO)
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<LixeiraExibicaoDTO> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .ok(lixeiraService.buscarPorId(id));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<LixeiraExibicaoDTO> listarTodas() {
        return lixeiraService.listarTodas();
    }

    @PutMapping
    public ResponseEntity<LixeiraExibicaoDTO> atualizar(
            @RequestBody @Valid LixeiraCadastroDTO lixeiraDTO) {
        return ResponseEntity.ok(
                lixeiraService.atualizar(lixeiraDTO)
        );
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        lixeiraService.excluir(id);
    }
}