package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.user.UserDAO;
import com.github.mambabosso.dfb.util.Result;
import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterService extends BaseService<RegisterService> {

    private final UserDAO userDAO;

    public RegisterService(@NonNull final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Result<User> register(@NonNull final String name, @NonNull final String password) {
        try {
            if (name.trim().isEmpty() || !ValidatorService.isValidName(name)) {
                return Result.failure(Errors.USER_NAME_VALIDATION_FAIL);
            }
            if (password.trim().isEmpty() || !ValidatorService.isValidPassword(password)) {
                return Result.failure(Errors.USER_PASSWORD_VALIDATION_FAIL);
            }
            if (userDAO.getUserByName(name).isPresent()) {
                return Result.failure(Errors.USER_NAME_ALREADY_TAKEN);
            }
            User user = new User();
            user.setName(name);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(14)));
            if (userDAO.create(user) != null) {
                return Result.success(user);
            }
            return Result.failure(Errors.UNKNOWN);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
