package it.polimi.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LibroResponse {

  private Long libroId;
  private String titolo;
  private String autore;
  private Integer anno;
  private String descrizione;
  private String genere;
  private String usernameProprietario;
}
