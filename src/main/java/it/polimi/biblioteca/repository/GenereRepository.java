package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Genere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenereRepository extends JpaRepository<Genere, Long> {

  Optional<Genere> getByNome(String nome);
}
