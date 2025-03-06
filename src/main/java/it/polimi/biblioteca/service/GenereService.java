package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.response.GenereResponse;
import it.polimi.biblioteca.repository.GenereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenereService {

  private final GenereRepository genereRepository;

  public List<GenereResponse> getAll() {
    return genereRepository.findAll().stream()
            .map(genere -> new GenereResponse(genere.getNome()))
            .collect(java.util.stream.Collectors.toList());
  }
}
