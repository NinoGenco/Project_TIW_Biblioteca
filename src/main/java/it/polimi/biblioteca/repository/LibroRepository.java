package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Libro;
import it.polimi.biblioteca.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long>{

  List<Libro> getAllByProprietarioIsNot(Utente utente);
  List<Libro> getAllByProprietario(Utente utente);
}
