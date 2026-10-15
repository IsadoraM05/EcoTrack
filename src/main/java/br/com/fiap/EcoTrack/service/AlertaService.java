package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.dto.AlertaCadastroDTO;
import br.com.fiap.EcoTrack.dto.AlertaExibicaoDTO;
import br.com.fiap.EcoTrack.model.Alerta;
import br.com.fiap.EcoTrack.model.Lixeira;
import br.com.fiap.EcoTrack.repository.AlertaRepository;
import br.com.fiap.EcoTrack.repository.LixeiraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private LixeiraRepository lixeiraRepository;

    public AlertaExibicaoDTO salvarAlerta(AlertaCadastroDTO alertaDTO) {

        Alerta alerta = new Alerta();
        BeanUtils.copyProperties(alertaDTO, alerta);

        alerta.setDataCriacao(LocalDateTime.now());
        alerta.setResolvido(false);

        Lixeira lixeira = lixeiraRepository
                .findById(alertaDTO.lixeiraId())
                .orElseThrow(() -> new RuntimeException("Lixeira não encontrada!"));

        alerta.setLixeira(lixeira);

        Alerta alertaSalvo = alertaRepository.save(alerta);

        return new AlertaExibicaoDTO(alertaSalvo);
    }

    public AlertaExibicaoDTO buscarPorId(Long id) {

        Optional<Alerta> alertaOptional = alertaRepository.findById(id);

        if (alertaOptional.isPresent()) {
            return new AlertaExibicaoDTO(alertaOptional.get());
        } else {
            throw new RuntimeException("Alerta não encontrado!");
        }
    }

    public List<AlertaExibicaoDTO> listarTodos(Pageable paginacao) {
        return alertaRepository
                .findAll(paginacao)
                .stream()
                .map(AlertaExibicaoDTO::new)
                .toList();
    }

    public void excluir(Long id) {

        Optional<Alerta> alertaOptional = alertaRepository.findById(id);

        if (alertaOptional.isPresent()) {
            alertaRepository.delete(alertaOptional.get());
        } else {
            throw new RuntimeException("Alerta não encontrado!");
        }
    }

    public AlertaExibicaoDTO atualizar(AlertaCadastroDTO alertaDTO) {

        Optional<Alerta> alertaOptional =
                alertaRepository.findById(alertaDTO.alertaId());

        if (alertaOptional.isPresent()) {

            Alerta alerta = alertaOptional.get();

            alerta.setMensagem(alertaDTO.mensagem());
            alerta.setNivelAlerta(alertaDTO.nivelAlerta());

            Lixeira lixeira = lixeiraRepository
                    .findById(alertaDTO.lixeiraId())
                    .orElseThrow(() -> new RuntimeException("Lixeira não encontrada!"));

            alerta.setLixeira(lixeira);

            return new AlertaExibicaoDTO(alertaRepository.save(alerta));

        } else {
            throw new RuntimeException("Alerta não encontrado!");
        }
    }

    public AlertaExibicaoDTO resolverAlerta(Long id) {

        Optional<Alerta> alertaOptional = alertaRepository.findById(id);

        if (alertaOptional.isPresent()) {

            Alerta alerta = alertaOptional.get();
            alerta.setResolvido(true);

            return new AlertaExibicaoDTO(alertaRepository.save(alerta));

        } else {
            throw new RuntimeException("Alerta não encontrado!");
        }
    }
}