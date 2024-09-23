package com.emerdinger.smashtorunament.helpers.errors;

import lombok.Getter;

@Getter
public class AuthApiError extends RuntimeException{
    private final Integer code;
    public AuthApiError(String msg, Integer code) {super(msg); this.code=code; }
}
