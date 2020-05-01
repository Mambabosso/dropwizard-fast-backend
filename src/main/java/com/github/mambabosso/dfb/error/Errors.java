package com.github.mambabosso.dfb.error;

import com.github.mambabosso.dfb.model.Models;

public enum Errors {

    UNKNOWN(Models.UNDEFINED, -1),

    UNKNOWN_PASSWORD_FAILURE(Models.PASSWORD, 1),
    UNKNOWN_ROLE_FAILURE(Models.ROLE, 2),
    UNKNOWN_USER_FAILURE(Models.USER, 3),

    INVALID_PASSWORD(Models.PASSWORD, 100),
    INVALID_ROLE(Models.ROLE, 200),
    INVALID_USER(Models.USER, 300),

    PASSWORD_NOT_FOUND(Models.PASSWORD, 101),
    ROLE_NOT_FOUND(Models.ROLE, 201),
    USER_NOT_FOUND(Models.USER, 301),

    PASSWORD_VALIDATION_FAILURE(Models.PASSWORD, 102),
    ROLE_VALIDATION_FAILURE(Models.ROLE, 202),
    USER_VALIDATION_FAILURE(Models.USER, 302),

    PASSWORD_PERSISTENCE_FAILURE(Models.PASSWORD, 103),
    ROLE_PERSISTENCE_FAILURE(Models.ROLE, 203),
    USER_PERSISTENCE_FAILURE(Models.USER, 303),

    ROLE_ALREADY_EXISTS(Models.ROLE, 204),
    USER_ALREADY_EXISTS(Models.USER, 304);

    private final Models model;
    private final int code;

    private Errors(Models model, int code) {
        this.model = model;
        this.code = code;
    }

    public Models getModel() {
        return model;
    }

    public int getCode() {
        return code;
    }

    public ErrorCode get(Exception ex) {
        return ErrorCode.create(model, code, ex);
    }

    public ErrorCode get() {
        return get(null);
    }
    
}
