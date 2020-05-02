package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.model.password.PasswordService;
import com.github.mambabosso.dfb.model.role.RoleService;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.model.user.UserService;
import com.github.mambabosso.dfb.util.Result;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RegisterService {

    private final PasswordService passwordService;
    private final RoleService roleService;
    private final UserService userService;

    public Result<User> register(String name, String plain_password) {
        return Result.failure(null);
    }

}
