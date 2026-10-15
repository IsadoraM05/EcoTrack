package br.com.fiap.EcoTrack.controller;

import br.com.fiap.EcoTrack.dto.AlertaCadastroDTO;
import br.com.fiap.EcoTrack.dto.AlertaExibicaoDTO;
import br.com.fiap.EcoTrack.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @PostMapping
    public ResponseEntity<AlertaExibicaoDTO> salvar(
            @RequestBody @Valid AlertaCadastroDTO alertaDTO){

        return ResponseEntity.ok(
                alertaService.salvarAlerta(alertaDTO)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaExibicaoDTO> buscarPorId(
            @PathVariable Long id) {

        try {
            return ResponseEntity
                    .ok(alertaService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping
    public List<AlertaExibicaoDTO> listarTodos(Pageable paginacao) {
        return alertaService.listarTodos(paginacao);
    }

    @PutMapping
    public ResponseEntity<AlertaExibicaoDTO> atualizar(
            @RequestBody @Valid AlertaCadastroDTO alertaDTO){
        try {

            return ResponseEntity.ok(
                    alertaService.atualizar(alertaDTO)
            );
        } catch (Exception e){

            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<AlertaExibicaoDTO> resolver(
            @PathVariable Long id) {

        try {
            return ResponseEntity
                    .ok(alertaService.resolverAlerta(id));
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        try {
            alertaService.excluir(id);

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