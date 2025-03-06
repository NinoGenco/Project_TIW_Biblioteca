package it.polimi.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Libro")
@Table(name = "libro")
public class Libro {

    @Id
    @SequenceGenerator(
            name = "libro_sequence",
            sequenceName = "libro_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "libro_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "libro_id", nullable = false, updatable = false)
    private Long libroId;

    @Column
    private String titolo;

    @Column
    private String autore;

    @Column
    private Integer anno;

    @Column
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "genere_id")
    private Genere genere;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente proprietario;

    @OneToMany(mappedBy = "libroProprietario", fetch = FetchType.LAZY)
    private List<Scambio> scambiProprietario;

    @OneToMany(mappedBy = "libroRichiedente", fetch = FetchType.LAZY)
    private List<Scambio> scambiRichiedente;
}
