package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.request.LoginRequest;
import it.polimi.biblioteca.dto.request.RegisterRequest;
import it.polimi.biblioteca.dto.response.LoginResponse;
import it.polimi.biblioteca.exception.CustomException;
import it.polimi.biblioteca.model.Ruolo;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.UtenteRepository;
import it.polimi.biblioteca.security.service.JwtService;
import it.polimi.biblioteca.util.ControlloDati;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(@NonNull RegisterRequest request) {

        ControlloDati.controlloStringhe(List.of(request.getUsername(), request.getNome(), request.getEmail(), request.getPassword(), request.getTelefono(), request.getComunita()));

        Optional<Utente> userAlreadyRegistered = utenteRepository.findByUsername(request.getUsername());
        if(userAlreadyRegistered.isPresent()) {
            throw new CustomException("Username già registrato");
        }

        if(!isEmailValida(request.getEmail().trim().toLowerCase())) {
            throw new CustomException("Email non valida");
        }

        Utente utente = Utente.builder()
                .username(request.getUsername())
                .nome(request.getNome())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .password(passwordEncoder.encode(request.getPassword()))
                .comunita(request.getComunita())
                .notifica(false)
                .ruolo(Ruolo.USER)
                .build();

        utenteRepository.save(utente);

        Optional<Utente> userRegistered = utenteRepository.findByUsername(request.getUsername());

        if(userRegistered.isEmpty()) {
            throw new CustomException("Registrazione non riuscita");
        }
    }

    public LoginResponse login(@NonNull LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername().trim().toLowerCase(),
                        request.getPassword()
                )
        );

        Utente utente = utenteRepository.findByUsername(request.getUsername()).orElseThrow();

        return new LoginResponse(
                utente.getUserId(),
                utente.getUsername(),
                utente.getNome(),
                utente.getEmail(),
                utente.getTelefono(),
                utente.getComunita(),
                utente.isNotifica(),
                utente.getRuolo(),
                jwtService.generateToken(utente)
        );
    }

    public HttpHeaders putJwtInHttpHeaders(String jwt) {

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer: " + jwt);

        return headers;
    }

    private boolean isEmailValida(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    private boolean isPasswordValida(String password) {
        String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }
}
