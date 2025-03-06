package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Recensione;
import it.polimi.biblioteca.model.Scambio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

  List<Recensione> findByScambio(Scambio scambio);
}
