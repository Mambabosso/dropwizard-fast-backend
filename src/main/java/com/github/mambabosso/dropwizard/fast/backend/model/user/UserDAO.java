package com.github.mambabosso.dropwizard.fast.backend.model.user;

import com.github.mambabosso.dropwizard.fast.backend.dao.BaseDAO;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class UserDAO extends BaseDAO<User> {

    private final QUser user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public UUID create(User user) {
        return (UUID)save(user);
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return Optional.ofNullable(query().select(user).from(user).where(user.name.eq(name)).fetchFirst());
    }

}
