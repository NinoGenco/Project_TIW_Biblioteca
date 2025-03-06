package it.polimi.biblioteca.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatResponse {

  private String testo;
  private String usernameMittente;
  private String usernameDestinatario;
  private LocalDateTime dataInvio;
}
