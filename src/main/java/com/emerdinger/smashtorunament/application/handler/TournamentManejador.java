package com.emerdinger.smashtorunament.application.handler;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.useCase.TournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TournamentManejador {

    private final TournamentUseCase tournamentUseCase;

    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentUseCase.createTournament(tournament);
    }

    public Mono<Tournament> updateTournament(Tournament tournament) {
        return tournamentUseCase.updateTournament(tournament);
    }

    public Mono<Tournament> findById(String id) {
        return tournamentUseCase.findById(id);
    }

    public Flux<Tournament> findAll() {
        return tournamentUseCase.findAll();
    }
}
