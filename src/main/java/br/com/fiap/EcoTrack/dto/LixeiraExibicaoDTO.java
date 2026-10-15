package br.com.fiap.EcoTrack.dto;

import br.com.fiap.EcoTrack.model.Lixeira;

public record LixeiraExibicaoDTO(

        Long lixeiraId,
        String tipoResiduo,
        Double capacidadeMaxima,
        Double nivelAtual,
        String status,
        Long localizacaoId,
        String nomeLocalizacao

) {

    public LixeiraExibicaoDTO(Lixeira lixeira) {
        this(
                lixeira.getLixeiraId(),
                lixeira.getTipoResiduo(),
                lixeira.getCapacidadeMaxima(),
                lixeira.getNivelAtual(),
                lixeira.getStatus(),
                lixeira.getLocalizacao() != null ? lixeira.getLocalizacao().getLocalizacaoId() : null,
                lixeira.getLocalizacao() != null ? lixeira.getLocalizacao().getNome() : null
        );
    }
}