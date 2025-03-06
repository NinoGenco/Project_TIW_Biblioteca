package it.polimi.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRequest {

    private String messaggio;
    private String mittenteId;
    private String usernameDestinatario;
}
