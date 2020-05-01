package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.service.BaseDAOService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class UserService extends BaseDAOService<UserDAO> {

    private final Errors errors = Errors.newInstance();

    public UserService(UserDAO baseDAO) {
        super(baseDAO);
    }

    public Result<User> create(Validator<String> name, Validator<Password> password) {
        try {
            UserDAO dao = getBaseDAO();
            if (name == null || !name.isValid() || password == null || !password.isValid()) {
                return Result.failure(errors.USER_VALIDATION_FAILURE);
            }
            String userName = name.get();
            if (dao.getUserByName(userName).isPresent()) {
                return Result.failure(errors.USER_ALREADY_EXISTS);
            }
            User user = new User();
            user.setName(userName);
            user.setPassword(password.get());
            UUID id = dao.insert(user);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(errors.UNKNOWN_USER_FAILURE);
        } catch (PersistenceException ex) {
            return Result.failure(errors.USER_PERSISTENCE_FAILURE.setException(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(errors.USER_VALIDATION_FAILURE.setException(ex));
        } catch (Exception ex) {
            return Result.failure(errors.UNKNOWN_USER_FAILURE.setException(ex));
        }
    }

}
