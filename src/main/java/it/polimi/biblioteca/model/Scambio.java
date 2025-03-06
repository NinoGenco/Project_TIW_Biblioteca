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
@Entity(name = "Scambio")
@Table(name = "scambio")
public class Scambio {

    @Id
    @SequenceGenerator(
            name = "scambio_sequence",
            sequenceName = "scambio_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "scambio_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "scambio_id", nullable = false, updatable = false)
    private Long scambioId;

    @Column
    private String modalita;

    @Column
    private boolean avvenuto;

    @Column
    @Enumerated(EnumType.STRING)
    private Offerta tipoOfferta;

    @OneToMany
    private List<Recensione> recensioni;

    @ManyToOne
    private Utente proprietario;

    @ManyToOne
    private Libro libroProprietario;

    @ManyToOne
    private Utente richiedente;

    @ManyToOne
    private Libro libroRichiedente;
}
