package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.request.ModificaUtenteRequest;
import it.polimi.biblioteca.dto.response.MessaggioResponse;
import it.polimi.biblioteca.dto.response.UtenteResponse;
import it.polimi.biblioteca.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/utente")
public class UtenteController {

  private final UtenteService utenteService;

  @PutMapping("/modifica/{userId}")
  public ResponseEntity<MessaggioResponse> modifica(@RequestBody ModificaUtenteRequest request, @PathVariable Long userId) {

    utenteService.modifica(request, userId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Utente modificato correttamente"));
  }

  @GetMapping("/get/{userId}")
  public ResponseEntity<UtenteResponse> get(@PathVariable Long userId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(utenteService.getById(userId));
  }

  @GetMapping("/getAll/{userId}")
  public ResponseEntity<List<UtenteResponse>> getAll(@PathVariable Long userId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(utenteService.getAll(userId));
  }
}
