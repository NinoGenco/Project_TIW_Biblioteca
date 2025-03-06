package it.polimi.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Messaggio")
@Table(name = "messaggio")
public class Messaggio {

    @Id
    @SequenceGenerator(
            name = "messaggio_sequence",
            sequenceName = "messaggio_sequence",
            allocationSize = 1)
    @GeneratedValue(
            generator = "messaggio_sequence",
            strategy = GenerationType.SEQUENCE)
    @Column(name = "messaggio_id", nullable = false, updatable = false)
    private Long messaggioId;

    @Column(name = "testo", updatable = false, columnDefinition = "VARCHAR(1000)")
    private String testo;

    @Column(name = "data_invio", updatable = false)
    private LocalDateTime dataInvio;

    @ManyToOne
    private Utente mittente;

    @ManyToOne
    private Utente destinatario;
}