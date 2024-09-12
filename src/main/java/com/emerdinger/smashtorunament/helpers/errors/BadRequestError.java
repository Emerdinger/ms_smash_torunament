package com.emerdinger.smashtorunament.helpers.errors;

public class BadRequestError extends RuntimeException{
    public BadRequestError(String msg) {super(msg);}
}
