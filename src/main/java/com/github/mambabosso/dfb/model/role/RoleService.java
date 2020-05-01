package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.service.BaseDAOService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;
import org.joda.time.DateTime;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class RoleService extends BaseDAOService<RoleDAO> {

    public RoleService(RoleDAO baseDAO) {
        super(baseDAO);
    }

    public Result<Role> create(Validator<String> name) {
        try {
            RoleDAO dao = getBaseDAO();
            if (name == null || !name.isValid()) {
                return Result.failure(Errors.ROLE_VALIDATION_FAILURE.get());
            }
            String roleName = name.get();
            if (dao.getRoleByName(roleName).isPresent()) {
                return Result.failure(Errors.ROLE_ALREADY_EXISTS.get());
            }
            Role role = new Role();
            role.setName(roleName);
            role.setCreatedAt(DateTime.now());
            UUID id = dao.insert(role);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(Errors.UNKNOWN_ROLE_FAILURE.get());
        } catch (PersistenceException ex) {
            return Result.failure(Errors.ROLE_PERSISTENCE_FAILURE.get(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(Errors.ROLE_VALIDATION_FAILURE.get(ex));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_ROLE_FAILURE.get(ex));
        }
    }

    public Result<Long> update(UUID id, Role role) {
        try {
            RoleDAO dao = getBaseDAO();
            return Result.success(dao.update(id, role));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_ROLE_FAILURE.get(ex));
        }
    }

    public Result<Long> delete(UUID id) {
        try {
            RoleDAO dao = getBaseDAO();
            return Result.success(dao.delete(id));
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN_ROLE_FAILURE.get(ex));
        }
    }

}
