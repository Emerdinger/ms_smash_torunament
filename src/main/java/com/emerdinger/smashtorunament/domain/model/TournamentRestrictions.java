package com.emerdinger.smashtorunament.domain.model;

import com.emerdinger.smashtorunament.helpers.persistency.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentRestrictions extends Model<String> {
    private String tournamentId;
    private boolean isNationalRestriction;
    private boolean isRegionRestriction;
    private boolean isTeamRestriction;
}
