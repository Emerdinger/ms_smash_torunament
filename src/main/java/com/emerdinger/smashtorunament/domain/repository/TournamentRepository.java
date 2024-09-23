package com.emerdinger.smashtorunament.domain.repository;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface TournamentRepository {
    Mono<Tournament> createTournament(Tournament tournament);
    Mono<Tournament> updateTournament(Tournament tournament);
    Mono<Void> deleteTournament(String id);
    Mono<Tournament> findById(String id);
    Flux<Tournament> findAll();
    Flux<Tournament> findByFilters(Optional<String> status, Optional<Boolean> finished, Optional<String> owner, Optional<Boolean> open,
                                   Optional<Integer> qualifiedPlayersPerGroup, Optional<Boolean> needPassword, Optional<Integer> maxGroupPlayers,
                                   Optional<String> city);
    Mono<Tournament> updateStatus(String id, String status);
}
