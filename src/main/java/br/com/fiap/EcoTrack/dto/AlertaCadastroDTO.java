package br.com.fiap.EcoTrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlertaCadastroDTO(

        Long alertaId,

        @NotBlank(message = "A mensagem é obrigatória")
        String mensagem,

        @NotBlank(message = "O nível do alerta é obrigatório")
        String nivelAlerta,

        @NotNull(message = "A lixeira é obrigatória")
        Long lixeiraId

) {
}