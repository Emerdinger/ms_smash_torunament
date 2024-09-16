package com.emerdinger.smashtorunament.infraestructure.persistency.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tournamentRestrictions")
public class TournamentRestrictionsEntity {
    @Id
    private String id;
    private String tournamentId;
    private boolean isNationalRestriction;
    private boolean isRegionRestriction;
    private boolean isTeamRestriction;
}
