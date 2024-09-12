package com.emerdinger.smashtorunament.helpers.validators;

import com.emerdinger.smashtorunament.domain.model.Tournament;
import com.emerdinger.smashtorunament.helpers.errors.BadRequestError;
import reactor.core.publisher.Mono;

public class TournamentValidator {
    public static Mono<Tournament> validateTournament(Tournament tournament, String type) {
        if (type.equals("update")) {
            if (tournament.getId() == null || tournament.getId().isEmpty()) {
                return Mono.error(new BadRequestError("id field are null or empty"));
            }
        }

        if (tournament.getNeedPassword() == null) {
            return Mono.error(new BadRequestError("Need Password field are null"));
        }

        if (tournament.getName() == null || tournament.getName().isEmpty()) {
            return Mono.error(new BadRequestError("Name field are null or empty"));
        }


        if (tournament.getNeedPassword().equals(true)) {
            if(tournament.getPassword() == null || tournament.getPassword().isEmpty()) {
                return Mono.error(new BadRequestError("Set a password"));
            }
        } else {
            if(tournament.getPassword() == null) {
                return Mono.error(new BadRequestError("Password field are null or empty"));
            }
        }

        if (tournament.getDescription() == null || tournament.getDescription().isEmpty()) {
            return Mono.error(new BadRequestError("Description field are null or empty"));
        }

        if (tournament.getOwner() == null || tournament.getOwner().isEmpty()) {
            return Mono.error(new BadRequestError("Owner field are null or empty"));
        }

        if (tournament.getStatus() == null || tournament.getStatus().isEmpty()) {
            return Mono.error(new BadRequestError("Status field are null or empty"));
        }

        if (tournament.getGameDate() == null || tournament.getGameDate().isEmpty()) {
            return Mono.error(new BadRequestError("Game Date field are null or empty"));
        }

        if (tournament.getOpen() == null) {
            return Mono.error(new BadRequestError("Open field are null"));
        }

        if (tournament.getMaxGroupPlayers() == null || tournament.getMaxGroupPlayers().equals(0)) {
            return Mono.error(new BadRequestError("Max Group Players cannot be null or equal to 0"));
        }

        if (tournament.getQualifiedPlayersPerGroup() == null || tournament.getQualifiedPlayersPerGroup().equals(0)) {
            return Mono.error(new BadRequestError("Qualified Players Per Group cannot be null or equal to 0"));
        }

        if (tournament.getFinished() == null) {
            return Mono.error(new BadRequestError("Finished field are null"));
        }

        return Mono.just(tournament);
    }

}
