package it.polimi.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UtenteResponse {

  private Long userId;
  private String username;
  private String nome;
  private String email;
  private String telefono;
  private String comunita;
  private boolean isNotifica;
  @Setter
  private List<String> generiSelezionati;
}
