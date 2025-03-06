package it.polimi.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Genere")
@Table(name = "genere")
public class Genere {

    @Id
    @SequenceGenerator(
            name = "genere_sequence",
            sequenceName = "genere_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "genere_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "genere_id", nullable = false, updatable = false)
    private Long genereId;

    @Column
    private String nome;

    @OneToMany(mappedBy = "genere", fetch = FetchType.LAZY)
    private List<Libro> libri;

    @ManyToMany(mappedBy = "generiPreferiti")
    private List<Utente> utenti;
}
