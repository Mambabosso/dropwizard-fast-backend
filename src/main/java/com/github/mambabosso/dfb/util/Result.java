package com.github.mambabosso.dfb.util;

import com.github.mambabosso.dfb.error.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Optional;

@EqualsAndHashCode
public final class Result<T> implements Serializable {

    private boolean success;
    private T value;
    private ErrorCode error;

    private Result() {
    }

    public boolean isSuccess() {
        return success;
    }

    public T get() {
        return value;
    }

    public ErrorCode getError() {
        return error;
    }

    public Optional<T> optional() {
        if (success) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public <X> Result<X> byError() {
        if (success) {
            throw new IllegalStateException();
        }
        return Result.failure(error);
    }

    public static <T> Result<T> success(@NonNull final T value) {
        Result<T> result = new Result<>();
        result.success = true;
        result.value = value;
        return result;
    }

    public static <T> Result<T> failure(@NonNull final ErrorCode error) {
        Result<T> result = new Result<>();
        result.success = false;
        result.error = error;
        return result;
    }

}
