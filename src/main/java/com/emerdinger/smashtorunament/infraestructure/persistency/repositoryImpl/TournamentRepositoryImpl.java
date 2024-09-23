package com.emerdinger.smashtorunament.infraestructure.persistency.repositoryImpl;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.domain.model.TournamentRestrictions;
import com.emerdinger.smashtorunament.domain.repository.TournamentRepository;
import com.emerdinger.smashtorunament.infraestructure.persistency.dao.TournamentDao;
import com.emerdinger.smashtorunament.infraestructure.persistency.dao.TournamentRestrictionDao;
import com.emerdinger.smashtorunament.infraestructure.persistency.entity.TournamentEntity;
import com.emerdinger.smashtorunament.infraestructure.persistency.entity.TournamentRestrictionsEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TournamentRepositoryImpl implements TournamentRepository {

    private final TournamentDao tournamentDao;
    private final ObjectMapper objectMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final TournamentRestrictionDao tournamentRestrictionDao;

    @Override
    public Mono<Tournament> createTournament(Tournament tournament) {
        return tournamentDao.save(toTournamentEntity(tournament))
                .flatMap(tournamentSaved -> {
                    TournamentRestrictions restrictions = new TournamentRestrictions(tournamentSaved.getId(), false, false, false);
                    return tournamentRestrictionDao.save(objectMapper.convertValue(restrictions, TournamentRestrictionsEntity.class))
                            .thenReturn(toTournament(tournamentSaved));
                });
    }

    @Override
    public Mono<Tournament> updateTournament(Tournament tournament) {
        return tournamentDao.save(toTournamentEntity(tournament))
                .map(this::toTournament);
    }

    @Override
    public Mono<Void> deleteTournament(String id) {
        return tournamentDao.deleteById(id);
    }

    @Override
    public Mono<Tournament> findById(String id) {
        return tournamentDao.findById(id)
                .map(this::toTournament);
    }

    @Override
    public Mono<Tournament> findByIdAndOwner(String id, String ownerId) {
        return tournamentDao.findByIdAndOwner(id, ownerId)
                .map(this::toTournament);
    }

    @Override
    public Flux<Tournament> findAll() {
        return tournamentDao.findAll()
                .map(this::toTournament);
    }

    @Override
    public Flux<Tournament> findByFilters(Optional<String> status, Optional<Boolean> finished, Optional<String> owner, Optional<Boolean> open,
                                          Optional<Integer> qualifiedPlayersPerGroup, Optional<Boolean> needPassword, Optional<Integer> maxGroupPlayers,
                                          Optional<String> city) {
        Query query = new Query();

        status.ifPresent(s -> query.addCriteria(Criteria.where("status").is(s)));
        finished.ifPresent(f -> query.addCriteria(Criteria.where("finished").is(f)));
        owner.ifPresent(o -> query.addCriteria(Criteria.where("owner").is(o)));
        open.ifPresent(op -> query.addCriteria(Criteria.where("open").is(op)));
        qualifiedPlayersPerGroup.ifPresent(q -> query.addCriteria(Criteria.where("qualifiedPlayersPerGroup").is(q)));
        needPassword.ifPresent(np -> query.addCriteria(Criteria.where("needPassword").is(np)));
        maxGroupPlayers.ifPresent(mgp -> query.addCriteria(Criteria.where("maxGroupPlayers").is(mgp)));
        city.ifPresent(ct -> query.addCriteria(Criteria.where("city").is(ct)));

        return reactiveMongoTemplate.find(query, Tournament.class);
    }

    public Tournament toTournament(TournamentEntity tournamentEntity) {
        return objectMapper.convertValue(tournamentEntity, Tournament.class);
    }

    private TournamentEntity toTournamentEntity(Tournament tournament) {
        return objectMapper.convertValue(tournament, TournamentEntity.class);
    }

}
