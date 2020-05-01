package com.github.mambabosso.dfb.model.password;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.service.BaseDAOService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class PasswordService extends BaseDAOService<PasswordDAO> {

    private final Errors errors = Errors.newInstance();

    public PasswordService(PasswordDAO baseDAO) {
        super(baseDAO);
    }

    public Result<Password> create(Validator<String> plain_password) {
        try {
            PasswordDAO dao = getBaseDAO();
            if (plain_password == null || !plain_password.isValid()) {
                return Result.failure(errors.PASSWORD_VALIDATION_FAILURE);
            }
            Password password = new Password();
            password.setHash(BCrypt.hashpw(plain_password.get(), BCrypt.gensalt(14)));
            password.setLastAccess(DateTime.now());
            password.setCreatedAt(password.getLastAccess());
            UUID id = dao.insert(password);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(errors.UNKNOWN_PASSWORD_FAILURE);
        } catch (PersistenceException ex) {
            return Result.failure(errors.PASSWORD_PERSISTENCE_FAILURE.setException(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(errors.PASSWORD_VALIDATION_FAILURE.setException(ex));
        } catch (Exception ex) {
            return Result.failure(errors.UNKNOWN_PASSWORD_FAILURE.setException(ex));
        }
    }

}
