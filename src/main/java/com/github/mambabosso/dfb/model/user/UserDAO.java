package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.dao.BaseDAO;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class UserDAO extends BaseDAO<User, UUID> {

    private final QUser user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID create(User user) {
        return (UUID)save(user);
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return Optional.ofNullable(query().select(user).from(user).where(user.name.eq(name)).fetchFirst());
    }

}
