package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.request.AggiungiLibroRequest;
import it.polimi.biblioteca.dto.response.LibroResponse;
import it.polimi.biblioteca.dto.response.MessaggioResponse;
import it.polimi.biblioteca.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/libro")
public class LibroController {

  private final LibroService libroService;

  @PostMapping("/aggiungi/{userId}")
  public ResponseEntity<MessaggioResponse> aggiungi(@RequestBody AggiungiLibroRequest request, @PathVariable Long userId) {

    libroService.aggiungi(request, userId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Libro aggiunto con successo"));
  }

  @GetMapping("/getAll/{userId}")
  public ResponseEntity<List<LibroResponse>> getAll(@PathVariable Long userId) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(libroService.getAll(userId));
  }

  @GetMapping("/getByProprietario/{userId}")
  public ResponseEntity<List<LibroResponse>> getByProprietario(@PathVariable Long userId) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(libroService.getByProprietario(userId));
  }
}
