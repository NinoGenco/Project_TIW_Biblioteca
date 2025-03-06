package it.polimi.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScambioRequest {

  private Long proprietarioId;
  private Long libroId;
  private String modalitaScambio;
  private Long libroIdRichiedente;
}
