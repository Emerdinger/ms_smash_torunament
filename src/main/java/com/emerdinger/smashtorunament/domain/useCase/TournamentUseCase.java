package com.emerdinger.smashtorunament.domain.useCase;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import com.emerdinger.smashtorunament.helpers.errors.NotFoundError;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentRepository.createTournament(tournament);
    }

    public Mono<Tournament> updateTournament(Tournament tournament) {
        return tournamentRepository.findById(tournament.getId())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("Tournament not found"))))
                .map(tour -> tournament)
                .flatMap(tournamentRepository::updateTournament);
    }
}
