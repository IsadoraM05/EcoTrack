package br.com.fiap.EcoTrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ColetaCadastroDTO(

        Long coletaId,

        @NotNull(message = "A quantidade coletada é obrigatória")
        @Positive(message = "A quantidade coletada deve ser maior que zero")
        Double quantidadeColetada,

        @NotNull(message = "A data da coleta é obrigatória")
        LocalDateTime dataColeta,

        @NotBlank(message = "O responsável é obrigatório")
        String responsavel,

        @NotBlank(message = "O destino é obrigatório")
        String destino,

        @NotNull(message = "A lixeira é obrigatória")
        Long lixeiraId

) {
}