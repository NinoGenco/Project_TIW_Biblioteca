package it.polimi.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ModificaUtenteRequest {

  private String username;
  private String nome;
  private String email;
  private String telefono;
  private String vecchiaPassword;
  private String nuovaPassword;
  private String comunita;
  private boolean isNotifica;
  private List<String> generiSelezionati;
}
