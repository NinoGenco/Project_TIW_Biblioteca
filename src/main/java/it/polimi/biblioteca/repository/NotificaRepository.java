package it.polimi.biblioteca.repository;

import it.polimi.biblioteca.model.Notifica;
import it.polimi.biblioteca.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificaRepository extends JpaRepository<Notifica, Long> {

  List<Notifica> findAllByUtente(Utente utente);
}
