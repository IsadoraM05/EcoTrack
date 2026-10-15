package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.dto.ColetaCadastroDTO;
import br.com.fiap.EcoTrack.dto.ColetaExibicaoDTO;
import br.com.fiap.EcoTrack.model.Coleta;
import br.com.fiap.EcoTrack.model.Lixeira;
import br.com.fiap.EcoTrack.repository.ColetaRepository;
import br.com.fiap.EcoTrack.repository.LixeiraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColetaService {

    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private LixeiraRepository lixeiraRepository;

    public ColetaExibicaoDTO salvarColeta(ColetaCadastroDTO coletaDTO) {

        Coleta coleta = new Coleta();
        BeanUtils.copyProperties(coletaDTO, coleta);

        Lixeira lixeira = lixeiraRepository
                .findById(coletaDTO.lixeiraId())
                .orElseThrow(() -> new RuntimeException("Lixeira não encontrada!"));

        coleta.setLixeira(lixeira);

        Double novoNivel = lixeira.getNivelAtual() - coletaDTO.quantidadeColetada();

        if (novoNivel < 0) {
            novoNivel = 0.0;
        }

        lixeira.setNivelAtual(novoNivel);

        atualizarStatusLixeira(lixeira);

        lixeiraRepository.save(lixeira);

        Coleta coletaSalva = coletaRepository.save(coleta);

        return new ColetaExibicaoDTO(coletaSalva);
    }

    public ColetaExibicaoDTO buscarPorId(Long id) {

        Optional<Coleta> coletaOptional = coletaRepository.findById(id);

        if (coletaOptional.isPresent()) {
            return new ColetaExibicaoDTO(coletaOptional.get());
        } else {
            throw new RuntimeException("Coleta não encontrada!");
        }
    }

    public List<ColetaExibicaoDTO> listarTodas(Pageable paginacao) {
        return coletaRepository
                .findAll(paginacao)
                .stream()
                .map(ColetaExibicaoDTO::new)
                .toList();
    }

    public void excluir(Long id) {

        Optional<Coleta> coletaOptional = coletaRepository.findById(id);

        if (coletaOptional.isPresent()) {
            coletaRepository.delete(coletaOptional.get());
        } else {
            throw new RuntimeException("Coleta não encontrada!");
        }
    }

    public ColetaExibicaoDTO atualizar(ColetaCadastroDTO coletaDTO) {

        Optional<Coleta> coletaOptional =
                coletaRepository.findById(coletaDTO.coletaId());

        if (coletaOptional.isPresent()) {

            Coleta coleta = coletaOptional.get();
            BeanUtils.copyProperties(coletaDTO, coleta);

            Lixeira lixeira = lixeiraRepository
                    .findById(coletaDTO.lixeiraId())
                    .orElseThrow(() -> new RuntimeException("Lixeira não encontrada!"));

            coleta.setLixeira(lixeira);

            return new ColetaExibicaoDTO(coletaRepository.save(coleta));

        } else {
            throw new RuntimeException("Coleta não encontrada!");
        }
    }
    private void atualizarStatusLixeira(Lixeira lixeira) {

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
}