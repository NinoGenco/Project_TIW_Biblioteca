package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.request.RecensioneRequest;
import it.polimi.biblioteca.dto.response.MessaggioResponse;
import it.polimi.biblioteca.dto.response.RecensioneResponse;
import it.polimi.biblioteca.service.RecensioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recensione")
public class RecensioneController {

  private final RecensioneService recensioneService;

  @PostMapping("/invia")
  public ResponseEntity<MessaggioResponse> invia(@RequestBody RecensioneRequest request) {

    recensioneService.invia(request);

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(new MessaggioResponse("Recensione inviata con successo"));
  }

  @GetMapping("/getByScambio/{scambioId}")
  public ResponseEntity<List<RecensioneResponse>> getByScambio(@PathVariable Long scambioId) {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(recensioneService.getByScambio(scambioId));
  }
}
