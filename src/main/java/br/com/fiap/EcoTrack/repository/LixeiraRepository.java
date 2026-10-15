package br.com.fiap.EcoTrack.repository;

import br.com.fiap.EcoTrack.model.Lixeira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LixeiraRepository extends JpaRepository<Lixeira, Long> {
    Long countByStatus(String status);
}