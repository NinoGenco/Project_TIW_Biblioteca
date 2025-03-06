package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.response.GenereResponse;
import it.polimi.biblioteca.service.GenereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genere")
public class GenereController {

  private final GenereService genereService;

  @GetMapping("/getAll")
  public ResponseEntity<List<GenereResponse>> getAll() {

    return ResponseEntity
      .status(HttpStatus.OK)
      .body(genereService.getAll());
  }
}
