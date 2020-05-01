package com.github.mambabosso.dfb.error;

import com.github.mambabosso.dfb.model.Models;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public final class ErrorCode implements Serializable {

    private final Models model;
    private final int code;

    private Exception exception;

    private ErrorCode(final Models model, final int code, final Exception exception) {
        this.model = model;
        this.code = code;
        this.exception = exception;
    }

    public ErrorCode setException(@NonNull final Exception exception) {
        this.exception = exception;
        return this;
    }

    public static ErrorCode create(final Models model, final int code, final Exception exception) {
        if (code == 0) {
            throw new IllegalArgumentException("code can not be zero");
        }
        return new ErrorCode(model, code, exception);
    }

    public static ErrorCode create(final Models model, final int code) {
        return create(model, code, null);
    }

}
