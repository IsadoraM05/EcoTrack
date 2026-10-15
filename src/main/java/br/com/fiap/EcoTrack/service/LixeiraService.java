package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.dto.LixeiraCadastroDTO;
import br.com.fiap.EcoTrack.dto.LixeiraExibicaoDTO;
import br.com.fiap.EcoTrack.model.Alerta;
import br.com.fiap.EcoTrack.model.Lixeira;
import br.com.fiap.EcoTrack.model.Localizacao;
import br.com.fiap.EcoTrack.repository.AlertaRepository;
import br.com.fiap.EcoTrack.repository.LixeiraRepository;
import br.com.fiap.EcoTrack.repository.LocalizacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LixeiraService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private LixeiraRepository lixeiraRepository;

    public LixeiraExibicaoDTO salvarLixeira(LixeiraCadastroDTO lixeiraDTO) {

        Lixeira lixeira = new Lixeira();
        BeanUtils.copyProperties(lixeiraDTO, lixeira);

        if (lixeiraDTO.localizacaoId() != null) {
            Localizacao localizacao = localizacaoRepository
                    .findById(lixeiraDTO.localizacaoId())
                    .orElseThrow(() -> new RuntimeException("Localização não encontrada!"));

            lixeira.setLocalizacao(localizacao);
        }

        verificarStatus(lixeira);

        Lixeira lixeiraSalva = lixeiraRepository.save(lixeira);

        criarAlertaSeNecessario(lixeiraSalva);

        return new LixeiraExibicaoDTO(lixeiraSalva);
    }

    public LixeiraExibicaoDTO buscarPorId(Long id) {

        Optional<Lixeira> lixeiraOptional =
                lixeiraRepository.findById(id);

        if (lixeiraOptional.isPresent()) {
            return new LixeiraExibicaoDTO(lixeiraOptional.get());
        } else {
            throw new RuntimeException("Lixeira não existe!");
        }
    }

    public List<LixeiraExibicaoDTO> listarTodas() {
        return lixeiraRepository
                .findAll()
                .stream()
                .map(LixeiraExibicaoDTO::new)
                .toList();
    }

    public void excluir(Long id) {

        Optional<Lixeira> lixeiraOptional =
                lixeiraRepository.findById(id);

        if (lixeiraOptional.isPresent()) {
            lixeiraRepository.delete(lixeiraOptional.get());
        } else {
            throw new RuntimeException("Lixeira não encontrada!");
        }
    }

    public LixeiraExibicaoDTO atualizar(LixeiraCadastroDTO lixeiraDTO) {

        Optional<Lixeira> lixeiraOptional =
                lixeiraRepository.findById(lixeiraDTO.lixeiraId());

        if (lixeiraOptional.isPresent()) {

            Lixeira lixeira = lixeiraOptional.get();

            BeanUtils.copyProperties(lixeiraDTO, lixeira);

            if (lixeiraDTO.localizacaoId() != null) {
                Localizacao localizacao = localizacaoRepository
                        .findById(lixeiraDTO.localizacaoId())
                        .orElseThrow(() -> new RuntimeException("Localização não encontrada!"));

                lixeira.setLocalizacao(localizacao);
            }

            verificarStatus(lixeira);

            Lixeira lixeiraAtualizada = lixeiraRepository.save(lixeira);

            criarAlertaSeNecessario(lixeiraAtualizada);

            return new LixeiraExibicaoDTO(lixeiraAtualizada);

        } else {
            throw new RuntimeException("Lixeira não encontrada!");
        }
    }

    private void verificarStatus(Lixeira lixeira) {

        Double percentual =
                (lixeira.getNivelAtual() / lixeira.getCapacidadeMaxima()) * 100;

        if (percentual >= 100) {
            lixeira.setStatus("CHEIA");
        } else if (percentual >= 80) {
            lixeira.setStatus("QUASE_CHEIA");
        } else {
            lixeira.setStatus("DISPONIVEL");
        }
    }

    private void criarAlertaSeNecessario(Lixeira lixeira) {

        Double percentual =
                (lixeira.getNivelAtual() / lixeira.getCapacidadeMaxima()) * 100;

        if (percentual >= 100) {

            Alerta alerta = new Alerta();
            alerta.setMensagem("Lixeira atingiu a capacidade máxima");
            alerta.setNivelAlerta("CRITICO");
            alerta.setDataCriacao(LocalDateTime.now());
            alerta.setResolvido(false);
            alerta.setLixeira(lixeira);

            alertaRepository.save(alerta);

        } else if (percentual >= 80) {

            Alerta alerta = new Alerta();
            alerta.setMensagem("Lixeira próxima da capacidade máxima");
            alerta.setNivelAlerta("ALTO");
            alerta.setDataCriacao(LocalDateTime.now());
            alerta.setResolvido(false);
            alerta.setLixeira(lixeira);

            alertaRepository.save(alerta);
        }
    }
}