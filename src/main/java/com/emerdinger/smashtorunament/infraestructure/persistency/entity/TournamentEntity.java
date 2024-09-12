package com.emerdinger.smashtorunament.infraestructure.persistency.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document(collection = "tournament")
@NoArgsConstructor
@AllArgsConstructor
public class TournamentEntity {
    @Id
    private String id;
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
