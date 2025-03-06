package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessaggioRepository extends JpaRepository<Messaggio, Long>{
}
