package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Scambio;
import it.polimi.biblioteca.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScambioRepository extends JpaRepository<Scambio, Long> {

  List<Scambio> findAllByProprietarioIsNotAndAvvenutoFalse(Utente utente);
  List<Scambio> findAllByProprietario(Utente utente);
  List<Scambio> findAllByRichiedenteAndAvvenutoFalse(Utente utente);
  List<Scambio> findAllByAvvenutoTrue();
}
