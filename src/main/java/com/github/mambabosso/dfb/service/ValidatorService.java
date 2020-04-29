package com.github.mambabosso.dfb.service;

import java.util.regex.Pattern;

public final class ValidatorService {

    private static final String USER_NAME_REGEX = "^(?![0-9])[a-zA-Z0-9]{4,30}$";
    private static final String USER_PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,;])(?=\\S+$).{6,}$";

    private static final String ROLE_NAME_REGEX = "^(?![0-9_-])[a-zA-Z0-9_-]{2,30}$";

    private ValidatorService() {
    }

    public static boolean isValidUserName(final String name) {
        Pattern pattern = Pattern.compile(USER_NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    public static boolean isValidUserPassword(final String password) {
        Pattern pattern = Pattern.compile(USER_PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

    public static boolean isValidRoleName(final String name) {
        Pattern pattern = Pattern.compile(ROLE_NAME_REGEX);
        return pattern.matcher(name).matches();
    }

}
