package com.emerdinger.smashtorunament.domain.useCase;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import com.emerdinger.smashtorunament.helpers.errors.NotFoundError;
import com.emerdinger.smashtorunament.helpers.validators.TournamentValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public Mono<Tournament> createTournament(Tournament tournament) {
        return TournamentValidator.validateTournament(tournament, "create")
                .flatMap(validTournament -> tournamentRepository.createTournament(tournament));
    }

    public Mono<Tournament> updateTournament(Tournament tournament) {
        return TournamentValidator.validateTournament(tournament, "update")
                .flatMap(validTournament -> tournamentRepository.findById(tournament.getId())
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("Tournament not found"))))
                        .map(tour -> tournament)
                        .flatMap(tournamentRepository::updateTournament));
    }

    public Mono<Void> deleteTournament(String id) {
        return tournamentRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("Tournament not found"))))
                .map(tour -> id)
                .flatMap(tournamentRepository::deleteTournament);
    }

    public Mono<Tournament> findById(String id) {
        return tournamentRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("Tournament not found"))));
    }

    public Flux<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

}
