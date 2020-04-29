package com.github.mambabosso.dfb.model.user;

import com.github.mambabosso.dfb.dao.BaseDAO;
import lombok.NonNull;
import org.hibernate.SessionFactory;

import java.util.Optional;
import java.util.UUID;

public class UserDAO extends BaseDAO<User, UUID> {

    private final QUser _user = QUser.user;

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UUID insert(@NonNull User user) {
        return (UUID)save(user);
    }

    @Override
    public boolean update(@NonNull UUID id, @NonNull User user) {
        return update(_user).where(_user.id.eq(id)).set(_user, user).execute() > 0;
    }

    @Override
    public boolean delete(@NonNull UUID id) {
        return delete(_user).where(_user.id.eq(id)).execute() > 0;
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return Optional.ofNullable(query().select(_user).from(_user).where(_user.name.eq(name)).fetchFirst());
    }

}
