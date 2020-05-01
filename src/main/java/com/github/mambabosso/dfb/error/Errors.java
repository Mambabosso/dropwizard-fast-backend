package com.github.mambabosso.dfb.error;

import com.github.mambabosso.dfb.model.Models;

public final class Errors {

    private Errors() {
    }

    public static Errors newInstance() {
        return new Errors();
    }

    public final ErrorCode UNKNOWN = ErrorCode.create(Models.UNDEFINED, -1);

    public final ErrorCode UNKNOWN_PASSWORD_FAILURE = ErrorCode.create(Models.PASSWORD, 1);
    public final ErrorCode UNKNOWN_ROLE_FAILURE = ErrorCode.create(Models.ROLE, 2);
    public final ErrorCode UNKNOWN_USER_FAILURE = ErrorCode.create(Models.USER, 3);

    public final ErrorCode INVALID_PASSWORD = ErrorCode.create(Models.PASSWORD, 100);
    public final ErrorCode INVALID_ROLE = ErrorCode.create(Models.ROLE, 200);
    public final ErrorCode INVALID_USER = ErrorCode.create(Models.USER, 300);

    public final ErrorCode PASSWORD_NOT_FOUND = ErrorCode.create(Models.PASSWORD, 101);
    public final ErrorCode ROLE_NOT_FOUND = ErrorCode.create(Models.ROLE, 201);
    public final ErrorCode USER_NOT_FOUND = ErrorCode.create(Models.USER, 301);

    public final ErrorCode PASSWORD_VALIDATION_FAILURE = ErrorCode.create(Models.PASSWORD, 102);
    public final ErrorCode ROLE_VALIDATION_FAILURE = ErrorCode.create(Models.ROLE, 202);
    public final ErrorCode USER_VALIDATION_FAILURE = ErrorCode.create(Models.USER, 302);

    public final ErrorCode PASSWORD_PERSISTENCE_FAILURE = ErrorCode.create(Models.PASSWORD, 103);
    public final ErrorCode ROLE_PERSISTENCE_FAILURE = ErrorCode.create(Models.ROLE, 203);
    public final ErrorCode USER_PERSISTENCE_FAILURE = ErrorCode.create(Models.USER, 303);

    public final ErrorCode ROLE_ALREADY_EXISTS = ErrorCode.create(Models.ROLE, 204);
    public final ErrorCode USER_ALREADY_EXISTS = ErrorCode.create(Models.USER, 304);
    
}
