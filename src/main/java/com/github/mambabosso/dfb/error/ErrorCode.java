package com.github.mambabosso.dfb.error;

import lombok.Data;

import java.io.Serializable;

@Data
public final class ErrorCode implements Serializable {

    private final int code;

    private ErrorCode(final int code) {
        this.code = code;
    }

    public static ErrorCode create(final int code) {
        return new ErrorCode(code);
    }

}
