package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.dao.BaseDAOService;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.password.Password;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;
import org.joda.time.DateTime;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.UUID;

public class UserService extends BaseDAOService<UserDAO> {

    public UserService(UserDAO baseDAO) {
        super(baseDAO);
    }

    public Result<User> create(Validator<String> name, Validator<Password> password, Validator<Set<Role>> roles) {
        try {
            UserDAO dao = getBaseDAO();
            if (name == null || !name.isValid() || password == null || !password.isValid() || (roles != null && !roles.isValid())) {
                return Result.failure(Errors.USER_VALIDATION_FAILURE.get());
            }
            String userName = name.get();
            if (dao.getUserByName(userName).isPresent()) {
                return Result.failure(Errors.USER_ALREADY_EXISTS.get());
            }
            User user = new User();
            user.setName(userName);
            user.setPassword(password.get());
            if (roles != null) {
                user.setRoles(roles.get());
            }
            user.setCreatedAt(DateTime.now());
            UUID id = dao.insert(user);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(Errors.UNKNOWN_USER_FAILURE.get());
        } catch (PersistenceException ex) {
            return Result.failure(Errors.USER_PERSISTENCE_FAILURE.get(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(Errors.USER_VALIDATION_FAILURE.get(ex));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_USER_FAILURE.get(ex));
        }
    }

    public Result<User> create(Validator<String> name, Validator<Password> password) {
        return create(name, password, null);
    }

    public Result<User> get(UUID id) {
        try {
            UserDAO dao = getBaseDAO();
            return Result.success(dao.getById(id));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_USER_FAILURE.get(ex));
        }
    }

    public Result<Long> update(UUID id, User user) {
        try {
            UserDAO dao = getBaseDAO();
            return Result.success(dao.update(id, user));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_USER_FAILURE.get(ex));
        }
    }

    public Result<Long> delete(UUID id) {
        try {
            UserDAO dao = getBaseDAO();
            return Result.success(dao.delete(id));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_USER_FAILURE.get(ex));
        }
    }

}
