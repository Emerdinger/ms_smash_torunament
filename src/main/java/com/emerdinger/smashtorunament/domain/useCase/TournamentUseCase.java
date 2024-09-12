package com.emerdinger.smashtorunament.domain.useCase;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentRepository.createTournament(tournament);
    }
}
