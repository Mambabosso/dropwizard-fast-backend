package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.password.PasswordService;
import com.github.mambabosso.dfb.model.role.RoleService;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.model.user.UserService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Expressions;
import com.github.mambabosso.dfb.validator.RegexValidator;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public class RegisterService {

    private final PasswordService passwordService;
    private final RoleService roleService;
    private final UserService userService;

    public Result<User> register(String name, String plain_password) {

        RegexValidator nameValidator = Expressions.USER_NAME.validator(name);
        RegexValidator passwordValidator = Expressions.PLAIN_PASSWORD.validator(plain_password);

        if (!nameValidator.isValid()) {
            return Result.failure(Errors.USER_VALIDATION_FAILURE.get());
        }
        if (!passwordValidator.isValid()) {
            return Result.failure(Errors.PASSWORD_VALIDATION_FAILURE.get());
        }

        val password = passwordService.create(passwordValidator);
        if (password.isSuccess()) {
            val user = userService.create(nameValidator, password::get);
            if (user.isSuccess()) {
                return Result.success(user.get());
            } else {
                passwordService.delete(password.get().getId());
            }
            return user.byError();
        }
        return password.byError();

    }

}
