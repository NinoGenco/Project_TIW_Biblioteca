package it.polimi.biblioteca.controller;

import it.polimi.biblioteca.dto.request.ChatRequest;
import it.polimi.biblioteca.service.MessaggioService;
import it.polimi.biblioteca.dto.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messaggio")
public class MessaggioController {

    private final MessaggioService messaggioService;

    @PostMapping("/invia")
    public ResponseEntity<ChatResponse> invia(@RequestBody ChatRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messaggioService.invia(request));
    }

    @PostMapping("/get")
    public ResponseEntity<List<ChatResponse>> get(@RequestBody ChatRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messaggioService.get(request));
    }
}
