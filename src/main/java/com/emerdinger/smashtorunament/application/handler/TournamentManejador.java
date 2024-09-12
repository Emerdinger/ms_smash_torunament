package com.emerdinger.smashtorunament.application.handler;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.useCase.TournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TournamentManejador {

    private final TournamentUseCase tournamentUseCase;

    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentUseCase.createTournament(tournament);
    }
}
