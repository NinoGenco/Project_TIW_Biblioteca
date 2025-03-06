package it.polimi.biblioteca.dto.response;

import it.polimi.biblioteca.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long userId;
    private String username;
    private String nome;
    private String email;
    private String telefono;
    private String comunita;
    private boolean notifica;
    private Ruolo ruolo;
    private String jwt;
}
