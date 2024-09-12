package com.emerdinger.smashtorunament.domain.repository;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TournamentRepository {
    Mono<Tournament> createTournament(Tournament tournament);
    Mono<Tournament> updateTournament(Tournament tournament);
    Mono<Tournament> findById(String id);
    Flux<Tournament> findAll();
}
