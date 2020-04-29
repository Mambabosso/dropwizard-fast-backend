package com.github.mambabosso.dfb.error;

public final class Errors {

    private Errors() {
    }

    public static final ErrorCode UNKNOWN = ErrorCode.create(0);
    public static final ErrorCode INVALID_INPUT = ErrorCode.create(1);

    // invalid start
    public static final ErrorCode INVALID_USER = ErrorCode.create(1011);
    public static final ErrorCode INVALID_USER_NAME = ErrorCode.create(1012);
    public static final ErrorCode INVALID_USER_PASSWORD = ErrorCode.create(1013);

    public static final ErrorCode INVALID_ROLE = ErrorCode.create(1021);
    public static final ErrorCode INVALID_ROLE_NAME = ErrorCode.create(1022);
    // invalid end

    // not found start
    public static final ErrorCode USER_NOT_FOUND = ErrorCode.create(2011);

    public static final ErrorCode ROLE_NOT_FOUND = ErrorCode.create(2021);
    // not found end

    // validation fail start
    public static final ErrorCode USER_NAME_VALIDATION_FAIL = ErrorCode.create(3011);
    public static final ErrorCode USER_PASSWORD_VALIDATION_FAIL = ErrorCode.create(3012);

    public static final ErrorCode ROLE_NAME_VALIDATION_FAIL = ErrorCode.create(3021);
    // validation fail end

    // already taken start
    public static final ErrorCode USER_NAME_ALREADY_TAKEN = ErrorCode.create(4011);

    public static final ErrorCode ROLE_NAME_ALREADY_TAKEN = ErrorCode.create(4021);
    // already taken end


}
