package com.github.mambabosso.dfb.model;

import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.token.Token;
import com.github.mambabosso.dfb.model.user.User;
import lombok.NonNull;

public enum Models {

    UNDEFINED(null), PASSWORD(Password.class), ROLE(Role.class), TOKEN(Token.class), USER(User.class);

    private final Class<?> modelClass;

    private Models(final Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    public final Class<?> getModelClass() {
        return modelClass;
    }

    public static Models of(@NonNull final String value) {
        return Models.valueOf(value.toUpperCase());
    }

}
