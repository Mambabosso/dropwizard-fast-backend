package com.github.mambabosso.dfb.model.token;

public enum TokenType {

    JWT("jwt"), JWT_ID("jwt_id"),
    SESSION("session"), SESSION_ID("session_id"),
    REFRESH("refresh"), ACCESS("access"),
    ID("id"),
    CUSTOM("custom");

    private final String tokenType;

    private TokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

}
