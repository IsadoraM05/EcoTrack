package br.com.fiap.EcoTrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="tbl_coletas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Coleta {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_COLETAS"
    )
    @SequenceGenerator(
            name = "SEQ_COLETAS",
            sequenceName = "SEQ_COLETAS",
            allocationSize = 1
    )

    private Long coletaId;

    @Column(name = "QUANTIDADE_COLETADA")
    private Double quantidadeColetada;

    @Column(name = "DATA_COLETA")
    private LocalDateTime dataColeta;

    @Column(name = "RESPONSAVEL")
    private String responsavel;

    private String destino;

    @ManyToOne
    @JoinColumn(name = "LIXEIRA_ID")
    private Lixeira lixeira;
}
