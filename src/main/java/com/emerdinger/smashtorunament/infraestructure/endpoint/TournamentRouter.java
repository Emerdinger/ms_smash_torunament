package com.emerdinger.smashtorunament.infraestructure.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TournamentRouter {

    @Bean
    public RouterFunction<ServerResponse> createRouteTournament(TournamentHandler tournamentHandler) {
        return route(GET("/health"), request -> tournamentHandler.health())
                .andRoute(POST("/basic-tournament").and(accept(MediaType.APPLICATION_JSON)), tournamentHandler::createTournament)
                .andRoute(PUT("/basic-tournament").and(accept(MediaType.APPLICATION_JSON)), tournamentHandler::updateTournament)
                .andRoute(GET("/basic-tournament/find/{tournamentId}").and(accept(MediaType.APPLICATION_JSON)), tournamentHandler::findById)
                .andRoute(GET("/basic-tournament/findAll").and(accept(MediaType.APPLICATION_JSON)), request -> tournamentHandler.findAll())
                .andRoute(DELETE("/basic-tournament/delete/{tournamentId}").and(accept(MediaType.APPLICATION_JSON)), tournamentHandler::deleteTournament);

    }
}
