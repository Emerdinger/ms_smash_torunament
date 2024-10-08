package com.emerdinger.smashtorunament.domain.model;

import com.emerdinger.smashtorunament.helpers.persistency.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tournament extends Model<String> {
    private String name;
    private String description;
    private Boolean open;
    private String password;
    private String owner;
    private String status;
    private String gameDate;
    private Integer maxGroupPlayers;
    private Integer qualifiedPlayersPerGroup;
    private Boolean finished;
    private Boolean needPassword;
    private String city;
}
