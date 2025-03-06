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
@Entity(name = "Notifica")
@Table(name = "notifica")
public class Notifica {

  @Id
  @SequenceGenerator(
    name = "notifica_sequence",
    sequenceName = "notifica_sequence",
    allocationSize = 1)
  @GeneratedValue(
    generator = "notifica_sequence",
    strategy = GenerationType.SEQUENCE)
  @Column(name = "notifica_id", nullable = false, updatable = false)
  private Long notificaId;

  @Column(name = "data", updatable = false)
  private LocalDateTime data;

  @Column(name = "testo", updatable = false, columnDefinition = "VARCHAR(1000)")
  private String testo;

  @ManyToOne
  private Utente utente;
}
