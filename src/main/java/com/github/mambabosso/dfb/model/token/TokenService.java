package com.github.mambabosso.dfb.model.token;

import com.github.mambabosso.dfb.dao.BaseDAOService;
import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;
import org.joda.time.DateTime;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class TokenService extends BaseDAOService<TokenDAO> {

    public TokenService(TokenDAO baseDAO) {
        super(baseDAO);
    }

    public Result<Token> create(Validator<String> value, Validator<TokenType> type, Validator<DateTime> expiresAt) {
        try {
            if (value == null || !value.isValid() || type == null || !type.isValid() || !expiresAt.isValid()) {
                return Result.failure(Errors.TOKEN_VALIDATION_FAILURE.get());
            }
            TokenDAO dao = getBaseDAO();
            Token token = new Token();
            token.setValue(value.get());
            token.setType(type.get());
            token.setExpiresAt(expiresAt.get());
            token.setLastAccess(DateTime.now());
            token.setCreatedAt(token.getLastAccess());
            UUID id = dao.insert(token);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get());
        } catch (PersistenceException ex) {
            return Result.failure(Errors.TOKEN_PERSISTENCE_FAILURE.get(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(Errors.TOKEN_VALIDATION_FAILURE.get(ex));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

    public Result<Token> get(UUID id) {
        try {
            TokenDAO dao = getBaseDAO();
            Token token = dao.getById(id);
            if (token != null) {
                return Result.success(token);
            }
            return Result.failure(Errors.TOKEN_NOT_FOUND.get());
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

    public Result<Token> getByValue(String value) {
        try {
            TokenDAO dao = getBaseDAO();
            Token token = dao.getByValue(value);
            if (token != null) {
                return Result.success(token);
            }
            return Result.failure(Errors.TOKEN_NOT_FOUND.get());
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

    public Result<Long> update(UUID id, Token token) {
        try {
            TokenDAO dao = getBaseDAO();
            return Result.success(dao.update(id, token));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

    public Result<Long> lockToken(UUID id) {
        return update(id, Token.builder().locked(true).build());
    }

    public Result<Long> delete(UUID id) {
        try {
            TokenDAO dao = getBaseDAO();
            return Result.success(dao.delete(id));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_TOKEN_FAILURE.get(ex));
        }
    }

}
