package it.polimi.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecensioneResponse {

  private String usernameUtente;
  private String testo;
  private Integer voto;
}
