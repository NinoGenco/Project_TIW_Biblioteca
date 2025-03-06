package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.request.RecensioneRequest;
import it.polimi.biblioteca.dto.response.RecensioneResponse;
import it.polimi.biblioteca.model.Recensione;
import it.polimi.biblioteca.model.Scambio;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.RecensioneRepository;
import it.polimi.biblioteca.repository.ScambioRepository;
import it.polimi.biblioteca.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecensioneService {

  private final RecensioneRepository recensioneRepository;
  private final UtenteRepository utenteRepository;
  private final ScambioRepository scambioRepository;

  public void invia(RecensioneRequest request) {
    Optional<Utente> utente = utenteRepository.findById(request.getUserId());
    if (utente.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    Optional<Scambio> scambio = scambioRepository.findById(request.getScambioId());
    if (scambio.isEmpty()) {
      throw new RuntimeException("Scambio non trovato");
    }
    Recensione recensione = Recensione.builder()
      .testo(request.getTesto())
      .voto(request.getVoto())
      .utente(utente.get())
      .scambio(scambio.get())
      .build();
    recensioneRepository.save(recensione);
  }

  public List<RecensioneResponse> getByScambio(Long scambioId) {
    Optional<Scambio> scambio = scambioRepository.findById(scambioId);
    if (scambio.isEmpty()) {
      throw new RuntimeException("Scambio non trovato");
    }
    return recensioneRepository.findByScambio(scambio.get()).stream()
      .map(recensione -> new RecensioneResponse(
        recensione.getUtente().getUsername(),
        recensione.getTesto(),
        recensione.getVoto()
      )).toList();
  }
}
