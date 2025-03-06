package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.request.AggiungiLibroRequest;
import it.polimi.biblioteca.dto.response.LibroResponse;
import it.polimi.biblioteca.exception.CustomException;
import it.polimi.biblioteca.model.Genere;
import it.polimi.biblioteca.model.Libro;
import it.polimi.biblioteca.model.Notifica;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.GenereRepository;
import it.polimi.biblioteca.repository.LibroRepository;
import it.polimi.biblioteca.repository.NotificaRepository;
import it.polimi.biblioteca.repository.UtenteRepository;
import it.polimi.biblioteca.util.ControlloDati;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroService {

  private final LibroRepository libroRepository;
  private final GenereRepository genereRepository;
  private final UtenteRepository utenteRepository;
  private final NotificaRepository notificaRepository;

  public void aggiungi(AggiungiLibroRequest request, Long userId) {
    ControlloDati.controlloStringhe(List.of(request.getTitolo(), request.getAutore(), request.getGenere(), request.getDescrizione()));
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if(utente.isEmpty()) {
      throw new CustomException("Utente non presente");
    }
    Optional<Genere> genere = genereRepository.getByNome(request.getGenere());
    if(genere.isEmpty()) {
      throw new CustomException("Genere non presente");
    }
    libroRepository.save(
        Libro.builder()
          .titolo(request.getTitolo())
          .autore(request.getAutore())
          .genere(genere.get())
          .anno(request.getAnno())
          .descrizione(request.getDescrizione())
          .proprietario(utente.get())
          .build()
    );
    List<Utente> utenti = utenteRepository.findAllByNotificaTrueAndGeneriPreferitiContains(genere.get());
    for(Utente u : utenti) {
        if(!u.equals(utente.get())) {
          notificaRepository.save(Notifica.builder()
            .data(LocalDateTime.now())
            .testo("L'utente " + utente.get().getUsername() + " ha pubblicato un nuovo libro!")
            .utente(u)
            .build());
        }
    }
  }

  public List<LibroResponse> getAll(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if(utente.isEmpty()) {
      throw new CustomException("Utente non presente");
    }
    return libroRepository.getAllByProprietarioIsNot(utente.get()).stream()
      .map(libro -> new LibroResponse(
        libro.getLibroId(),
        libro.getTitolo(),
        libro.getAutore(),
        libro.getAnno(),
        libro.getDescrizione(),
        libro.getGenere().getNome(),
        libro.getProprietario().getNome()
      ))
      .toList();
  }

  public List<LibroResponse> getByProprietario(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    Optional<Utente> utente = utenteRepository.findById(userId);
    if(utente.isEmpty()) {
      throw new CustomException("Utente non presente");
    }
    return libroRepository.getAllByProprietario(utente.get()).stream()
      .map(libro -> new LibroResponse(
        libro.getLibroId(),
        libro.getTitolo(),
        libro.getAutore(),
        libro.getAnno(),
        libro.getDescrizione(),
        libro.getGenere().getNome(),
        libro.getProprietario().getNome()
      ))
      .toList();
  }
}
