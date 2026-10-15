package br.com.fiap.EcoTrack.dto;

import br.com.fiap.EcoTrack.model.Alerta;
import java.time.LocalDateTime;

public record AlertaExibicaoDTO(

        Long alertaId,
        String mensagem,
        String nivelAlerta,
        LocalDateTime dataCriacao,
        Boolean resolvido,
        Long lixeiraId,
        String tipoResiduo
) {

    public AlertaExibicaoDTO(Alerta alerta){
        this(
                alerta.getAlertaId(),
                alerta.getMensagem(),
                alerta.getNivelAlerta(),
                alerta.getDataCriacao(),
                alerta.getResolvido(),
                alerta.getLixeira() != null ? alerta.getLixeira().getLixeiraId() : null,

                alerta.getLixeira() != null ? alerta.getLixeira().getTipoResiduo() : null
        );
    }
}