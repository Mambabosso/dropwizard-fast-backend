package com.github.mambabosso.dfb.validator;

import java.util.regex.Pattern;

public final class Expressions {

    private Expressions() {
    }

    public static final Pattern PLAIN_PASSWORD = Pattern.compile("^(?![0-9_-])[a-zA-Z0-9_-]{2,30}$");
    public static final Pattern ROLE_NAME = Pattern.compile("^(?![0-9_-])[a-zA-Z0-9_-]{2,30}$");
    public static final Pattern USER_NAME = Pattern.compile("^(?![0-9])[a-zA-Z0-9]{4,30}$");

}
