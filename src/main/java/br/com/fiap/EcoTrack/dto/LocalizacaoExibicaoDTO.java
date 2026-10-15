package br.com.fiap.EcoTrack.dto;

import br.com.fiap.EcoTrack.model.Localizacao;

public record LocalizacaoExibicaoDTO(

        Long localizacaoId,
        String nome,
        String tipoLocal,
        String endereco

) {

    public LocalizacaoExibicaoDTO(Localizacao localizacao) {
        this(
                localizacao.getLocalizacaoId(),
                localizacao.getNome(),
                localizacao.getTipoLocal(),
                localizacao.getEndereco()
        );
    }
}