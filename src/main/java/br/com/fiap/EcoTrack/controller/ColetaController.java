package br.com.fiap.EcoTrack.controller;

import br.com.fiap.EcoTrack.dto.ColetaCadastroDTO;
import br.com.fiap.EcoTrack.dto.ColetaExibicaoDTO;
import br.com.fiap.EcoTrack.service.ColetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    @Autowired
    private ColetaService coletaService;

    @PostMapping
    public ResponseEntity<ColetaExibicaoDTO> salvar(
            @RequestBody @Valid ColetaCadastroDTO coletaDTO) {

        return ResponseEntity
                .ok(coletaService.salvarColeta(coletaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetaExibicaoDTO> buscarPorId(
            @PathVariable Long id) {

        try {
            return ResponseEntity
                    .ok(coletaService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping
    public List<ColetaExibicaoDTO> listarTodas(Pageable paginacao) {
        return coletaService.listarTodas(paginacao);
    }

    @PutMapping
    public ResponseEntity<ColetaExibicaoDTO> atualizar(
            @RequestBody @Valid ColetaCadastroDTO coletaDTO) {

        try {
            return ResponseEntity
                    .ok(coletaService.atualizar(coletaDTO));
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        try {
            coletaService.excluir(id);

            return ResponseEntity
                    .noContent()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}