package it.polimi.biblioteca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String nome;
    private String email;
    private String telefono;
    private String password;
    private String comunita;
}
