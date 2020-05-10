package com.github.mambabosso.dfb.util;

import lombok.NonNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static List<?> objectToList(@NonNull Object obj) {
        List<?> result = null;
        if (obj.getClass().isArray()) {
            result = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            result = new ArrayList<>((Collection<?>)obj);
        }
        return result;
    }

    public static <T> List<T> streamToList(@NonNull Stream<?> stream, @NonNull Function<Object, T> converter) {
        return stream.map(converter).collect(Collectors.toList());
    }

    public static <T> Set<T> streamToSet(@NonNull Stream<?> stream, @NonNull Function<Object, T> converter) {
        return stream.map(converter).collect(Collectors.toSet());
    }

}
