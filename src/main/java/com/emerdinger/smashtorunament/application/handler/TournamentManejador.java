package com.emerdinger.smashtorunament.application.handler;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.useCase.TournamentUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
public class TournamentManejador {

    private final TournamentUseCase tournamentUseCase;

    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentUseCase.createTournament(tournament);
    }

    public Mono<Tournament> updateTournament(Tournament tournament) {
        return tournamentUseCase.updateTournament(tournament);
    }

    public Mono<Void> deleteTournament(String id, String userId) {
        return tournamentUseCase.deleteTournament(id, userId);
    }

    public Mono<Tournament> findById(String id) {
        return tournamentUseCase.findById(id);
    }

    public Flux<Tournament> findAll() {
        return tournamentUseCase.findAll();
    }

    public Flux<Tournament> findByFilters(Optional<String> status, Optional<Boolean> finished, Optional<String> owner, Optional<Boolean> open,
                                          Optional<Integer> qualifiedPlayersPerGroup, Optional<Boolean> needPassword, Optional<Integer> maxGroupPlayers,
                                          Optional<String> city) {
        return tournamentUseCase.findByFilters(status, finished, owner, open, qualifiedPlayersPerGroup, needPassword, maxGroupPlayers, city);
    }

    public Mono<Tournament> updateStatus(String id, String status, String userId){
        return tournamentUseCase.updateStatus(id, status, userId);
    }
}
