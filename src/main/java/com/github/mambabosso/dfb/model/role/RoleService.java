package com.github.mambabosso.dfb.model.role;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.service.BaseDAOService;
import com.github.mambabosso.dfb.util.Result;
import com.github.mambabosso.dfb.validator.Validator;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.UUID;

public class RoleService extends BaseDAOService<RoleDAO> {

    private final Errors errors = Errors.newInstance();

    public RoleService(RoleDAO baseDAO) {
        super(baseDAO);
    }

    public Result<Role> create(Validator<String> name) {
        try {
            RoleDAO dao = getBaseDAO();
            if (name == null || !name.isValid()) {
                return Result.failure(errors.ROLE_VALIDATION_FAILURE);
            }
            String roleName = name.get();
            if (dao.getRoleByName(roleName).isPresent()) {
                return Result.failure(errors.ROLE_ALREADY_EXISTS);
            }
            Role role = new Role();
            role.setName(roleName);
            UUID id = dao.insert(role);
            if (id != null) {
                return Result.success(dao.getById(id));
            }
            return Result.failure(errors.UNKNOWN_ROLE_FAILURE);
        } catch (PersistenceException ex) {
            return Result.failure(errors.ROLE_PERSISTENCE_FAILURE.setException(ex));
        } catch (ConstraintViolationException ex) {
            return Result.failure(errors.ROLE_VALIDATION_FAILURE.setException(ex));
        } catch (Exception ex) {
            return Result.failure(errors.UNKNOWN_ROLE_FAILURE.setException(ex));
        }
    }

}
