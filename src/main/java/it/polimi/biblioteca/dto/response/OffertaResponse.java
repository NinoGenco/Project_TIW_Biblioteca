package it.polimi.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OffertaResponse {

  private Long scambioId;
  private String modalita;
  private String tipoOfferta;
  private String titoloLibro;
  private String autoreLibro;
  private String titoloLibroRichiedente;
  private String autoreLibroRichiedente;
}
