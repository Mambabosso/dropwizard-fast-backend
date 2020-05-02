package com.github.mambabosso.dfb.validator;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class RegexValidator implements Validator<String> {

    private final String value;
    private final Pattern pattern;

    @Override
    public String get() {
        if (value == null) {
            throw new NullPointerException("value");
        }
        return value;
    }

    @Override
    public boolean isValid() {
        if (pattern == null) {
            throw new NullPointerException("pattern");
        }
        return pattern.matcher(get()).matches();
    }

}
