package com.github.mambabosso.dfb.util;

import lombok.NonNull;

import java.util.function.Consumer;

public final class ConditionUtils {

    private ConditionUtils() {
    }

    public static <T> void ifNotNull(final T val, @NonNull final Consumer<T> consumer) {
        if (val != null) {
            consumer.accept(val);
        }
    }

}
