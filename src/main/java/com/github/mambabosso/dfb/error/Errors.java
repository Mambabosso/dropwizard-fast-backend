package com.github.mambabosso.dfb.error;

import com.github.mambabosso.dfb.model.Models;

public enum Errors {

    UNKNOWN(Models.UNDEFINED, -1),

    UNKNOWN_PASSWORD_FAILURE(Models.PASSWORD, 1),
    UNKNOWN_ROLE_FAILURE(Models.ROLE, 2),
    UNKNOWN_TOKEN_FAILURE(Models.TOKEN, 3),
    UNKNOWN_USER_FAILURE(Models.USER, 4),

    INVALID_PASSWORD(Models.PASSWORD, 100),
    INVALID_ROLE(Models.ROLE, 200),
    INVALID_TOKEN(Models.TOKEN, 300),
    INVALID_USER(Models.USER, 400),

    PASSWORD_NOT_FOUND(Models.PASSWORD, 101),
    ROLE_NOT_FOUND(Models.ROLE, 201),
    TOKEN_NOT_FOUND(Models.TOKEN, 301),
    USER_NOT_FOUND(Models.USER, 401),

    PASSWORD_VALIDATION_FAILURE(Models.PASSWORD, 102),
    ROLE_VALIDATION_FAILURE(Models.ROLE, 202),
    TOKEN_VALIDATION_FAILURE(Models.TOKEN, 302),
    USER_VALIDATION_FAILURE(Models.USER, 402),

    PASSWORD_PERSISTENCE_FAILURE(Models.PASSWORD, 103),
    ROLE_PERSISTENCE_FAILURE(Models.ROLE, 203),
    TOKEN_PERSISTENCE_FAILURE(Models.TOKEN, 303),
    USER_PERSISTENCE_FAILURE(Models.USER, 403),

    ROLE_ALREADY_EXISTS(Models.ROLE, 204),
    USER_ALREADY_EXISTS(Models.USER, 404);

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
