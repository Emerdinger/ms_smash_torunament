package com.emerdinger.smashtorunament.infraestructure.endpoint;

import com.emerdinger.smashtorunament.application.handler.TournamentManejador;
import com.emerdinger.smashtorunament.domain.model.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TournamentHandler {

    private final TournamentManejador tournamentManejador;

    public Mono<ServerResponse> health() {
        return ServerResponse.ok().bodyValue("Ok");
    }

    public Mono<ServerResponse> createTournament(ServerRequest request) {
        return request.bodyToMono(Tournament.class)
                .doOnError(error -> System.err.println("Error de decodificación: " + error.getMessage()))
                .flatMap(tournamentManejador::createTournament)
                .flatMap(tournament -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournament));
    }
}
