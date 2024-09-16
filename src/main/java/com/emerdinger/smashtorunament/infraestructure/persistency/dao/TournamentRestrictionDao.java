package com.emerdinger.smashtorunament.infraestructure.persistency.dao;

import com.emerdinger.smashtorunament.infraestructure.persistency.entity.TournamentRestrictionsEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TournamentRestrictionDao extends ReactiveMongoRepository<TournamentRestrictionsEntity, String> {
}
