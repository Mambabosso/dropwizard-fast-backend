package com.github.mambabosso.dfb.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class RegexValidator implements Validator<String>, Serializable {

    @Getter
    private String value;

    @Getter
    private String regex;

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
