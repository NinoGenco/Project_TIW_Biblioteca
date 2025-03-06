package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Genere;
import it.polimi.biblioteca.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByUsername(String username);
    List<Utente> findAllByNotificaTrueAndGeneriPreferitiContains(Genere genere);
}
