package br.com.fiap.EcoTrack.repository;

import br.com.fiap.EcoTrack.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
}