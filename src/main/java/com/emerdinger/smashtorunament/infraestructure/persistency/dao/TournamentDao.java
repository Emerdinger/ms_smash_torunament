package com.emerdinger.smashtorunament.infraestructure.persistency.dao;

import com.emerdinger.smashtorunament.infraestructure.persistency.entity.TournamentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentDao extends ReactiveMongoRepository<TournamentEntity, String> {
}
