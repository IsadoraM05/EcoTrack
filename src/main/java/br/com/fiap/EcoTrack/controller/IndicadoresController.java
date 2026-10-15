package br.com.fiap.EcoTrack.controller;

import br.com.fiap.EcoTrack.dto.IndicadoresDTO;
import br.com.fiap.EcoTrack.service.IndicadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indicadores")
public class IndicadoresController {

    @Autowired
    private IndicadoresService indicadoresService;

    @GetMapping
    public ResponseEntity<IndicadoresDTO> gerarIndicadores() {

        return ResponseEntity.ok(
                indicadoresService.gerarIndicadores()
        );

    }

}