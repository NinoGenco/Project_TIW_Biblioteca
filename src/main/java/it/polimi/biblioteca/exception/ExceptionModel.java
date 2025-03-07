package it.polimi.biblioteca.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ExceptionModel {

    private String message;

    private HttpStatus status;
}
