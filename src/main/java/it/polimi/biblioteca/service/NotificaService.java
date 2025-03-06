package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.response.NotificaResponse;
import it.polimi.biblioteca.exception.CustomException;
import it.polimi.biblioteca.model.Notifica;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.NotificaRepository;
import it.polimi.biblioteca.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificaService {

  private final NotificaRepository notificaRepository;
  private final UtenteRepository utenteRepository;


  public List<NotificaResponse> getAllByUserId(Long userId) {

    if(userId < 1) {
      throw new CustomException("Id non valido");
    }

    Optional<Utente> utenteExists = utenteRepository.findById(userId);

    if(utenteExists.isEmpty()) {
      throw new CustomException("Utente non trovato");
    }

    List<Notifica> notifiche = notificaRepository.findAllByUtente(utenteExists.get());

    List<NotificaResponse> response = new ArrayList<>();

    for(Notifica notifica : notifiche) {
      response.add(new NotificaResponse(notifica.getData(), notifica.getTesto()));
    }

    return response;
  }
}
