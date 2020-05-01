package com.github.mambabosso.dfb.validator;

public interface Validator<T> {

    public T get();

    public default boolean isValid() {
        return true;
    }

}
