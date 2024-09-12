package com.emerdinger.smashtorunament.infraestructure.persistency.repositoryImpl;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import com.emerdinger.smashtorunament.infraestructure.persistency.dao.TournamentDao;
import com.emerdinger.smashtorunament.infraestructure.persistency.entity.TournamentEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TournamentRepositoryImpl implements TournamentRepository {

    private final TournamentDao tournamentDao;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentDao.save(objectMapper.convertValue(tournament, TournamentEntity.class))
                .map(tournamentSaved -> objectMapper.convertValue(tournamentSaved, Tournament.class));
    }

    @Override
    public Mono<Tournament> updateTournament(Tournament tournament) {
        return tournamentDao.save(objectMapper.convertValue(tournament, TournamentEntity.class))
                .map(tournamentUpdated -> objectMapper.convertValue(tournamentUpdated, Tournament.class));
    }

    @Override
    public Mono<Tournament> findById(String id) {
        return tournamentDao.findById(id)
                .map(tournament -> objectMapper.convertValue(tournament, Tournament.class));
    }

    @Override
    public Flux<Tournament> findAll() {
        return tournamentDao.findAll()
                .map(tournament -> objectMapper.convertValue(tournament, Tournament.class));
    }
}
