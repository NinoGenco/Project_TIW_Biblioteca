package it.polimi.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AggiungiLibroRequest {

  private String titolo;
  private String autore;
  private Integer anno;
  private String descrizione;
  private String genere;
}
