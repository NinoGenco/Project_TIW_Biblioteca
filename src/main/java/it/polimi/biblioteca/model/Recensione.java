package it.polimi.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Recensione")
@Table(name = "recensione")
public class Recensione {

  @Id
  @SequenceGenerator(
    name = "recensione_sequence",
    sequenceName = "recensione_sequence",
    allocationSize = 1)
  @GeneratedValue(
    generator = "recensione_sequence",
    strategy = GenerationType.SEQUENCE)
  @Column(name = "recensione_id", nullable = false, updatable = false)
  private Long recensioneId;

  @Column
  private String testo;

  @Column
  private Integer voto;

  @ManyToOne
  private Scambio scambio;

  @ManyToOne
  private Utente utente;
}
