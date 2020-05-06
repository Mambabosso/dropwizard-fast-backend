package com.github.mambabosso.dfb.validator;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class RegexValidator implements Validator<String> {

    private final String value;
    private final String regex;

    @Override
    public String get() {
        if (value == null) {
            throw new NullPointerException("value");
        }
        return value;
    }

    @Override
    public boolean isValid() {
        if (regex == null) {
            throw new NullPointerException("regex");
        }
        return Pattern.compile(regex).matcher(get()).matches();
    }

}
