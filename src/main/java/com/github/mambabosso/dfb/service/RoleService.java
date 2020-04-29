package com.github.mambabosso.dfb.service;

import com.github.mambabosso.dfb.error.Errors;
import com.github.mambabosso.dfb.model.role.Role;
import com.github.mambabosso.dfb.model.role.RoleDAO;
import com.github.mambabosso.dfb.util.Result;

public class RoleService extends BaseDAOService<RoleDAO> {

    public RoleService(final RoleDAO baseDAO) {
        super(baseDAO);
    }

    public Result<Role> create(final String name) {
        try {
            RoleDAO dao = getBaseDAO();
            if (name == null || name.trim().isEmpty() || !ValidatorService.isValidRoleName(name)) {
                return Result.failure(Errors.ROLE_NAME_VALIDATION_FAIL);
            }
            if (dao.getRoleByName(name).isPresent()) {
                return Result.failure(Errors.ROLE_NAME_ALREADY_TAKEN);
            }
            Role role = new Role();
            role.setName(name);
            if (dao.insert(role) != null) {
                return Result.success(role);
            }
            return Result.failure(Errors.UNKNOWN);
        } catch (Exception ex) {
            return Result.failure(Errors.UNKNOWN);
        }
    }

}
