package br.com.fiap.EcoTrack.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_lixeiras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Lixeira {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_LIXEIRAS"
    )
    @SequenceGenerator(
            name = "SEQ_LIXEIRAS",
            sequenceName = "SEQ_LIXEIRAS",
            allocationSize = 1
    )
    private Long lixeiraId;

    @Column(name = "TIPO_RESIDUO")
    private String tipoResiduo;

    @Column(name = "CAPACIDADE_MAXIMA")
    private Double capacidadeMaxima;

    @Column(name = "NIVEL_ATUAL")

    private Double nivelAtual;

    private String status;

    @ManyToOne
    @JoinColumn(name = "LOCALIZACAO_ID")
    private Localizacao localizacao;
}