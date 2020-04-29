package com.github.mambabosso.dfb.error;

public final class Errors {

    private Errors() {
    }

    public static final ErrorCode UNKNOWN = ErrorCode.create(0);

    // misc
    public static final ErrorCode INVALID_STRING = ErrorCode.create(101);
    public static final ErrorCode INVALID_USER = ErrorCode.create(102);
    public static final ErrorCode INVALID_USER_NAME = ErrorCode.create(103);
    public static final ErrorCode INVALID_USER_PASSWORD = ErrorCode.create(104);
    public static final ErrorCode INVALID_ROLE = ErrorCode.create(105);
    public static final ErrorCode INVALID_ROLE_NAME = ErrorCode.create(106);


    public static final ErrorCode USER_NOT_FOUND = ErrorCode.create(201);
    public static final ErrorCode ROLE_NOT_FOUND = ErrorCode.create(202);


    // register
    public static final ErrorCode USER_NAME_VALIDATION_FAIL = ErrorCode.create(1001);
    public static final ErrorCode USER_PASSWORD_VALIDATION_FAIL = ErrorCode.create(1003);

    public static final ErrorCode USER_NAME_ALREADY_TAKEN = ErrorCode.create(1010);

}
