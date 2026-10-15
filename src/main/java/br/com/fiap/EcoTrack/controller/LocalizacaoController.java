package br.com.fiap.EcoTrack.controller;

import br.com.fiap.EcoTrack.dto.LocalizacaoCadastroDTO;
import br.com.fiap.EcoTrack.dto.LocalizacaoExibicaoDTO;
import br.com.fiap.EcoTrack.service.LocalizacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @PostMapping
    public ResponseEntity<LocalizacaoExibicaoDTO> salvar(
            @RequestBody @Valid LocalizacaoCadastroDTO localizacaoDTO){

        return ResponseEntity.ok(
                localizacaoService.salvarLocalizacao(localizacaoDTO)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalizacaoExibicaoDTO> buscarPorId(
            @PathVariable Long id) {

        try {
            return ResponseEntity
                    .ok(localizacaoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping
    public List<LocalizacaoExibicaoDTO> listarTodas() {
        return localizacaoService.listarTodas();
    }

    @PutMapping
    public ResponseEntity<LocalizacaoExibicaoDTO> atualizar(
            @RequestBody @Valid LocalizacaoCadastroDTO localizacaoDTO){

        try {
            return ResponseEntity.ok(
                    localizacaoService.atualizar(localizacaoDTO)
            );

        } catch (Exception e){

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        try {
            localizacaoService.excluir(id);
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