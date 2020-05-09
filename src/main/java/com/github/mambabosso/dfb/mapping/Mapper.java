package com.github.mambabosso.dfb.mapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

import java.util.Map;

public final class Mapper {

    private Mapper() {
    }

    public static <T> Map<String, Object> toMap(@NonNull final T obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> T fromMap(@NonNull final Map<String, Object> map, @NonNull final Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, tClass);
    }

}
