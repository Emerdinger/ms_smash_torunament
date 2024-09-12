package com.emerdinger.smashtorunament.domain.model;

import com.emerdinger.smashtorunament.helpers.persistency.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tournament extends Model<String> {
    private String name;
    private String description;
    private boolean open;
    private String password;
    private String owner;
    private String status;
    private String gameDate;
    private int maxGroupPlayers;
    private int qualifiedPlayersPerGroup;
    private boolean finished;
}
