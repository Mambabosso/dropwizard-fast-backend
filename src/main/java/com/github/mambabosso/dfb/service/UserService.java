package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.model.user.User;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.user.UserDAO;
import com.github.mambabosso.dfb.util.Result;
import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

public class UserService extends BaseDAOService<UserDAO> {

    public UserService(@NonNull final UserDAO baseDAO) {
        super(baseDAO);
    }

    public Result<User> create(final String name, final String password) {
        try {
            UserDAO dao = getBaseDAO();
            if (name == null || name.trim().isEmpty() || !ValidatorService.isValidUserName(name)) {
                return Result.failure(Errors.USER_NAME_VALIDATION_FAIL);
            }
            if (password == null || password.trim().isEmpty() || !ValidatorService.isValidUserPassword(password)) {
                return Result.failure(Errors.USER_PASSWORD_VALIDATION_FAIL);
            }
            if (dao.getUserByName(name).isPresent()) {
                return Result.failure(Errors.USER_NAME_ALREADY_TAKEN);
            }
            User user = new User();
            user.setName(name);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(14)));
            if (dao.create(user) != null) {
                return Result.success(user);
            }
            return Result.failure(Errors.UNKNOWN);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
