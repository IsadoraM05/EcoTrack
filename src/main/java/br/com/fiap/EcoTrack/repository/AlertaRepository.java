package br.com.fiap.EcoTrack.repository;

import br.com.fiap.EcoTrack.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository
        extends JpaRepository<Alerta, Long> {
    Long countByResolvido(Boolean resolvido);
}