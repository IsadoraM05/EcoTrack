package br.com.fiap.EcoTrack.dto;

public record IndicadoresDTO(

        Long totalLixeiras,
        Long lixeirasCheias,
        Long lixeirasQuaseCheias,
        Long alertasPendentes,
        Long coletasRealizadas

) {
}