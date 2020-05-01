package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.model.password.PasswordService;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.model.user.UserService;
import com.github.mambabosso.dfb.util.Result;
import lombok.NonNull;

public class RegisterService {

    private final UserService userService;
    private final PasswordService passwordService;

    public RegisterService(@NonNull UserService userService, @NonNull PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    public Result<User> register(String name, String plain_password) {
        return Result.failure(null);
    }

}
