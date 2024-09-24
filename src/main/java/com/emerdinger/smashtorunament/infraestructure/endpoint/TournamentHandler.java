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

    private static final String TOURNAMENT_ID = "tournamentId";
    private static final String USER_ID = "userId";
    private static final String ID = "id";
    private static final String STATUS = "status";

    public Mono<ServerResponse> health() {
        return ServerResponse.ok().bodyValue("Ok");
    }

    public Mono<ServerResponse> createTournament(ServerRequest request) {
        return request.bodyToMono(Tournament.class)
                .flatMap(tournament -> {
                    tournament.setOwner((String) request.attributes().get(USER_ID));
                    return tournamentManejador.createTournament(tournament);
                })
                .flatMap(tournament -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournament));
    }

    public Mono<ServerResponse> updateTournament(ServerRequest request) {
        return request.bodyToMono(Tournament.class)
                .flatMap(tournament -> {
                    tournament.setOwner((String) request.attributes().get(USER_ID));
                    return tournamentManejador.updateTournament(tournament);
                })
                .flatMap(tournament -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournament));
    }

    public Mono<ServerResponse> deleteTournament(ServerRequest request) {
        return tournamentManejador.deleteTournament(request.pathVariable(TOURNAMENT_ID), request.attributes().get(USER_ID).toString())
                .then(ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("Deleted"));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return tournamentManejador.findById(serverRequest.pathVariable(TOURNAMENT_ID))
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
                .flatMap(body -> tournamentManejador.updateStatus((String) body.get(ID), (String) body.get(STATUS),
                        (String) serverRequest.attributes().get(USER_ID)))
                .flatMap(tournamentUpdated -> ServerResponse.status(200)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(tournamentUpdated));
    }
}
