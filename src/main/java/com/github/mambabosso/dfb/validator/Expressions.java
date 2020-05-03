package com.github.mambabosso.dfb.validator;

import lombok.NonNull;

import java.util.regex.Pattern;

public enum Expressions {

    PLAIN_PASSWORD("^[a-zA-Z0-9_+-.,;*#]{6,60}$"),
    ROLE_NAME("^(?![0-9_-])[a-zA-Z0-9_-]{2,30}$"),
    USER_NAME("^(?![0-9])[a-zA-Z0-9]{4,30}$");

    private final String regex;

    private Expressions(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public boolean matches(@NonNull String str) {
        return Pattern.compile(regex).matcher(str).matches();
    }

    public RegexValidator validator(@NonNull String str) {
        return new RegexValidator(str, Pattern.compile(regex));
    }

}
