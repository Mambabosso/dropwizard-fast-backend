package com.github.mambabosso.dfb.model.token;

public enum TokenTypes {

    JWT("jwt"), JWT_ID("jwt_id"), SESSION("session"), SESSION_ID("session_id"), ID("id"), CUSTOM("custom");

    private final String tokenType;

    private TokenTypes(final String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

}
