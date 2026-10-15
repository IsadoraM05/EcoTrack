package br.com.fiap.EcoTrack.dto;

import br.com.fiap.EcoTrack.model.Coleta;

import java.time.LocalDateTime;

public record ColetaExibicaoDTO(

        Long coletaId,
        Double quantidadeColetada,
        LocalDateTime dataColeta,
        String responsavel,
        String destino,
        Long lixeiraId,
        String tipoResiduo

) {

    public ColetaExibicaoDTO(Coleta coleta) {
        this(
                coleta.getColetaId(),
                coleta.getQuantidadeColetada(),
                coleta.getDataColeta(),
                coleta.getResponsavel(),
                coleta.getDestino(),
                coleta.getLixeira() != null ? coleta.getLixeira().getLixeiraId() : null,
                coleta.getLixeira() != null ? coleta.getLixeira().getTipoResiduo() : null
        );
    }
}