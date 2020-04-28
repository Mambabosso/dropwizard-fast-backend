package com.github.mambabosso.dropwizard.fast.backend.model.role;

import com.github.mambabosso.dropwizard.fast.backend.dao.BaseDAO;
import org.hibernate.SessionFactory;

public class RoleDAO extends BaseDAO<Role> {

    public RoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
