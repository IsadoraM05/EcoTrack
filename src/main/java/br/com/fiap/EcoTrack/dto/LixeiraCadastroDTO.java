package br.com.fiap.EcoTrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record LixeiraCadastroDTO(

        Long lixeiraId,

        @NotBlank(message = "O tipo de resíduo é obrigatório")
        String tipoResiduo,

        @NotNull(message = "A capacidade máxima é obrigatória")
        @PositiveOrZero(message = "A capacidade máxima não pode ser negativa")
        Double capacidadeMaxima,

        @NotNull(message = "O nível atual é obrigatório")
        @PositiveOrZero(message = "O nível atual não pode ser negativo")
        Double nivelAtual,

        String status,

        @NotNull(message = "A localização é obrigatória")
        Long localizacaoId

) {
}