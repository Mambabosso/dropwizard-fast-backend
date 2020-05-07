package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.password.PasswordService;
import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.model.user.UserService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RegisterService {

    private final UserService userService;
    private final PasswordService passwordService;

    public Result<User> register(@NonNull Validator<String> user_name, @NonNull Validator<String> plain_password) {

        if (!user_name.isValid()) {
            return Result.failure(Errors.USER_VALIDATION_FAILURE.get());
        }

        if (!plain_password.isValid()) {
            return Result.failure(Errors.PASSWORD_VALIDATION_FAILURE.get());
        }

        Result<Password> password = passwordService.create(plain_password);
        if (password.isSuccess()) {

            Result<User> user = userService.create(user_name, password::get);
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
