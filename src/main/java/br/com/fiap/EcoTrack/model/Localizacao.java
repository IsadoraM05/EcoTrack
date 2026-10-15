package br.com.fiap.EcoTrack.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_localizacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Localizacao {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_LOCALIZACOES"
    )
    @SequenceGenerator(
            name = "SEQ_LOCALIZACOES",
            sequenceName = "SEQ_LOCALIZACOES",
            allocationSize = 1
    )
    @Column(name = "LOCALIZACAO_ID")
    private Long localizacaoId;

    private String nome;

    @Column(name = "TIPO_LOCAL")
    private String tipoLocal;

    private String endereco;

}
