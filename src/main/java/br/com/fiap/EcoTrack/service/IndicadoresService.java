package br.com.fiap.EcoTrack.service;

import br.com.fiap.EcoTrack.dto.IndicadoresDTO;
import br.com.fiap.EcoTrack.repository.AlertaRepository;
import br.com.fiap.EcoTrack.repository.ColetaRepository;
import br.com.fiap.EcoTrack.repository.LixeiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicadoresService {

    @Autowired
    private LixeiraRepository lixeiraRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private ColetaRepository coletaRepository;

    public IndicadoresDTO gerarIndicadores() {

        Long totalLixeiras = lixeiraRepository.count();

        Long lixeirasCheias =
                lixeiraRepository.countByStatus("CHEIA");

        Long lixeirasQuaseCheias =
                lixeiraRepository.countByStatus("QUASE_CHEIA");

        Long alertasPendentes =
                alertaRepository.countByResolvido(false);

        Long coletasRealizadas =
                coletaRepository.count();

        return new IndicadoresDTO(
                totalLixeiras,
                lixeirasCheias,
                lixeirasQuaseCheias,
                alertasPendentes,
                coletasRealizadas
        );
    }
}