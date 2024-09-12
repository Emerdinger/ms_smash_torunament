package com.emerdinger.smashtorunament.helpers.errors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TournamentError {
    private String exception;
    private String message;
    private int code;

    public TournamentError(String exception, String message, int code) {
        this.exception = exception;
        this.message = message;
        this.code = code;
    }
}
