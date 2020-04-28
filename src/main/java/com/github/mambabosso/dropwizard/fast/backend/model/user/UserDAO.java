package com.github.mambabosso.dropwizard.fast.backend.model.user;

import com.github.mambabosso.dropwizard.fast.backend.dao.BaseDAO;
import org.hibernate.SessionFactory;

public class UserDAO extends BaseDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
