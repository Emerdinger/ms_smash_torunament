package com.emerdinger.smashtorunament.domain.useCase;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import com.emerdinger.smashtorunament.helpers.errors.NotFoundError;
import com.emerdinger.smashtorunament.helpers.validators.TournamentValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
                        .map(tour -> {
                            if (!tour.getOwner().equals(tournament.getOwner())) {
                                throw new NotFoundError("This tournament is not yours");
                            } else {
                                return tournament;
                            }
                        })
                        .flatMap(tournamentRepository::updateTournament));
    }

    public Mono<Void> deleteTournament(String id, String userId) {
        return tournamentRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("Tournament not found"))))
                .map(tournament -> {
                    if (!tournament.getOwner().equals(userId)) {
                        throw new NotFoundError("This tournament is not yours");
                    } else {
                        return id;
                    }
                })
                .flatMap(tournamentRepository::deleteTournament);
    }

    public Mono<Tournament> findById(String id) {
        return tournamentRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("Tournament not found"))));
    }

    public Flux<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public Flux<Tournament> findByFilters(Optional<String> status, Optional<Boolean> finished, Optional<String> owner, Optional<Boolean> open,
                                          Optional<Integer> qualifiedPlayersPerGroup, Optional<Boolean> needPassword, Optional<Integer> maxGroupPlayers,
                                          Optional<String> city) {
        return tournamentRepository.findByFilters(status, finished, owner, open, qualifiedPlayersPerGroup, needPassword, maxGroupPlayers, city);
    }

    public Mono<Tournament> updateStatus(String id, String status, String userId) {
        return TournamentValidator.validUpdateStatus(id, status)
                        .then(tournamentRepository.findByIdAndOwner(id, userId)
                                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundError("This tournament is not yours"))))
                                .flatMap(tournament -> {
                                    tournament.setStatus(status);
                                    return tournamentRepository.updateTournament(tournament);
                                }));
    }

}
