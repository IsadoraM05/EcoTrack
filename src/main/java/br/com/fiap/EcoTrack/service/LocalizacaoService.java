package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.dto.LocalizacaoCadastroDTO;
import br.com.fiap.EcoTrack.dto.LocalizacaoExibicaoDTO;
import br.com.fiap.EcoTrack.model.Localizacao;
import br.com.fiap.EcoTrack.repository.LocalizacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalizacaoService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public LocalizacaoExibicaoDTO salvarLocalizacao(LocalizacaoCadastroDTO localizacaoDTO) {

        Localizacao localizacao = new Localizacao();
        BeanUtils.copyProperties(localizacaoDTO, localizacao);

        Localizacao localizacaoSalva = localizacaoRepository.save(localizacao);

        return new LocalizacaoExibicaoDTO(localizacaoSalva);
    }

    public LocalizacaoExibicaoDTO buscarPorId(Long id) {

        Optional<Localizacao> localizacaoOptional =
                localizacaoRepository.findById(id);

        if (localizacaoOptional.isPresent()) {
            return new LocalizacaoExibicaoDTO(localizacaoOptional.get());
        } else {
            throw new RuntimeException("Localização não existe!");
        }
    }

    public List<LocalizacaoExibicaoDTO> listarTodas() {

        return localizacaoRepository
                .findAll()
                .stream()
                .map(LocalizacaoExibicaoDTO::new)
                .toList();
    }

    public void excluir(Long id) {

        Optional<Localizacao> localizacaoOptional =
                localizacaoRepository.findById(id);

        if (localizacaoOptional.isPresent()) {
            localizacaoRepository.delete(localizacaoOptional.get());
        } else {
            throw new RuntimeException("Localização não encontrada!");
        }
    }

    public LocalizacaoExibicaoDTO atualizar(LocalizacaoCadastroDTO localizacaoDTO) {

        Optional<Localizacao> localizacaoOptional =
                localizacaoRepository.findById(localizacaoDTO.localizacaoId());

        if (localizacaoOptional.isPresent()) {

            Localizacao localizacao = new Localizacao();
            BeanUtils.copyProperties(localizacaoDTO, localizacao);

            return new LocalizacaoExibicaoDTO(localizacaoRepository.save(localizacao));

        } else {
            throw new RuntimeException("Localização não encontrada!");
        }
    }
}