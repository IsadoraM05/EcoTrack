package br.com.fiap.EcoTrack.dto;

import jakarta.validation.constraints.NotBlank;

public record LocalizacaoCadastroDTO(

        Long localizacaoId,

        @NotBlank(message = "O nome da localização é obrigatório")
        String nome,

        @NotBlank(message = "O tipo do local é obrigatório")
        String tipoLocal,

        @NotBlank(message = "O endereço é obrigatório")
        String endereco

) {
}
