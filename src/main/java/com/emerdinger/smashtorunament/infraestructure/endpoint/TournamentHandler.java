package com.emerdinger.smashtorunament.infraestructure.endpoint;

import com.emerdinger.smashtorunament.application.handler.TournamentManejador;
import com.emerdinger.smashtorunament.domain.model.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
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
                .flatMap(tournamentManejador::createTournament)
                .flatMap(tournament -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournament));
    }

    public Mono<ServerResponse> updateTournament(ServerRequest request) {
        return request.bodyToMono(Tournament.class)
                .flatMap(tournamentManejador::updateTournament)
                .flatMap(tournament -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournament));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return tournamentManejador.findById(serverRequest.pathVariable("tournamentId"))
                .flatMap(findTournament -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(findTournament));
    }

    public Mono<ServerResponse> findAll() {
        return tournamentManejador.findAll()
                .collectList()
                .flatMap(tournament -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournament));
    }
}
