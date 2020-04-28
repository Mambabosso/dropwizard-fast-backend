package com.github.mambabosso.dropwizard.fast.backend.service;

import lombok.NonNull;

import java.util.regex.Pattern;

public final class ValidatorService {

    private static final String NAME_REGEX = "^@[a-zA-Z0-9]{4,20}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,;])(?=\\S+$).{6,}$";

    private ValidatorService() {
    }

    public static boolean isValidName(@NonNull final String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidPassword(@NonNull final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

}
