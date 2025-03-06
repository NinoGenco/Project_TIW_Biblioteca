package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.request.ScambioRequest;
import it.polimi.biblioteca.dto.response.OffertaResponse;
import it.polimi.biblioteca.model.Libro;
import it.polimi.biblioteca.model.Offerta;
import it.polimi.biblioteca.model.Scambio;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.LibroRepository;
import it.polimi.biblioteca.repository.ScambioRepository;
import it.polimi.biblioteca.repository.UtenteRepository;
import it.polimi.biblioteca.util.ControlloDati;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScambioService {

  private final ScambioRepository scambioRepository;
  private final UtenteRepository utenteRepository;
  private final LibroRepository libroRepository;

  public void inScambio(ScambioRequest request) {
    ControlloDati.controlloNumeri(List.of(request.getProprietarioId(), request.getLibroId()));
    Optional<Utente> proprietario = utenteRepository.findById(request.getProprietarioId());
    if (proprietario.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    Optional<Libro> libro = libroRepository.findById(request.getLibroId());
    if (libro.isEmpty()) {
      throw new RuntimeException("Libro non trovato");
    }
    Optional<Libro> libroRichiedente = libroRepository.findById(request.getLibroIdRichiedente());
    if (libroRichiedente.isEmpty()) {
      throw new RuntimeException("Libro richiedente non trovato");
    }
    scambioRepository.save(Scambio.builder()
            .proprietario(proprietario.get())
            .libroProprietario(libro.get())
            .libroRichiedente(libroRichiedente.get())
            .tipoOfferta(Offerta.SCAMBIO)
            .avvenuto(false)
            .modalita(request.getModalitaScambio())
            .build());
  }

  public void inPrestito(ScambioRequest request) {
    ControlloDati.controlloNumeri(List.of(request.getProprietarioId(), request.getLibroId()));
    Optional<Utente> proprietario = utenteRepository.findById(request.getProprietarioId());
    if (proprietario.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    Optional<Libro> libro = libroRepository.findById(request.getLibroId());
    if (libro.isEmpty()) {
      throw new RuntimeException("Libro non trovato");
    }
    scambioRepository.save(Scambio.builder()
      .proprietario(proprietario.get())
      .libroProprietario(libro.get())
      .tipoOfferta(Offerta.PRESTITO)
      .avvenuto(false)
      .build());
  }

  public List<OffertaResponse> getOfferte(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if (utente.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    List<OffertaResponse> offerte = new java.util.ArrayList<>(scambioRepository.findAllByProprietarioIsNotAndAvvenutoFalse(utente.get()).stream()
      .map(scambio -> new OffertaResponse(
        scambio.getScambioId(),
        scambio.getModalita(),
        scambio.getTipoOfferta().toString(),
        scambio.getLibroProprietario().getTitolo(),
        scambio.getLibroProprietario().getAutore(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getTitolo(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getAutore()
      ))
      .toList());
    List<String> titoli = libroRepository.getAllByProprietario(utente.get())
      .stream().map(Libro::getTitolo).toList();
    offerte.removeIf(offerta -> (!titoli.contains(offerta.getTitoloLibroRichiedente()) && offerta.getTipoOfferta().equals("SCAMBIO")));
    return offerte;
  }

  public void ottieni(Long scambioId, Long riceventeId) {
    ControlloDati.controlloNumeri(List.of(scambioId, riceventeId));
    Optional<Scambio> scambio = scambioRepository.findById(scambioId);
    if (scambio.isEmpty()) {
      throw new RuntimeException("Scambio non trovato");
    }
    Optional<Utente> ricevente = utenteRepository.findById(riceventeId);
    if (ricevente.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    scambio.get().setRichiedente(ricevente.get());
    scambio.get().setAvvenuto(true);
    scambioRepository.save(scambio.get());
  }

  public List<OffertaResponse> getPropriScambi(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if (utente.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    return scambioRepository.findAllByProprietario(utente.get()).stream()
      .map(scambio -> new OffertaResponse(
        scambio.getScambioId(),
        scambio.getModalita(),
        scambio.getTipoOfferta().toString(),
        scambio.getLibroProprietario().getTitolo(),
        scambio.getLibroProprietario().getAutore(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getTitolo(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getAutore()
      ))
      .toList();
  }

  public void richiediPerScambio(ScambioRequest request) {
    ControlloDati.controlloNumeri(List.of(request.getProprietarioId(), request.getLibroId()));
    Optional<Utente> proprietario = utenteRepository.findById(request.getProprietarioId());
    if (proprietario.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    Optional<Libro> libro = libroRepository.findById(request.getLibroId());
    if (libro.isEmpty()) {
      throw new RuntimeException("Libro non trovato");
    }
    Optional<Libro> libroRichiedente = libroRepository.findById(request.getLibroIdRichiedente());
    if (libroRichiedente.isEmpty()) {
      throw new RuntimeException("Libro richiedente non trovato");
    }
    scambioRepository.save(Scambio.builder()
      .proprietario(libro.get().getProprietario())
      .libroProprietario(libro.get())
      .tipoOfferta(Offerta.SCAMBIO)
      .avvenuto(false)
      .modalita(request.getModalitaScambio())
      .richiedente(proprietario.get())
      .libroRichiedente(libroRichiedente.get())
      .build());
  }

  public void richiediInPrestito(ScambioRequest request) {
    ControlloDati.controlloNumeri(List.of(request.getProprietarioId(), request.getLibroId()));
    Optional<Utente> proprietario = utenteRepository.findById(request.getProprietarioId());
    if (proprietario.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    Optional<Libro> libro = libroRepository.findById(request.getLibroId());
    if (libro.isEmpty()) {
      throw new RuntimeException("Libro non trovato");
    }
    scambioRepository.save(Scambio.builder()
      .proprietario(libro.get().getProprietario())
      .libroProprietario(libro.get())
      .tipoOfferta(Offerta.PRESTITO)
      .avvenuto(false)
      .richiedente(proprietario.get())
      .modalita(request.getModalitaScambio())
      .build());
  }

  public List<OffertaResponse> getProprieRichieste(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if (utente.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    return scambioRepository.findAllByRichiedenteAndAvvenutoFalse(utente.get()).stream()
      .map(scambio -> new OffertaResponse(
        scambio.getScambioId(),
        scambio.getModalita(),
        scambio.getTipoOfferta().toString(),
        scambio.getLibroProprietario().getTitolo(),
        scambio.getLibroProprietario().getAutore(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getTitolo(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getAutore()
      ))
      .toList();
  }

  public void accettaRichiesta(Long scambioId) {
    ControlloDati.controlloNumeri(List.of(scambioId));
    Optional<Scambio> scambio = scambioRepository.findById(scambioId);
    if (scambio.isEmpty()) {
      throw new RuntimeException("Scambio non trovato");
    }
    scambio.get().setAvvenuto(true);
    scambioRepository.save(scambio.get());
  }

  public void rifiutaRichiesta(Long scambioId) {
    ControlloDati.controlloNumeri(List.of(scambioId));
    Optional<Scambio> scambio = scambioRepository.findById(scambioId);
    if (scambio.isEmpty()) {
      throw new RuntimeException("Scambio non trovato");
    }
    scambioRepository.delete(scambio.get());
  }

  public List<OffertaResponse> getOfferteConcluse(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if (utente.isEmpty()) {
      throw new RuntimeException("Utente non trovato");
    }
    return scambioRepository.findAllByAvvenutoTrue().stream()
      .filter(scambio -> scambio.getProprietario().equals(utente.get()) || scambio.getRichiedente().equals(utente.get()))
      .map(scambio -> new OffertaResponse(
        scambio.getScambioId(),
        scambio.getModalita(),
        scambio.getTipoOfferta().toString(),
        scambio.getLibroProprietario().getTitolo(),
        scambio.getLibroProprietario().getAutore(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getTitolo(),
        scambio.getLibroRichiedente() == null ? "" : scambio.getLibroRichiedente().getAutore()
      ))
      .toList();
  }
}
