package com.github.mambabosso.dfb.model.password;

import com.github.mambabosso.dfb.dao.BaseDAOService;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class PasswordService extends BaseDAOService<PasswordDAO> {

    public PasswordService(PasswordDAO baseDAO) {
        super(baseDAO);
    }

    public Result<Password> create(Validator<String> plain_password) {
        try {
            PasswordDAO dao = getBaseDAO();
            if (plain_password == null || !plain_password.isValid()) {
                return Result.failure(Errors.PASSWORD_VALIDATION_FAILURE.get());
            }
            Password password = new Password();
            password.setHash(BCrypt.hashpw(plain_password.get(), BCrypt.gensalt(14)));
            password.setLastAccess(DateTime.now());
            password.setCreatedAt(password.getLastAccess());
            UUID id = dao.insert(password);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(Errors.UNKNOWN_PASSWORD_FAILURE.get());
        } catch (PersistenceException ex) {
            return Result.failure(Errors.PASSWORD_PERSISTENCE_FAILURE.get(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(Errors.PASSWORD_VALIDATION_FAILURE.get(ex));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_PASSWORD_FAILURE.get(ex));
        }
    }

    public Result<Password> get(UUID id) {
        try {
            PasswordDAO dao = getBaseDAO();
            Password password = dao.getById(id);
            if (password != null) {
                return Result.success(password);
            }
            return Result.failure(Errors.PASSWORD_NOT_FOUND.get());
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_PASSWORD_FAILURE.get(ex));
        }
    }

    public Result<Long> update(UUID id, Password password) {
        try {
            PasswordDAO dao = getBaseDAO();
            return Result.success(dao.update(id, password));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_PASSWORD_FAILURE.get(ex));
        }
    }

    public Result<Long> lockPassword(UUID id) {
        return update(id, Password.builder().locked(true).build());
    }

    public Result<Long> delete(UUID id) {
        try {
            PasswordDAO dao = getBaseDAO();
            return Result.success(dao.delete(id));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_PASSWORD_FAILURE.get(ex));
        }
    }

}
