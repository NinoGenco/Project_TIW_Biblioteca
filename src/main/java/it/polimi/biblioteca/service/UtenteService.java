package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.request.ModificaUtenteRequest;
import it.polimi.biblioteca.dto.response.UtenteResponse;
import it.polimi.biblioteca.exception.CustomException;
import it.polimi.biblioteca.model.Genere;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.GenereRepository;
import it.polimi.biblioteca.repository.UtenteRepository;
import it.polimi.biblioteca.util.ControlloDati;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UtenteService {

  private final UtenteRepository utenteRepository;
  private final GenereRepository genereRepository;
  private final PasswordEncoder passwordEncoder;

  public void modifica(ModificaUtenteRequest request, Long userId) {
    if(userId < 1) {
      throw new CustomException("Id non valido");
    }

    Optional<Utente> utenteExists = utenteRepository.findById(userId);

    if(utenteExists.isEmpty()) {
      throw new CustomException("Utente non trovato");
    }

    Utente utente = utenteExists.get();

    if(!request.getUsername().isEmpty() &&
      !request.getUsername().isBlank() &&
      !request.getUsername().equals(utente.getUsername())) {

      Optional<Utente> userWithUsername = utenteRepository.findByUsername(request.getUsername());

      if(userWithUsername.isPresent()) {
        throw new CustomException("Username già registrato");
      } else {
        utente.setUsername(request.getUsername());
      }
    }

    if(request.getNome().isEmpty() && !request.getNome().isBlank()) {
      utente.setNome(request.getNome());
    }

    if(!request.getEmail().isEmpty() && !request.getEmail().isBlank()) {
      utente.setEmail(request.getEmail());
    }

    if(!request.getTelefono().isEmpty() && !request.getTelefono().isBlank()) {
      utente.setTelefono(request.getTelefono());
    }

    if(!request.getNuovaPassword().isEmpty() &&
      !request.getNuovaPassword().isBlank() &&
      !request.getVecchiaPassword().isEmpty() &&
      !request.getVecchiaPassword().isBlank()) {

      if(!passwordEncoder.matches(request.getVecchiaPassword(), utente.getPassword())) {
        throw new CustomException("Password errata");
      }

      if(request.getVecchiaPassword().equals(request.getNuovaPassword())) {
        throw new CustomException("Le password sono uguali");
      }

      utente.setPassword(passwordEncoder.encode(request.getNuovaPassword()));
    }

    if(!request.getComunita().isEmpty() && !request.getComunita().isBlank()) {
      utente.setComunita(request.getComunita());
    }

    utente.setNotifica(request.isNotifica());

    utente.setGeneriPreferiti(new ArrayList<>(
      genereRepository.findAll()
        .stream()
        .filter(genere -> request.getGeneriSelezionati().contains(genere.getNome()))
        .toList()
      )
    );

    utenteRepository.save(utente);
  }

  public UtenteResponse getById(Long userId) {
    Utente utente = utenteRepository.findById(userId).orElseThrow(()-> new CustomException("Utente non trovato"));
    UtenteResponse u= new UtenteResponse(
      utente.getUserId(),
      utente.getUsername(),
      utente.getNome(),
      utente.getEmail(),
      utente.getTelefono(),
      utente.getComunita(),
      utente.isNotifica(),
      null
    );
    if(utente.getGeneriPreferiti()!=null)u.setGeneriSelezionati(utente.getGeneriPreferiti().stream().map(Genere::getNome).collect(Collectors.toList()));
    return u;
  }

  public List<UtenteResponse> getAll(Long userId) {
    ControlloDati.controlloNumeri(List.of(userId));
    return utenteRepository.findAll().stream()
      .filter(utente -> !utente.getUserId().equals(userId))
      .map(utente -> new UtenteResponse(
        utente.getUserId(),
        utente.getUsername(),
        utente.getNome(),
        utente.getEmail(),
        utente.getTelefono(),
        utente.getComunita(),
        utente.isNotifica(),
        utente.getGeneriPreferiti().stream().map(Genere::getNome).collect(Collectors.toList())
      ))
      .collect(Collectors.toList());
  }
}
