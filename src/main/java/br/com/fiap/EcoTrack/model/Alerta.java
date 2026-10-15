package br.com.fiap.EcoTrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tbl_alertas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Alerta {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_ALERTAS"
    )
    @SequenceGenerator(
            name = "SEQ_ALERTAS",
            sequenceName = "SEQ_ALERTAS",
            allocationSize = 1
    )

    @Column(name = "ALERTA_ID")
    private Long alertaId;

    private String mensagem;

    @Column(name = "NIVEL_ALERTA")
    private String nivelAlerta;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao;

    private Boolean resolvido;

    @ManyToOne
    @JoinColumn(name = "LIXEIRA_ID")
    private Lixeira lixeira;

}
