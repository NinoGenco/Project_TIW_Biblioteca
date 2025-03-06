package it.polimi.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecensioneRequest {

  private String testo;
  private Integer voto;
  private Long userId;
  private Long scambioId;
}
