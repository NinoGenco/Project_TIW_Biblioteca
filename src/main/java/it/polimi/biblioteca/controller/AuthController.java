package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.request.LoginRequest;
import it.polimi.biblioteca.dto.request.RegisterRequest;
import it.polimi.biblioteca.dto.response.LoginResponse;
import it.polimi.biblioteca.dto.response.MessaggioResponse;
import it.polimi.biblioteca.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessaggioResponse> register(@RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessaggioResponse("Registrazione completata!"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {

        final LoginResponse response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(authService.putJwtInHttpHeaders(response.getJwt()))
                .body(response);
    }
}
