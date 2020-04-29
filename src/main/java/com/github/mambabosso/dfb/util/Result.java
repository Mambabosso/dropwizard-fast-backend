package com.github.mambabosso.dfb.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mambabosso.dfb.error.ErrorCode;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Optional;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Result<T> implements Serializable {

    @JsonProperty
    private boolean success;

    @JsonProperty
    private T value;

    @JsonProperty
    private ErrorCode error;

    private Result() {
    }

    public Optional<T> optional() {
        if (success) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static <T> Result<T> success(@NonNull final T value) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setValue(value);
        return result;
    }

    public static <T> Result<T> failure(@NonNull final ErrorCode error) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setError(error);
        return result;
    }

}
