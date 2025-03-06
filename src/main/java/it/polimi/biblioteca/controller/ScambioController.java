package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.request.ScambioRequest;
import it.polimi.biblioteca.dto.response.MessaggioResponse;
import it.polimi.biblioteca.dto.response.OffertaResponse;
import it.polimi.biblioteca.model.Offerta;
import it.polimi.biblioteca.service.ScambioService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scambio")
public class ScambioController {

  private final ScambioService scambioService;

  @PostMapping("/inScambio")
  public ResponseEntity<MessaggioResponse> inScambio(@RequestBody ScambioRequest request) {

    scambioService.inScambio(request);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Scambio proposto con successo"));
  }

  @PostMapping("/inPrestito")
  public ResponseEntity<MessaggioResponse> inPrestito(@RequestBody ScambioRequest request) {

    scambioService.inPrestito(request);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Prestito proposto con successo"));
  }

  @GetMapping("/getOfferte/{userId}")
  public ResponseEntity<List<OffertaResponse>> getOfferte(@PathVariable Long userId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(scambioService.getOfferte(userId));
  }

  @GetMapping("/ottieni/{scambioId}/{riceventeId}")
  public ResponseEntity<MessaggioResponse> ottieni(@PathVariable Long scambioId, @PathVariable Long riceventeId) {

    scambioService.ottieni(scambioId, riceventeId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Scambio avvenuto con successo"));
  }

  @GetMapping("/getPropriScambi/{userId}")
  public ResponseEntity<List<OffertaResponse>> getPropriScambi(@PathVariable Long userId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(scambioService.getPropriScambi(userId));
  }

  @PostMapping("/richiediPerScambio")
  public ResponseEntity<MessaggioResponse> richiediPerScambio(@RequestBody ScambioRequest request) {

      scambioService.richiediPerScambio(request);

      return ResponseEntity
        .status(HttpStatus.OK)
        .body(new MessaggioResponse("Richiesta di scambio inviata con successo"));
  }

  @PostMapping("/richiediInPrestito")
  public ResponseEntity<MessaggioResponse> richiediInPrestito(@RequestBody ScambioRequest request) {

    scambioService.richiediInPrestito(request);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Richiesta di prestito inviata con successo"));
  }

  @GetMapping("/getProprieRichieste/{userId}")
  public ResponseEntity<List<OffertaResponse>> getProprieRichieste(@PathVariable Long userId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(scambioService.getProprieRichieste(userId));
  }

  @GetMapping("/accettaRichiesta/{scambioId}")
  public ResponseEntity<MessaggioResponse> accettaRichiesta(@PathVariable Long scambioId) {

    scambioService.accettaRichiesta(scambioId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Richiesta accettata con successo"));
  }

  @DeleteMapping("/rifiutaRichiesta/{scambioId}")
  public ResponseEntity<MessaggioResponse> rifiutaRichiesta(@PathVariable Long scambioId) {

    scambioService.rifiutaRichiesta(scambioId);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Richiesta rifiutata con successo"));
  }

  @GetMapping("/getOfferteConcluse/{userId}")
  public ResponseEntity<List<OffertaResponse>> getOfferteConcluse(@PathVariable Long userId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(scambioService.getOfferteConcluse(userId));
  }
}
