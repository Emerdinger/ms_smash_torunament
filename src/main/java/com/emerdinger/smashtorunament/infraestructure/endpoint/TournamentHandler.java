package com.emerdinger.smashtorunament.infraestructure.endpoint;

import com.emerdinger.smashtorunament.application.handler.TournamentManejador;
import com.emerdinger.smashtorunament.domain.model.Tournament;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

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

    public Mono<ServerResponse> deleteTournament(ServerRequest request) {
        return tournamentManejador.deleteTournament(request.pathVariable("tournamentId"))
                .then(ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("Deleted"));
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

    public Mono<ServerResponse> findByFilters(ServerRequest request) {
        // Extraer parámetros de consulta
        Optional<String> status = request.queryParam("status");
        Optional<Boolean> finished = request.queryParam("finished")
                .map(Boolean::parseBoolean);
        Optional<String> owner = request.queryParam("owner");
        Optional<Boolean> open = request.queryParam("open")
                .map(Boolean::parseBoolean);
        Optional<Integer> qualifiedPlayersPerGroup = request.queryParam("qualifiedPlayersPerGroup")
                .map(Integer::parseInt);
        Optional<Boolean> needPassword = request.queryParam("needPassword")
                .map(Boolean::parseBoolean);
        Optional<Integer> maxGroupPlayers = request.queryParam("maxGroupPlayers")
                .map(Integer::parseInt);
        Optional<String> city = request.queryParam("city");

        return tournamentManejador.findByFilters(status, finished, owner, open, qualifiedPlayersPerGroup, needPassword, maxGroupPlayers, city)
                .collectList()
                .flatMap(tournaments -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournaments));
    }

    public Mono<ServerResponse> updateStatus(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Map.class)
                .flatMap(body -> ServerResponse.status(200).
                        contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournamentManejador.updateStatus(body.get("id").toString(), body.get("status").toString())));
    }
}
