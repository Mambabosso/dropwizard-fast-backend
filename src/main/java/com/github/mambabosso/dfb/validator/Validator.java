package com.github.mambabosso.dfb.validator;

public interface Validator<T> {

    public default boolean validate() {
        return get() != null;
    }

    public T get();

}
