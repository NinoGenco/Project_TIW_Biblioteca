package it.polimi.biblioteca.service;

import it.polimi.biblioteca.dto.request.ChatRequest;
import it.polimi.biblioteca.dto.response.ChatResponse;
import it.polimi.biblioteca.exception.CustomException;
import it.polimi.biblioteca.model.Messaggio;
import it.polimi.biblioteca.model.Utente;
import it.polimi.biblioteca.repository.MessaggioRepository;
import it.polimi.biblioteca.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessaggioService {

    private final MessaggioRepository messaggioRepository;
    private final UtenteRepository utenteRepository;

    public ChatResponse invia(ChatRequest request) {

        if(request.getMessaggio().isEmpty() || request.getMessaggio().isBlank() || request.getMittenteId().isBlank() ||
                request.getUsernameDestinatario().isBlank() || request.getUsernameDestinatario().isEmpty() ||
                request.getMittenteId().isEmpty()) {
            throw new CustomException("Inserire i campi correttamente");
        }

        Optional<Utente> mittente = utenteRepository.findById(Long.parseLong(request.getMittenteId()));

        Optional<Utente> destinatario = utenteRepository.findByUsername(request.getUsernameDestinatario());

        if(mittente.isEmpty() || destinatario.isEmpty()) {
            throw new CustomException("Utente non trovato");
        }

        Messaggio messaggio = Messaggio.builder()
                .testo(request.getMessaggio())
                .dataInvio(LocalDateTime.now())
                .mittente(mittente.get())
                .destinatario(destinatario.get())
                .build();

        messaggioRepository.save(messaggio);

        return new ChatResponse(
                messaggio.getTesto(),
                messaggio.getMittente().getUsername(),
                messaggio.getDestinatario().getUsername(),
                messaggio.getDataInvio()
        );
    }

    public List<ChatResponse> get(ChatRequest request) {

        if(request.getMittenteId().isBlank() || request.getMittenteId().isEmpty() ||
                request.getUsernameDestinatario().isBlank() || request.getUsernameDestinatario().isEmpty()) {
            throw new CustomException("Inserire i campi correttamente");
        }

        Optional<Utente> mittente = utenteRepository.findById(Long.parseLong(request.getMittenteId()));

        Optional<Utente> destinatario = utenteRepository.findByUsername(request.getUsernameDestinatario());

        if(mittente.isEmpty() || destinatario.isEmpty()) {
            throw new CustomException("Utente non trovato");
        }

        List<Messaggio> messaggi = messaggioRepository.findAll();

        List<ChatResponse> response = new ArrayList<>();

        for(Messaggio m : messaggi) {
            if((Objects.equals(m.getMittente().getUserId(), mittente.get().getUserId()) &&
              Objects.equals(m.getDestinatario().getUserId(), destinatario.get().getUserId())
                    || (Objects.equals(m.getMittente().getUserId(), destinatario.get().getUserId()) &&
              Objects.equals(m.getDestinatario().getUserId(), mittente.get().getUserId())))){
                response.add(new ChatResponse(
                        m.getTesto(),
                        m.getMittente().getUsername(),
                        m.getDestinatario().getUsername(),
                        m.getDataInvio()
                ));
            }
        }

        return response;
    }
}
